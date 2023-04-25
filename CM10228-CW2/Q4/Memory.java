import java.lang.reflect.Array;
import java.util.Arrays;

public class Memory {
	
	int memorySize = 0;
	Node[] memory = new Node[memorySize];
	
	//Adds the node to the array
	public void add(Node n) {
		memorySize++;
		
		//Copies contents and increases the size of the array
		memory = Arrays.copyOf(memory, memorySize);
		memory[memory.length-1] = n;
	}
	
	//Returns the node that is wanted by index
	public Node get(int index) {
		Node node;
		node = memory[index];
		return node;
	}
	
	//Returns size of the array
	public int size() {
		int memorySize = 0;
		memorySize = Array.getLength(memory);
		return memorySize;
	}
	
	//Return the index of where the node is in the array
	public int indexOf(Node n) {
		for (int i = 0; i < memory.length; i++) {
			if (memory[i].equals(n)) {
				return i;
			}
		}
		return 0;
	}
	
	//Removes element at index in array
	public void remove(int removeIndex) {
		
		//Checks that the index isn't invalid
		if ((memory == null) || (removeIndex >= memory.length)  || (removeIndex < 0)) { 
			return;
	    } 
	  
		//Creates a temp array that is smaller by 1
	    Node[] tempArray = new Node[memory.length - 1]; 
	  
	    //Copies elements apart from the removeIndex
	    int k = 0;
	    for (int i = 0; i < memory.length; i++) {
	    	
	    	//Only copies elements that are not the removeIndex
	    	if (i != removeIndex) { 
	    		tempArray[k] = memory[i]; 
		    	k++;
	    	}
	    }
		//Sets the memory to the new array
	    memory = tempArray;
	}
}
