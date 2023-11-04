package fopt2.uebung12_1;


class ReentrantLockThread extends Thread {

    private ReentrantLock lock;

    private final static int SLEEP = 1;
    public ReentrantLockThread(ReentrantLock l) {
        lock = l;
        this.start();
    }


    public void run() {

        boolean doubleLock = false;
        try {
            while (true) {

                System.out.println(getName() + " trying to get lock...");
                lock.lock();
                int loop = ((int)(Math.random() * 10));
                if (loop % 2 == 0) {
                    doubleLock = true;
                    System.out.println(getName() + " trying to get lock again...");
                    for (int i = 0; i < loop; i++) {
                        Thread.sleep((int) (Math.random() * SLEEP));
                        lock.lock();
                    }
                }
                Thread.sleep((int) (Math.random() * SLEEP));

                if (doubleLock) {
                    for (int i = 0; i < loop; i++) {
                        Thread.sleep((int) (Math.random() * SLEEP));
                        lock.unlock();
                    }
                }
                lock.unlock();
                Thread.sleep((int) (Math.random() * SLEEP));

                doubleLock = false;
            }
        } catch (InterruptedException ignored) {

        } finally {
            lock.unlock();
        }
    }

}

class ReentrantLock {

    private int lockCount = 0;

    private Thread threadHolder;


    public synchronized int lockCount() {
            return lockCount;
    }

    public synchronized void lock() {

        Thread t = Thread.currentThread();

        while (lockCount != 0 && threadHolder != t) {
            try {
                System.out.println(t.getName() + " waiting...");
                this.wait();
            } catch (InterruptedException ignored) {

            }
        }

        if (threadHolder == t) {
            System.out.println("  +++ " + Thread.currentThread().getName() + " re-acquires lock. " + (lockCount()));
        } else {
            System.out.println(Thread.currentThread().getName() + " acquires lock. " + (lockCount()));
        }

        threadHolder = t;
        lockCount++;
        // this would allow to check for the condition for all
        // waiting threads, so that the owning thread could re-enter
        // @see unlock()
        // notifyAll();
    }

    public synchronized void unlock() {
        lockCount--;
        System.out.println(Thread.currentThread().getName() + " released  lock. " + (lockCount()));

        if (lockCount == 0) {
            threadHolder = null;
        }
        // notify all in case current lockHolder wants to re-acquire lock and
        // is not next in waiting queue. This would mean to give the owning thread priority
        // notifyAll();
        notify();
    }
}

public class ReentrantLockDemo {


    public static void main(String args[]) {

        ReentrantLock lock = new ReentrantLock();

        ReentrantLockThread t1 = new ReentrantLockThread(lock);
        ReentrantLockThread t2 = new ReentrantLockThread(lock);
        ReentrantLockThread t3 = new ReentrantLockThread(lock);
        ReentrantLockThread t4 = new ReentrantLockThread(lock);
        ReentrantLockThread t5 = new ReentrantLockThread(lock);
        ReentrantLockThread t6 = new ReentrantLockThread(lock);


    }



}
