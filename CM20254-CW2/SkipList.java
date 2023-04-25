class SkipList {
    private SkipListNode[] head;
    private int n = 0; // list size
	
    public SkipList() {
		head = new SkipListNode[5];
    }
	
	//creates 5 strings in list
    public void createTestList() {
		SkipListNode Anne = new SkipListNode("Anne");
		Anne.next = new SkipListNode[3];
		
		SkipListNode Ben = new SkipListNode("Ben");
		Ben.next = new SkipListNode[3];
		
		SkipListNode Charlie = new SkipListNode("Charlie");
		Charlie.next = new SkipListNode[3];
		
		SkipListNode Don = new SkipListNode("Don");
		Don.next = new SkipListNode[3];
		
		SkipListNode Ernie = new SkipListNode("Ernie");
		Ernie.next = new SkipListNode[3];
		
		Anne.next[0] = Ben;
		Anne.next[1] = Charlie;
		Anne.next[2] = Ernie;
		
		head[0] = Anne;
		head[1] = Anne;
		head[2] = Anne;
		
		Ben.next[0] = Charlie;
		Ben.next[1] = null;
		Ben.next[2] = null;
		
		Charlie.next[0] = Don;
		Charlie.next[1] = Ernie;
		Charlie.next[2] = null;
		
		Don.next[0] = Ernie;
		Don.next[1] = null;
		Don.next[2] = null;
		
		Ernie.next[0] = null;
		Ernie.next[1] = null;
		Ernie.next[2] = null;
		
		n = 5;
 		
		// “Anne” and “Ernie”
		//“Anne”, “Charlie”, and “Ernie”
		//“Anne”, “Ben”, “Charlie”, “Don” and “Ernie”
    }
	
	//prints each lane in list
    public void print() {		
		SkipListNode temp = new SkipListNode(null);
		SkipListNode temp2 = new SkipListNode(null);
		
		int level = 4;
		
		//no elements
		if (n == 0){
			return;
		}
		
		while (level >= 0){
			while (head[level] == null){
				//System.out.println(level + ": ");
				level--;
			}
			temp = head[level];
			System.out.print(temp.element);
			temp2 = temp;
			while (temp2.next[level] != null){
				temp2 = temp2.next[level];
				System.out.print("," + temp2.element);
			}
			System.out.println();
			level--;
		}
    }
	
	//checks if string is in list
    public boolean inList(String o) {		
		SkipListNode temp = new SkipListNode(null);
		int level = 4;
		
		//empty
		if (n == 0){
			return false;
		}
		
		while (head[level] == null && level >= 0){
			level--;
		}
		
		temp = head[level];

		//searches for string
		while (level >= 0){
			while(temp.next[level] == null){
				level--;
				if (level < 0){
					return false;
				}
			}
			if (o.compareTo(temp.next[level].element) == 0){
				return true;
			} else if (o.compareTo(temp.next[level].element) < 0){
				level--;
			} else if (o.compareTo(temp.next[level].element) > 0){
				temp = temp.next[level];
			}
		}
		return false;
    }

	//inserts element into list
    public void insert(String o) {
		n++;
		SkipListNode temp = new SkipListNode(null);
		SkipListNode prevTemp = new SkipListNode(null);
		int level = 4;
		
		int numNodes = 0;
		SkipListNode newNode = new SkipListNode(o);
		numNodes = newNode.l;
		
		//deals with the heads
		while (level >= 0){
			if(head[level] == null){
				if (level+1 <= numNodes){
					head[level] = newNode;
				}
				level--;
			} else if (o.compareTo(head[level].element) > 0){
				prevTemp = head[level];
				temp = head[level];
				temp = temp.next[level];
				break;
			} else if (o.compareTo(head[level].element) < 0){
				if (level+1 <= numNodes){
					newNode.next[level] = head[level];
					head[level] = newNode;
				}
				level--;
			} else if (o.compareTo(head[level].element) == 0){
				if (level+1 <= numNodes){
					prevTemp = head[level];
					temp = head[level];
					temp = temp.next[level];
					break;
				}
				level--;
			}
		}
		
		//inserts the node
		while (level >= 0){
			if (temp == null){
				if (o.compareTo(prevTemp.element) > 0){
					if (level+1 <= numNodes){
						prevTemp.next[level] = newNode;
					}
					if (level == 0){
						return;
					}
					level--;
					temp = prevTemp.next[level];
				} else if (o.compareTo(prevTemp.element) == 0){
					if (level+1 <= numNodes){
						prevTemp.next[level] = newNode;
					}
					if (level == 0){
						return;
					}
					level--;
					temp = prevTemp.next[level];
				}
			} else if (o.compareTo(temp.element) > 0){
				prevTemp = temp;
				temp = temp.next[level];
			} else if (o.compareTo(temp.element) < 0){
				if (level+1 <= numNodes){
					prevTemp.next[level] = newNode;
					newNode.next[level] = temp;
					temp = prevTemp;
					level--;
				} else {
					temp = prevTemp;
					level--;
				}
			} else if (o.compareTo(temp.element) == 0){
				prevTemp = temp;
				temp = temp.next[level];
			}
		}
	}
}