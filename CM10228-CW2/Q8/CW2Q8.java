import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CW2Q8 {

	public static void main(String[] args) {
		
		String userInput = "0";
		int inputNum = 0;
		calculateSpiral(1001);
		
		BufferedReader spiralcurrentNumber = new BufferedReader(new InputStreamReader(System.in));
		
		try {
			//Gets users input and to check that it is an appropriate number
			while(true) {				
				System.out.println("Type in the x by x number of the spiral:");
				userInput = spiralcurrentNumber.readLine();
				
				try {
					inputNum = StringToInt(userInput);
					if ((inputNum < 5) || (inputNum%2 == 0) || (inputNum > 1001)) {
						System.out.println("A number must be entered that is larger than 4, smaller than 1001 \nand is an odd number.");
					} else {
						break;
					}
				} catch(Exception e) {
						System.out.println("A number must be entered that is larger than 4, smaller than 1001 \nand is an odd number.");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}	
		
		calculateSpiral(inputNum);
	}
	
	public static void calculateSpiral(int inputNum){
		//Calculates the spiral
		int result = 1;
		int currentNumber = 1;
		int currentPlus = 2;
		
		while(currentPlus < inputNum) {
			for (int i = 0; i < 4 ; i++) {
				currentNumber = currentNumber + currentPlus;
				result += currentNumber;
			}
			currentPlus += 2;
		}
		System.out.println( inputNum + " by " + inputNum + " spiral. Result: " + result);
	}
	
	//Converts the string given into a number
	public static int StringToInt(String number){
		int convertedNum = 0;
		
		/*loops for each character and compares it to the switch cases
		and gets the appropriate number*/
		for (int i = 0; i < number.length(); i++) {
			int num = 1;
			int times;
			
			switch (number.charAt(i)) {
				case '0':
					num = 0;
					break;
				case '1':
					num = 1;
					break;
				case '2':
					num = 2;
					break;
				case '3':
					num = 3;
					break;
				case '4':
					num = 4;
					break;
				case '5':
					num = 5;
					break;
				case '6':
					num = 6;
				    break;
				case '7':
					num = 7;
					break;
				case '8':
					num = 8;
					break;
				case '9':
					num = 9;
					break;
			}
			//To calculate the power of 10 of each number
			//eg. 234 = 200 , 30, 4
			times = (number.length()-(i+1));
			
			for (int j = 1; j <= times; j++ ){
				num = num*10;
			}
			
			convertedNum = convertedNum + num;
		}

		return convertedNum;
	}
}
