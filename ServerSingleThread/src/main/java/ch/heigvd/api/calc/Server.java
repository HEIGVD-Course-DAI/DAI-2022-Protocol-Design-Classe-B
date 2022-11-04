package ch.heigvd.api.calc;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.Double.parseDouble;


/**
 * Calculator server implementation - single threaded
 */
public class Server {

    private final static Logger LOG = Logger.getLogger(Server.class.getName());

    private final int PORT = 1000;

    /**
     * Main function to start the server
     */
    public static void main(String[] args) throws IOException {
        // Log output on a single line
        System.setProperty("java.util.logging.SimpleFormatter.format", "%4$s: %5$s%6$s%n");

        (new Server()).start();
    }

    /**
     * Start the server on a listening socket.
     */
    private void start() throws IOException {
        /* TODO: implement the receptionist server here.
         *  The receptionist just creates a server socket and accepts new client connections.
         *  For a new client connection, the actual work is done by the handleClient method below.
         */
        ServerSocket serverSocket;
        Socket clientSocket = null;

        try {
            serverSocket = new ServerSocket(PORT);
            while (true) {
                clientSocket = serverSocket.accept();
                handleClient(clientSocket);
                clientSocket.close();
            }
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }


    }

    /**
     * Handle a single client connection: receive commands and send back the result.
     *
     * @param clientSocket with the connection with the individual client.
     */
    private void handleClient(Socket clientSocket) throws IOException {

        /* TODO: implement the handling of a client connection according to the specification.
         *   The server has to do the following:
         *   - initialize the dialog according to the specification (for example send the list
         *     of possible commands)
         *   - In a loop:
         *     - Read a message from the input stream (using BufferedReader.readLine)
         *     - Handle the message
         *     - Send to result to the client
         */
        BufferedReader in = null;
        BufferedWriter out = null;

        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), StandardCharsets.UTF_8));
            out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream(), StandardCharsets.UTF_8));
            int result = 0;
            String line;
            out.write("Welcome to the Single-Threaded Server.\nSend me text lines and I will calculate : ");
            out.flush();
            while ((line = in.readLine()) != null) {
                String[] cuttedline = line.split(" ");

                if (cuttedline.length != 3){
                    out.write("the calculation should look like this : operation;arg1;arg2 ! Try again :\r\n");
                    out.flush();
                    continue;
                }

                double[] numbers = {parseDouble(cuttedline[1]), parseDouble(cuttedline[2])};
                switch (cuttedline[0]) {
                    case "ADD":
                        out.write("Resultat : " + (numbers[0] + numbers[1]) + "\n");
                        break;
                    case "SUB":
                        out.write("Resultat : " + (numbers[0] - numbers[1]) + "\n");
                        break;
                    case "MULT":
                        out.write("Resultat : " + (numbers[0] * numbers[1]) + "\n");
                        break;
                    case "DIV":
                        out.write("Resultat : " + (numbers[0] / numbers[1]) + "\n");
                        break;
                    default:
                        break;
                }
                out.write("Vous pouvez recommencer :\r\n");
                out.flush();
                out.flush();
            }
            in.close();
            out.close();


        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                out.close();
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
