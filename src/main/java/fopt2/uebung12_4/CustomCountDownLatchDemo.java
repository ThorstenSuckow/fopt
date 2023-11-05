package fopt2.uebung12_4;



import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class CountDownLatchThread extends Thread {

    CountDownLatch latch;
    public CountDownLatchThread(CountDownLatch cl) {
        latch = cl;
        this.start();
    }

    public void run () {

        long number;
        while ((number = latch.getCount()) != 0) {
            System.out.println("... " + number);
            latch.countDown();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

}

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

class CountDownLatch {

    long number;

    ReentrantLock lock;

    Condition zeroCondition;

    public CountDownLatch(long number) {

        if (number < 0) {
            throw new IllegalArgumentException();
        }
        this.number = number;
        lock = new ReentrantLock();
        zeroCondition = lock.newCondition();
    }

    public long getCount() {
        lock.lock();
        try {
            return number;
        } finally {

            lock.unlock();
        }

    }

    public void countDown() {
        lock.lock();

        try {
            number--;

            if (number == 0) {
                zeroCondition.signalAll();
            }
        } finally {
            lock.unlock();
        }
    }

    public void await() throws InterruptedException{
        lock.lock();

        try {
            while (number > 0) {
                zeroCondition.await();
            }
        } finally {
            lock.unlock();
        }
    }


}


public class CustomCountDownLatchDemo {

    public static void main (String[] args) {
        CountDownLatch cl = new CountDownLatch(5);
        CountDownLatchThread t = new CountDownLatchThread(cl);
        CountDownLatchWaiter w1 = new CountDownLatchWaiter(cl);
        CountDownLatchWaiter w2 = new CountDownLatchWaiter(cl);
        CountDownLatchWaiter w3 = new CountDownLatchWaiter(cl);
    }


}
