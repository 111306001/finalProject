
public class Web {
	public WebPage rootPage;
	public WebTree tree;
	
	public Web(String url) {
		// TODO Auto-generated method stub
		rootPage = new WebPage(url);		
		tree = new WebTree(rootPage);
	}
	public void treeRootAddChild(String query, String word) {
		tree.root.addChild(new WebNode(new WebPage(query)));
	}

}
