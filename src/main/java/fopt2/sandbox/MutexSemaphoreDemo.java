package fopt2.sandbox;


class MutexThread extends Thread {


    private Semaphore sem;
    public MutexThread(Semaphore sem) {

        this.sem = sem;
        start();
    }


    public void run() {

        // uncomment while() to make sure threads execute cricticalBlock in a loop
        while (true) {
            sem.p();

            criticalBlock: {
                int foo = 1;
                System.out.println(getName() + " enter critical");


                try {
                    Thread.sleep((int) (Math.random() * 1000));
                } catch (InterruptedException ignored) {

                }
            }
            System.out.println(getName() + " exit critical");
            sem.v();
        }

    }



}

public class MutexSemaphoreDemo {


    public static void main(String[] args) {

        Semaphore semaphore = new Semaphore(1);

        MutexThread t1 = new MutexThread(semaphore);
        MutexThread t2 = new MutexThread(semaphore);
        MutexThread t3 = new MutexThread(semaphore);

    }

}
