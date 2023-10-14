package fopt1.sandbox;


class Client extends Thread {

    Target target;
    public Client(Target target) {
        this.target = target;
    }

    public void run() {

        long sleepEnter = (long) (Math.random() * 1000);
        long sleepExit = (long) (Math.random() * 2000);

        System.out.println("Found " + target.slots + " free slots. " + Thread.currentThread().getName() + " trying to enter after " + sleepEnter + "ms");

        try {
            Thread.sleep(sleepEnter);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        target.dropIn();

        try {
            Thread.sleep(sleepExit);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        target.dropOut();
    }


}

class Target {

    public int slots = 1;

    public synchronized void dropIn()  {

        while (slots == 0) {

            try {
                System.out.println(Thread.currentThread().getName() + " waiting for free slots! Free slots now:  " + slots);
                this.wait();
            } catch (InterruptedException ignored) {
                System.out.println("Unexpected InterruptedExcepion");
            }

        }

        slots--;
        System.out.println(Thread.currentThread().getName() + " entered! Free slots now:  " + slots);
    }


    public synchronized void dropOut() {

        slots++;
        System.out.println(Thread.currentThread().getName() + " exited! Free slots now:  " + slots);
        this.notify();

    }

}

public class WaitNotify {



    public static void main(String[] args) {

        Target target = new Target();

        Client c1 = new Client(target);
        Client c2 = new Client(target);

        c1.start();
        c2.start();

        try {
            c1.join();
            c2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        System.out.println("Exiting");
    }

}
