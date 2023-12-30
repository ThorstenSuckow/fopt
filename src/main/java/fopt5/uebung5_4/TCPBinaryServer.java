package fopt5.uebung5_4;

import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;

public class TCPBinaryServer {


    public static void main(String[] args) throws IOException {

        int counter = 0;

        try (ServerSocket s = new ServerSocket(9999)) {


            while (true) {

                System.out.println("waiting for connection...");
                try (TCPBinarySocket bs = new TCPBinarySocket(s.accept())) {

                    while (true) {
                        int i = -1;

                        try {
                            i = bs.receive();
                        } catch (EOFException e) {
                            System.out.println("client disconnect");
                            break;
                        }
                        System.out.println("received \"" + i + "\"");

                        if (i == 1) {
                            System.out.println("incrementing...");
                            counter++;
                        } else if (i == 0) {
                            System.out.println("resetting...");
                            counter = 0;
                        } else {
                            break;
                        }

                        bs.reply(counter);
                    }


                } catch (Exception e) {
                    System.out.println("Error: " + e);
                }


            }


        } catch (Exception e) {
            System.out.println("ServerSocket error: " + e);
        }

        System.out.println("server ended.");


    }


}
