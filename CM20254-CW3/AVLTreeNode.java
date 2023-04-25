class AVLTreeNode {
	String value;
	int balance;
	AVLTreeNode right;
	AVLTreeNode left;
	
	public AVLTreeNode(String v) {
		value = v; 
	}

	public AVLTreeNode(String v, AVLTreeNode l, AVLTreeNode r) {
		value = v; 
		left = l;
		right = r;
	}
}
