import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.net.SocketException;


public class ServerReceiveThread implements Runnable {
	
	private Socket clientSocket;
	private ServerSendThread send;
	private ChatServer server;
	private ServerListener serverListener;
	private ServerGUIController serverGUIController;
	
	private String clientInput;//Gets the Client's messgge
	private int clientNum;//Clients unique number
	private String clientName;
	private boolean getClientName = false;
	private boolean useGui = false;
	private boolean running = false;
	
	public ServerReceiveThread(Socket clientSocket, ChatServer server, int clientNum, ServerListener serverListener, ServerSendThread send, ServerGUIController serverGUIController) {
		this.clientSocket = clientSocket;
		this.server = server;
		this.clientNum = clientNum;
		this.serverListener = serverListener;
		this.send = send;
		this.serverGUIController = serverGUIController;
		this.useGui = server.getUseGui();
		running = true;
	}
	
	public void run() {
		try {
			InputStreamReader r = new InputStreamReader(clientSocket.getInputStream());
			BufferedReader clientIn = new BufferedReader(r);
			
			while(running) {
				try {
					clientInput = clientIn.readLine();//Reads the client message from the stream
				} catch (SocketException se) {
					return;
				}
				
				//Gets the client's username
				if (getClientName == false) {
					clientName = clientInput;
					send.setClientName(clientName);
					getClientName = true;
					
					//Displays to the clients and server that a new client has connected to the server
					if (useGui == true){
						serverGUIController.receiveConDis(clientName + " has connected. - " + clientSocket.getPort());
					} 
					else {
						System.out.println(clientName + " has connected to the server.");
					}
					
					server.sendToAllMessage("Server", 0, clientName + " has connected.");
				}
				else {
					//After the server has the name of the new client that has connected
					if (clientInput != null) {
						if(clientInput.equals("EXIT")) {
							closeThread();
						}
						else {
							if (useGui == true) {
								serverGUIController.receiveMessages(clientName + " > " + clientInput);//Displays the messages in the chatRoom
							}
							else {
								System.out.println(clientName + " > " + clientInput);
							}
							
							server.sendToAllMessage(clientName, clientNum, clientInput);//Sends the received message to every client
						}
					}
				}
			}
			
			//When the client has disconnected from the server
			server.sendToAllMessage("Server", 0, clientName + " has disconnected. - "  + clientSocket.getPort());
			serverListener.decreaseNumOfClients();
			
			//Displays on the server the disconnected client
			if (useGui == true) {
				serverGUIController.receiveConDis(clientName + " has disconnected. - " + clientSocket.getPort());
				serverGUIController.updateNumClients(serverListener.getNumOfClients());//updates the number of clients connected to the server
			}
			else {
				System.out.println(clientName + " has disconnected.");
				serverListener.printNumClients();
			}
			
			//Removing and closing everything on the server to do with the "EXITING" client
			send.closeThread();
			server.removeReceiveList(this);
			server.removeSendList(send);
			server.removeClientSocket(clientSocket);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void closeThread() {
		running = false;
	}
}