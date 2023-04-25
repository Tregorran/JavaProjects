import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.net.SocketException;

public class ChatServer {
	
	private ServerSocket serverSocket;
	private ServerListener serverListener;
	private ServerGUIController serverGUIController;
	private Thread listenerThread;
	
	private static boolean useGui = false;//If the user wants to use a Gui
	private ArrayList<ServerSendThread> sendList = new ArrayList<ServerSendThread>();//Stores each ServerSendThread object created
	private ArrayList<ServerReceiveThread> receiveList = new ArrayList<ServerReceiveThread>();//Stores each ServerReceiveThread object created
	private ArrayList<Socket> clientSocketList = new ArrayList<Socket>();//Stores the connection between the server and each client
	
	public static void main(String[] args) {
		int portNum = 14001;//Default port number
		
		if (args.length != 0) {
			try {
				/*Checks for csp argument to change the port number, checks that the port number is larger than 1023 for none root ports
				and checks for the gui argument if they want to use a gui*/
				for(int i = 0; i < args.length; i++) {
					if (args[i].equals("-csp") && Integer.parseInt(args[i+1]) > 1023) {
						portNum = Integer.parseInt(args[i+1]);
					}
					else if (args[i].equals("-gui")) {
						useGui = true;
					}
					else if (args[i].equals("-csp") && Integer.parseInt(args[i+1]) < 1024) {
						System.out.println("Port number is smaller than 1024, using default port number.");
					}
				}
			} catch(Exception e) {
				System.out.println("An argument was invalid, using default port");
				portNum = 14001;
			}
		}
		new ChatServer(portNum);
	}

	//Creates the serverSocket
	public ChatServer(int port) {
		if (useGui == true) {
			ServerGUI serverGUI = new ServerGUI();
				
			serverGUIController = new ServerGUIController(serverGUI, this);
			serverGUIController.checkPort();//Getting the port number from the GUI
		} 
		else {
			try {
				serverSocket = new ServerSocket(port);
				System.out.println("Created server with port number: " + port);
			} catch (IOException e) {
				e.printStackTrace();
			}
			start();
		}
	}

	public void start() {
		if (useGui == true) {
			Thread serverGuiThread = new Thread(serverGUIController);
			serverGuiThread.start();
		}
		
		//Creates the ServerListener to receive client connections
		serverListener = new ServerListener(this, serverGUIController);
		listenerThread = new Thread(serverListener);
		listenerThread.start();
			
		try {
			BufferedReader serverInput = new BufferedReader(new InputStreamReader(System.in));
			String input;
			
			while(true) {
				//Checks if the server has typed in anything
				if ((input = serverInput.readLine()) != null) {
					
					//If the user has typed "EXIT" runs the stop method to close the server
					if (input.equals("EXIT")) {
						sendToAllMessage("Server", 0, "Server Shutting down... good bye.");
						System.out.println("Server shutting down...");
						stop();
					}
					//Sends the input message to every client connected
					else {
						if (useGui == false) {
							sendToAllMessage("Server", 0, input);
						}
					}
				}
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void createGuiPortNum(int port){
		try {
			if (port > 1023) {
				serverSocket = new ServerSocket(port);
				System.out.println("Created server with port number: " + port);
				serverGUIController.receiveMessages("Created server with port number: " + port);
				start();
			} 
			else {
				port = 14001;
				System.out.println("Invalid port number, using default port number.");
				serverGUIController.receiveMessages("Invalid port number, using default port number.");
					
				serverSocket = new ServerSocket(port);
					
				System.out.println("Created server with port number: " + port);
				serverGUIController.receiveMessages("Created server with port number: " + port);
				start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*Method sends a message to each client the server is connected to.
	serviceType - Server or client name
	clientNum - So that the message isn't sent to the same client that sent the message*/
	public synchronized void sendToAllMessage(String serviceType, int clientNum, String Message){
		Message = (serviceType + " > " + Message);
		
		for(ServerSendThread send : sendList) {
			send.sendMessage(Message, clientNum);
		}
	}
	
	//The stop method closes all threads and sockets and exits
	public void stop() {
		try {
			//Closes each Server Receive thread
			for(ServerReceiveThread receive : receiveList) {
				receive.closeThread();
			}
			
			//Closes each Server send thread
			for(ServerSendThread send : sendList) {
				send.closeThread();
			}
			
			listenerThread.interrupt();
			
			//Closes the connection to each client socket
			for(Socket c : clientSocketList) {
				c.close();
			}
			
			serverSocket.close();
			System.exit(0);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean getUseGui(){
		return useGui;
	}

	public ServerSocket getSocket() {
		return serverSocket;
	}
	
	public void addSendList(ServerSendThread send) {
		sendList.add(send);
	}
	
	public void addReceiveList(ServerReceiveThread receive) {
		receiveList.add(receive);
	}
	
	public void addClientSocket(Socket clientSocket) {
		clientSocketList.add(clientSocket);
	}
	
	public void removeSendList(ServerSendThread send) {
		sendList.remove(send);
	}
	
	public void removeReceiveList(ServerReceiveThread receive) {
		receiveList.remove(receive);
	}
	
	public void removeClientSocket(Socket clientSocket) {
		clientSocketList.remove(clientSocket);
	}	
}
