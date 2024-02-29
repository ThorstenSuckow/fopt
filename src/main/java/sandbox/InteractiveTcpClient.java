package sandbox;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class InteractiveTcpClient extends Thread {

    private Scanner scanner;

    private int port;

    public InteractiveTcpClient(int port){
        this.port = port;
        scanner = new Scanner(System.in);
        start();
    }


    public void run() {

        try (Socket client = new Socket(InetAddress.getLocalHost(), port)) {

            try (
                OutputStreamWriter osw = new OutputStreamWriter(client.getOutputStream());
                BufferedWriter w = new BufferedWriter(osw);
                InputStreamReader isr = new InputStreamReader(client.getInputStream());
                BufferedReader bf = new BufferedReader(isr)) {

                while (true) {

                    String message = scanner.nextLine().trim();
                    w.write(message);
                    w.newLine();
                    w.flush();

                    String reply = bf.readLine();
                    System.out.println("[client] got response: " + reply);
                }


            } catch (Exception e) {
                System.err.println("[client] error while sending/ receiving: " + e);
            }

        } catch (Exception e) {
            System.err.println("[client] error: " + e);
        }


    }


    public static void main(String... args) {

        new InteractiveTcpClient(Integer.parseInt(args[0]));
    }



}