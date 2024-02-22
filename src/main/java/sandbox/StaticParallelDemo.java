package sandbox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class StaticParallelDemo {

    private static class Client {

    }

    private static class StaticServer {

        private static class ServerThread extends Thread {

            private final ServerSocket sck;
            public ServerThread(ServerSocket sck) {
                this.sck = sck;
                this.start();
            }

            public void run() {

                while (true) {

                    try (Socket client = sck.accept()) {

                        while (true) {
                            BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                            if (reader.readLine() == null) {
                                break;
                            }
                        }


                    } catch (IOException e) {

                    }


                }

            }

        }

        public StaticServer() {
            init();
        }


        private void init() {

            ServerSocket sck;

            try {
                sck = new ServerSocket(8888);

                for (int i = 0; i < 20; i++) {
                    new ServerThread(sck);
                }

            } catch (IOException e) {
                return;
            }


        }

    }


    public static void main(String[] args) {

        if (args.length == 0) {
            new StaticServer();
        } else {

            new Client();
        }

    }

}
