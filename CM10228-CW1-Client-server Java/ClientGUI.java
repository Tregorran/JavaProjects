import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.text.DefaultCaret;

public class ClientGUI{
	private JTextArea chatRoom;
	private JTextField enterField;
	
	private String userMsg;
	private String portNum;
	private String ipStr;
	
	public ClientGUI(){
		draw();
	}
	
	public void draw() {
		JFrame frame = new JFrame("Client chat service");

		frame.setSize(700,350);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTHWEST;
		
		Color backgroundColour = new Color(242,242,242);
		panel.setBackground(backgroundColour);
		
		//The ChatRoom where all the messages are displayed
		chatRoom = new JTextArea(12,40);
		chatRoom.setFont(new Font("Arial", Font.PLAIN, 15));
		chatRoom.setForeground(Color.BLACK);
		chatRoom.setEditable(false);
		JScrollPane chatScroll = new JScrollPane(chatRoom);
		chatScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		DefaultCaret chatCaret = (DefaultCaret) chatRoom.getCaret();
        chatCaret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		//Where the IP address is typed in
		JTextField ipField = new JTextField("localhost");
		ipField.setColumns(10);
		ipField.setFont(new Font("Arial", Font.PLAIN, 15));
		ipField.setForeground(Color.BLACK);
		
		JLabel ipLabel = new JLabel("IP address");
		ipLabel.setFont(new Font("Arial", Font.PLAIN, 15));
		ipLabel.setForeground(Color.BLACK);
		
		//Where the Port number is typed in
		JTextField portField = new JTextField();
		portField.setColumns(10);
		portField.setFont(new Font("Arial", Font.PLAIN, 15));
		portField.setForeground(Color.BLACK);
		
		JLabel portLabel = new JLabel("Port number");
		portLabel.setFont(new Font("Arial", Font.PLAIN, 15));
		portLabel.setForeground(Color.BLACK);
		
		//Button to connect to the IP Address and port number specified
		JButton connectButton = new JButton("Connect");
		connectButton.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) {
				ipStr = ipField.getText();
				portNum = portField.getText();
				ipField.setEditable(false);
				portField.setEditable(false);
			} 
		});
		
		//Where the client user can type a message or type EXIT
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
		
		c.insets = new Insets(10,0,0,0);
		c.weighty = 1;
		c.gridx = 0;
		c.gridy = 1;
		panel.add(chatScroll, c);
		
		c.gridx = 0;
		c.gridy = 3;
		panel.add(ipField, c);
		
		c.gridx = 0;
		c.gridy = 4;
		panel.add(portField, c);
		
		c.gridx = 1;
		c.gridy = 4;
		panel.add(connectButton, c);
		
		c.gridx = 0;
		c.gridy = 5;
		panel.add(enterField, c);
		
		c.insets = new Insets(10,-100,0,0);
		c.gridx = 1;
		c.gridy = 5;
		panel.add(sendButton, c);
		
		c.insets = new Insets(10,-450,0,0);
		
		c.gridx = 1;
		c.gridy = 3;
		panel.add(ipLabel, c);
		
		c.gridx = 1;
		c.gridy = 4;
		panel.add(portLabel, c);
		
		c.insets = new Insets(10,-350,0,0);
		c.gridx = 1;
		c.gridy = 4;
		panel.add(connectButton, c);
		
		frame.add(panel);
		frame.setVisible(true);
	}
	
	//Messages to be displayed in the chatroom
	public void chatRoomMsg(String message) {
		chatRoom.append(message + "\n");
	}
	
	public String getUserMessage(){
		return userMsg;
	}
	
	public void setUserMessage(String msg){
		userMsg = msg;
	}
	
	public String getIpStr(){
		return ipStr;
	}
	
	public String getPortNum(){
		return portNum;
	}
}
