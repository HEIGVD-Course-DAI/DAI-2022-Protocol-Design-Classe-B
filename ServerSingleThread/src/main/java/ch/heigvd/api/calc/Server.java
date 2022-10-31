package ch.heigvd.api.calc;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Calculator server implementation - single threaded
 */
public class Server {

    private final static Logger LOG = Logger.getLogger(Server.class.getName());

    /**
     * Main function to start the server
     */
    public static void main(String[] args) {
        // Log output on a single line
        System.setProperty("java.util.logging.SimpleFormatter.format", "%4$s: %5$s%6$s%n");

        (new Server()).start();
    }

    /**
     * Start the server on a listening socket.
     */
    private void start() {
        /* TODO: implement the receptionist server here.
         *  The receptionist just creates a server socket and accepts new client connections.
         *  For a new client connection, the actual work is done by the handleClient method below.
         */


        Socket clientSocket = null;

        try{
            ServerSocket serverSocket = new ServerSocket(9999);

            while(true){
                clientSocket = serverSocket.accept();
                handleClient(clientSocket);
                clientSocket.close();
            }
        }
        catch (IOException ex){
            LOG.log(Level.SEVERE, ex.getMessage());
        }

    }

    /**
     * Handle a single client connection: receive commands and send back the result.
     *
     * @param clientSocket with the connection with the individual client.
     */
    private void handleClient(Socket clientSocket) {

        /* TODO: implement the handling of a client connection according to the specification.
         *   The server has to do the following:
         *   - initialize the dialog according to the specification (for example send the list
         *     of possible commands)
         *   - In a loop:
         *     - Read a message from the input stream (using BufferedReader.readLine)
         *     - Handle the message
         *     - Send to result to the client
         */

        BufferedReader is = null;
        BufferedWriter os = null;

        char[] operationsList = {'+', '-', '/', '*'};

        try{
            is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), StandardCharsets.UTF_8));
            os = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream(), StandardCharsets.UTF_8));

            LOG.log(Level.INFO, "A new client has arrived");

            os.write("Hello client, i can do those operations : ");
            for (char c : operationsList) os.write(c + " ");

            os.flush();

            String line;
            while((line = is.readLine()) != null){
                String[] calculs = line.split(";");

                if (calculs.length != 3){
                    os.write("the calculation should look like this : operation;arg1;arg2 ! Try again :\r\n");
                    os.flush();
                    continue;
                }

                char operation = calculs[0].charAt(0);
                double arg1    = Double.parseDouble(calculs[1]);
                double arg2    = Double.parseDouble(calculs[2]);

                double result = 0;

                switch (operation){
                    case '+':
                        result = arg1 + arg2;
                        break;
                    case '-':
                        result = arg1 - arg2;
                        break;
                    case '/':
                        result = arg1 / arg2;
                        break;
                    case '*':
                        result = arg1 * arg2;
                        break;
                    default:
                        break;
                }

                os.write(result + "\r\n");
                os.write("Try again if you want :\r\n");
                os.flush();
            }

            LOG.info("Close connection with client");
            is.close();
            os.close();

        }catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        } finally {
            try {
                is.close();
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                os.close();
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                clientSocket.close();
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
}