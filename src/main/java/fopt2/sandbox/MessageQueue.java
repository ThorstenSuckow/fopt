package fopt2.sandbox;

import java.util.Arrays;

public class MessageQueue {

    byte[][] queue;


    int head;
    int tail;

    int used;

    public MessageQueue(int size) {

        queue = new byte[size][];

        used = 0;
    }

    public synchronized void put(byte[] message) {

        while (used == queue.length) {

            try {
                System.out.println(Arrays.toString(message) + " is waiting");
                this.wait();
            } catch (InterruptedException ignored) {

            }
        }

        queue[head++] = message.clone();
        used++;
        head %= queue.length;
        System.out.println("++ added " + Arrays.toString(message) + "; head: " + head);
        notifyAll();
    }


    public synchronized byte[] get() {

        while (used == 0) {
            try {
                this.wait();
            } catch (InterruptedException ignored) {

            }
        }

        byte[] message = queue[tail];
        queue[tail++] = new byte[]{0};
        tail %= queue.length;

        System.out.println("-- removed " + Arrays.toString(message) + "; tail: " + tail);
        used--;
        notifyAll();
        return message;
    }

    public synchronized byte[][] getQueue() {
        return queue.clone();
    }

    @Override
    public String toString() {
        return Arrays.toString(queue);
    }

}
