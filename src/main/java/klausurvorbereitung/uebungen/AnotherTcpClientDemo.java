package klausurvorbereitung.uebungen;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;

public class AnotherTcpClientDemo {

    private static class TcpClient extends Thread {

        public TcpClient() {
            start();
        }


        public void run() {

            try (Socket s = new Socket(InetAddress.getLocalHost(), 7777);
                 OutputStreamWriter osw = new OutputStreamWriter(s.getOutputStream());
                 BufferedWriter w = new BufferedWriter(osw);
            ) {

                for (int i = 0; i < 100; i++) {
                    w.write("increment");
                    w.newLine();
                    w.flush();
                }


            } catch (Exception e) {
                System.out.println("[error] " + e);
            }

        }
    }

    public static void main(String... args) {

        new TcpClient();

    }

}
