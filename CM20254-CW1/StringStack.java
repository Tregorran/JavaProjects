/** A stack abstract data type that contains Strings. */
public class StringStack {
	String stack[];
	int pointer;
	
	/**
	* Constructor for creating a new StringStack with a certain capacity.
	* @param capacity the maximum number of strings the stack can hold
	*/
	public StringStack(int capacity) {
		//To check for negative values that are not allowed
		if (capacity<0)
		{
			capacity = 0;
		}
		
		stack = new String[capacity];
		pointer = 0;
	}
	
	/**
	* Puts the given String on top of the stack (if there is enough space).
	* @param s the String to add to the top of the stack
	* @return false if there was not enough space in the stack to add the string;
	*         otherwise true
	*/
	public boolean push(String s) {
		if (pointer < stack.length){
			stack[pointer] = s;
			pointer += 1;
			return true;
		}
		return false;
	}
	
	/**
	* Removes the String on top of the stack from the stack and returns it.
	* @return the String on top of the stack, or null if the stack is empty.
	*/
	public String pop() {
		String temp;
		if (pointer > 0)
		{
			pointer -= 1;
			temp = stack[pointer];
			stack[pointer] = null;
			return temp;
		}
		return null;
	}		
	
	/**
	* Returns the number of Strings in the stack.
	* @return the number of Strings in the stack
	*/
	public int count() {
		return pointer;
	}
}
