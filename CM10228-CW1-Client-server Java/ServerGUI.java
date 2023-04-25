import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.text.DefaultCaret;

public class ServerGUI{
	private JTextArea chatRoom;
	private JTextField enterField;
	private JTextField numClientsField;
	private JTextArea historyArea;
	
	private String userMsg;
	private String portNum;
	
	public ServerGUI(){
		draw();
	}
	
	public void draw() {
		JFrame frame = new JFrame("Server chat service");

		frame.setSize(925,500);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTHWEST;
		
		Color backgroundColour = new Color(242,242,242);
		panel.setBackground(backgroundColour);
		
		//The ChatRoom where all the messages are displayed
		chatRoom = new JTextArea(20,40);
		chatRoom.setFont(new Font("Arial", Font.PLAIN, 15));
		chatRoom.setForeground(Color.BLACK);
		chatRoom.setEditable(false);
		JScrollPane chatScroll = new JScrollPane(chatRoom);
		chatScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		DefaultCaret chatCaret = (DefaultCaret) chatRoom.getCaret();
        chatCaret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		//Displays the number of clients connected to the server
		numClientsField = new JTextField();
		numClientsField.setColumns(3);
		numClientsField.setFont(new Font("Arial", Font.PLAIN, 15));
		numClientsField.setForeground(Color.BLACK);
		numClientsField.setEditable(false);
		
		//Displays each client that connects and disconnects from the server
		historyArea = new JTextArea(20,19);
		historyArea.setFont(new Font("Arial", Font.PLAIN, 15));
		historyArea.setForeground(Color.BLACK);
		historyArea.setEditable(false);
		JScrollPane historyScroll = new JScrollPane(historyArea);
		historyScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		DefaultCaret historyCaret = (DefaultCaret) historyArea.getCaret();
        historyCaret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		historyArea.setText("	History");
		
		//Where the server user can type a message or type EXIT
		enterField = new JTextField();
		enterField.setColumns(30);
		enterField.setFont(new Font("Arial", Font.PLAIN, 15));
		enterField.setForeground(Color.BLACK);
		
		//To send off the message the user has typed in
		JButton sendButton = new JButton("Send");
		sendButton.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				userMsg = enterField.getText();
				enterField.setText("");
			} 
		});
		
		//Where the user can type in the port
		JTextField portField = new JTextField();
		portField.setColumns(8);
		portField.setFont(new Font("Arial", Font.PLAIN, 15));
		portField.setForeground(Color.BLACK);
		
		//To create the server with the port that they typed in
		JButton portButton = new JButton("Port");
		portButton.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				portNum = portField.getText();
				portField.setEditable(false);
			} 
		});
		
		c.insets = new Insets(10,0,0,0);
		c.weighty = 1;
		c.gridx = 0;
		c.gridy = 1;
		panel.add(chatScroll, c);
		
		c.gridx = 1;
		c.gridy = 1;
		panel.add(numClientsField, c);
		
		c.gridx = 1;
		c.gridy = 1;
		panel.add(historyScroll, c);
		
		c.gridx = 0;
		c.gridy = 2;
		panel.add(enterField, c);
		
		c.insets = new Insets(10,-100,0,0);
		c.gridx = 1;
		c.gridy = 2;
		panel.add(sendButton, c);
		
		c.insets = new Insets(10,-250,0,0);
		c.gridx = 2;
		c.gridy = 2;
		panel.add(portField, c);
		
		c.insets = new Insets(10,-100,0,0);
		c.gridx = 3;
		c.gridy = 2;
		panel.add(portButton, c);
		
		frame.add(panel);
		frame.setVisible(true);
	}
	
	//Messages to be displayed in the chatroom
	public void chatRoomMsg(String message) {
		chatRoom.append(message + "\n");
	}
	
	//Updates the number of clients connected to the server to be displayed
	public void updateClientNum(int numOfClients, int maxClients){
		numClientsField.setText(numOfClients + "/" + maxClients);
	}
	
	//Updates what client has connected/disconnected
	public void updateHistory(String disconnectMessage){
		historyArea.append("\n" + disconnectMessage);
	}
	
	public String getUserMessage(){
		return userMsg;
	}
	
	public void setUserMessage(String msg){
		userMsg = null;
	}
	
	public String getPortNum(){
		return portNum;
	}
	
	public void setPortNum(String num){
		portNum = num;
	}
}
