import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class HumanPlayer extends Player
{
	private int gold;//The player's amount of gold
	private BufferedReader in;
	
	/**
	 * Default constructor, sets x and y and starting gold amount for the HumanPlayer.
	 * Including input from the console
	 */
	public HumanPlayer()
	{
		x = 1;
		y = 1;
		gold = 0;
		in = new BufferedReader(new InputStreamReader(System.in));
	}
	
	/**
     * Processes command input from console, returns the player's input.
     * Invalid command, returns the string "Invalid".
     *
     * @return input : Processed output or Invalid if the input from the player is an invalid command.
	 * @throws : IOException 
     */
	protected String getNextAction() throws IOException 
	{
		//Processes and checks inputs is a command
		while(true)
		{
			String input = getInputFromConsole().toUpperCase().trim();
			
			if (input.equals("HELLO"))
			{
				return input;
			}
			else if (input.equals("GOLD"))
			{
				return input;
			}
			else if (input.equals("MOVE N") || input.equals("MOVE E") || input.equals("MOVE S") || input.equals("MOVE W"))
			{
				return input;
			}
			else if (input.equals("LOOK"))
			{
				return input;
			}
			else if (input.equals("PICKUP"))
			{
				return input;
			}
			else if (input.equals("QUIT"))
			{
				return input;
			}
			else
			{
				return "Invalid";
			}
		}
    }
	
    /**
     * Reads in player's input from the console.
     * 
     * @return input : A string containing the input the player entered.
     * @throws IOException 
     */
    protected String getInputFromConsole() throws IOException 
    {
    	String input = in.readLine();
        return input;
    }
	
    /**
     * @return gold : The amount of gold the player has
     */
    protected int getGold()
	{
		return gold;
	}
    
    /**
     * @param gold : Sets the new amount of gold the player has
     */
    protected void setGold(int gold)
	{
		this.gold = gold;
	}

}
