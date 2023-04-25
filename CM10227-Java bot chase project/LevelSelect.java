import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class LevelSelect 
{
	List<String> results = new ArrayList<String>();//Names of the files
	List<String> directory = new ArrayList<String>();//Directory of the files
	private BufferedReader in;
	private BufferedReader in2;
		
	/**
	 * Default constructor, adds file names and file directories to respective lists
	 */
	public LevelSelect() throws IOException
	{		
		File[] files = new File("Maps").listFiles();

		for (File file : files) 
		{
		    if (file.isFile()) 
		    {
		        results.add(file.getName());//file names get added to the results list 
		        directory.add(file.getPath());//file directories are added to the directory list
		    }
		}
		in = new BufferedReader(new InputStreamReader(System.in));
	}
	
	/**
	 * Displays results list, showing files the user can choose from
	 * Allows user to choose a map
	 *
	 * @return "default" : Chosen default map
	 * @return directory.get(i) : Directory of the map chosen
	 * @throws IOException
	 */
	public String fileName() throws IOException
	{
		String correctForm = "";
		System.out.println("Type in the name or number of the map you would like to play:");
		System.out.println("0. default");
		
		//Prints out the file names 
		for(int i = 0; i < results.size(); i++)
		{
			System.out.println((i+1)+ ". " + results.get(i));
		}
		
		//Gets user input, gets the file that the user has chosen
		while(true)
		{
			correctForm = "noFileLoc";
			String input = in.readLine();
			
			if (input.equals("default") || input.equals("0"))
			{
				return "default";
			}
			
			//Compares user input to file names and their order numbers
			for (int i = 0; i < results.size(); i++)
			{
				if (input.equals(results.get(i)))
				{
					correctForm = checkFileFormat(i);
				}
				else if (input.equals(Integer.toString(i+1)))
				{
					correctForm = checkFileFormat(i);
				}
				
				if (correctForm == "true")
				{
					return directory.get(i);
				}
			}
			
			if (correctForm == "false")
			{
				System.out.println("Please type in a map that is in the correct format");
			}
			else if (correctForm == "noFileLoc")
			{
				System.out.println("Please type in a map that exists in the file location.");
			}
		}
	}
	
	/**
	 * Checks the file that the user has chosen is in the correct format
	 * And if it is allowed to be loaded
	 *
	 * @param i : The index of the file chosen in the lists
	 * @return : True or false if the file is in the correct format
	 */
	public String checkFileFormat(int i) throws IOException
	{
		String name = "";
		String win = "";
		String theWinNum = "";
		int winNum = 0;//To check if there is enough gold to meet the required win of the map
		int numOfDots = 0;//To check there is a minimum number of spaces on the map
	    int numOfExits = 0;//To check if there is at least one exit symbol on the map
	    int numOfGold = 0;//To check if there is enough gold to meet the required win of the map
	    int j = 0;
			
		in2 = new BufferedReader(new FileReader(directory.get(i)));
	    String line = in2.readLine();
	    	
	    //Stores the first four letters in the line
	    for (j = 0; j < 4; j++)
	    {
	    	name += line.charAt(j);
	    }
	    
	    //Checks that name is the four letters in the file
	    if (!(name.equals("name")))
	    { 
	    	return "false";
	    }    	   	
	    	
	    line = in2.readLine();
	    
	    //Stores the first three letters of the second line
	    for (j = 0; j < line.length(); j++)
	    {
			if (j < 3)
			{
				win += line.charAt(j);
			}
				
			if (j > 3)
			{
				theWinNum += Character.toString(line.charAt(j));//Stores gold required to beat the map
			}
	    }
		
	    try
		{
			winNum = Integer.valueOf(theWinNum);
	    }
	    catch(Exception e)
	    {
	    	return "false";
	    }
	    
	    //Checks that win is in the file
	    if (!(win.equals("win")))
	    {
	    	return "false";
	    }
	    	
	    line = in2.readLine();
	    	
	    //Checks symbols
	    while (line != null)
		{
		    int length = line.length();
			    
		    //Checks each character of the map
		    for(j = 0; j < length; j++)
		    { 
		    	char symbol = line.charAt(j);
			    	
		    	//Checks only these characters are used for the map in the file
		    	if ((symbol != '#')  && (symbol != '.') && (symbol != 'G') && (symbol != 'E'))
		    	{
		    		return "false";
		    	}
			    	
		    	//Adds up the number or required symbols
		    	if (symbol == '.')
		    	{
		    		numOfDots += 1;
		    	}
		    	else if (symbol == 'G')
		    	{
		    		numOfGold += 1;
		    	}
		    	else if (symbol == 'E')
		    	{
		    		numOfExits += 1;
		    	}
		    }
		    line = in2.readLine();
		}
	    	
	    //The map has to have at least 4 spaces
	    if (numOfDots < 4)
	    {
	    	return "false";
	    }
	    	
	    //There needs to be the same or more gold on the map than the win condition
	    if (numOfGold < winNum)
	    {
	    	return "false";
	    }
	    	
	    //There has to be at least 1 exit symbol
	    if (numOfExits < 1)
	    {
	    	return "false";
	    }
	    return "true";
	}
}
