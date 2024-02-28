package sandbox;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class SimpleUdpServer extends Thread {

    private int port;
    public SimpleUdpServer(int port) {
        this.port = port;
        start();
    }


    public void run() {


        try (DatagramSocket server = new DatagramSocket(port)) {

            while (true) {

                try {
                    DatagramPacket p = new DatagramPacket(new byte[1024], 1024);
                    server.receive(p);

                    ByteArrayInputStream bais = new ByteArrayInputStream(p.getData());
                    ObjectInputStream ois = new ObjectInputStream(bais);
                    Object o = ois.readObject();

                    System.out.println("[server] received message: " + o);

                    byte[] reply = p.getData();
                    DatagramPacket response = new DatagramPacket(reply, reply.length, p.getAddress(), p.getPort());
                    server.send(response);
                } catch (IOException e) {
                    System.out.println("[server] error while receiving/sending: " + e);
                }
            }


        } catch (Exception e) {
            throw new RuntimeException(e);
        }



    }


    public static void main (String... args) {

        new SimpleUdpServer(Integer.parseInt(args[0]));

    }
}
