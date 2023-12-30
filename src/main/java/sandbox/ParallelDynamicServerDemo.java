package sandbox;

import lib.TCPSocket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketTimeoutException;

/**
 * According to listing 5.11 in [Oec22, 296]
 */
public class ParallelDynamicServerDemo {


    static class ServerThread extends Thread {

        private TCPSocket socket;

        public ServerThread(TCPSocket socket) {
            this.socket = socket;
            this.start();
        }

        public void run() {

            try (TCPSocket s = socket) {

                while (true) {

                    String command = socket.receiveLine();

                    if (command != null) {

                        try {
                            int waitingTime = Integer.parseInt(command);
                            s.sendLine("sleeping for " + waitingTime + "ms...");
                            Thread.sleep(waitingTime);
                            s.sendLine("done.");
                        } catch (Exception e) {
                            System.err.println(e.getMessage());
                        }

                        s.sendLine("Processed command \"" + command + "\".");

                    } else {
                        break;
                    }

                }

            } catch (Exception e) {
                System.err.println(e.getMessage());
            }

            System.out.println("connection closed");
        }

    }

    public final static int DEFAULT_PORT = 9999;


    public static void main(String[] args) throws IOException {



        int port = DEFAULT_PORT;

        if (args.length == 1) {
            port = Integer.parseInt(args[0]);
        }

        try (ServerSocket serverSocket = new ServerSocket(port)) {

            while (true) {

                System.out.println("waiting for connections...");

                try {

                    TCPSocket tcpSocket = new TCPSocket(serverSocket.accept());
                    new ServerThread(tcpSocket);

                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }

            }

        } catch (Exception e) {
            System.err.println("Server error");
            System.err.println(e.getMessage());
        }


    }

}
