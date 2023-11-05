package fopt2.uebung12_3;

import java.util.concurrent.CountDownLatch;

class CountDownLatchWaiter extends Thread {

    CountDownLatch latch;
    public CountDownLatchWaiter(CountDownLatch cl) {
        latch = cl;
        this.start();
    }

    public void run() {

        try {
            latch.await();
            System.out.println("Happy New CountDownLatch from " + Thread.currentThread().getName());
        } catch (InterruptedException ignored) {

        }

    }
}


public class CountDownLatchDemo {

    public static void main(String[] args) {

        CountDownLatch latch = new CountDownLatch(5);

        Thread t1 = new Thread(()->{
            long number;
            while ((number = latch.getCount()) > 0) {
                System.out.println("... " + number);
                try {
                    latch.countDown();
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) {
                }
            }
        });

        CountDownLatchWaiter w1 = new CountDownLatchWaiter(latch);
        CountDownLatchWaiter w2 = new CountDownLatchWaiter(latch);

        t1.start();

    }

}
