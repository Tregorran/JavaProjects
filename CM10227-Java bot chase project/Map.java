import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Map 
{
	private char[][] map;//Representation of the map
	private String mapName;//Map name
	private int goldRequired;//Gold required for the human player to win
	private BufferedReader in;
	private BufferedReader fr;
	
	/**
	 * Default constructor, creates the default map "Default Labyrinth of Doom".
	 */
	public Map() 
	{
		mapName = "Default Labyrinth of Doom";
		goldRequired = 2;
		map = new char[][]{
		{'#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#'},
		{'#','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','#'},
		{'#','.','.','.','.','.','.','G','.','.','.','.','.','.','.','.','.','E','.','#'},
		{'#','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','#'},
		{'#','.','.','E','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','#'},
		{'#','.','.','.','.','.','.','.','.','.','.','.','G','.','.','.','.','.','.','#'},
		{'#','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','#'},
		{'#','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','#'},
		{'#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#'}
		};
	}
	
	/**
	 * Constructor that accepts a map to read in from.
	 *
	 * @param fileName : The filename of the map file.
	 * @throws IOException 
	 */
	public Map(String fileName) throws IOException 
	{
		readMap(fileName);//Reads in the file specified by the user
	}
	
	/**
	 * Reads in from the file acquiring the map name, the map gold required
	 * and reads in the map
	 *
	 * @param fileName : The filename of the map file.
	 * @throws IOException
	 */
	protected void readMap(String fileName) throws IOException 
	{
    	int lines = 0;//Counts number of lines of the map
    	int newLength = 0;//Compares newLength to length
    	int length = 0;//Counts length of the map
    	
    	in = new BufferedReader(new FileReader(fileName));
		String numLine = in.readLine();
		
		/* Calculates the number of lines and the length of the map in the file
		 * and gets the stores the mapName and stores the gold required
		 */
		while (numLine != null) 
		{
			lines++;
			if (numLine.contains("name"))
    		{ 
    			numLine = numLine.replace("name ", "");
    			mapName = numLine;
    		}
    		else if (numLine.contains("win"))
    		{ 
    			numLine = numLine.replace("win ", "");
    			goldRequired = Integer.parseInt(numLine);
    		}
		    
		    if (lines >= 3)//In the map section of the file
		    {
		    	newLength = numLine.length();
		    	if (newLength > length)//If there is a longer line of symbols
		    	{
		    		length = newLength;
		    	}
		    }
		    numLine = in.readLine();
		}
		lines -= 2;//Removes the name and win text from the number of map lines
    	
		map = new char [lines][length];//Creates size of the map array
		
		fr = new BufferedReader(new FileReader(fileName));
		
    	String line = fr.readLine();
    	
    	//Reads in all the map symbols
    	for (int j = -2; line != null; j++)
    	{
    		if (j >= 0)
    		{
	    		for (int i = 0; i < line.length(); i++)
	    		{
	    			map[j][i]= line.charAt(i);//Reads in each character of the map in the file into map array
	   			}
    		}
    		line = fr.readLine();
    	}
    	fr.close();
    }
	
	/**
     * @return mapName : The name of the current map.
     */
    protected String getMapName() {
        return mapName;
    }
	
    /**
     * @return goldRequired : Gold required to exit the current map.
     */
    protected int getGoldRequired() {
        return goldRequired;
    }
    
	/**
     * @return map.length : Returns height of the map
     */
	protected int getMapHeight()
	{
		return map.length;
	}
	
	/**
     * @return map[0].length : Returns length of the map
     */
	protected int getMapLength()
	{
		return map[0].length;
	}
	
	/**
	 * @param x : x coordinate on the map
	 * @param y : y coordinate on the map
     * @return symbol : returns the symbol from the x and y location on the map
     */
	protected char getPosSymbol(int x, int y)
	{
		char symbol = map[y][x];
		return symbol;
	}
    
	/**
	 * @param x : x coordinate on the map
	 * @param y : y coordinate on the map
	 * @param symbol : Symbol to update map
     */
	protected void updateMap(int x, int y, char symbol)
    {
    	map[y][x] = symbol;
    }

    /**
     * @return map : The map as stored in memory.
     */
    protected char[][] getMap() {
        return map;
    }
}
