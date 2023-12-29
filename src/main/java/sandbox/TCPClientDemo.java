package sandbox;

import lib.TCPSocket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class TCPClientDemo {


    public static void main(String[] args) throws UnknownHostException {

        String serverAddress =InetAddress.getLocalHost().getHostAddress();
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

                System.out.println("sending \"" + msg + "\"...");
                client.sendLine(msg);

                while (true) {
                    String reply = client.receiveLine();
                    System.out.println("   received msg " + reply);
                    break;
                }

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }


}
