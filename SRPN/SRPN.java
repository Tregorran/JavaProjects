import java.io.*;
import java.util.Stack;

public class SRPN {
	
	boolean commenting = false;//Global variable if there is commenting
	static int randomChoice = 0;//Global variable to choose from the randomNums array
	
	public static void main(String[] args) {
        SRPN sprn = new SRPN();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        
        try 
		{
            while(true)//Keep on accepting input from the command-line
			{
                String command = reader.readLine();
                
                if(command == null)//Close on an End-of-file (EOF) (Ctrl-D on the terminal)
                {
					System.exit(0);//Exit code 0 for a graceful exit
                }

                sprn.processCommand(command);//Otherwise, (attempt to) process the character         
            }
        } 
		catch(IOException e) 
		{
			System.err.println(e.getMessage());
			System.exit(1);
        }
    }
	
	Stack<Integer> numStack = new Stack<Integer>();
	
	/*
	* Method splits up relevent characters
	* Decides if the input has comments
	* Passes processed data into the calculator method
	*/
    private void processCommand(String input)
	{
		String splitInputs[] = input.split("((?<=[a-z])(?=\\d)|(?<=\\d)(?=[a-z])|\\s+)");//Splits up the input into integers, letters/words and spaces 
		
		for (String singleInputs : splitInputs)
		{
			if (singleInputs.contains("#"))//If there is a hashtag flip commenting boolean
			{
				commenting = !commenting;
			}
			
			if (commenting == false)//If not in a comment
			{
				if (!(singleInputs.length() == 0))//Not empty input
				{
					boolean isDigit = singleInputs.matches("-?[0-9]+");
					
					if (isDigit == false)//Input is not a digit
					{
						String eachChar[] = singleInputs.split("");//Splits each individual character
						
						for (String singleChar : eachChar)
						{
							calculator(numStack, singleChar, isDigit);
						}
					}
					
					else//Input is a digit
					{		
						if (input.charAt(0) == '0')//Checks if the user has entered an octal number
						{
							singleInputs = convertOctal(singleInputs);
						}
						checkStackPush(numStack, singleInputs);//Checks integer and stack, pushes the integer into the stack
					}
				}
			}
		}
    }
	
	/*
	* Method does the operator calculations from the values from the stack
	* As well as use other signs from the srpn program, d and r
	*/
	private void calculator(Stack<Integer> numStack, String input, boolean isDigit)
	{
		long result = -1;//Stores result of operations between num1 and num2
		String signs = "+-*/^%";
		String otherSigns = "dr";
		String randomNums[] =
		{
			"1804289383",
			"846930886",
			"1681692777",
			"1714636915",
			"1957747793",
			"424238335",
			"719885386",
			"1649760492",
			"596516649",
			"1189641421",
			"1025202362",
			"1350490027",
			"783368690",
			"1102520059",
			"2044897763",
			"1967513926",
			"1365180540",
			"1540383426",
			"304089172",
			"1303455736",
			"35005211",
			"521595368"
		};//The "random" numbers from the srpn program
		
		if (signs.contains(input))//if the input contain an operator
		{
			try
			{
				long num2 = numStack.pop();
				long num1 = numStack.pop();
				
				switch (input)
				{
					case "+":
						if (num1 + num2 > Integer.MAX_VALUE)//Checks sum is within max value of an integer
						{
							result = Integer.MAX_VALUE;
						}
						
						else if (num1 + num2 < Integer.MIN_VALUE)//Checks sum is within min value of an integer
						{
							result = Integer.MIN_VALUE;
						}
						
						else//Sum is within range of an integer
						{
							result = num1 + num2;
						}
						break;
						
					case "-":
						if (num1 - num2 > Integer.MAX_VALUE)
						{
							result = Integer.MAX_VALUE;
						}
						
						else if (num1 - num2 < Integer.MIN_VALUE)
						{
							result = Integer.MIN_VALUE;
						}
						
						else//Subtration is within range of an integer
						{
							result = num1 - num2;
						}
						break;
						
					case "*":
						if (num1 * num2 > Integer.MAX_VALUE)
						{
							result = Integer.MAX_VALUE;
						}
						
						else if (num1 * num2 < Integer.MIN_VALUE)
						{
							result = Integer.MIN_VALUE;
						}
						
						else//Multiplication is within range of an integer
						{
							result = num1 * num2;
						}
						break;
						
					case "/":
						if (num2 == 0)//Checks that you cannot divide by 0
						{
							numStack.push((int)(num1));
							result = num2;//So that result doesn't push in -1
							System.out.println("Divide by 0.");
						}
						
						else//num2 is not 0
						{
							result = num1 / num2;
						}
						break;
						
					case "^":
						if (num2 < 0)//Checks that you cannot have a negative power
						{
							numStack.push((int)(num1));
							result = num2;//So that result doesn't push in -1
							System.out.println("Negative power.");
						}
						
						else//num2 is not a negative number
						{
							if (Math.pow(num1, num2) > Integer.MAX_VALUE)
							{
							result = Integer.MAX_VALUE;
							}
							
							else if (Math.pow(num1, num2) < Integer.MIN_VALUE)
							{
							result = Integer.MIN_VALUE;
							}
							
							else//num1 to the power of num2 is within range of an integer
							{
							double doubleResult = Math.pow(num1, num2);//num1 to the power of num2
							result = Math.round(doubleResult);
							}
						}
						break;
						
					case "%":
						result = num1 % num2;//Remainder of num1 divide num2
						break;					
				}
				numStack.push((int)result);//Pushes the result of the calculation onto the stack
			}
			catch(Exception e)
			{
				System.out.println("Stack underflow.");
			}
		}
		
		else if (otherSigns.contains(input))//Else if the input contains d or r
		{
			switch (input)
			{
				case "d":
					printStack(numStack);
					break;
					
				case "r":
					numStack.push(Integer.parseInt(randomNums[randomChoice]));//Pushes a number onto the stack from the string array randomNums
					
					if (randomChoice > 21)//Goes back to the begining of the randomNums array once larger than 21
					{
						randomChoice = -1;
					}
					randomChoice++;//Increments through the String randomNums array
					break;
			}
		}
		
		else if (input.charAt(input.length()-1) == '=')//Else if an "=" is received
		{
			try
			{
				result = numStack.peek();
				System.out.println(result);//Print the top of the stack
			}
			catch(Exception e)
			{
				System.out.println("Stack empty.");
			}
		}
		
		else if (!(input.charAt(0) == '#') && !(isDigit))//Else if, input is not a "#" or a digit
		{
			System.out.printf("Unrecognised operator or operand \"" + input + "\".\n");
		}
	}	
	
	/*
	* Checks size of Stack 
	* Pushes input into stack
	*/
	private void checkStackPush(Stack<Integer> numStack, String input)
	{
		int intInput = checkMaxMin(input);//Checks if the input is within integer max and min value range
			
		if (numStack.size() > 22)
		{
			System.out.println("Stack overflow.");
		}
		
		else//Size of Stack is within limit
		{
			numStack.push(intInput);
		}
	}

	/*
	* Converts an octal number into decimal
	*/
	private static String convertOctal(String input)
	{
		int octValue = 0;
		String output;
		
		for(int i = 0; i < input.length(); i++)//Increments "i" to the length of the input
		{
			octValue += Character.getNumericValue(input.charAt(i));//Adds the next digit of input into the variable octValue
			if (!(i == input.length()-1))//If it is not the last digit in input do the following
			{
				octValue = octValue * 8;
			}
		}
		output = Integer.toString(octValue);
		return output;//Returns the decimal value of the input
	}
	
	/*
	* Checks that the input is within the range of an integer
	*/
	private static int checkMaxMin(String value)
	{
		int intInput;
		
		if (value.charAt(0) == '-')//If a negative number in the string
		{
			try//Try to convert the string into an integer
			{
				intInput = Integer.parseInt(value);
			}
			catch(Exception e)//Can't convert string to integer
			{
				intInput = Integer.MIN_VALUE;//Input equals min value of an integer
			}
		}
		
		else//Is a positive integer in the string
		{
			try
			{
				intInput = Integer.parseInt(value);
			}
			catch(Exception e)
			{
				intInput = Integer.MAX_VALUE;//Input equals max value of an integer
			}
		}
		return(intInput);
	}
	
	/*
	* Prints the contents of the stack
	*/
	private void printStack(Stack<Integer> numStack)
	{
		if (numStack.isEmpty())
		{
			System.out.println(Integer.MIN_VALUE);//Prints the min value of an Integer
		}
		
		else//Stack is not empty
		{
			for(Integer eachNum: numStack)//Goes through each element in the stack
			{
				System.out.println(eachNum);
			}
			
		}
	}
}
