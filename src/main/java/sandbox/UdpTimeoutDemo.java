package sandbox;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UdpTimeoutDemo {



    public static void main (String... args) {


        if (args.length == 0) {

            try (DatagramSocket s = new DatagramSocket(8888)) {

                while (true) {

                    DatagramPacket p = new DatagramPacket(new byte[64], 64);
                    s.receive(p);

                    ByteArrayInputStream bais = new ByteArrayInputStream(p.getData());
                    ObjectInputStream ois = new ObjectInputStream(bais);
                    String t = (String) ois.readObject();

                    System.out.print("[server]: received " + t);

                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {

                    }

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ObjectOutputStream oos = new ObjectOutputStream(baos);
                    oos.writeObject(t);
                    oos.flush();

                    byte[] buffer = baos.toByteArray();
                    DatagramPacket response = new DatagramPacket(buffer, buffer.length, p.getAddress(), p.getPort());
                    s.send(response);

                }

            } catch (Exception e) {
                System.out.println("[server] " + e);
            }

        } else {


            try (DatagramSocket s = new DatagramSocket()) {
                s.setSoTimeout(250);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject("Hello World");

                byte[] message = baos.toByteArray();
                DatagramPacket p = new DatagramPacket(message, message.length, InetAddress.getLocalHost(), 8888);

                s.send(p);

                DatagramPacket response = new DatagramPacket(new byte[64], 64);
                s.receive(response);
                ByteArrayInputStream bais = new ByteArrayInputStream(response.getData());
                ObjectInputStream ois = new ObjectInputStream(bais);
                System.out.println("[client]: received " + ois.readObject());

            } catch (Exception e) {
                System.out.println("[client] " + e);
            }
        }

    }

}
