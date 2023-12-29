package sandbox;

import lib.UDPMulticastSocket;

import java.io.IOException;

public class MulticastSocketServerDemo {

    public static String DEFAULT_ADDRESS = "228.5.6.7";
    public static int DEFAULT_PORT = 6789;


    public static void main(String[] args) throws IOException {

        int id = (int) (System.currentTimeMillis() / 1000);
        int port = args.length > 1 ? Integer.parseInt(args[1]) : DEFAULT_PORT;;
        String address = args.length > 0 ? args[0] : DEFAULT_ADDRESS;

        System.out.println("Starting server["+ id + "] on " + address + ":" + port + "..." );

        try (UDPMulticastSocket mcsocket = new UDPMulticastSocket(port)) {

            mcsocket.join(address);

            System.out.println("ok. ttl is " + mcsocket.getTimeToLive() + ", listening.");

            while (true) {
                String msg = mcsocket.receive(1024);
                System.out.println("Received " +  msg);

                mcsocket.reply("Server[" + id +"] sends ack.");
            }

        }
    }

}
