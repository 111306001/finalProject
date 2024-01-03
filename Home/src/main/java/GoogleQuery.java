import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GoogleQuery {

	public String searchKeyword;
	public String url;
	public String content;
	public Web webs;
	public WordCounter wordCounter;
	public WebNode node;
	public ArrayList<String> urlList;
	public ArrayList<Keyword> keywordList;
	
	public PriorityQueue<WebNode> heap;

	public GoogleQuery(String searchKeyword){
		this.searchKeyword = searchKeyword;
		
		keywordList = new ArrayList<Keyword>();
		this.url = "http://www.google.com/search?q="+searchKeyword+"&oe=utf8&num=10";
		keywordList.add(new Keyword(searchKeyword, 0, 1));
		node = new WebNode(new WebPage(url));
		urlList = new ArrayList<String>();
	}

	private String fetchContent() throws IOException{
		
		String retVal = "";
		URL u = new URL(url);
		URLConnection conn = u.openConnection();
		conn.setRequestProperty("User-agent", "Chrome/7.0.517.44");
		InputStream in = conn.getInputStream();
		InputStreamReader inReader = new InputStreamReader(in,"utf-8");
		BufferedReader bufReader = new BufferedReader(inReader);
		
		String line = null;
		while((line = bufReader.readLine())!= null)
		{	
			webs = new Web(line);
			webs.treeRootAddChild(line, searchKeyword);
			urlList.add(line);
			retVal += line;
		}

		return retVal;
	}
	public void Rank() throws IOException {
		//rank網頁按照分數
		//按照大小存到heap 再取代原本的
		if(content==null){
			content= fetchContent();
		}
		heap = new PriorityQueue<>((a, b) -> {
			try {
				return Double.compare(b.getNodeScore(keywordList), a.getNodeScore(keywordList));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return 0;
		});
		for(WebNode child : node.children) {
	        heap.add(child);
	    }
	}
	public HashMap<String, String> query() throws IOException{
		
		Rank();
		HashMap<String, String> retVal = new HashMap<String, String>();
		
		Document doc = Jsoup.parse(content);
		System.out.println(doc.text());
		Elements lis = doc.select("div");
		System.out.println(lis);
		lis = lis.select(".kCrYT");
		System.out.println(lis.size());
		
		for(Element li : lis){
			try {
				String citeUrl = li.select("a").get(0).attr("href");
				String title = li.select("a").get(0).select(".vvjwJb").text();
				if(title.equals("")) {
					continue;
				}
				
				System.out.println(title + ","+citeUrl);
				retVal.put(title, citeUrl);

			} catch (IndexOutOfBoundsException e) {

				e.printStackTrace();

			}
	}
		return retVal;

	}

}