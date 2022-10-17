Protocol objectives: what does the protocol do? allow a client to ask a server to compute a calculation and to return the result

Overall behavior:

What transport protocol do we use? TCP

How does the client find the server (addresses and ports)? the port is known by the client and the server . For the 
adresse, we will use localhost to dont have to change the adresse a the beginning of every session.

Who speaks first? client

Who closes the connection and when? client when he doesn't have more calculation to do

Messages:

What is the syntax of the messages? a number , an operator and a number

What is the sequence of messages exchanged by the client and the server? (flow) a calculation by the client and an 
answer by the server.

What happens when a message is received from the other party? (semantics)

Specific elements (if useful)

Supported operations +,-,*,/

Error handling

Extensibility implement more operator and more than 3 number for every calculation

Examples: examples of some typical dialogs.

client: 2 + 3

server: 5

client: 45 * 3

server: 135