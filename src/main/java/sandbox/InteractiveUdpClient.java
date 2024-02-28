package sandbox;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class InteractiveUdpClient extends Thread {

    private Scanner scanner;

    private int port;

    public InteractiveUdpClient(int port){
        this.port = port;
        scanner = new Scanner(System.in);
        start();
    }


    public void run() {

        while (true) {
            String message = scanner.nextLine().trim();

            byte[] buffer;
            DatagramPacket p = null;

            try (DatagramSocket s = new DatagramSocket();
                 ByteArrayOutputStream baos = new ByteArrayOutputStream(128);
                 ObjectOutputStream oos = new ObjectOutputStream(baos)) {
                s.setSoTimeout(1000);

                oos.writeObject(message);
                oos.flush();

                byte[] msg = baos.toByteArray();
                p = new DatagramPacket(msg, msg.length, InetAddress.getLocalHost(), port);
                s.send(p);

                buffer = new byte[1024];
                DatagramPacket response = new DatagramPacket(buffer, buffer.length);

                s.receive(response);
                try(ByteArrayInputStream bais = new ByteArrayInputStream(response.getData());
                    ObjectInputStream dis = new ObjectInputStream(bais)) {
                    System.out.println("[client] got response: " + dis.readObject());
                }

            } catch (ClassNotFoundException|IOException e) {
                System.err.println("[client] error: " + e);
            }

        }

    }


    public static void main(String... args) {

        new InteractiveUdpClient(Integer.parseInt(args[0]));
    }



}