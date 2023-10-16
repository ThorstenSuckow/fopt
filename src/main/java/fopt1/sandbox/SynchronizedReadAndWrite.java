package fopt1.sandbox;

import java.util.ArrayList;
import java.util.List;

class Producer extends Thread {

    int value;
    public synchronized void produce() {
        value = (int) (Math.random() * 1000);
        System.out.println("Produced " + value + ", notifying consumer...");
        this.notify();
    }


    public void run() {
        while (true) {

            if (value == 0) {
                produce();
            }

           /* try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {

            }*/
        }
    }

    public synchronized int retrieve() {

        while (value == 0) {
            try {
                System.out.println("value is 0, waiting for producer");
                this.wait();
            } catch (InterruptedException ignored) {

            }
        }

        int ret = value;
        value = 0;
        return ret;
    }


}


class Consumer extends Thread {

    Producer p;
    public Consumer(Producer p) {
        this.p = p;
    }

    public void run() {
        int v = p.retrieve();
        System.out.println("value is "+ v + ", retrieving");
    }

}


/**
 * Consumer reads a value from Producer if Producer has produced a value.
 * The consumer is on hold until notified by the Producer.
 */
public class SynchronizedReadAndWrite {


    public static void main(String[] args) {

        List<Consumer> consumerList = new ArrayList<>();
        Producer p = new Producer();
        p.start();

        for (int i = 0; i < 100; i++) {
            Consumer c = new Consumer(p);
            consumerList.add(c);
            c.start();
        }


        try {
          p.join();

        } catch (InterruptedException ignored) {

        }


    }

}
