package ch.heigvd.api.calc;

import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Calculator worker implementation
 */
public class ServerWorker implements Runnable {

    private final static Logger LOG = Logger.getLogger(ServerWorker.class.getName());
    private Socket clientSocket;
    private BufferedReader in = null;
    private BufferedWriter out = null;


    /**
     * Instantiation of a new worker mapped to a socket
     *
     * @param clientSocket connected to worker
     */
    public ServerWorker(Socket clientSocket) throws IOException {
        // Log output on a single line
        System.setProperty("java.util.logging.SimpleFormatter.format", "%4$s: %5$s%6$s%n");
        try {
            this.clientSocket = clientSocket;
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        } catch (IOException e) {
            LOG.log(Level.SEVERE, null, e);
        }
    }

    /**
     * Run method of the thread.
     */
    @Override
    public void run() {
        String line;
        String pattern = "\\d+[/+*-]\\d+";
        try {
            out.write("Hello user!\n");
            out.flush();

            while (true) {
                out.write("Please enter two integers seperated by any operation sign (+, -, *, /)\n");
                out.flush();
                LOG.info("Waiting for user input...");

                line = in.readLine();

                if (line.equals("exit")) break;

                if (line.matches(pattern)) {
                    LOG.info("Computing Value...");
                    String[] numbers = line.split("\\D");
                    String operator = line.replaceAll("\\d", "");
                    String res = compute(Integer.parseInt(numbers[0]), Integer.parseInt(numbers[1]), operator);
                    out.write(line + "=" + res + "\n");
                    out.flush();
                } else {
                    LOG.info("Incorrect input");
                    out.write("Error incorrect input !\n");
                    out.flush();
                }
            }
            out.write("Bye !\n");
            out.flush();
            LOG.log(Level.INFO, "Closing connection...");
        } catch (IOException e) {
            LOG.log(Level.SEVERE, null, e);
        } finally {
            try {
                if (out != null) out.close();
            } catch (IOException ex) {
                LOG.log(Level.SEVERE, ex.toString(), ex);
            }
            try {
                if (in != null) in.close();
            } catch (IOException ex) {
                LOG.log(Level.SEVERE, ex.toString(), ex);
            }
            try {
                if (clientSocket != null && !clientSocket.isClosed()) clientSocket.close();
            } catch (IOException ex) {
                LOG.log(Level.SEVERE, ex.toString(), ex);
            }
        }
    }

    private String compute(int a, int b, String op) {
        switch (op) {
            case "+":
                return String.valueOf(a + b);
            case "-":
                return String.valueOf(a - b);
            case "*":
                return String.valueOf(a * b);
            case "/":
                if (b == 0) {
                    return "Error ! Division by 0";
                }
                return String.valueOf((double) a / b);
            default:
                return "Error ! Could not compute";
        }
    }
}
