import java.util.Random;

public class Player 
{
	protected int x;//Player's x location on the map
	protected int y;//Player's y location on the map
	protected char currentSymbol;//The symbol the player is on top of on the map
	protected int newX;//Player's next x location on the map
	protected int newY;//Player's next y location on the map
	
	/**
	 * Default constructor, sets current symbol.
	 */
	public Player()
	{
		currentSymbol = '.';
	}
	
	/**
     * Places the player on a random position on the map
     * By giving the player random x and y
     *
     * @param map : Map object
     * @param playerSymbol : Symbol of the current player
     */
	protected void randomPlayerPlace(Map map, char playerSymbol)
	{
    	int mapLength = map.getMapLength()-1;
		int mapHeight = map.getMapHeight()-1;
		
		x = randomPosX(mapLength);//Random x position constrained by mapLength
		y = randomPosY(mapHeight);//Random y position constrained by mapHeight
		
		char symbol = map.getPosSymbol(x, y);//Get map symbol from the random x and y
		
		//Checks if the player can be placed on the random x and y
		if ((symbol == '.') || (symbol == 'E'))
		{
			setSymbol(symbol);
			map.updateMap(x, y, playerSymbol);
		}
		else
		{
			randomPlayerPlace(map, playerSymbol);
		}
	}
    
	/**
     * Calculates a random number for x within the constraints of mapLength
     *
     * @param mapLength : The number of characters in a width of the map
     * @return : A random x value
     */
    protected int randomPosX(int mapLength)
    {
    	int max = mapLength;
    	int min = 0;
    	
    	Random r = new Random();
    	int randomNumberX = r.nextInt((max - min) + 1) + min;
    	
		x = randomNumberX;
		return randomNumberX;
    }
    
    /**
     * Calculates a random number for y within the constraints of mapHeight
     *
     * @param mapHeight : The number of characters in a Height of the map
     * @return : A random y value
     */
    protected int randomPosY(int mapHeight)
    {
    	int max = mapHeight;
    	int min = 0;
    	
    	Random r = new Random();
    	int randomNumberY = r.nextInt((max - min) + 1) + min;
    	
		y = randomNumberY;
		return randomNumberY;
    }
    
    /**
     * @return newX : The next x position of the player
     */
    protected int getNewX()
    {
    	return newX;
    }
    
    /**
     * @return newY : The next y position of the player
     */
    protected int getNewY()
    {
    	return newY;
    }
    
    /**
     * @return currentSymbol : returns the symbol that the player is on top of, on the map
     */
    protected char getSymbol()
    {
    	return currentSymbol;
    }
    
    /**
     * @return x : Returns current x coordinate
     */
    protected int getX()
    {
    	return x;
    }
    
    /**
     * @return y : Returns current y coordinate
     */
    protected int getY()
    {
    	return y;
    }
    
    /**
     * @param newX : Sets the next x position of the player
     */
    protected void setNewX(int newX)
    {
    	this.newX = newX;
    }
    
    /**
     * @param newY : Sets the next y position of the player
     */
    protected void setNewY(int newY)
    {
    	this.newY = newY;
    }
    
    /**
     * @param currentSymbol : Sets the new symbol that the player is on top of, on the map
     */
    protected void setSymbol(char currentSymbol)
    {
    	this.currentSymbol = currentSymbol;
    }
    
    /**
     * @param x : Sets new x coordinate
     */
    protected void setX(int x)
    {
    	this.x = x;
    }
    
    /**
     * @param y : Sets new y coordinate
     */
    protected void setY(int y)
    {
    	this.y = y;
    }
}
