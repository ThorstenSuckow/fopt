package sandbox;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class DynamicParallelTcpServer extends Thread {

    private Socket sck;


    public DynamicParallelTcpServer(Socket s) {
        sck = s;
        start();
    }


    public void run() {

        try (Socket client = sck;
            InputStreamReader isr = new InputStreamReader(client.getInputStream());
            OutputStreamWriter osw = new OutputStreamWriter(client.getOutputStream());
            BufferedReader r = new BufferedReader(isr);
            BufferedWriter w = new BufferedWriter(osw)) {

            while (true) {

                String msg = r.readLine();

                if (msg == null) {
                    System.out.println("[server(" + getName() + ")] client disconnect!");
                    break;
                }

                w.write(msg);
                w.newLine();
                w.flush();
            }

        } catch (Exception e) {
            System.err.println("[server(" + getName() + ")] error "+ e);
            System.err.println("         ending Thread");
        }

    }


    public static void main (String... args) {

        try(ServerSocket s = new ServerSocket(Integer.parseInt(args[0]))) {

            while (true) {
                new DynamicParallelTcpServer(s.accept());
            }

        } catch (IOException e) {
            System.err.println("[server] error " + e);
        }


    }


}
