package klausurvorbereitung.foptws23;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class DynamicParallelServerDemo {

    static class Client extends Thread {

        private final String name;
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
    static class Server {

        class ServerThread extends Thread {
            private Socket client;
            public ServerThread(Socket sck) {
                client = sck;
                start();
            }
            public void run() {
                try (Socket socket = client) {
                    while (true) {

                        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

                        String line = reader.readLine();

                        writer.write("Received line: \"" + line + "\"");
                        writer.newLine();
                        writer.flush();

                    }
                } catch (Exception e) {
                    System.err.println("[serverthread error] " + e);
                }
            }
        }

        public Server() {
            init();
        }

        private void init() {

            try (ServerSocket server = new ServerSocket(8888)) {

                while (true) {

                    try {
                        new ServerThread(server.accept());
                        System.out.println("[server] Server accepted new connection!");
                    } catch (Exception e) {
                        System.err.println("[server error]  " + e);
                    }

                }
            } catch (Exception e) {
                System.err.println("[server error] " + e);
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
