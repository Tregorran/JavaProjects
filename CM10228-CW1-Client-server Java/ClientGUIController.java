public class ClientGUIController implements Runnable{
	
	private ClientGUI clientGUI;
	private ChatClient chatClient;
	
	private String userMsg;
	private String portNum;
	private String IpStr;
	private boolean gotIpPortNum = false;
	private boolean running = false;
	
	public ClientGUIController(ClientGUI clientGUI, ChatClient chatClient) {
		this.clientGUI = clientGUI;
		this.chatClient = chatClient;
		running = true;
	}
	
	public void run() {
		while(running) {
			userMsg = clientGUI.getUserMessage();//Gets server user's message
			
			//Sends the client's message
			if (userMsg != null) {
				clientGUI.setUserMessage(null);
				chatClient.sendGuiMessage(userMsg);
				receiveMessages("Me > " + userMsg);
			}
		}
	}
	
	//Gets the IP address and the porn number that the user has types in
	public void IpPortCheck() {
		while(true) {
			if (gotIpPortNum == false) {
				IpStr = clientGUI.getIpStr();
				portNum = clientGUI.getPortNum();
				
				if (portNum != null) {
					gotIpPortNum = true;
					
					try {
						chatClient.createGuiIpPortNum(IpStr, Integer.parseInt(portNum));//Creates the socket
					} catch(Exception e) {
						receiveMessages("Error with given port or given IP address, using default port and default IP address");
						chatClient.createGuiIpPortNum("localhost", 14001);
					}
					return;
				}
			}
		}
	}
	
	//Receive message to be displayed in the chatroom
	public void receiveMessages(String msg) {
		clientGUI.chatRoomMsg(msg);
	}
	
	public void closeThread() {
		running = false;
	}
}
