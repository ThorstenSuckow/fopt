package fopt2.sandbox.uebung9_1;

public class Lock {

    boolean locked;

    public Lock() {

    }



    public synchronized void lock() {

        while (locked) {

            try {
                System.out.println(Thread.currentThread().getName() + " waiting ...");
                this.wait();
            } catch (InterruptedException ignored) {

            }
        }


        System.out.println(Thread.currentThread().getName() + " obtaining lock!");
        locked = true;

    }

    public synchronized void unlock() {

        /**
         * notify() only required if we are unlocking!
         */
        if (locked) {
            locked = false;
            notify();
        }
    }


}
