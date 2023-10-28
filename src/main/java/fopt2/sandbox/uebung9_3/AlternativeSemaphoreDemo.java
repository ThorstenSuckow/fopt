package fopt2.sandbox.uebung9_3;


import fopt2.sandbox.Semaphore;

class AlternatingSemaphoreAccessThread extends Thread {

    Semaphore semaphore1;
    Semaphore semaphore2;

    String text;

    int order = -1;

    public AlternatingSemaphoreAccessThread(
        Semaphore semaphore1,
        Semaphore semaphore2,
        String text
    ) {
        this.semaphore1 = semaphore1;
        this.semaphore2 = semaphore2;
        this.text = text;

    }

    public AlternatingSemaphoreAccessThread(
        Semaphore semaphore1,
        Semaphore semaphore2,
        int order,
        String text
    ) {
        this.semaphore1 = semaphore1;
        this.semaphore2 = semaphore2;
        this.order= order;
        this.text = text;
    }


    public void run() {

        if (order != -1) {
            this.altRun();
        }

        while (true) {

            // initialized with value = 1, t1 can start immediately
            semaphore1.p();

            System.out.println(text + " - " + order);

            // sem2 can start once  t2 is done
            semaphore2.v();

        }
    }


    public void altRun() {



        while (true) {

            if (order == 1) {

                // t1 has an interest going into the critical area
                semaphore1.p();

            } else if (order == 2) {

                // t2 starts and allows t1 to proceed, then announces interest
                // to go into cirtical area in turn
                semaphore1.v();
                semaphore2.p();
            }

            System.out.println(text + " - " + order);

            try {
                Thread.sleep((int)(100));
            } catch (InterruptedException ignores) {

            }

            if (order == 1) {
                // t2 has previously announced its interest - t1 will
                // make sure t2 is allowed to proceed
                semaphore2.v();
            }
        }

    }


}

public class AlternativeSemaphoreDemo {

    public static void main(String[] args) {

        if (args.length > 0 && args[0] == "id") {

            System.out.println("Semaphore identifies requesting threads:");

            // solution w/ identifying threads in the Semaphore
            Semaphore sem1 = new Semaphore(0);
            Semaphore sem2 = new Semaphore(0);

            AlternatingSemaphoreAccessThread t1 = new AlternatingSemaphoreAccessThread(sem1, sem2, 1, "(alt.) ich bin der eine");
            AlternatingSemaphoreAccessThread t2 = new AlternatingSemaphoreAccessThread(sem1, sem2, 2, "(alt.) ich bin der andere");

            t1.start();
            t2.start();
        } else {

            System.out.println("Semaphores initialized w/ 1 - 0:");
            // Solution w/ Semaphore1 intialized with 1
            Semaphore sem1 = new Semaphore(1);
            Semaphore sem2 = new Semaphore(0);

            AlternatingSemaphoreAccessThread t1 = new AlternatingSemaphoreAccessThread(sem1, sem2,  "ich bin der eine");
            // shuffle semaphores in the next line
            AlternatingSemaphoreAccessThread t2 = new AlternatingSemaphoreAccessThread(sem2, sem1,  "ich bin der andere");


            t1.start();
            t2.start();
        }

    }


}
