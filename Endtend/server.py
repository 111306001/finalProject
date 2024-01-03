from flask import Flask, request
import gensim
from gensim.models.word2vec import Word2Vec
import json
app = Flask(__name__)

@app.route("/", methods=['POST'])
def hello():
    keyword = json.loads(request.values['data'])
    keyweight = {}
    for key in keyword:
        result = final_recommended(key)
        keyweight[key] = 1
        if result is None:
            continue
        for recom, weight in result:
            keyweight[recom] = weight
        
    output = []
    for key, value in keyweight.items():
        output.append(f"{key},{value}")
    output =':'.join(output)
    return output


if __name__ == '__main__':
    print("Preprocess Start!")

    # Load word vector model
    model = gensim.models.KeyedVectors.load_word2vec_format('y_360W_cbow_2D_300dim_2020v1.bin', 
                                                        unicode_errors='ignore', 
                                                        binary=True)

    # Preprocess cities and properties

    import pandas as pd
    from collections import defaultdict
    import numpy as np
    from unidecode import unidecode
    import json

    df=pd.read_excel("worldcities.xlsx")
    data = json.load(open('output.json', mode='r', encoding='utf-8'))

    '''
    city_set['France']['Bretagne'](法國, 布列塔尼)

    Request:
    1. 布列塔尼 -> France
    2. France -> 其他 city
    '''

    en2country = {}
    ch2country = {}

    county_population = defaultdict(int)
    country2city = defaultdict(set)

    city_pos = defaultdict(list)
    county_pos = defaultdict(list)

    for idx, row in list(df.iterrows())[::-1]:
        city_name = row['city_ascii']
        county_name = row['admin_name']
        country_name = unidecode(row['country'])
        population = row['population']
        pos = np.array([float(row['lat']), float(row['lng'])])

        if pd.isna(city_name) or pd.isna(country_name) or pd.isna(county_name):
            continue
        
        ch2country[unidecode(data[city_name]).lower()] = country_name.lower()
        en2country[unidecode(city_name).lower()] = country_name.lower()
        ch2country[unidecode(data[county_name]).lower()] = country_name.lower()
        en2country[unidecode(county_name).lower()] = country_name.lower()

        city_pos[unidecode(city_name).lower()].append(pos)
        city_pos[unidecode(county_name).lower()].append(pos)
        city_pos[unidecode(country_name).lower()].append(pos)
        
        county_pos[unidecode(county_name).lower()].append(pos)
        if not pd.isna(population):
            county_population[(country_name.lower(), unidecode(county_name).lower())] += population
        country2city[country_name.lower()].add(unidecode(county_name).lower())

    # Calculate latitude and longitude of each county
    for city, positions in city_pos.items():
        city_pos[city] =  np.sum(positions, 0) / len(positions)

    for county, positions in county_pos.items():
        county_pos[county] = np.sum(positions, 0) / len(positions)

    # Get the most cloest city
        
    from googletrans import Translator
    translator = Translator(service_urls=['translate.googleapis.com'])

    def recommended_city(keyword, topn=3):
        # Is keyword in en2country
        inp = None
        keyword = keyword.lower()
        if keyword in en2country:
            inp = en2country[keyword]
        elif keyword in ch2country:
            inp = ch2country[keyword]
        else:
            trans_en = translator.translate(keyword, src='zh-tw', dest='en').text.strip().lower()
            if trans_en in en2country:
                inp = en2country[trans_en]
                keyword = trans_en
            print(inp)
        if inp is None:
            return None
        else:
            inp = inp.lower()
            all_county = country2city[inp]
            popcount = []
            for county in all_county:
                popcount.append((county_population[(inp, county)], county))
            output = sorted(popcount,key= lambda x:x[0])[::-1]

            origin_pos = city_pos[keyword.lower()]

            candidates = []
            for city, pos in county_pos.items():
                if city == inp:
                    continue
                elif city not in country2city[inp]:
                    continue
                else:
                    candidates.append((np.linalg.norm(origin_pos - pos, ord=2), city))
            candidates = sorted(candidates)
            return [ name for distance, name in candidates[:topn] ]
        
    # merge wv and geometry
    def final_recommended(keyword):
        candidates = recommended_city(keyword, 10)
        if candidates is None:
            return None
        final_candidates = []
        for candidate in candidates:
            trans_ch = translator.translate(candidate, src='en', dest='zh-tw').text.strip()

            if trans_ch in model.key_to_index:
                print(candidate, trans_ch, model.similarity(keyword, trans_ch))
                final_candidates.append((model.similarity(keyword, trans_ch), trans_ch))
            elif candidate in model.key_to_index:
                print(candidate, model.similarity(keyword, candidate))
                final_candidates.append((model.similarity(keyword, candidate), candidate))
            else:
                print(candidate, "Failed")
        
        return [(name, score*0.5) for score, name in sorted(final_candidates)[::-1][:5]]


    print("Preprocess Finish!")
    app.run(debug=True)