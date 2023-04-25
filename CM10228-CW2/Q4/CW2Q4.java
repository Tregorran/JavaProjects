import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class CW2Q4 {
	static Memory meme = new Memory();

	static Node head = null;
	
	public static void main(String[] args) {	
		
		meme.add(new Node(0, "Null"));
		
		File namesFile = new File("names.txt");
		BufferedReader br = null;
		String st;
		String[] names = null;
		
		//Reads the names into memory
		try {			
			br = new BufferedReader(new InputStreamReader(new FileInputStream(namesFile), "UTF-8"));
			
			while ((st = br.readLine()) != null) {
				names = st.split(",");
			}
			br.close();
		} catch (Exception e) {
			System.out.println("Could not find file.");
			System.exit(0);
		}
		
		//Removes " from the names
		for (int i = 0; i < names.length; i++) {
			insert(names[i].replaceAll("\"", ""));
		}
		
		//Demontrating the use of the methods
		insertAfter("ALONSO","ALESSIO");
		insertAfter("HAI","JEFF");
		insertAfter("DARELL","SAM");

		insertBefore("MARY", "ALEX");
		insertBefore("ALESSIO", "LARRY");
		
		removeAfter("DORSEY","DARELL");
		
		removeBefore("ALONSO","BRODERICK");
		
		//Prints their position in memory : name : XOR of previous and next
		for (int i = 0; i < meme.size(); i++) {
			System.out.println(i + ":" + meme.get(i).getName() + ":" + meme.get(i).getNpx());
		}
	}
	
	//Inserts the names into memory
	public static void insert(String newName) {
		//Checks that there is a head if not sets head to the new node
		if (head == null) {
			head = meme.get(0);
			meme.get(addressNode(head)).setName(newName);
			return;
		}
		//Adds a temp to end of array called Tail
		meme.add(new Node(XOR(meme.size()-1,0), "Tail"));
		
		//starts from the head;
		int currentIndex = addressNode(head);
		int previousIndex = addressNode(head);
		int previousIndexTemp = currentIndex;
		
		//loops until end of list and adds the new node
		while (true) {
			previousIndexTemp = currentIndex;
			currentIndex = XOR(meme.get(currentIndex).getNpx(), previousIndex);
			previousIndex = previousIndexTemp;
			
			if (XOR(meme.get(currentIndex).getNpx(), previousIndex) == 0) {
				int theAfter = meme.size()-1;
				
				//Sets the Npx of the node before the end node
				meme.get(currentIndex).setNpx(XOR(theAfter, previousIndex));
				
				previousIndexTemp = currentIndex;
				currentIndex = XOR(meme.get(currentIndex).getNpx(), previousIndex);
				previousIndex = previousIndexTemp;
				
				meme.get(currentIndex).setName(newName);
				break;
			}
		}
	}
	
	//Gets the address of the node given
	public static int addressNode(Node n) {
		int index = 0;
		index = meme.indexOf(n);
		return index;
	}
	
	public static void insertAfter(String after, String newObj) {
		//Creates new node in memory
		if (after == "Null") {
			head = meme.get(0);
			meme.get(addressNode(head)).setName(newObj);
			return;
		}
		
		meme.add(new Node(XOR(meme.size()-1,0), "Tail"));
		int theAfter = meme.size()-3;
		int theBefore = meme.size()-1;

		if (theAfter < 0) {
			theAfter = 0;
		}
		
		//starts from the head
		int currentIndex = addressNode(head);
		int previousIndex = addressNode(head);
		int previousIndexTemp = currentIndex;
		int foundName = 0;
		
		//loops to find the name "after" until reached end of meme
		while(true) {
			//Compares the current name to "after"
			if (meme.get(currentIndex).getName().equals(after)){
				foundName = 1;
				break;
			}
			
			//checks haven't reached the end of meme
			if (XOR(meme.get(currentIndex).getNpx(), previousIndex) != 0) {
				
				//moves forwards through meme
				previousIndexTemp = currentIndex;
				currentIndex = XOR(meme.get(currentIndex).getNpx(), previousIndex);
				previousIndex = previousIndexTemp;
			//reached the end of the meme and have't found "after"	
			} else {
				meme.remove(meme.size()-1);
				System.out.println(after + " does not exist");
				break;
			}
		}
		
		//found "after" name
		if (foundName == 1) {

			//Calculates the npx of the previous node to account for the new added node
			meme.get(meme.size()-2).setNpx(XOR(theAfter, theBefore));
			
			String nameTemp;
			String nameTemp2;
			
			//moves to the name after "after"
			previousIndexTemp = currentIndex;
			currentIndex = XOR(meme.get(currentIndex).getNpx(), previousIndex);
			previousIndex = previousIndexTemp;
			
			//Stores the name after "after"
			nameTemp2 = meme.get(currentIndex).getName();
			meme.get(currentIndex).setName(newObj);
			
			//loops until end of meme
			while (true) {
				//checks if reached end of meme
				if (XOR(meme.get(currentIndex).getNpx(), previousIndex) == 0) {
					break;
				}
				
				//moves through the meme
				previousIndexTemp = currentIndex;
				currentIndex = XOR(meme.get(currentIndex).getNpx(), previousIndex);
				previousIndex = previousIndexTemp;
				
				//store next name and replace the name
				nameTemp = meme.get(currentIndex).getName();
				meme.get(currentIndex).setName(nameTemp2);
				
				//checks if reached end of meme
				if (XOR(meme.get(currentIndex).getNpx(), previousIndex) == 0) {
					break;
				}
				
				//moves through the meme
				previousIndexTemp = currentIndex;
				currentIndex = XOR(meme.get(currentIndex).getNpx(), previousIndex);
				previousIndex = previousIndexTemp;
				
				//store next name and replace the name
				nameTemp2 = meme.get(currentIndex).getName();
				meme.get(currentIndex).setName(nameTemp);
				
				//checks if reached end of meme
				if (XOR(meme.get(currentIndex).getNpx(), previousIndex) == 0) {
					break;
				}
			}	
		}
	}
	
	public static void insertBefore(String before, String newObj) {
		
		if (before == "Null") {
			head = meme.get(0);
			meme.get(addressNode(head)).setName(newObj);
			return;
		}
		
		//Creates new node in meme
		meme.add(new Node(XOR(meme.size()-1,0), "Tail"));
		int theAfter = meme.size()-3;
		int theBefore = meme.size()-1;
		
		if ((theAfter) < 0) {
			theAfter = 0;
		}
		
		//starts at the head
		int currentIndex = addressNode(head);
		int previousIndex = addressNode(head);
		int previousIndexTemp = currentIndex;
		int foundName = 0;
			
		//loops to find the name "before" until reached end of meme
		while(true) {
			//Compares the current name to "after"
			if (meme.get(currentIndex).getName().equals(before)){
				foundName = 1;
				break;
			}
			
			//checks haven't reached the end of the meme
			if (XOR(meme.get(currentIndex).getNpx(), previousIndex) != 0) {
				
				//moves forwards through the meme
				previousIndexTemp = currentIndex;
				currentIndex = XOR(meme.get(currentIndex).getNpx(), previousIndex);
				previousIndex = previousIndexTemp;
			//reached the end of the meme and have't found "after"
			} else {
				meme.remove(meme.size()-1);
				System.out.println(before + " does not exist");
				break;
			}
		}
		
		//found "before" name
		if (foundName == 1) {
			
			//Calculates the npx of the new node
			meme.get(meme.size()-2).setNpx(XOR(theAfter, theBefore));
			
			String nameTemp;
			String nameTemp2;
			
			//Store before name and replace with newObj
			nameTemp2 = meme.get(currentIndex).getName();
			
			meme.get(currentIndex).setName(newObj);
			
			if (nameTemp2.equals(head.getName())){
				head = meme.get(currentIndex);
			}
			
			//loops until end of meme
			while (true) {
				//Checks if reached end of list
				if (XOR(meme.get(currentIndex).getNpx(), previousIndex) == 0) {
					break;
				}
				
				//moves through memory
				previousIndexTemp = currentIndex;
				currentIndex = XOR(meme.get(currentIndex).getNpx(), previousIndex);
				previousIndex = previousIndexTemp;
		
				//store next name and replace the name
				nameTemp = meme.get(currentIndex).getName();
				meme.get(currentIndex).setName(nameTemp2);
				
				//Checks if reached end of list
				if (XOR(meme.get(currentIndex).getNpx(), previousIndex) == 0) {
					break;
				}
				
				//moves through the memory
				previousIndexTemp = currentIndex;
				currentIndex = XOR(meme.get(currentIndex).getNpx(), previousIndex);
				previousIndex = previousIndexTemp;
				
				//store next name and replace the name
				nameTemp2 = meme.get(currentIndex).getName();
				meme.get(currentIndex).setName(nameTemp);
				
				//Checks if reached end of list
				if (XOR(meme.get(currentIndex).getNpx(), previousIndex) == 0) {
					break;
				}
			}
		}
	}
	
	public static void removeAfter(String after, String newObj) {
		//Start at head
		int currentIndex = addressNode(head);
		int previousIndex = addressNode(head);
		int previousIndexTemp = currentIndex;
		int secondPrevious = 0;
		int foundName = 0;
		
		//Loops through the memory until "after" is found
		while(true) {
			//Compares the current name to "after"
			if (meme.get(currentIndex).getName().equals(after)){
				foundName = 1;
				break;
			}
			
			//Checks haven't reached the end of the meme
			if (XOR(meme.get(currentIndex).getNpx(), previousIndex) != 0) {
				
				//Moves forwards through meme
				previousIndexTemp = currentIndex;
				currentIndex = XOR(meme.get(currentIndex).getNpx(), previousIndex);
				previousIndex = previousIndexTemp;
			} else {
				break;
			}
		}
		
		//Found "after" name
		if (foundName == 1) {
			
			//Moves to the name after "after"
			secondPrevious = previousIndex;
			previousIndexTemp = currentIndex;
			currentIndex = XOR(meme.get(currentIndex).getNpx(), previousIndex);
			previousIndex = previousIndexTemp;
			
			//checks the name newObj is in fact after "after
			if (!(meme.get(currentIndex).getName().equals(newObj))) {
				System.out.println(newObj + " is not after " + after + ", cannot remove.");
				return;
			}
			
			//removes chosen name
			meme.remove(currentIndex);
			
			//sets the new values before the removed index
			int nextIndex = currentIndex;
			currentIndex = previousIndex;
			previousIndex = secondPrevious;
			
			//Set npx of the rest of the nodes until the end of meme
			try {
				while(true) {
					if (nextIndex > meme.size()-1) {
						nextIndex = 0;
					}
					
					if (previousIndex < 0) {
						previousIndex = 0;
					}
					
					//Set new Npx
					meme.get(currentIndex).setNpx(XOR(previousIndex, nextIndex));
					
					if (XOR(meme.get(currentIndex).getNpx(), previousIndex) == 0) {
						break;
					}
					
					//Move forward through meme
					previousIndexTemp = currentIndex;
					currentIndex = XOR(meme.get(currentIndex).getNpx(), previousIndex);
					previousIndex = previousIndexTemp;
					nextIndex++;
					
					if (XOR(meme.get(currentIndex).getNpx(), previousIndex) == 0) {
						break;
					}
				}
			} catch (Exception e) {
				return;
			}
		}
	}

	public static void removeBefore(String before, String newObj) {
		//Start from the head
		int currentIndex = addressNode(head);
		int previousIndex = addressNode(head);
		int previousIndexTemp = currentIndex;
		int secondPrevious = 0;
		int foundName = 0;
		
		//Loops through the memory until "before" is found
		while(true) {
			//Compares the current name to "before"
			if (meme.get(currentIndex).getName().equals(before)) {
				foundName = 1;
				break;
			}
			
			//Checks haven't reached the end of the meme
			if (XOR(meme.get(currentIndex).getNpx(), previousIndex) != 0) {
				
				//Moves forwards through meme
				secondPrevious = previousIndex;
				previousIndexTemp = currentIndex;
				currentIndex = XOR(meme.get(currentIndex).getNpx(), previousIndex);
				previousIndex = previousIndexTemp;
				
			} else {
				break;
			}
		}
		
		//Found "before" name
		if (foundName == 1) {
			
			//Checks if the previous is the newObj
			if (!(meme.get(previousIndex).getName().equals(newObj))) {
				System.out.println(meme.get(previousIndex).getName());
				System.out.println(newObj + " is not before " + before + ", cannot remove.");
				return;
			}
			
			//Sets the new each if needed
			if (newObj.equals(head.getName())) {
				head = meme.get(currentIndex);
			}
			
			//removes chosen name
			meme.remove(previousIndex);
			
			//sets the new values after the removed index
			int nextIndex = currentIndex;
			currentIndex = previousIndex;
			previousIndex = secondPrevious;
			
			//Set npx of the rest of the nodes until the end of meme
			try {
				while(true) {
					if (nextIndex > meme.size()-1) {
						nextIndex = 0;
					}
					
					if (previousIndex < 0) {
						previousIndex = 0;
					}
					
					//Set new Npx
					meme.get(currentIndex).setNpx(XOR(previousIndex, nextIndex));
					
					if (XOR(meme.get(currentIndex).getNpx(), previousIndex) == 0) {
						break;
					}
					
					//Move forward through meme
					previousIndexTemp = currentIndex;
					currentIndex = XOR(meme.get(currentIndex).getNpx(), previousIndex);
					previousIndex = previousIndexTemp;
					nextIndex++;
					
					if (XOR(meme.get(currentIndex).getNpx(), previousIndex) == 0) {
						break;
					}
				}
			} catch (Exception e) {
				return;
			}
		}
	}
	
	//Calculates the XOR of the two values given
	public static int XOR(int value1, int value2) {
		int XORed = value1 ^ value2;
		return XORed;
	}

}
