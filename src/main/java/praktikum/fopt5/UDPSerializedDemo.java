package praktikum.fopt5;

import java.io.Serializable;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPSerializedDemo {

    private final static int PORT = 9999;

    private static class Dummy implements Serializable {
        public String toString() {
            return "[Dummy] Hello World!";
        }
    }



    public static void main(String... args) {

        try {

            if (args.length == 0) {

                UDPSocketAdvanced udpSocketAdvanced = new UDPSocketAdvanced(PORT);

                while (true) {

                    Object o = udpSocketAdvanced.readObject(1024);
                    System.out.println("[udpserver] received: " + o);
                }

            } else {

                Dummy o = new Dummy();

                UDPSocketAdvanced udpSocket = new UDPSocketAdvanced(new DatagramSocket());
                udpSocket.sendObject(o, InetAddress.getLocalHost(),  PORT);
                System.out.println("[client] server replies: " + udpSocket.receiveInt());
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
