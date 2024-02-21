package praktikum.fopt5;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.time.LocalTime;

public class DynamicParallelUdpServerDemo {

    private static int PORT = 9999;

    private static int SERVER_REPLY_SLEEP = 1000;


    private static class Server extends Thread {

        private DatagramPacket datagramPacket;
        private DatagramSocket datagramSocket;

        public Server(DatagramSocket socket, DatagramPacket packet) {
            datagramPacket = packet;
            datagramSocket = socket;
            start();
        }

        public void run() {

            byte[] message = datagramPacket.getData();
            try {
                String str = new String(message, 0, message.length);

                System.out.println("[server "+Thread.currentThread().getName()+"] received: " + str);

                ByteArrayOutputStream boas = new ByteArrayOutputStream();
                OutputStreamWriter writer = new OutputStreamWriter(boas);
                writer.write(LocalTime.now().toString());

                writer.flush();
                writer.close();

                try {
                    Thread.sleep((int)(Math.random() * SERVER_REPLY_SLEEP));
                } catch (InterruptedException e) {

                }

                byte[] buffer = boas.toByteArray();
                datagramSocket.send(
                    new DatagramPacket(
                        buffer, buffer.length,
                        datagramPacket.getAddress(),
                        datagramPacket.getPort()
                    )
                );

            } catch (IOException e) {
                System.out.println("[server "+Thread.currentThread().getName()+"] error: " + e);
            }


        }


    }



    public static void main(String...args) {

        if (args.length == 0) {

            try (DatagramSocket datagramSocket = new DatagramSocket(PORT)) {


                while (true) {

                    DatagramPacket datagramPacket = new DatagramPacket(new byte[64], 64);
                    datagramSocket.receive(datagramPacket);

                    new Server(datagramSocket, datagramPacket);

                }



            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        } else {

            for (int i = 0; i < 100; i++) {

                int finalI = i;
                new Thread(() -> {

                    try (DatagramSocket socket = new DatagramSocket()){

                        byte[] buffer = ("Hello World ("+ finalI + ")!").getBytes();

                        DatagramPacket packet = new DatagramPacket(
                            buffer, buffer.length,
                            InetAddress.getLocalHost(),
                            PORT
                        );
                        socket.send(packet);
                        DatagramPacket input = new DatagramPacket(new byte[20], 20);
                        socket.receive(input);
                        System.out.println(
                            "[client " + finalI + "] received " +
                                (new String(input.getData(), 0, input.getLength()))
                        );

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }).start();

            }

        }



    }

}
