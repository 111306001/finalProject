# SimSearch



## Run Python Server

* Python version: 3.9
* Install package: `jieba, flask, flask-cors, requests, gensim, googletrans==3.1.0a0, pandas, Unidecode, openpyxl`

```
python server.py
```

### Java Function

* If you have any issue, compile with `javac -encoding utf-8 Client.java`

* `getKeywordWeight(ArrayList<String>)`
  * Input: 把關鍵字 ( `String` 格式 ) 放進 ArrayList 後呼叫這個函式（這個函式可以複製過去或者直接把 `Client.java` 放在 java src 的資料夾裡就可以了）
  * Output: `ArrayList<Keyword>`
    * 這裡的 `Keyword` class 跟作業的 `Keyword.java` 是一樣的。
    * 回傳會給建議的關鍵字以及權重，但 count 跟這邊沒關係所以預設都是 0。
  * 目前只是先給什麼關鍵字就回傳一樣的關鍵字但權重都給 `1`，具體推薦的關鍵字還在調。

## Technical Notes

我們想要在給定一個關鍵字的時候，去搜尋意義相近的關鍵字。於是我們使用了一些機器學習的技巧，其中之一就是 Word Vector。

### Word Vector (詞向量)

什麼是 Word Vector 呢？就是在做任何文字相關的NLP (自然語言處理) 任務時，我們會將離散的文字通過某種方式對應到一個連續的向量空間，以便模型可以使用 Gradient Descent (梯度下降法) 學習。我們會期望 Word Vector在經過大量文本學習後，可以學習到用向量來表達字與字的關係。舉例來說：
![](https://miro.medium.com/v2/resize:fit:2000/format:webp/1*SYiW1MUZul1NvL1kc1RxwQ.png)

我們會發現如果 Word Vector是已經經過模型充分學習後的結果，那麼 (woman - man) 與 (queen - king) 的向量應該是接近的。而兩個意思相近的 word 用 word vector 來表達的時候也會很接近。

### Word Segmentation (中文斷詞)

此外，我們在做英文的NLP任務時，習慣將一個 word 當作最低單位來處理，但中文卻不一樣。舉例來說「今天天氣真好」的最小單位應該是「『今天』『天氣』『真』『好』」，因此我們需要先將中文的句子拆開來才可以做處理。

#### Implementation

* Word vector: 我們使用 `gensim` 套件來處理，並且使用網路上已經預先訓練過的模型來處理。
* 中文斷詞系統: 我們使用 `jieba` 這個套件。

### 城市相對位置

我們的目標是找到旅遊相關的關鍵字，這其實非常的有挑戰性。舉例來說，我們想要查詢「巴西」關鍵字，直接通過 google 搜尋引擎的「相關搜尋」，他只會給我們**巴西时间**，**巴西英文**，這種根旅遊毫無相關的關鍵字。因此我們想要改變這一現狀：我們會將查詢的城市以及其相鄰的城市納入考量，推薦其附近的城市。

但是要解析城市的名稱也不是個簡單的事情，因為可能會多個譯名的城市。舉例來說：「加州」和「加洲」對一般人來說並不是差別很大，但這兩個字在程式裡就是完全不同的字，就像「加州」跟「台灣」一樣。所以我們會統一將程式名稱轉換成英文，在進行判斷。

#### Implementation

* 搜尋所有國家城市和經緯度: 使用網路上的資料統計。(資料來源：https://simplemaps.com/data/world-cities)
  * 我們會考量一個城市的經緯度以及人口數量來做推薦。

* 中文或其他語言轉換成英文: 使用 `googletrans` 套件，他可以串接 google translate api 來進行翻譯。

#### Python 與 Java 溝通

我們採取的方法就像是前端的html和後端的java一樣，我們通過網路的方式供 java 傳遞資料給 python，具體使用的套件為 `flask`。

### 權重設計

假設關鍵字為一個地區，都市，或者城鎮，我們會找出與之最近的 $10$ 個縣，並且為同個國家。接著，我們會將這個縣市與原本的關鍵字透過 `gensim` 衡量 word vector 的距離，給出權重。考量 `gensim` 衡量距離的方法為 `cosine similarity`，其值域為 $[-1, 1]$，如果權重給得過高可能會影響原本的關鍵字計算，所以我們會將期權重乘以 $0.5$ 來當作新的關鍵字的權重。 

