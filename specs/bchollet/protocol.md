Protocol objectives: what does the protocol do?
- It allows user to execute simple arithmetic operation on integers and get the result.

Overall behavior:<br>
What transport protocol do we use? <br>
- TCP

How does the client find the server (addresses and ports)?<br>
- People who want to use this application must share the server address to users since the server will be a basic laptop in a network with DHCP. The port will be set to 8008 and again should be shared to all users directly

Who speaks first? <br>
- The server will show a hello message then ask for user input.

Who closes the connection and when? <br>

Messages:<br>
What is the syntax of the messages?<br>
- User should sent one digit followed by an operation sign and conclued with another digit. All this infos should be sent in one input. i.e. "100+250"

What is the sequence of messages exchanged by the client and the server? (flow) <br>
1) Server welcomes new user
2) Server asks for two integers seperated by an operation on the same line.
3) Client sends command
   1) if command not correct => Server shows error message and asks for user input again
   2) if division by zero => Server responds with a specific message and asks for user input againe
4) Server responds with operation result
5) Server asks for user input and so on...

What happens when a message is received from the other party? (semantics)<br>
- Only an  error message is sent server-side if client sends an incorrect input.

Specific elements (if useful)<br>
Supported operations<br>
- +, -, *, /

Error handling<br>
- Parsing an incorrect input results in the server showing an error message
- float or double will be considered as parsing errors.
- Division by zero is prohibited and will be catched. Doing so will send a specific message to client

Extensibility<br>
- Application will be extensible enough to acquire new operations, as long as they are assign to a single character.

Examples directly from client-console output:<br> 
Hello user!<br>
Please enter two integers seperated by any operation sign (+, -, *, /)<br>
coucou<br>
Error: incorrect input ! <br>
Please enter two integers seperated by any operation sign (+, -, *, /)<br>
32/0<br>
Error: division by zero !<br>
Please enter two integers seperated by any operation sign (+, -, *, /)<br>
32+15<br>
32+15=47<br>
Please enter two integers seperated by any operation sign (+, -, *, /)<br>
...



