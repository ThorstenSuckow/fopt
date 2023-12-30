package fopt5.uebung5_4;

import java.io.IOException;
import java.net.InetAddress;

public class TCPBinaryClient {


    public static void main (String[] args) throws IOException {


        try(TCPBinarySocket client = new TCPBinarySocket(InetAddress.getLocalHost().getHostAddress(), 9999)) {

            client.setTimeout(2000);


            for (int i = 0; i < 10; i++) {
                client.send(1);
                System.out.println("[client received]" + client.receive());
                Thread.sleep(1000);
            }

            client.send(0);
            System.out.println("[client received]" + client.receive());

        } catch (Exception e) {
           System.out.println("client error: " + e);
        }


    }

}
