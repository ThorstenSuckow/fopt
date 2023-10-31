package fopt2.sandbox;

import java.util.Arrays;

public class BufferN {


    int[] queue;


    int head;
    int tail;

    int used;

    public BufferN(int size) {

        queue = new int[size];

        used = 0;
    }

    public synchronized void put(int x) {



        while (used == queue.length) {

            try {
                System.out.println(x + " is waiting");
                this.wait();
            } catch (InterruptedException ignored) {

            }
        }

        queue[head++] = x;
        used++;
        head %= queue.length;
        System.out.println("++ added " + x + "; head: " + head);
        notifyAll();
    }


    public synchronized int get() {

        while (used == 0) {
            try {
                this.wait();
            } catch (InterruptedException ignored) {

            }
        }

        int data = queue[tail];
        queue[tail++] = 0;
        tail %= queue.length;

        System.out.println("-- removed " + data + "; tail: " + tail);
        used--;
        notifyAll();
        return data;
    }

    public synchronized int[] getQueue() {
        return queue.clone();
    }

    @Override
    public String toString() {
        return Arrays.toString(queue);
    }

}
