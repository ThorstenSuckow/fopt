package sandbox;

import lib.TCPSocket;

import java.io.IOException;
import java.net.ServerSocket;

public class TCPServerDemo {


    public final static int DEFAULT_PORT = 9999;



    public static void main(String[] args) throws IOException {


        int port = DEFAULT_PORT;

        if (args.length == 1) {
            port = Integer.parseInt(args[0]);
        }

        try (ServerSocket serverSocket = new ServerSocket(port)) {

            while (true) {

                try (TCPSocket tcpSocket = new TCPSocket(serverSocket.accept())) {

                    while (true) {
                        String command = tcpSocket.receiveLine();
                        System.out.println(" - server received command " + command);
                        tcpSocket.sendLine("ok " + command + " from " + serverSocket.getLocalSocketAddress().toString());
                    }

                }

            }


        }





    }

}
