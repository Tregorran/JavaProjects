import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientSendThread implements Runnable{
	
	private Socket socket;
	private ChatClient chatClient;
	private PrintWriter print;
	private BufferedReader userIn;
	
	private String clientName;
	private boolean useGui = false;
	private boolean running = false;
	
	public ClientSendThread(Socket socket, ChatClient chatClient) {
		this.socket = socket;
		this.chatClient = chatClient;
		this.clientName = chatClient.getClientName();
		this.useGui = chatClient.getUseGui();
		running = true;
	}
	
	public void run() {
		try {
			if(socket.isConnected()) {
				print = new PrintWriter(socket.getOutputStream(), true);
				userIn = new BufferedReader(new InputStreamReader(System.in));
				
				//Sends the username to the server
				print.println(clientName);
				print.flush();
				
				if (useGui == false) {
					while(running) {
						System.out.print("Me > ");
							
						String userInput = userIn.readLine();//Gets the user's input
						print.println(userInput);
						print.flush();
						
						checkExit(userInput);
					}
					System.exit(0);
				}
			}
		}catch (UnknownHostException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//Sends the gui message to the server
	public void guiMessage(String Message){
		print.println(Message);
		print.flush();
		checkExit(Message);//Checks if the client has typed in "EXIT"
	}
	
	//When the user enters EXIT all threads are closed
	public void checkExit(String userInput) {
		if(userInput.equals("EXIT")) {
			System.out.println("Closing service...");
			chatClient.closeAllThreads();
			System.exit(0);
		}
	}
	
	public void closeThread() {
		running = false;
	}
}

