class AVLTree {
	AVLTreeNode root;//links the top of the tree

	public AVLTree() {
	}

	//Creates a test tree
	public void createTestTree() {
		AVLTreeNode one = new AVLTreeNode("1");
		AVLTreeNode two = new AVLTreeNode("2");
		AVLTreeNode three = new AVLTreeNode("3");
		AVLTreeNode four = new AVLTreeNode("4");
		AVLTreeNode five = new AVLTreeNode("5");
		AVLTreeNode six = new AVLTreeNode("6");
		AVLTreeNode seven = new AVLTreeNode("7");
		
		root = four;
		four.left = two;
		four.right = six;
		
		two.left = one;
		two.right = three;
		
		six.left = five;
		six.right = seven;
	}

	//Prints using preOrder
	public void print() {
		preOrderPrint(root, 0);
	}
	
	//prints using preOrder
	public void preOrderPrint(AVLTreeNode cur, int row){
		if (cur == null){
			return;
		}
		
		int tempRow = row;
		
		for(int i = 0; i < tempRow; i++){
			System.out.print("  ");
		}
		
		System.out.println(cur.value);

		tempRow++;
		
		preOrderPrint(cur.left, tempRow);
		preOrderPrint(cur.right, tempRow);
	}

	//check the tree if the string e exists in the tree
	public boolean inTree(String e) {
		String theValue = e;
		
		AVLTreeNode node = root;
		//Goes through the tree to find e
		while (node != null){
			if (theValue.compareTo(node.value) == 0){
				return true;
			}
			if (theValue.compareTo(node.value) < 0){
				node = node.left;
			} else if (theValue.compareTo(node.value) > 0){
				node = node.right;
			}
		}
		return false;
	}

	//Insert unbalanced
	public void insert(String e) {
		String theValue = e;
		AVLTreeNode node = root;
		AVLTreeNode prevNode;
		
		//tree is empty
		if (root == null){
			root = new AVLTreeNode(theValue);
		}
		//find place to insert
		while (node != null){
			if (theValue.compareTo(node.value) == 0){
				return;
			}
			if (theValue.compareTo(node.value) < 0){
				prevNode = node;
				node = node.left;
				if (node == null){
					prevNode.left = new AVLTreeNode(theValue);
				}
				
			} else if (theValue.compareTo(node.value) > 0) {
				prevNode = node;
				node = node.right;
				if (node == null){
					prevNode.right = new AVLTreeNode(theValue);
				}
			}
		}
	}
	
	//Insert balanced
	public void insertBalanced(String e) {
		//Insert the value
		insert(e);
		
		boolean again = true;
		//checks if it needs to balance the tree again
		while (again){
			again = false;
			//Calculates the balance of all the nodes
			calcBalance(root, 0);
			//Balances the tree using rotations
			again = BalanceTree();
		}
	}
	
	//Does the rotations
	public boolean BalanceTree(){
		boolean again = false;
		//used to loop through the tree
		AVLTreeNode tempa = root;
		AVLTreeNode tempb = root;
		AVLTreeNode tempc = root;
		
		//values to use when doing the rotations
		AVLTreeNode temp = root;
		AVLTreeNode prev = root;
		AVLTreeNode prever = root;
		
		if (tempa.balance > 1){
			prever = root;
			temp = tempa.right;
		} else if (tempa.balance < 1){
			prever = root;
			temp = tempa.left;
		}
		
		//if there is nothing in the tree
		if (temp != null){
			if (temp.balance >= 1){
				prev = temp;
				temp = temp.right;
			} else if (temp.balance <= 1){
				prev = temp;
				temp = temp.left;
			}
		}

		//Find out where to do the rotations
		//find the last 2 and the one before it and after it
		while (tempa != null){
			if (tempa.balance >= 1){
				tempc = tempb;
				tempb = tempa;
				if (tempa.right == null){
					break;
				}
				tempa = tempa.right;
				if (tempa.balance == 2){
					prever = tempb;
					prev = tempa;
					temp = tempa.right;
				}
			} else if (tempa.balance <= -1){
				tempc = tempb;
				tempb = tempa;
				if (tempa.left == null){
					break;
				}
				tempa = tempa.left;
				if (tempa.balance == -2){
					prever = tempb;
					prev = tempa;
					temp = tempa.left;
				}
			} else {
				break;
			}
		}
		
		//Decides which rotation to do
		if (prev.balance == 2){
			if (temp.balance == 1 || temp.balance == 0){
				leftRotTwo(temp, prev, prever);
				again = true;
			} else if (temp.balance == -1){
				rightRotMinOne(temp, prev, prever);
				again = true;
			}
		} else if (prev.balance == -2){
			if (temp.balance == 1){
				leftRotOne(temp, prev, prever);
				again = true;
			} else if (temp.balance == -1){
				rightRotMinTwo(temp, prev, prever);
				again = true;
			}
		} else if (prever.balance == 2){
			root = prev;
			prever.right = prev.left;
			prev.left = prever;
			prev.right = temp;
			again = true;
		} else if (prever.balance == -2){
			root = prev;
			prever.left = prev.right;
			prev.right = prever;
			prev.left = temp;
			again = true;
		}
		return again;
	}
	
	//2 1
	public void leftRotTwo(AVLTreeNode temp, AVLTreeNode prev, AVLTreeNode prever){
		prever.right = temp;
		prev.right = temp.left;
		temp.left = prev;
	}
	
	//2 -1
	public void rightRotMinOne(AVLTreeNode temp, AVLTreeNode prev, AVLTreeNode prever){
		AVLTreeNode tempNext;
		tempNext = temp.left;
		
		prev.right = temp.left;
		tempNext.right = temp;
		temp.left = null;
		calcBalance(root, 0);
		BalanceTree();
	}
	
	//-2 1
	public void leftRotOne(AVLTreeNode temp, AVLTreeNode prev, AVLTreeNode prever){
		AVLTreeNode tempNext;
		tempNext = temp.right;
		
		prev.left = temp.right;
		tempNext.left = temp;
		temp.right = null;
		calcBalance(root, 0);
		BalanceTree();
	}
	
	//-2 -1
	public void rightRotMinTwo(AVLTreeNode temp, AVLTreeNode prev, AVLTreeNode prever){
		prever.left = temp;
		prev.left = temp.right;
		temp.right = prev;
	}
	
	//calculates the balances of each node
	//performs recursion to calculate the balances of each node
	public int calcBalance(AVLTreeNode cur, int height){
		int heightLeft;
		int heightRight;
		
		if (cur == null){
			return height;
		}
		
		height++;
		
		heightLeft = calcBalance(cur.left, height);
		heightRight = calcBalance(cur.right, height);
		
		cur.balance = heightRight - heightLeft;
		
		if (heightLeft > heightRight){
			height = heightLeft;
		} else if (heightLeft < heightRight){
			height = heightRight;
		} else if (heightLeft == heightRight){
			height = heightRight;
		}
		
		return height;
	}
}