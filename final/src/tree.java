

public class tree {
	private TreeNode root;
	public tree() {
		this.root =null;
	}
	 private TreeNode insertRec(TreeNode root, keyword keyword) {
	        if (root == null) {
	            root = new TreeNode(keyword);
	            return root;
	        }

	        if (keyword.weight < root.data.weight) {
	            root.left = insertRec(root.left, keyword);
	        } else if (keyword.weight > root.data.weight) {
	            root.right = insertRec(root.right, keyword);
	        }

	        return root;
	    }
	 public void  insert(keyword keyword) {
		 root =insertRec(root,keyword) ;
	 }
	  public void reverseInOrder(TreeNode node) {
	        if (node == null) {
	            return;
	        }

	        // 先遍歷右子樹
	        reverseInOrder(node.right);
	        // 遍歷左子樹
	        reverseInOrder(node.left);
	        // 輸出節點的字符串
	        System.out.println(node.data.name );

	       
	    }

	    // 在 BinaryTree 中添加方法，以反向中序遍歷方式輸出節點的字符串
	    public void printNodesInDescendingOrder() {
	        reverseInOrder(root);
	    }
	 
	
}
