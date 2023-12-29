package sandbox;

import lib.UDPSocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;

public class MulticastClientDemo {

    public static void main(String[] args) {

        String address = MulticastSocketServerDemo.DEFAULT_ADDRESS;
        int port = MulticastSocketServerDemo.DEFAULT_PORT;

        if (args.length == 1) {
            address = args[0];
        }
        if (args.length == 2) {
            port = Integer.parseInt(args[1]);
        }


        try (UDPSocket socket = new UDPSocket()) {

            InetAddress receiver = InetAddress.getByName(address);

            while (true) {
                System.out.print("Enter message: ");
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                String msg = reader.readLine();

                System.out.println("sending \"" + msg + "\"...");
                socket.send(msg, receiver, port);

                socket.setTimeout(5000);

                while (true) {
                    try {
                        String reply = socket.receive(1024);
                        System.out.println("   received msg " + reply);
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                        break;
                    }
                }


            }

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

}
