import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.net.ConnectException;

public class ChatClient {
	
	private Socket server;
	private ClientSendThread sendThread;
	private ClientReceiveThread receiveThread;
	private ClientGUIController clientGUIController;
	
	private String clientName;
	private static boolean useGui = false;
	
	public static void main(String[] args) {
		
		String IPAddress = "localhost";//Default IPAddress
		int portNum = 14001;//Default port Number

		if(args.length != 0) {
			try {
				//Checks for the two arguments -cca, -ccp and their parameters or if they want to use the GUI
				for(int i = 0; i < args.length; i++) {
					if (args[i].equals("-cca")) {
						IPAddress = args[i+1];
					}
					else if (args[i].equals("-ccp")) {
						portNum = Integer.parseInt(args[i+1]);
					}
					else if (args[i].equals("-gui")) {
						useGui = true;
					}
				}
			} catch(Exception e) {
				System.out.println("An argument was invalid, using default port and IP Address");
				IPAddress = "localhost";
				portNum = 14001;
			}
		}
		new ChatClient(IPAddress, portNum);
	}

	public ChatClient(String address, int port) {
		//Get the client's name from the console
		try {
			System.out.println("Please type in your name:");
			InputStreamReader isr = new InputStreamReader(System.in);
			BufferedReader br = new BufferedReader(isr);
			clientName = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if (useGui == true) {
			ClientGUI clientGUI = new ClientGUI();
			
			clientGUIController = new ClientGUIController(clientGUI, this);
			clientGUIController.IpPortCheck();//Getting the IP Address and Port from the GUI
		} 
		else {
			try {
				server = new Socket(address, port);
			} catch (UnknownHostException e) {
				System.out.println("Failed to connect to host.");
				System.exit(0);
			} catch (IOException e) {
				System.out.println("Failed to connect to host.");
				System.exit(0);
			}
		}
		start();
	}
	
	//Method for creating the send and receive threads for the new client including the GUI if they wanted
	public void start() {
		if (useGui == true) {
			Thread clientGUIThread = new Thread(clientGUIController);
			clientGUIThread.start();
		}
		
		System.out.println("You have connected to " + server.getInetAddress() + " on port " + server.getPort());
		
		sendThread = new ClientSendThread(server, this);
		Thread t1 = new Thread(sendThread);
		t1.start();
			
		receiveThread = new ClientReceiveThread(server, this, clientGUIController);
		Thread t2 = new Thread(receiveThread);
		t2.start();
	}
	
	//Create the socket from the GUI's input
	public void createGuiIpPortNum(String address, int port){
		try{
			server = new Socket(address,port);
		} catch (UnknownHostException e) {
			System.out.println("Failed to connect to host.");
			System.exit(0);
		} catch (IOException e) {
			System.out.println("Failed to connect to host.");
			System.exit(0);
		}
	}
	
	//Closes the send and receive thread for this client
	public void closeAllThreads() {
		sendThread.closeThread();
		receiveThread.closeThread();
		
		if (useGui == true) {
			clientGUIController.closeThread();
		}
	}
	
	public void sendGuiMessage(String msg){
		sendThread.guiMessage(msg);
	}
	
	public boolean getUseGui(){
		return useGui;
	}
	
	public String getClientName(){
		return clientName;
	}
}