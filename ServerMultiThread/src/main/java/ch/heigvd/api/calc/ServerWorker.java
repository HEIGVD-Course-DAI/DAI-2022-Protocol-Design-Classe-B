package ch.heigvd.api.calc;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Calculator worker implementation
 */
public class ServerWorker implements Runnable {

    private final static Logger LOG = Logger.getLogger(ServerWorker.class.getName());


    Socket clientSocket;
    BufferedReader in = null;
    PrintWriter  out = null;

    private enum Operation {
        ADD,
        MULTIPLY,
        UNKNOWN
    }

    Operation getOperation(String s) {
        switch(s.toUpperCase()) {
            case "ADD":
                return Operation.ADD;
            case "MULTIPLY":
                return Operation.MULTIPLY;
            default:
                return Operation.UNKNOWN;
        }
    }

    boolean isOperation(String s) {
        return getOperation(s) != Operation.UNKNOWN;
    }

    int compute(Operation operation, int op1, int op2) {
        switch (operation) {
            case ADD:
                return op1 + op2;
            case MULTIPLY:
                return op1 * op2;
            default:
                return 0;
        }
    }

    /**
     * Instantiation of a new worker mapped to a socket
     *
     * @param clientSocket connected to worker
     */
    public ServerWorker(Socket clientSocket) {
        // Log output on a single line
        System.setProperty("java.util.logging.SimpleFormatter.format", "%4$s: %5$s%6$s%n");

        /* TODO: prepare everything for the ServerWorker to run when the
         *   server calls the ServerWorker.run method.
         *   Don't call the ServerWorker.run method here. It has to be called from the Server.
         */

        this.clientSocket = clientSocket;

        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream());
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    /**
     * Run method of the thread.
     */
    @Override
    public void run() {
        /* TODO: implement the handling of a client connection according to the specification.
         *   The server has to do the following:
         *   - initialize the dialog according to the specification (for example send the list
         *     of possible commands)
         *   - In a loop:
         *     - Read a message from the input stream (using BufferedReader.readLine)
         *     - Handle the message
         *     - Send to result to the client
         */
        try {
            String line;

            // Welcome message + commands
            out.write("HELLO\nBienvenue les opérations sont les suivantes: ADD, MULTIPLY\nFORMAT: OPERATION OPERAND1 OPERAND2\nPour quitter: QUIT\nEND_WELCOME\n");
            out.flush();


            while ((line = in.readLine()) != null) {

                if (line.equalsIgnoreCase("QUIT")) {
                    out.write("BYE\n");
                    out.flush();
                    break;
                }

                String[] command = line.split(" ");
                int operand1, operand2;
                Operation operation;

                if (command[0] != null && isOperation(command[0])) {

                    operation = getOperation(command[0]);

                    // Parsing first operand
                    try
                    {
                        operand1 = Integer.parseInt(command[1]);
                    }
                    catch (NumberFormatException nfe)
                    {
                        out.write("ERROR 20\n");
                        out.flush();
                        LOG.log(Level.INFO, "Bad first operand");
                        continue;
                    }

                    // Parsing second operand
                    try
                    {
                        operand2 = Integer.parseInt(command[2]);
                    }
                    catch (NumberFormatException nfe)
                    {
                        out.write("ERROR 21\n");
                        out.flush();
                        LOG.log(Level.INFO, "Bad second operand");
                        continue;
                    }
                } else {
                    out.write("ERROR 10\n");
                    out.flush();
                    continue;
                }

                out.write("RESULT " + compute(operation, operand1, operand2) + "\n");
                out.flush();
            }

            in.close();
            out.close();

        } catch (IOException ex) {

            if (in != null) {
                try {
                    in.close();
                } catch (IOException ex1) {
                    LOG.log(Level.SEVERE, ex1.getMessage(), ex1);
                }
            }
            if (out != null) {
                out.close();
            }
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
}
