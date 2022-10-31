#Protocol

#Overall behavior

We use the protocol TCP
The server listen to port 3003 and waits for client requests and the adress varies with the machine of the client
The client finds the server by making a request
The client closes the connection when he got all the answers he wanted

#Messages
The syntax of the message is the following :
	<operation> <x> <y>, x and y are numbers that are the operands
Once the operation is recieved by the server, he executes the operation and returns the following message :
0 <x>, x being the result of the operation

#Specific elements
The supported operations are : addition <add> and substitution <sub>
When an error occurs, the server returns -1 instead of 0
Concerning the extensibility, different operations can be added

#Example
Typical client server dialogue:
client : sub 2 3 // The client asks to substract 3 to 2
server : 0 -1    // The server execute the operation and answers
client : add 2   // The client makes a wrong request
server : -1      // The server answers with the error code -1