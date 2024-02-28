package sandbox;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class StaticParallelUdpServer extends Thread {

    private DatagramSocket server;
    public StaticParallelUdpServer(DatagramSocket server) {
        this.server = server;
        start();
    }

    public void run() {

        try (DatagramSocket s = server) {

            while (true) {

                try {
                    DatagramPacket p = new DatagramPacket(new byte[128], 128);
                    s.receive(p);

                    ByteArrayInputStream bais = new ByteArrayInputStream(p.getData());
                    ObjectInputStream ois = new ObjectInputStream(bais);

                    String message = (String) ois.readObject();

                    System.out.println("[server]received: " + message);

                    DatagramPacket answer = new DatagramPacket(p.getData(), p.getLength(), p.getAddress(), p.getPort());
                    s.send(answer);
                } catch (Exception e) {
                    System.err.println("[server] error: " + e);
                }
            }


        } catch (Exception e) {
            System.err.println("[server] thread shutting down: " + e);
        }

    }


    public static void main (String... args) {

        DatagramSocket server = null;

        try {
            server = new DatagramSocket(Integer.parseInt(args[0]));
        } catch (IOException e) {
            System.err.println("[server]: could not start server");
            System.exit(1);
        }

        for (int i = 0; i < 20; i++) {
            new StaticParallelUdpServer(server);
        }


    }
}
