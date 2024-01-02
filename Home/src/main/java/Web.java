import java.io.IOException;

public class Web {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		WebPage rootPage = new WebPage("http://soslab.nccu.edu.tw/Welcome.html", "Soslab");		
		WebTree tree = new WebTree(rootPage);
		
		//build childnode
		tree.root.addChild(new WebNode(new WebPage("http://soslab.nccu.edu.tw/Publications.html","Publication")));
		tree.root.addChild(new WebNode(new WebPage("http://soslab.nccu.edu.tw/Projects.html","Projects")));
		tree.root.children.get(1).addChild(new WebNode(new WebPage("https://vlab.cs.ucsb.edu/stranger/", "Stranger")));
//		tree.root.children.get(1).addChild(new WebNode(new WebPage("http://soslab.xyz:7777", "AppBeach")));
		tree.root.addChild(new WebNode(new WebPage("http://soslab.nccu.edu.tw/Members.html", "Member")));
		tree.root.addChild(new WebNode(new WebPage("http://soslab.nccu.edu.tw/Courses.html","Course")));
		
	}

}
