package fopt2.sandbox;

import java.util.Arrays;

public class MessageQueue {

    byte[][] queue;


    int head;
    int tail;

    int used;

    boolean logEnabled;


    public MessageQueue(int size, boolean logEnabled) {

        queue = new byte[size][];
        used = 0;

        this.logEnabled = logEnabled;

    }
    public MessageQueue(int size) {
       this(size, true);
    }

    public synchronized void put(byte[] message) {

        while (used == queue.length) {

            try {
                log(Arrays.toString(message) + " is waiting");
                this.wait();
            } catch (InterruptedException ignored) {

            }
        }

        queue[head++] = message.clone();
        used++;
        head %= queue.length;
        log("++ added " + Arrays.toString(message) + "; head: " + head);
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

        log("-- removed " + Arrays.toString(message) + "; tail: " + tail);

        used--;
        notifyAll();
        return message;
    }

    private void log(String txt) {
        if (!logEnabled) {
            return;
        }
        System.out.println(txt);
    }

    public synchronized byte[][] getQueue() {
        return queue.clone();
    }

    @Override
    public String toString() {
        return Arrays.toString(queue);
    }

}
