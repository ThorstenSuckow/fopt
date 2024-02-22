package praktikum.fopt5;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class StaticParallelUdpServerDemo {

    private static final int PORT = 9999;

    private static final int SERVER_REPLY_SLEEP = 1000;
    private static class UdpServer extends Thread {

        private DatagramSocket datagramSocket;

        public UdpServer(DatagramSocket socket) {
            datagramSocket = socket;
            start();
        }

        public void run() {

            while (true) {

                DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);

                try {
                    datagramSocket.receive(packet);

                    System.out.println(
                        "[server " + Thread.currentThread().getName() + "] received " +
                        new String(packet.getData(), 0, packet.getLength())
                    );

                    try {
                        Thread.sleep(SERVER_REPLY_SLEEP);

                        String reply = "OK!";
                        datagramSocket.send(
                            new DatagramPacket(reply.getBytes(), reply.getBytes().length, packet.getAddress(), packet.getPort())
                        );
                    } catch (IOException|InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                } catch (IOException e) {
                    System.err.println(
                        "[server " + Thread.currentThread().getName() + "] error" + e
                    );
                }


            }

        }

    }

    public static void main(String... args){


        if (args.length == 0) {

            DatagramSocket datagramSocket;

            try {
                datagramSocket = new DatagramSocket(PORT);
            } catch (SocketException e) {
                throw new RuntimeException(e);
            }

            for (int i = 0; i < 20; i++) {
                new UdpServer(datagramSocket);
            }

        } else {

            for (int i = 0; i < 100; i++) {

                int finalI = i;
                new Thread(() -> {

                    String msg = "Hello World!";



                    try (DatagramSocket datagramSocket = new DatagramSocket()) {

                        datagramSocket.send(
                            new DatagramPacket(
                                msg.getBytes(), msg.getBytes().length, InetAddress.getLocalHost(), PORT
                            )
                        );
                        DatagramPacket reply = new DatagramPacket(new byte[16], 16);
                        datagramSocket.receive(reply);
                        System.out.println("[client " + finalI + "] received" + new String(reply.getData(), 0, reply.getLength()));

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }).start();



            }


        }


    }
}
