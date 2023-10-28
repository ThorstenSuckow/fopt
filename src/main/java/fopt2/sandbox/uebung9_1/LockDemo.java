package fopt2.sandbox.uebung9_1;

class LockAccessThread implements Runnable {

    Lock l;

    public LockAccessThread(Lock lock) {
        l = lock;
    }

    public void run() {

        while (true) {

            l.lock();

            System.out.println(Thread.currentThread().getName() + " in Critical section");

            try {
                Thread.sleep((int)(Math.random() * 100));
            } catch (InterruptedException ignores) {

            }

            l.unlock();
        }

    }


}


public class LockDemo {


    public static void main(String[] args) {

        Lock lock = new Lock();

        Thread t1 = new Thread(new LockAccessThread(lock), "t1");
        Thread t2 = new Thread(new LockAccessThread(lock), "t2");
        Thread t3 = new Thread(new LockAccessThread(lock), "t3");

        t1.start();
        t2.start();
        t3.start();

    }

}
