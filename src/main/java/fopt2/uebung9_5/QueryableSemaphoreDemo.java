package fopt2.uebung9_5;


class SemaphoreAccessThread extends Thread {

    QueryableSemaphore semaphore;

    public SemaphoreAccessThread(QueryableSemaphore semaphore) {

        this.semaphore = semaphore;

    }

    public void run() {

        while (true) {

            semaphore.p();

            System.out.println(Thread.currentThread().getName() + " in Critical section");

            try {
                Thread.sleep((int)(100));
            } catch (InterruptedException ignored) {

            }

            System.out.println(semaphore.getWaitingListSize() + " threads waiting to acquire the Semaphore.");

            semaphore.v();
        }

    }


}

public class QueryableSemaphoreDemo {

    public static void main(String[] args) {

        QueryableSemaphore qsem = new QueryableSemaphore(1);
        SemaphoreAccessThread t1 = new SemaphoreAccessThread(qsem);
        SemaphoreAccessThread t2 = new SemaphoreAccessThread(qsem);
        SemaphoreAccessThread t3 = new SemaphoreAccessThread(qsem);

        t1.start();
        t2.start();
        t3.start();


    }

}
