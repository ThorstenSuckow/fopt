package fopt2.uebung12_2;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class AdditiveSemaphoreLockThread extends Thread {

    int x;

    AdditiveSemaphoreLock sem;

    public AdditiveSemaphoreLockThread(int x, AdditiveSemaphoreLock sem) {
        this.x = x;
        this.sem = sem;
    }

    public void run()  {

        while (true) {
            sem.p(x);
            System.out.println(getName() + " entering critical area");
            try {
                Thread.sleep((int)(Math.random() * 1000));
            } catch (InterruptedException ignored) {

            }
            sem.v(x);
        }

    }

}

class AdditiveSemaphoreLock {

    ReentrantLock lock;
    int value;
    Condition valueCondition;

    public AdditiveSemaphoreLock(int value) {

        this.value = value;
        lock = new ReentrantLock();

        valueCondition = lock.newCondition();
    }

    public void p(int v) {
        lock.lock();

        try {
            while (value - v < 0) {
                valueCondition.awaitUninterruptibly();
            }
            value -= v;
        } finally {
            lock.unlock();
        }
    }


    public void v(int v){
        lock.lock();

        value += v;
        try {
            valueCondition.signalAll();
        } finally {
            lock.unlock();
        }
    }
}



public class AdditiveSemaphoreLockDemo {



    public static void main (String[] args) {

        AdditiveSemaphoreLock sem = new AdditiveSemaphoreLock(4);

        AdditiveSemaphoreLockThread t1 = new AdditiveSemaphoreLockThread(3, sem);
        AdditiveSemaphoreLockThread t2 = new AdditiveSemaphoreLockThread(2, sem);

        t1.start();
        t2.start();

    }


}
