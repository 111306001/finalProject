import requests
import re
import jieba
from html import unescape

pattern = re.compile(r'>.*?<')
data = requests.get("https://www.travel4u.com.tw/group/category/7/europe/").text
data = unescape(data)

for text in pattern.findall(data):
    text = text[1:-1].strip()
    if not text or len(text) < 10:
        continue
    print(list(jieba.cut(text, cut_all=False)))
# print(data)