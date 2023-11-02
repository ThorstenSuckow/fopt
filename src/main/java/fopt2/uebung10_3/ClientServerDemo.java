package fopt2.uebung10_3;

import fopt2.sandbox.MessageQueue;

import java.util.Arrays;

abstract class ClientServerThread extends Thread {

    MessageQueue clientQueue;
    MessageQueue serverQueue;

    public ClientServerThread(MessageQueue clientQueue, MessageQueue serverQueue) {
        this.clientQueue = clientQueue;
        this.serverQueue = serverQueue;
    }


}

class Client extends ClientServerThread {

    public Client(MessageQueue clientQueue, MessageQueue serverQueue) {
        super(clientQueue, serverQueue);
    }

    public void run() {

        while (true) {
            byte[] message = new byte[]{(byte)(Math.random()*10)};

            System.out.println("[client] sending to server: " + Arrays.toString(message));

            serverQueue.put(message);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("[client] received from server" + Arrays.toString(clientQueue.get()));
        }

    }
}


class Server extends ClientServerThread {

    public Server(MessageQueue clientQueue, MessageQueue serverQueue) {
        super(clientQueue, serverQueue);
    }

    public void run() {

        while (true) {
            byte[] message = serverQueue.get();

            System.out.println("[server] received from client: " + Arrays.toString(message));

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("[server] sending to client: " + Arrays.toString(message));
            clientQueue.put(message);
        }

    }

}



public class ClientServerDemo {

    public static void main (String[] args) {



        MessageQueue clientQueue = new MessageQueue(10, false);
        MessageQueue serverQueue = new MessageQueue(10, false);

        Client c = new Client(clientQueue, serverQueue);
        Server s = new Server(clientQueue, serverQueue);

        c.start();
        s.start();


    }

}
