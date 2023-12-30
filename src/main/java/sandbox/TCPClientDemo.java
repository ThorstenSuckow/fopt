package sandbox;

import lib.TCPSocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class TCPClientDemo {


    public static void main(String[] args) throws UnknownHostException {

        String serverAddress = InetAddress.getLocalHost().getHostAddress();
        int port = TCPServerDemo.DEFAULT_PORT;

        if (args.length > 0) {
            serverAddress = args[0];
        }

        if (args.length > 1) {
            port = Integer.parseInt(args[1]);
        }

        try (TCPSocket client = new TCPSocket(serverAddress, port)) {


            while (true) {
                System.out.print("Enter message: ");
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                String msg = reader.readLine();

                if (msg.startsWith("timeout")) {
                    try {
                        String[] parts = msg.split("=");
                        int timeout = Integer.parseInt(parts[1]);
                        client.setTimeout(timeout);
                        System.out.println("[client] setting timeout to " + timeout);
                        continue;
                    } catch (Exception e) {
                        System.err.println("[client] setting timeout failed: " + e);
                    }
                }

                System.out.println("sending \"" + msg + "\"...");
                client.sendLine(msg.trim());


                while (true) {
                    try {
                        String reply = client.receiveLine();
                        System.out.println("   received msg " + reply);
                    } catch (SocketTimeoutException e) {
                        System.err.println("[client] socket timeout.");
                        break;
                    }
                }

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }


}
