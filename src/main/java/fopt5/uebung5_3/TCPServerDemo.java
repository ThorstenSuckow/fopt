package fopt5.uebung5_3;

import lib.TCPSocket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketTimeoutException;


/**
 * Creating a ServerSocket makes sure that incoming connections are buffered by the OS.
 * Once the server accepts connections (application layer), those connections are picked
 * from the buffer, if any. Thus, if any connections is currently blocking another connection,
 * the connection can be processed once the busy connection has ended. Data already sent by the
 * client will then be processed.
 */
public class TCPServerDemo {


    public final static int DEFAULT_PORT = 9999;

    public final static int TIMEOUT = 10000;


    public static void main(String[] args) throws IOException {

        int counter = 0;

        int port = DEFAULT_PORT;

        if (args.length == 1) {
            port = Integer.parseInt(args[0]);
        }

        try (ServerSocket serverSocket = new ServerSocket(port)) {

            while (true) {

                System.out.println("Waiting for connection on " + port + "...");

                System.out.println("sleeping...");
                Thread.sleep(15000);
                System.out.println("waking up.");


                try (TCPSocket tcpSocket = new TCPSocket(serverSocket.accept())) {
                    // free connection after idling
                    tcpSocket.setTimeout(TIMEOUT);
                    while (true) {

                        try {
                            String command = tcpSocket.receiveLine();

                            if (command != null) {
                                System.out.println(" - server received command " + command);
                                switch (command) {
                                    case "increment" -> counter++;
                                    case "reset" -> counter = 0;
                                }

                                tcpSocket.sendLine("ok. (\"" + command + "\" from " + serverSocket.getLocalSocketAddress().toString() + ")");
                                tcpSocket.sendLine("(counter at " + counter + ")");

                            } else {
                                System.out.println("Closing connection.");
                                break;
                            }

                        } catch (SocketTimeoutException e) {
                            // make sure SocketTimeoutException (subclass of IOException) is used to catch timeouts!
                            // otherwise, server will loop on IOException
                            System.out.println(e.getMessage());
                            System.out.println("[server] closing client connection...");
                        }
                    }

                } catch (Exception e) {
                    System.err.println(e.getMessage());
                    System.err.println("Connection closed.");
                }

            }

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }

}
