package klausurvorbereitung.ss19;

import fopt2.sandbox.Buffer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPDemo {


    public static void main(String[] args) throws IOException {

        if (args.length != 0) {

            try (Socket client = new Socket(InetAddress.getLocalHost().getHostAddress(), 7777)) {

                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
                int i = 0;
                while (i++ < 5) {
                    writer.write(""+i);
                    writer.newLine();
                    writer.flush();
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {

                    }
                }



            } catch (Exception e) {

            }
            return;
        }

        /*
        try(ServerSocket ss = new ServerSocket(7777); Socket s = ss.accept()) {
            BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));
            String line;
            while((line = input.readLine()) != null) {
                //mache etwas mit line, z.B.
                System.out.println(line);
            }
        }

        return;*/

        try(ServerSocket server = new ServerSocket(7777)) {
            while (true) {
                try (Socket conn = server.accept()) {
                    try {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        while (true) {
                            String line = reader.readLine();
                            System.out.println(line);
                            if (line == null) {
                                System.out.println("conn close");
                                break;
                            }
                        }
                    } catch (IOException e) {
                        break;
                    }
                } catch (Exception e) {
                    System.out.println("Fehler: " + e);
                }
            }
        } catch (Exception e) {
            System.out.println("Fehler: " + e);
        }

    }

}
