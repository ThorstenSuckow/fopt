package praktikum.fopt5.probe;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;

public class Probe extends Thread {


    public static void init() {
        new Probe();
    }
    private Probe() {
        start();
    }

    public void run() {
        Random r = new Random();
        ByteArrayOutputStream boas = new ByteArrayOutputStream(4);
        DataOutputStream dos = new DataOutputStream(boas);
        byte[] buffer;

        while (true) {
            try(DatagramSocket datagramSocket = new DatagramSocket()) {
                int value = r.nextInt( 100 ) + 1;
                dos.writeInt(value);
                buffer = boas.toByteArray();

                try {
                    DatagramPacket packet = new DatagramPacket(
                        buffer, buffer.length, InetAddress.getLocalHost(), RmiMeasuringStation.PORT
                    );
                    System.out.println("[probe]: " + value);
                    datagramSocket.send(packet);
                } catch (UnknownHostException e) {
                    throw new RuntimeException(e);
                }
                boas.reset();

                try {
                    Thread.sleep(RmiMeasuringStation.PROBE_SLEEP);
                } catch (InterruptedException ignored) {

                }
            } catch (IOException ignored) {}

        }
    }

}
