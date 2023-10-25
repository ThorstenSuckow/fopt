package fopt2.sandbox;


/**
 * The order in which the threads are executed is hardcoded in the Thread's run() method
 */

abstract class OrderThread extends Thread {

    Semaphore[] sem;


    public OrderThread(Semaphore[] sem) {
        this.sem = sem;
    }

    public void execute() {
        System.out.println(getName() + " is running");

    }

}

class T1 extends OrderThread {
    public T1(Semaphore[] sem) {
        super(sem);
        start();
    }

    public void run() {
        execute();
        sem[0].v();

    }
}

class T2 extends OrderThread {
    public T2(Semaphore[] sem) {
        super(sem);
        start();
    }

    public void run() {
        sem[0].p();
        execute();
        sem[1].v();
    }
}

class T3 extends OrderThread {
    public T3(Semaphore[] sem) {
        super(sem);
        start();
    }

    public void run() {
        sem[1].p();
        execute();
    }
}

public class OrderedThreadProcessDemo {


    public static void main(String[] args) throws InterruptedException {
        Semaphore semaphore1 = new Semaphore(0);
        Semaphore semaphore2 = new Semaphore(0);
        Semaphore semaphore3 = new Semaphore(0);

        Semaphore[] sem = {semaphore1, semaphore2, semaphore3};

        T1 t1 = new T1(sem);
        T2 t2 = new T2(sem);
        T3 t3 = new T3(sem);


    }
}
