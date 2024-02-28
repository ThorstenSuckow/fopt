package sandbox;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class DynamicParallelUdpServer extends Thread {

    private DatagramPacket message;
    private DatagramSocket server;

    public DynamicParallelUdpServer(DatagramPacket packet, DatagramSocket server) {
        message = packet;
        this.server = server;
        start();
    }


    public void run() {

        try (ByteArrayInputStream bais = new ByteArrayInputStream(message.getData());
             ObjectInputStream ois = new ObjectInputStream(bais)){

            String msg = (String) ois.readObject();
            System.out.println("[server] received " + msg);

            DatagramPacket p = new DatagramPacket(
                message.getData(), message.getLength(),
                message.getAddress(), message.getPort()
            );

            server.send(p);
        } catch (Exception e) {
            System.err.println("[server thread] error: " + e);
        }

    }

    public static void main (String... args) {

        int port = Integer.parseInt(args[0]);

        try (DatagramSocket s = new DatagramSocket(port)) {

            while (true) {

                try {
                    DatagramPacket p = new DatagramPacket(new byte[128], 128);
                    s.receive(p);
                    new DynamicParallelUdpServer(p, s);

                } catch (Exception e) {
                    System.err.println("[server] error: "+ e);
                }
            }

        } catch (SocketException e) {
            System.err.println("[server] error: " + e);
        }


    }

}
