package sandbox;

import lib.TCPSocket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketTimeoutException;

/**
 * According to listing 5.10 in [Oec22, 295]
 */
public class SequentialServerDemo {


    public final static int DEFAULT_PORT = 9999;


    public static void main(String[] args) throws IOException {

        int port = DEFAULT_PORT;

        if (args.length == 1) {
            port = Integer.parseInt(args[0]);
        }

        try (ServerSocket serverSocket = new ServerSocket(port)) {

            while (true) {

                System.out.println("Waiting for connection on " + port + "...");
                try (TCPSocket tcpSocket = new TCPSocket(serverSocket.accept())) {

                    while (true) {

                        try {
                            String command = tcpSocket.receiveLine();

                            if (command != null) {
                                System.out.println(" - server received command " + command);

                                try {
                                    int waitingTime = Integer.parseInt(command);
                                    tcpSocket.sendLine("ok. waiting" + command + "ms...");
                                    Thread.sleep(waitingTime);

                                } catch (Exception e) {
                                    System.err.println(e);
                                }

                                tcpSocket.sendLine("done.");

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

        }


    }

}
