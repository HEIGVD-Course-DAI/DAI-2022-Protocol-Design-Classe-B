# My super protocol

## What the protocol do ?
1.It allows a client to send a request for an addition operation (at least)

2.It responds with the answer for the calculus
## Overall behavior:
### What transport protocol do we use?
Probably TPC as we want to guarantee the response sent to client
### How does the client find the server (addresses and ports)?
Either a broadcast from the server or it is known beforehand
### Who speaks first?
Client ask server
### Who closes the connection and when?
Client when no more enquiries.
## Messages
### What is the syntax of the messages?
operation;arg1;arg2
### What is the sequence of messages exchanged by the client and the server? (flow)
    -c. Hello to server
    -s. Hello client. I can do those operations : adition
    -c. I want to calculate this 
    -s. response
    -c. I want to calculate this
    -s. response
    -c. Bye
### What happens when a message is received from the other party? (semantics)
Check if valid. If yes, sent response. If not send error message.

## Specific elements (if useful)
### Supported operations
For now only addition
### Error handling
Send an error message
### Extensibility
Allow to add more operation. subtraction, multiplication and so on
## Example:
    -Hello
    -Hello, I can do "addtion"
    -addtion;1;3
    -result;4
    -substraction;1;3
    -error;"Unknown operation"
    -bye
    EOC