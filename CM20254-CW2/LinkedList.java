class LinkedList {
	private ListNode head = null;
	private ListNode tail = null;
	private int n = 0; 
	
	//Adds element to beginning of the list
    public void addFirst(Object o) {   
		head = new ListNode(o, head);
		if (n == 0){
			tail = head;
		}
		n++;
    }
	
	//Gets the ith element from the list
    public Object get(int i) {
		//error
		if (i < 0 || i >= n){
			return "i is out of range";
		}
		
		ListNode node = head;
		
		//finds element
		for (int j = 0; j < i; j++){
			node = node.next;
		}

		return String.valueOf(node.element);
    }
	
	//Inserting a given element at a given index position
    public void insert(Object o, int i) {
		//error
		if ((i > n) || (i < 0)){
			System.out.println("i is out of range?!");
			return;
		}
		
		//is at head
		if (i == 0){
			addFirst(o);
			return;
		}
		
		ListNode node = head;
		
		//find the one before the insert
		for (int j = 0; j < i-1; j++){
			node = node.next;
		}
		
		//insert at end
		if (i == n){
			tail.next = new ListNode(o, null);
			tail = tail.next;
			node.next = tail;
			n++;
			return;
		}
		
		node.next = new ListNode(o, node.next);
		n++;
    }

	//removing the element at a given index position
    public void remove(int i) {
		if (i < 0 || i >= n) {
			System.out.println("i is out of range");
			return;
		}
		
		//is at head
		if (i == 0) {
			head = head.next; 
			n--; 
			return; 
		}
		

		ListNode node = head;
		
		//reaches one before removal one
		for (int j = 0; j < i-1; j++){
			node = node.next;
		}
		
		//previous's next becomes equal to the one after the removal
		node.next = node.next.next;
		
		//replaces the tail
		if (i == n-1){
			tail = node;
		}
		n--;
    }
	
	//Add element to end of the list
    public void add(Object o) {
		//no elements in list
		if (n == 0){
			head = new ListNode(o, head);
			tail = head;
			n++;
			return;
		}
		
		//sets tail
		ListNode node = tail;
		tail = new ListNode(o,null);
		node.next = tail;
		n++;
    }
}
