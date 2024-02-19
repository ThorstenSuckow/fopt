package praktikum.fopt1und2;

import java.util.Random;

public class BoundedCounterDemo {

    private static class BoundedCounter {

        private int value;
        private int min;
        private int max;
        public BoundedCounter(int min, int max) {

            this.min = min;
            this.max = max;
            this.value = min;
        }

        public synchronized int get() { // eigentlich ohne synchronized, mit volatile,
                                        // aber Lehrbuch sagt, wir nutzen nur synchronized
            return value;
        }

        public synchronized void up() {

            while (value + 1 > max) {
                try {
                    log("[up] " + Thread.currentThread().getName() + " waiting...");
                    this.wait();
                } catch (InterruptedException ignored) {
                }
            }

            value++;
            log("    [up] " + Thread.currentThread().getName() + " set: " + value);
            notifyAll();
        }

        public synchronized void down() {
            while (value - 1 < min) {
                try {
                    log("[down] " + Thread.currentThread().getName() + " waiting...");
                    this.wait();
                } catch (InterruptedException ignored) {
                }
            }

            value--;
            log("    [down] " + Thread.currentThread().getName() + " set: " + value);
            notifyAll();
        }

        private void log(String msg) {
            System.out.println(msg);
        }
    }


    @SuppressWarnings("checkstyle:LeftCurly")
    public static void main(String[] args) {

        BoundedCounter bc = new BoundedCounter(1, 2);
        final int maxThreads = 2;

        for (int i = 0; i < maxThreads; i++) {

            if (i % 2 == 0) {
                new Thread(()->{while (true) {bc.up();}}).start();
            } else {
                new Thread(()->{while(true){bc.down();}}).start();
            }
        }

    }

}
