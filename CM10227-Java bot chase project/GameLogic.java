import java.io.IOException;

public class GameLogic 
{
	private Map map;
	private HumanPlayer player;
	private BotPlayer bot;
	private LevelSelect level;
	private boolean playerTurn = true;//Player's or bot's turn
	
	public static void main(String[] args) throws IOException 
	{
		GameLogic logic = new GameLogic();
		logic.playGame();
    }
	
	/**
	 * Default constructor, initialises objects LevelSelect, HumanPlayer, BotPlayer.
	 * Gets user to choose a map, then loads map
	 * Gives the players a random position on the map loaded
	 */
	private GameLogic() throws IOException
	{
		System.out.println("Dungeon of Doom");
		
		level = new LevelSelect();
		player = new HumanPlayer();
		bot = new BotPlayer();
		
		String fileName = level.fileName();//Gets a map decided by the player
		
		if (fileName == "default")
		{
			map = new Map();//Loads default map
		}
		else
		{
			map = new Map(fileName);//Loads map chosen
		}
		System.out.println("Map: " + map.getMapName() + ", loaded.");
		
		player.randomPlayerPlace(map, 'P');//Places the player randomly on the chosen map
		bot.randomPlayerPlace(map, 'B');//Places the bot randomly on the chosen map

		System.out.println("Please type in your commands:");
	}

	/**
	 * Gets the next action of the current turn player,
	 * Runs the relevant method from the command given by the current player.
	 */
	private void playGame() throws IOException
	{
		String input = "";
		
		while(gameRunning())//Loops until the player has been caught by the bot
		{
			if (playerTurn == true)//It is the player's turn
			{
				input = player.getNextAction();
				while (input.equals("Invalid"))
				{
					System.out.println("Invalid command.");
					input = player.getNextAction();
				}
			}
			else//It is the bot's turn
			{
				input = bot.getNextAction();
			}
			
			if (input.equals("HELLO"))
			{
				System.out.println("Gold to win: <" + hello() + ">");//Displays required gold for the map
			}				
			else if (input.equals("GOLD"))
			{
				System.out.println("Gold owned: <" + gold() + ">");//Displays amount of gold the player has
			}		
			else if (input.equals("MOVE N") || input.equals("MOVE E") || input.equals("MOVE S") || input.equals("MOVE W"))
			{
				String state = move(input.charAt(5));//Moves the player in a certain direction
				if (playerTurn == true)
				{
					System.out.println(state);
				}
			}			
			else if (input.equals("PICKUP"))
			{				
				System.out.println(pickup() + ". Gold owned: <" + player.getGold() + ">");//Picks up gold from the position of the player
			}
			else if (input.equals("LOOK"))
			{
				look();//Displays a 5x5 grid around the player
			}
			else if (input.equals("QUIT"))
			{
				quitGame();//Quits the game/Exits the dungeon
			}	
			
			playerTurn = !playerTurn;//Changes turn
		}
		System.exit(0);//When the player has been caught by the bot
	}
	
	 /**
		 * Checks if the game is running, which is when the player is not caught
		 * 
		 * @return : Returns false if the player is caught by the bot, returns true if not
	*/
	private boolean gameRunning()
	{
		if (bot.getX() == player.getX() && bot.getY() == player.getY())//Compares bot's x and y to player's
		{
			System.out.println("LOSE");
			System.out.println("You have been caught by the bot.");
			return false;
		}
		return true;
	}

    /**
     * @return mapGold : Gold required to win.
     */
    private String hello() 
    {
    	int mapGold = map.getGoldRequired();
        return Integer.toString(mapGold);
    }
	
	/**
     * @return playerGold : Gold currently owned by player.
     */
    private String gold() 
    {
    	int playerGold = player.getGold();
        return Integer.toString(playerGold);
    }
    
    /**
     * Decides what symbol to pass into the playerMove method
     *
     * @param direction : The direction of the movement.
     * @return : Protocol if success or not.
     */
    private String move(char direction)
    {
    	try 
    	{
	    	String state = "";
	    	
	    	if (playerTurn == true)
	    	{
	    		state = playerMove(direction, 'P');
	    	}
	    	else
	    	{
	    		if (bot.getState() == "moveRan")//Moving in random direction
	    		{
	    			bot.setState("look");//Look for player
	    			playerMove(direction, 'B');
	    		}
	    		else if (bot.getState() == "chase")//Moving towards player
	    		{
	    			playerMove(direction, 'B');
	    		}	
	    	}
	    	return state;
    	}
    	catch(Exception e)
    	{
    		return "FAIL";
    	}
    }
    
    /**
     * Checks if movement is legal and updates player's location on the map.
     *
     * @param direction : The direction of the movement.
     * @param charPlayer : The symbol of the player
     * @return : Protocol if success or not.
     */
    private String playerMove(char direction, char charPlayer)
    {
    	int oldX = 0;
    	int oldY = 0;
    	char currentSymbol = '.';
    	String state = "";
    	
    	//Gets the x, y and symbol of the current turn's player
    	if (charPlayer == 'P')
    	{
	    	oldX = player.getX();
	    	oldY = player.getY();
	    	currentSymbol = player.getSymbol();
    	}
    	else if (charPlayer == 'B')
    	{
    		oldX = bot.getX();
	    	oldY = bot.getY();
	    	currentSymbol = bot.getSymbol();
    	}
    	
    	int newX = oldX;
    	int newY = oldY;
    	
    	//Calculates the new x and y of the player
    	switch(direction)
    	{
    		case('N'):
    			newY -= 1;
    			break;
    		case('E'):
    			newX += 1;
    			break;
    		case('S'):
    			newY += 1;
    			break;
    		case('W'):
    			newX -= 1;
    			break;
    	}

    	char mapSymbol = map.getPosSymbol(newX, newY);//Get symbol on the map where the player will move to
    	
    	if ((mapSymbol == '#') || (mapSymbol == 0))//If the new x and y is a wall or none existent.
    	{
    		state = "FAIL";
    	}
    	else//Player is allowed to move to the new x and y
    	{
    		//Update original location with stored symbol and moves player to new location
    		map.updateMap(oldX, oldY, currentSymbol);
    		map.updateMap(newX, newY, charPlayer);
    		
    		//Sets the new x and y for the player and sets the new symbol stored by the player
    		if (charPlayer == 'P')
        	{
    			player.setX(newX);
        		player.setY(newY);
        		player.setSymbol(mapSymbol);
        	}
        	else
        	{
        		bot.setX(newX);
        		bot.setY(newY);
        		bot.setSymbol(mapSymbol);
        	}
    		
    		state = "SUCCESS";
    	}
    	return state;
    }
    
    /**
     * Checks if player is on a G symbol
     * Updates the player's gold amount,
     * Updates the symbol the player is on top of by replacing the symbol G with .
     *
     * @return : If the player successfully picked-up gold or not.
     */
    private String pickup() 
    {
    	String state = "";
    	char currentSymbol = player.getSymbol();
    	
    	if (currentSymbol == 'G')//If the player is on top of the symbol G
    	{
    		player.setGold(player.getGold()+1);//Increments the player's gold amount
    		player.setSymbol('.');//Replaces what the player is on top of on the map
    		state = "SUCCESS";
    	}
    	else//Player is not on top of a gold symbol
    	{
    		state = "FAIL";
    	}
        return state;
    }
    
    /**
     * Look method calculates the 5x5 grid around the player
     * If player's turn, prints 5x5 grid
     * If bot's turn, searches for player
     */
    private void look()
	{
    	int x = 0;
    	int y = 0;
    	char smallMap[][] = map.getMap();
    	
    	//Gets the player's x and y
    	if (playerTurn == true)
    	{
	    	x = player.getX();
	    	y = player.getY();
    	}
    	else
    	{
    		bot.setState("moveRan");//Set state for bot to random move
    		x = bot.getX();
	    	y = bot.getY();
    	}
    	
    	//Calculates the 5x5 grid around the player within the confines of the map
    	int leftX = x-2;
    	int rightX = x+3;
    	int upY = y-2;
    	int downY = y+3;
    	
    	if (leftX < 0)
    	{
    		leftX = 0;
    	}
		
    	if (rightX > map.getMapLength())
    	{
    		rightX = map.getMapLength();
    	}
    	
    	if (upY < 0)
    	{
    		upY = 0;
    	}
    	
    	if (downY > map.getMapHeight())
    	{
    		downY = map.getMapHeight();
    	}
    	
    	//Goes through each coordinate in the 5x5 grid
    	for (int i = upY; i < downY; i++)
		{
			for (int j = leftX; j < rightX; j++)
			{
				if (playerTurn == true)
		    	{
					System.out.print(smallMap[i][j]);//Prints the 5x5 grid for the player
		    	}
				else
				{
					if ((i == player.getY()) && (j == player.getX()))//If the HumanPlayer is within the bot's 5x5 grid
					{
						bot.setPy(i);//HumanPlayer's last know y
						bot.setPx(j);//HumanPlayer's last know x
						bot.setState("chase");//Bot chases after the player's last known x and y
						return;
					}
				}
			}
			if (playerTurn == true)
	    	{
				System.out.println("");
	    	}
		}
	}

    /**
     * Quits the game, shutting down the application.
     * If player is on the E symbol, decides if the player has enough gold to exit the dungeon
     * 
     * @throws : IOException
     */
    private void quitGame() throws IOException
    {
    	if (player.getSymbol() == 'E')//If the player is on an exit
    	{
    		if (player.getGold() >= map.getGoldRequired())//Checks if the player has enough Gold to exit
    		{
    			System.out.println("WIN");
    			System.out.println("Congradulations you have exited: " + map.getMapName());
    			System.exit(0);
    		}
    		else//Player does not have enough gold to exit
    		{
    			System.out.println("LOSE");
    			System.out.println("You had "+ player.getGold() + " Gold. You needed " + map.getGoldRequired() + " Gold.");
    			System.exit(0);
    		}
    	}
    	else
    	{
    		System.exit(0);
    	}
    }
}
