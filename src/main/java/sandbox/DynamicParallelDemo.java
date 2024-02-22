package sandbox;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class DynamicParallelDemo {

    private static class Client {

    }


    static class Server {

        class ServerThread extends Thread {

            private Socket sck;
            public ServerThread(Socket s) {
                this.sck = s;
                this.start();
            }

            public void run() {

                try (Socket client = sck) {

                    // read - write

                } catch (Exception e) {
                    // ....
                }
            }

        }

        public Server() {
            init();
        }

        private void init() {
            try (ServerSocket s = new ServerSocket(8888)) {
                while (true) {
                    new ServerThread(s.accept());
                }
            } catch(Exception e) {

            }
        }

    }





    public static void main(String[] args) {

        if (args.length == 0) {

            new Server();

        } else {
            new Client();
        }

    }

}
