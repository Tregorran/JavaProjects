<---ChatServer--->
To change the port number:
java ChatServer -csp portnumber

 - Port number should be larger than 1023 for none root ports


To access the server GUI:
Make sure Xming is running
java ChatServer -gui

 - Can type in port number within the GUI


 
-----------------------------------------------------------



<---ChatClient--->
To change the port number:
java ChatClient -ccp portnumber


To change the IP Address:
java ChatClient -cca IPAddress


To access the Client GUI:
Make sure Xming is running
java ChatClient -gui


 - Arguments can be typed in any order

 - You can type in the Port number and IP address in the GUI

 - Each time you run ChatClient you will need to type in your name
   to be clearly identified in the chat room


To exit either the console or the GUI:
Type "EXIT" and send