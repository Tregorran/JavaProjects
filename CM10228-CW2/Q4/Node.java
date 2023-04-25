
public class Node {
	String name;
	int npx;//XOR of previous and next
	
	public Node(int npx, String name) {
		this.npx = npx;
		this.name = name;
	}
	
	String getName() {
		return name;
	}
	
	int getNpx() {
		return npx;
	}
	
	void setName(String name) {
		this.name = name;
	}
	
	void setNpx(int npx) {
		this.npx = npx;
	}
}
