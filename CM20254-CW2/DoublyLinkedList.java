class DoublyLinkedList {
    private ListNode2 head = null;
    private int n = 0; // list size

	//Adding an element at the beginning of the list
	public void addFirst(Object o) {
		if (n == 0){
			head = new ListNode2(o, null, head);
		} else {
			ListNode2 node = head;
			head = new ListNode2(o, null, head);
			node.prev = head;
		}
		n++;
    }
	
	//Getting the ith element of the list
	public Object get(int i) {
		//error
		if (i < 0 || i >= n){
			return "i is out of range";
		}
		
		ListNode2 node = head;
		
		//Find node
		for (int j = 0; j < i; j++){
			node = node.next;
		}
	
		return String.valueOf(node.element);
    }
	
	//inserts a given element at a given index position
	public void insert(Object o, int i) {
		//error
		if (i > n || i < 0){
			System.out.println("i out of range?!");
			return;
		}
		
		//At head
		if (i == 0){
			addFirst(o);
			return;
		}
		
		ListNode2 node = head;
		
		//finds the prev node before the insert
		for (int j = 0; j < i-1; j++){
			node = node.next;
		}
		
		ListNode2 prev = node;
		ListNode2 next = node.next;
		
		ListNode2 newNode = new ListNode2(o, prev, next);
		prev.next = newNode;
		
		//assign the prev of the element after the inserted element
		if (i < n){
			next.prev = newNode;
		}
		
		n++;
    }

	//Removes the element at a given index position
    public void remove(int i) {
		//error
		if (i < 0 || i >= n) {
			System.out.println("i is out of range");
			return;
		}
		
		//Removes first element
		if (i == 0) {
			//one element in list
			if (n == 1){
				head = null;
				n--;
				return;
			}
			head = head.next;
			head.prev = null;
			n--; 
			return; 
		}

		ListNode2 node = head;
		
		//reaches one before removal one
		for (int j = 0; j < i-1; j++){
			node = node.next;
		}
		
		ListNode2 prev = node;
		ListNode2 next = node.next.next;
		
		if (i >= n-1){
			prev.next = null;
		} else{
			prev.next = next;
			next.prev = prev;
		}
		
		n--;
	}

	//prints front to back then back to front of list
    public void print() {
        //no elements to print for empty list
        if (head == null) {
            System.out.println("List is empty.");
            return;
        }

        //follow next references to list elements from the front to the back of the list
        System.out.print("front to back: ");
        ListNode2 node = head;
        System.out.print(node.element + " ");
		
        while (node.next != null) {
            node = node.next;
            System.out.print(node.element + " ");
        }

        //follow prev references to list elements from the back to the front of the list
        System.out.print("-- and back to front: ");
		
        while (node != null) {
            System.out.print(node.element + " ");
            node = node.prev;
        }
		
        System.out.println();
    }
}
