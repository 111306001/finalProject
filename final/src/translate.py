from googletrans import Translator
import json
import re
translator = Translator(service_urls=['translate.googleapis.com'])


import pandas as pd
df=pd.read_excel("worldcities.xlsx")

names = set()
for idx, name in df['admin_name'].items():
    names.add(name)
for idx, name in df['country'].items():
    names.add(name)
names = list(names)
JUMP = 1

data = json.load(open('output.json', mode='r', encoding='utf-8'))
for name in names:
    if pd.isna(name):
        continue
    if name in data:
        continue
    payload = name
    out = translator.translate(payload, dest='zh-tw').text.strip()
    print(name, out)
    data[name]=out
    json.dump(data, open('output.json', mode='w', encoding='utf-8'), ensure_ascii=False)