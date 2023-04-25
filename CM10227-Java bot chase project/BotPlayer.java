import java.util.Random;

public class BotPlayer extends Player
{
	private int pX;//Last known HumanPlayer's x
	private int pY;//Last known HumanPlayer's y
	private String botState;
	
	/**
	 * Default constructor, sets x and y and sets the current state of the bot.
	 */
	public BotPlayer()
	{
		x = 2;
		y = 1;
		botState = "look";
	}
	
	/**
     * Processes the bot's state. Returns the bots's command
     *
     * @return string : Processed command output
     */
	protected String getNextAction() 
	{
		char direction = decideMove();//Chooses direction to move towards player or changes bot state to "look"
		
		if (botState == "look")//Look for the player
		{
			pX = -1;//Reset HumanPlayer's last known x
			pY = -1;//Reset HumanPlayer's last known y
			return "LOOK";
		}
		else if (botState == "moveRan")//Move in a random direction
		{
			direction = randomMove();//Get a random direction to move in
			switch(direction)
			{
				case('N'):
					return "MOVE N";
				case('E'):
					return "MOVE E";
				case('S'):
					return "MOVE S";
				case('W'):
					return "MOVE W";
			}
		}
		else if (botState == "chase")//Go towards HumanPlayer's last known location
		{
			switch(direction)
			{
				case('N'):
					return "MOVE N";
				case('E'):
					return "MOVE E";
				case('S'):
					return "MOVE S";
				case('W'):
					return "MOVE W";
			}
		}
		return null;
    }
	
	/**
     * Decides which direction to move towards HumanPlayer's last known location
     *
     * @return direction : Direction towards the HumanPlayer's last known location
     */
	protected char decideMove()
    {
    	char direction = 'N';
    	
    	//Compares bot's x and y to player's last known x and y
    	if (x < pX)
    	{
    		direction = 'E';
    	}
    	else if (x > pX)
    	{
    		direction = 'W';
    	}
    	else if (y < pY)		
    	{
    		direction = 'S';
    	}
    	else if (y > pY)		
    	{
    		direction = 'N';
    	}
    	else
    	{
    		botState = "look";
    	}
		return direction;
    }
    
	/**
     * Chooses a random direction
     *
     * @return direction : Random direction to move in
     */
	protected char randomMove()
    {
    	char direction = 'N';
    	Random r = new Random();
    	int moveNum = r.nextInt((4 - 1) + 1) + 1;//Random number between 1-4
    	
    	switch(moveNum)
    	{
    		case(1):
    			direction = 'N';
    			break;
    		case(2):
    			direction = 'E';
    			break;
    		case(3):
    			direction = 'S';
    			break;
    		case(4):
    			direction = 'W';
    			break;
    	}
    	return direction;
    }
	
	/**
     * @return pX : Returns HumanPlayer's last known x location
     */
	protected int getPx()
	{
		return pX;
	}
	
	/**
     * @return pY : Returns HumanPlayer's last known y location
     */
	protected int getPy()
	{
		return pY;
	}
	
	/**
     * @return botState : Returns bot's current state
     */
	protected String getState()
	{
		return botState;
	}
	
	/**
     * @param pX : Sets new HumanPlayer's last known x location
     */
	protected void setPx(int pX)
	{
		this.pX = pX;
	}
	
	/**
     * @param pY : Sets new HumanPlayer's last known y location
     */
	protected void setPy(int pY)
	{
		this.pY = pY;
	}
	
	/**
     * @param botState : Sets new bot state
     */
	protected void setState(String botState)
	{
		this.botState = botState;
	}
}
