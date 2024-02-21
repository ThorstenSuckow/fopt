package praktikum.fopt5;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpBatchDemo {


    private final static int PORT = 9999;

    private final static int NUM_CLIENT_SENDS = 100;
    private final static int NUM_CLIENT_READS = 100;
    private static class IncrementServer {

        private int value;
        public IncrementServer() {
            init();
        }

        private void init() {

            try (ServerSocket socket = new ServerSocket(PORT)) {

                while (true) {

                    try (Socket client = socket.accept()){

                        BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));

                        while (true) {

                            String cmd = reader.readLine();
                            if (cmd == null) {
                                break;
                            }
                            System.out.println("[server] received " + cmd);
                            if (cmd.equals("increment")) {
                                value++;
                            } else if  (cmd.startsWith("reset")) {
                                value = 0;
                            }

                            writer.write("" + value);
                            writer.newLine();
                            writer.flush();

                        }


                    } catch (IOException e) {
                        System.err.println("[error] " + e);
                    }

                }


            } catch (IOException e) {
                System.err.println("[server]" + e);
            }


        }

    }




    public static void main(String... args) {

        if (args.length == 0) {

            new IncrementServer();

        } else {


            //try (Socket client = new Socket(InetAddress.getLocalHost().getHostName(), PORT)) {

            try {
                // two threads, remove from try-with-resources
                Socket client = new Socket(InetAddress.getLocalHost().getHostName(), PORT);

                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
                BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));

                new Thread(()-> {

                    for (int i = 0; i < NUM_CLIENT_SENDS; i++) {
                        try {
                            System.out.println("[client] sending increment");
                            writer.write("increment");
                            writer.newLine();
                            writer.flush();
                        } catch (IOException e) {
                            System.err.println("[client] send " + e);
                        }
                    }
                    try {
                        // shutdown output stream, not needed anymore
                        client.shutdownOutput();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }).start();

                /*
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) {

                }*/

                new Thread(()-> {
                    try {
                        client.setSoTimeout(250);
                        String line = reader.readLine();
                        while (line != null) {
                            System.out.println(line);
                            line = reader.readLine();
                        }
                        System.out.println("[client] connection closed");
                    } catch (IOException e) {
                        System.err.println("[client] read " + e);
                    }
                }).start();




            } catch (Exception e) {
                System.out.println("[client] " + e);
            }




        }


    }

}
