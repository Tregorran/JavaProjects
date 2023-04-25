import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.net.SocketException;

public class ServerListener implements Runnable {

    private ChatServer server;
    private ServerSocket serverSocket;
	private ServerGUIController serverGUIController;
	
	private int clientNum = 0;//unique number for each client that connects
	private int numOfClients = 0;//Number of clients connected to the server
	private int maxClients = 10;//max number of clients that can connect to the server
	private boolean useGui = false;

    public ServerListener(ChatServer server, ServerGUIController serverGUIController) {
        this.server = server;
		this.serverGUIController = serverGUIController;
		this.serverSocket = server.getSocket();
		this.useGui = server.getUseGui();
    }

    public void run() {
		try {
			//Tells the user that the server is running
			if (useGui == true) {
				serverGUIController.receiveMessages("<---- Server Running ---->");
				serverGUIController.receiveMessages("Server listening...");
				serverGUIController.updateMaxClients(maxClients);
			} 
			else {
				System.out.println("<---- Server Running ---->");
				System.out.println("Server listening...");
			}
			
			while(true) {
				Socket clientSocket = serverSocket.accept();
				
				clientNum += 1;//Gives each client a unique number so that the message sent is not sent to the same client that sent it
				numOfClients += 1;//Number of clients connected to the server
								
				//Limits the number of clients connected to the server
				if (numOfClients > maxClients) {
					clientLimitReached(clientSocket);
				}
				else {
					System.out.println("Server accepted connection on " + serverSocket.getLocalPort() + " ; " + clientSocket.getPort());
					
					//Creates a send thread for each client
					ServerSendThread send = new ServerSendThread(clientSocket, clientNum);
					Thread sendThread = new Thread(send);
					sendThread.start();
					server.addSendList(send);//Adds to arrayList each send object created
					
					//Creates a receive thread for each client
					ServerReceiveThread receive = new ServerReceiveThread(clientSocket, server, clientNum, this, send, serverGUIController);
					Thread receiveThread = new Thread(receive);
					receiveThread.start();
					server.addReceiveList(receive);//Adds to arrayList each receive object created
					
					server.addClientSocket(clientSocket);//Adds to arrayList each connection between the server and the client
				}
				
				//To display the number of users that are connected to the server
				if (useGui == true) {
					serverGUIController.updateNumClients(numOfClients);
				} 
				else {
					printNumClients();
				}
			}
		} catch(SocketException e) {
			
		} catch(IOException e) {
			e.printStackTrace();
		} 
    }
	
	//Closes the client's connection when the server has reached the max number of clients connected
	public void clientLimitReached(Socket clientSocket) throws IOException {
		//Sends message to the client that is trying to connect
		String limitMsg = "Server > Server has already reached max limit of users.";
		PrintWriter clientOut = new PrintWriter(clientSocket.getOutputStream(), true);
		clientOut.println(limitMsg);
		clientOut.flush();
		
		decreaseNumOfClients();
		
		//Refuse message to the server
		if (useGui == true){
			serverGUIController.receiveConDis("Client: "+ clientSocket.getPort() + "; refused, limit reached.");
		} 
		else {
			System.out.println("Client: "+ clientSocket.getPort() + "; refused, limit reached.");
		}
		clientSocket.close();
	}
	
	//Reduces the number of clients currently connected to the server
	public void decreaseNumOfClients() {
		numOfClients -= 1;
	}
	
	//Prints the number of clients currently connected to the server
	public void printNumClients() {
		System.out.println("Number of users: " + numOfClients + "/" + maxClients);
	}
	
	public int getNumOfClients(){
		return numOfClients;
	}
	
	public int getMaxClients(){
		return maxClients;
	}
}