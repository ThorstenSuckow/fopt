package klausurvorbereitung.foptws23;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class DynamicParallelServerDemo {

    static class Server extends Thread {

        static class ServerThread extends Thread {

            Socket server;
            public ServerThread(Socket s) {
                server = s;
                this.start();
            }

            public void run() {

                try (Socket client = server) {
                    while (true) {

                        BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                        String line = reader.readLine();

                        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));

                        writer.write("server answers " + line);
                        writer.newLine();
                        writer.flush();

                    }
                } catch (Exception e) {
                    System.out.println("[serverthread] " + e);
                }

            }

        }
        public Server() {
            start();
        }

        public void run() {

            try (ServerSocket server = new ServerSocket(8888)) {

                while (true) {


                    ServerThread serverThread = new ServerThread(server.accept());
                    System.out.println("       SERVER LOOP");

                }


            } catch (Exception e) {
                System.err.println("[server] " + e);
            }

        }


    }



    static class Client extends Thread {

        private String name;
        public Client(String name) {
            this.name = name;
            start();
        }

        public void run() {

            int i = 0;

            try (Socket s = new Socket(InetAddress.getLocalHost().getHostAddress(), 8888)) {
                while (true) {
                    Thread.sleep((int) (Math.random() * 1000));

                    BufferedWriter w = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
                    w.write(name + " sending " + i);
                    w.newLine();
                    w.flush();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
                    System.out.println("[client] received message from server \"" + reader.readLine() + "\"");
                    i++;

                }
            } catch (Exception e){
                System.err.println("[client] " + e);
            }

        }

    }


    public static void main(String[] args) {

        if (args.length == 0) {

            Server s = new Server();


        } else {

            Client c = new Client(args[0]);

        }


    }
}
