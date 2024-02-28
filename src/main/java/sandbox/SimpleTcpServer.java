package sandbox;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleTcpServer {


    public static void main(String... args) {


        try (ServerSocket server = new ServerSocket(Integer.parseInt(args[0]))) {

            while (true) {

                try (Socket client = server.accept();
                     OutputStreamWriter osw = new OutputStreamWriter(client.getOutputStream());
                     BufferedWriter bw = new BufferedWriter(osw);
                     InputStreamReader isr = new InputStreamReader(client.getInputStream());
                     BufferedReader br = new BufferedReader(isr)) {

                    while (true) {

                        String message = br.readLine();
                        if (message == null) {
                            System.out.println("[server] received NULL");
                            break;
                        }

                        System.out.println("[server] received: " + message);
                        bw.write(message);
                        bw.newLine();
                        bw.flush();
                    }

                } catch (Exception e) {
                    System.err.println("[server] error: " + e);
                    System.err.println("         waiting for new connection...");
                }
            }


        } catch (Exception e) {
            System.err.println("[server] error: " + e);
        }


    }

}
