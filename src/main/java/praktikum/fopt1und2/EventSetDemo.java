package praktikum.fopt1und2;

import java.util.Arrays;
import java.util.Random;

public class EventSetDemo {

    private static class EventSet {

        private boolean[] field;


        public EventSet(int length) {
            field = new boolean[length];
        }

        public synchronized void set(int pos, boolean value) {

            if (pos < 0 || pos >= field.length) {
                throw new IllegalArgumentException();
            }

            field[pos] = value;

            if (value) {
                log("[set] value was true, notifying all!");
                notifyAll();
            }
        }

        private void log(String msg) {
            System.out.println(msg);
        }

        public synchronized void waitAND() { // minterm

            while (hasFalse()) {
                try {
                    log("[waitAND] waiting: " + Arrays.toString(field));
                    wait();
                } catch (InterruptedException ignored) {}
            }

            log("[waitAND] passing: " + Arrays.toString(field));
        }

        public synchronized void waitOR() { // maxterm
            while (!hasTrue()) {
                try {
                    log("[waitOR] waiting: " + Arrays.toString(field));
                    wait();
                } catch (InterruptedException ignored) {}
            }


            log("[waitOR] passing: " + Arrays.toString(field));
        }

        private boolean hasFalse() {
            for (int i = 0; i < field.length; i++) {
                if (!field[i]) {
                    return true;
                }
            }
            return false;
        }

        private boolean hasTrue() {
            for (int i = 0; i < field.length; i++) {
                if (field[i]) {
                    return true;
                }
            }
            return false;
        }
    }



    public static void main(String[] args) {

        final int writerMax = 1;
        final int readerMax = 1;
        final int eventSetLength = 2;
        EventSet eventSet = new EventSet(eventSetLength);

        for (int i = 0; i < writerMax; i++) {
            new Thread(()->{
                Random r = new Random();
                while (true) {
                    eventSet.set(r.nextInt(eventSetLength), r.nextInt(2) % 2 == 0);
                    try {
                        Thread.sleep((r.nextInt(1) + 1) * 500);
                    } catch (InterruptedException ignroed){}
                }
            }).start();
        }

        for (int i = 0; i < readerMax; i++) {
            new Thread(()->{
                Random r = new Random();
                while (true) {
                    if (r.nextInt(2) % 2 == 0) {
                        eventSet.waitAND();
                    } else {
                        eventSet.waitOR();
                    }
                    try {
                        Thread.sleep((r.nextInt(1) + 1) * 1500);
                    } catch (InterruptedException ignroed){}
                }
            }).start();
        }

    }

}
