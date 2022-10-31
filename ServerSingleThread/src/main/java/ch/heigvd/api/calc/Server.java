package ch.heigvd.api.calc;

import jdk.dynalink.Operation;

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
        final int port = 31415;
        ServerSocket serverSocket;
        Socket clientSocket = null;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
            return;
        }

        while (true) {
            LOG.log(Level.INFO, "CALC: Waiting for a new client on port {0}", port);
            try {
               clientSocket = serverSocket.accept();
               handleClient(clientSocket);
               clientSocket.close();
            } catch (IOException ex) {
                if (clientSocket != null) {
                    try { clientSocket.close(); } catch (IOException ex1) {
                        LOG.log(Level.SEVERE, ex1.getMessage(), ex1);
                    }
                }
                LOG.log(Level.SEVERE, ex.getMessage(), ex);
            }

        }
    }

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
     * Handle a single client connection: receive commands and send back the result.
     *
     * @param clientSocket with the connection with the individual client.
     */
    private void handleClient(Socket clientSocket) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), StandardCharsets.UTF_8));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream(), StandardCharsets.UTF_8));
            String line;
            out.write("HELLO\nBienvenue les opérations sont les suivantes: ADD, MULTIPLY\nFORMAT: OPERATION OPERAND1 OPERAND2\nPour quitter: QUIT\nEND_WELCOME\n");
            out.flush();
            while ((line = in.readLine()) != null) {
                if (line.equalsIgnoreCase("QUIT")) {
                    out.write("BYE\n");
                    out.flush();
                    break;
                }
                String[] command = line.split(" ");
                int op1;
                int op2;
                Operation operation;

                if (command[0] != null && isOperation(command[0])) {
                    operation = getOperation(command[0]);
                    try {
                        op1 = Integer.parseInt(command[1]);
                    } catch (NumberFormatException ex) {
                        out.write("ERROR 20\n");
                        out.flush();
                        LOG.log(Level.INFO, "Malformed operand", ex);
                        continue;
                    }
                    try {
                        op2 = Integer.parseInt(command[2]);
                    } catch (NumberFormatException ex) {
                        out.write("ERROR 21\n");
                        out.flush();
                        LOG.log(Level.INFO, "Malformed operand", ex);
                        continue;
                    }
                } else {
                    out.write("ERROR 10\n");
                    out.flush();
                    continue;
                }
                out.write("RESULT " + compute(operation, op1, op2) + "\n");
                out.flush();
            }
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
        }

    }
}
