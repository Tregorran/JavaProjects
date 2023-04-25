Dungeons of Doom.

Introduction:
Dungeons of doom is a game made in Java that is played on a rectangular grid where the player is a brave 
fortune-hunter that has a set of commands to be used in order to collect enough gold to meet a win condition 
and then navigate to the exit and leave the dungeon to complete the map. You will be playing against a bot that 
will try and catch you. Both the player and the bot will start on a random location on the map. Each command 
you use will take up a turn, turning the turn over to the bot and vice versa.

How to install and run game:
1. Download the zip file CW2-am3292.zip
2. Unzip the file
3. Open linux.bath
4. Type in your login username and password
5. Use "ls" to view your current directory
6. Use "cd <name of file>" to navigate to the inside of the file "Project"
7. To compile the game use "javac GameLogic.java"
8. Then to run the game use "java GameLogic"

Instructions to play:
1. First you must choose a map to play, by entering its order number or the full name of the file, a list of 
   maps will be provided when starting the game.
2. Once the map is chosen the player and the bot will be placed randomly on the map chosen.
3. You the player, aims to pickup enough gold to exit the dungeon on an exit tile, this is how the player WINs.
4. Each map specifies a certain amount of gold that the player has to gather in order to exit.
5. If the player exits without the required amount of gold the player loses.
6. The bot's aim is to catch the player (land on the same tile as the player) resulting in a LOSE for the player.
7. The player has various COMMANDs as actions that the player can use, specified in the Commands section of this text file. 
8. Each command takes up a turn.

Types of tiles:
P - Player. Your character.
B - Bot. Computer-controlled player.
# - Wall. Blocks a player from moving through it.
. - Empty Floor. Allows a player to walk over it.
G - Gold. Can be picked up by the player.
E - Exit. Used by player to exit the dungeon when the player has enough GOLD to exit, using the QUIT command.

Commands:
HELLO - Displays the amount of gold required to win the map.
GOLD - Displays the amount of gold owned by the player.
MOVE <direction> - You can move one space in either 4 directions N, E, S, W. eg. "MOVE E" (Moves east 1 space).
PICKUP - Picks up gold on the player's current location.
LOOK - Displays a 5x5 grid of the map around the player.
QUIT - When on tile E and have enough gold to win, use command to win.

>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
Code implementation:
I have 6 classes.
GameLogic. The main method which processes the commands it is given from the HumanPlayer and BotPlayer objects.
Checks commands entered through if statements and executes the corresponding method.

HumanPlayer. Gets the user's input and processes it through if statements that matches up to the valid commands
and returns the input to GameLogic if it is a command otherwise returns "Invalid".

BotPlayer. How I implemented the bot was to give the bot different states which would act as what type of command it 
would return to GameLogic when it was the bot's turn, such as state "look" which will use the look command and try 
and look for the Humanplayer in the 5x5 grid. If it doesn't bot's state would be "moveRan" to move in a random 
direction returning "MOVE <random direction>", if it does find the Humanplayer in the 5x5 grid it will take the 
Humanplayer's last known x and y from the look and state will be changed to "chase" which will use command 
"MOVE <direction towards player>" each of its turns until it reaches the Humanplayer's last known x and y then 
goes back to state "look" and starts again looking for the player and moving in a random direction.

Player. Parent class of HumanPlayer and BotPlayer removing repeated methods and fields that are in both classes.

Map. Used to read in the default map or map chosen by the user, including reading the name and gold required to 
win of the map.

LevelSelect. Used to get the user to choose a map when the game starts, and also checks the format of the map.
Such as checking there is enough gold symbols on the map to meet the gold win condition.