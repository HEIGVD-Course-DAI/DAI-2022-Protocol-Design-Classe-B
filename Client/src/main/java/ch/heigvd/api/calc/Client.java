package ch.heigvd.api.calc;

import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Calculator client implementation
 */
public class Client {

    private static final Logger LOG = Logger.getLogger(Client.class.getName());

    /**
     * Main function to run client
     *
     * @param args no args required
     */
    public static void main(String[] args) {
        // Log output on a single line
        System.setProperty("java.util.logging.SimpleFormatter.format", "%4$s: %5$s%6$s%n");

        Socket clientSocket = null;
        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
        BufferedReader in = null;
        BufferedWriter out = null;

        String srvAddress = "192.168.2.221";
        int portNumber = 8008;

        String usrInput = "";

        try{
            clientSocket = new Socket(srvAddress, portNumber);
            out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            System.out.println(in.readLine()); //Server's Greetings
            while (!usrInput.equals("exit")) {
                System.out.println(in.readLine()); //Server's instruction

                usrInput = stdin.readLine();
                out.write(usrInput + "\n");
                out.flush();
                System.out.println(in.readLine()); //Server's response
            }

        } catch(IOException ex){
            System.out.println("Error : " + ex.getMessage());
        }
        finally {
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
                if (clientSocket != null && ! clientSocket.isClosed()) clientSocket.close();
            } catch (IOException ex) {
                LOG.log(Level.SEVERE, ex.toString(), ex);
            }
        }
    }
}
