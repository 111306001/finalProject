import java.io.IOException;

public class Web {
	public WebPage rootPage;
	public WebTree tree;
	public void Web(String query, String word) {
		// TODO Auto-generated method stub
		rootPage = new WebPage(query, word);		
		tree = new WebTree(rootPage);
	}
	public void treeRootAddChild(String query, String word) {
		tree.root.addChild(new WebNode(new WebPage(query, word)));
	}

}
