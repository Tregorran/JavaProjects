import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.net.SocketException;

public class ServerSendThread implements Runnable {
	
	private Socket clientSocket;
	private PrintWriter clientOut;
	
	private int clientNum;
	private String message;
	private String clientName;
	private boolean running = false;
	
	public ServerSendThread(Socket clientSocket, int clientNum) {
		this.clientSocket = clientSocket;
		this.clientNum = clientNum;
		running = true;
	}
	
	public void run() {
		try {
			clientOut = new PrintWriter(clientSocket.getOutputStream(), true);
			
			//Sends any message to the client that it receives
			while(running) {
				clientOut.flush();
				
				if (message!=null) {
					clientOut.println(message);
					message = null;
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//Gets a message to send to the client
	public void sendMessage(String message, int clientNum) {
		//Checks if clientNum is different so that the message won't be sent to the client that sent it
		if (this.clientNum != clientNum) {
			if (message!=null) {
				this.message = message;//Sets the new message
			}
		}
	}
	
	public void closeThread() {
		clientOut.flush();
		running = false;
	}
	
	public void setClientName(String name) {
		clientName = name;
		clientOut.println("<---- Welcome to the server " + clientName + " ---->");
	}
}