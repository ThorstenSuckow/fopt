package klausurvorbereitung.uebungen;

import java.util.ArrayList;
import java.util.List;

public class FairLockDemo {

    private static class FairLock {


        private List<Thread> list;

        private boolean locked;

        public FairLock() {
            list = new ArrayList<Thread>();
            locked = true;
        }


        public synchronized void lock() throws InterruptedException {

            Thread t = Thread.currentThread();
            locked = true;
            list.add(t);
            try {
                while (!t.isInterrupted() && locked || (!list.isEmpty() && list.get(0) != t)) {
                    wait();
                }
            } finally {
                list.remove(t);
                System.out.println(t.getName() + " leaving!");
                notifyAll();
            }
        }

        public synchronized void unlock() {
            if (list.isEmpty()) {
                throw new RuntimeException();
            }

            locked = false;
            notifyAll();
        }


    }



    public static void main(String ...args) throws InterruptedException {

        FairLock lock = new FairLock();
        Runnable r = ()-> {
            try {
                lock.lock();
            } catch (InterruptedException e) {
                System.err.println(e);
            }
        };



        new Thread(r).start();
        Thread.sleep(100);
        Thread t3 = new Thread(r);
        t3.start();
        t3.interrupt();
        Thread.sleep(100);

        new Thread(r).start();
        Thread.sleep(100);
        new Thread(r).start();

        Thread.sleep(100);

        new Thread(()->lock.unlock()).start();
    }
}
