package fopt1.sandbox;


class Lock {

    boolean isLocked = false;


    public synchronized void obtain()  {
        System.out.println(Thread.currentThread().getName() + " trying to obtain lock...");
        while (isLocked) {

            try {
                Thread.sleep(1);
            } catch (InterruptedException ignored) {
                System.out.println("InterruptedException!");
            }

        }
        isLocked = true;
        System.out.println(Thread.currentThread().getName() + " obtained lock!");

    }


    /**
     * Can be called even if the object is locked due to obtain()
     */
    public void release() {

        System.out.println(Thread.currentThread().getName() + " releases lock!");
        isLocked = false;


    }

    /**
     * Wont be processed if object is locked by obtain()
     */
    public synchronized void syncRelease() {

        isLocked = false;

        System.out.println(Thread.currentThread().getName() + " released lock (synced)");

    }

}


public class SynchronizedLocksObject {


    public static void main(String[] args) {

        Lock lock = new Lock();

        Thread t1 = new Thread(() -> lock.obtain());
        Thread t2 = new Thread(() -> lock.obtain());

        t1.start();
        t2.start();

        try {
            Thread.sleep(400);
            //lock.release(); // comment this line and uncomment the next one to see the effect of calling a synchronized
                            // method on an object that was locked by another thread
            lock.syncRelease(); // synchronized locks access to object locked by first thread , method cann ot be called


            t1.join();
            t2.join();
        } catch (InterruptedException ignored) {
            System.out.println("InterruptedException!");
        }

        System.out.println("Exiting.");

    }



}
