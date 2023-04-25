import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientReceiveThread implements Runnable{
	
	private Socket socket;
	private ChatClient chatClient;
	private ClientGUIController clientGUIController;
	private ClientGUI clientGUI;
	
	private boolean useGui = false;
	private boolean running = false;
	
	public ClientReceiveThread(Socket socket, ChatClient chatClient, ClientGUIController clientGUIController) {
		this.socket = socket;
		this.chatClient = chatClient;
		this.clientGUIController = clientGUIController;
		this.useGui = chatClient.getUseGui();
		running = true;
	}
	
	public void run() {
		try {
			if(socket.isConnected()) {
				InputStreamReader r = new InputStreamReader(socket.getInputStream());
				BufferedReader receive = new BufferedReader(r);
				
				while(running) {
					String userInput = receive.readLine();//Receive message from server
					
					//Checks that the server is still running
					if (userInput == null) {
						//Client closes if connection to server was lost
						System.out.print("\r");
						System.out.println("Connection to server was lost...");
						chatClient.closeAllThreads();
					}
					else {
						//Prints messages from server
						if (useGui == true) {
							clientGUIController.receiveMessages(userInput);
						} 
						else {
							System.out.print("\r" + userInput);
							System.out.print("\nMe > ");
						}
					}
				}
				terminate();
			}
		} catch (UnknownHostException e) {
		} catch (IOException e) {
		}
	}
	
	//Closes the socket
	private void terminate() {
		try {
			socket.close();
		} catch(IOException e) {
		}
		System.exit(0);
	}
	
	public void closeThread() {
		running = false;
	}
}