package klausurvorbereitung.uebungen;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * Beispiel zeigt, dass ein TCPClient Nachrichten in den Ausgabestream schreiben kann, ohne dabei blockirt zu werden.
 * Erst Leseoperationen sind wieder blockierend.
 *
 */
public class TCPClientDemo {

    static class ServerThread extends Thread {

        private final ServerSocket serverSocket;

        public ServerThread(ServerSocket s) {
            this.serverSocket = s;
            start();
        }


        public void run() {

            while (true) {
                try (Socket comm = serverSocket.accept()) {

                    System.out.println("[server:comm (\"+getName()+\")] accepted connection...");

                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(comm.getInputStream()));
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(comm.getOutputStream()));

                    while (true) {

                        System.out.println("[server:comm ("+getName()+")] waiting for message...");
                        String message = bufferedReader.readLine();
                        bufferedWriter.write("[server:comm ("+getName()+")] taking a nap after reading the line \"" + message +"\"");
                        bufferedWriter.newLine();
                        bufferedWriter.flush();
                        System.out.println("[server:comm ("+getName()+")] taking a nap after reading the line \"" + message +"\"");
                        Thread.sleep(1000);

                    }
                } catch (Exception e) {
                    System.out.println("[server (threaded)] " + e);
                }
            }
        }

    }
    public static void startClient() {

        int messagesSent = 0;

        try (Socket c = new Socket(InetAddress.getLocalHost().getHostAddress(), 8888)) {

            InputStreamReader streamReader = new InputStreamReader(c.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(streamReader);

            OutputStream os = c.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bufferedWriter = new BufferedWriter(osw);

            while (true) {

                for (int i = 0; messagesSent < 50 && i < 10; i++) {
                    messagesSent++;
                    bufferedWriter.write("" + messagesSent);
                    bufferedWriter.newLine();
                    System.out.println("[client] " + messagesSent + " sent");
                    bufferedWriter.flush();
                }


                String response = bufferedReader.readLine();
                System.out.println("[client] got response " + response);

            }
        } catch (Exception e) {
            System.out.println("[client] " + e);
        }



    }

    public static void startThreadedServer() {


        try (ServerSocket serverSocket = new ServerSocket(8888)) {

            while (true) {
                    ServerThread t = new ServerThread(serverSocket);
            }

        } catch (Exception e) {
            System.out.println("[server (threaded)] " + e);
        }
    }

    public static void startServer() {

        try (ServerSocket serverSocket = new ServerSocket(8888)) {

            while (true) {

                /**
                 * Der Server wird zunächst für 5 sek schlafen gelegt.
                 * Im Anschluss akzeptiert er auch Verbindungen, die bereits Daten an den Server
                 * geschickt haben (das wird vom OS gebuffert, s. a. Übung 5.3 im Skript FOPT5/6)
                 */
                System.out.println("[server:comm] running. Sleeping.");
                Thread.sleep(5000);
                System.out.println("[server:comm] waking up!");


                try (Socket comm = serverSocket.accept()) {
                    System.out.println("[server:comm] accepted connection...");

                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(comm.getInputStream()));
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(comm.getOutputStream()));

                    while (true) {

                        System.out.println("[server:comm] waiting for message...");
                        String message = bufferedReader.readLine();
                        bufferedWriter.write("[server:comm] taking a nap after reading the line \"" + message +"\"");
                        bufferedWriter.newLine();
                        bufferedWriter.flush();
                        System.out.println("[server:comm] taking a nap after reading the line \"" + message +"\"");
                        Thread.sleep(1000);

                    }

                } catch (Exception e) {
                    System.out.println("[server:comm] " + e);
                }
            }

        } catch (Exception e) {
            System.out.println("[server] " + e);
        }


    }

    public static void main(String[] args) {

        if (args.length > 0 && args[0].equals("client")) {

            startClient();
            return;
        }

        //startServer();
        startThreadedServer();

    }

}
