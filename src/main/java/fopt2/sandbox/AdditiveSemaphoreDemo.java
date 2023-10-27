package fopt2.sandbox;


class AdditiveThread extends Thread {

    int x;

    Semaphore sem;

    public AdditiveThread(int x, Semaphore sem) {
        this.x = x;
        this.sem = sem;
    }

    public void run() {

        while (true) {


            /*
            // deadlock if you uncomment the following lines
            for (int i = 0; i < x; i++) {
                sem.p(1);
            }
            */
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

public class AdditiveSemaphoreDemo {


    public static void main(String[] args) {

        Semaphore sem = new Semaphore(4);

        AdditiveThread t1 = new AdditiveThread(3, sem);
        AdditiveThread t2 = new AdditiveThread(3, sem);

        t1.start();
        t2.start();

    }
}
