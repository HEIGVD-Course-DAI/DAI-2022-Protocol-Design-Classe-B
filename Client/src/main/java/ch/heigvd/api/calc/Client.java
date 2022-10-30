package ch.heigvd.api.calc;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
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

        BufferedReader stdin = null;
        BufferedWriter out = null;
        BufferedReader in = null;

        InetAddress address = null;
        int port = 0;
        Socket sock = null;

        if (args.length != 2) {
            System.out.println("usage: client <address> <port>");
            return;
        }

        try {
            address = InetAddress.getByName((args[0]));
        } catch (UnknownHostException ex) {
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
            return;
        }
        try {
            port = Integer.parseInt(args[1]);
        } catch (NumberFormatException ex) {
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
            return;
        }

        try {
            sock = new Socket(address, port);
            in = new BufferedReader(new InputStreamReader(sock.getInputStream(), StandardCharsets.UTF_8));
            out = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream(), StandardCharsets.UTF_8));
            stdin = new BufferedReader(new InputStreamReader(System.in));

            // ugly way to read initialization data
            String info;
            while (in.ready() && (info = in.readLine()) != null) {
                System.out.println(info);
            }

            while (true) {
                System.out.print("Awaiting user input: ");
                String command = stdin.readLine();
                out.write(command + "\n");
                out.flush();
                System.out.println(in.readLine());
                if (command.equalsIgnoreCase("QUIT")) {
                    out.close();
                    in.close();
                    sock.close();
                    stdin.close();
                    break;
                }
            }

        } catch (IOException ex) {
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
}
