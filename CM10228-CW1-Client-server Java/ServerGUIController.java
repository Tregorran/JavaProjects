public class ServerGUIController implements Runnable{
	
	private ServerGUI serverGUI;
	private ChatServer chatServer;
	
	private String userMsg;
	private int numOfClients = 0;
	private int maxClients = 10;
	private String portNum;
	private boolean gotPortNum = false;
	private boolean running = false;
	
	public ServerGUIController(ServerGUI serverGUI, ChatServer chatServer){
		this.serverGUI = serverGUI;
		this.chatServer = chatServer;
		running = true;
	}
	
	public void run() {		
		while(running) {
			userMsg = serverGUI.getUserMessage();//Gets server's user's message
			
			if (userMsg != null) {
				serverGUI.setUserMessage(null);
				
				//If the user typed in EXIT shut down server
				if (userMsg.equals("EXIT")) {
					chatServer.sendToAllMessage("Server", 0, "Server Shutting down... good bye.");
					receiveMessages("Server shutting down...");
					chatServer.stop();
				} 
				else {
					//Sends the message normally
					chatServer.sendToAllMessage("Server", 0, userMsg);
					receiveMessages("Me > " + userMsg);
				}
			}
			serverGUI.updateClientNum(numOfClients, maxClients);//Updates the number of clients connected to the server
		}
	}
	
	//Gets the port number that they typed into the GUI
	public void checkPort() {
		while(true) {
			if (gotPortNum == false) {
				portNum = serverGUI.getPortNum();
				
				if (portNum != null) {
					gotPortNum = true;
					
					try {
						chatServer.createGuiPortNum(Integer.parseInt(portNum));//Creates the server socket
					} catch(Exception e) {
						receiveMessages("Error with given port, using default port");
						chatServer.createGuiPortNum(14001);
					}
					return;
				}
			}
		}
	}
	
	//Receive message to be displayed in the chatroom
	public void receiveMessages(String msg) {
		serverGUI.chatRoomMsg(msg);
	}
	
		//Recieve connection or disconnect messages
	public void receiveConDis(String disConMessage) {
		serverGUI.updateHistory(disConMessage);
	}
	
	public void closeThread() {
		running = false;
	}
	
	public void updateNumClients(int num) {
		numOfClients = num;
	}
	
	public void updateMaxClients(int maxNum) {
		maxClients = maxNum;
	}
}
