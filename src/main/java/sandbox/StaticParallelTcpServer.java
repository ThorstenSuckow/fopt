package sandbox;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class StaticParallelTcpServer extends Thread {

    ServerSocket server;
    public StaticParallelTcpServer(ServerSocket server) {
        this.server = server;
        start();
    }

    public void run() {

        while (true) {

            try (Socket s = server.accept();
                InputStreamReader isr = new InputStreamReader(s.getInputStream());
                OutputStreamWriter osw = new OutputStreamWriter(s.getOutputStream());
                BufferedReader br = new BufferedReader(isr);
                BufferedWriter bw = new BufferedWriter(osw);
            ) {

                while (true) {
                    String msg = br.readLine();
                    if (msg == null) {
                        System.out.println("[server("+getName()+")] client disconnect!");
                        break;
                    }
                    System.out.println("[server("+getName()+")] received: " + msg);
                    bw.write(msg);
                    bw.newLine();
                    bw.flush();
                }
            } catch (Exception e) {
                System.out.println("[server("+getName()+")] error: " + e);
                System.out.println("         waiting for new connection");
            }
        }
    }


    public static void main(String... args) throws IOException {
        ServerSocket s = new ServerSocket(Integer.parseInt(args[0]));

        for (int i = 0; i < 2; i++) {
            new StaticParallelTcpServer(s);
        }
    }

}
