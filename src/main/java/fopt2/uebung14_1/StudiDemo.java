package fopt2.uebung14_1;

import java.util.concurrent.Semaphore;


class Studi extends Thread {

    private Semaphore buch1, buch2, buch3;

    private boolean shouldSleep;

    public Studi(Semaphore b1, Semaphore b2, Semaphore b3, boolean shouldSleep) {
        buch1 = b1;
        buch2 = b2;
        buch3 = b3;

        this.shouldSleep = shouldSleep;

        this.start();
    }

    private void seminararbeit() throws InterruptedException {

        log(getName() + " schreibt Seminararbeit...");

        if (shouldSleep) {
            try {
                Thread.sleep((int) (Math.random() * 2000));
            } catch (InterruptedException ignored) {

            }
        }
        log(getName() + " gibt Seminararbeit ab.");
    }

    private void log(String s) {
        System.out.println(s);
       // system.out.print("\r");
    }


    public void run()
    {
        while (true) {

            log(getName() + " will beginnen.");

            // 1. Buch, dann 2. Buch, dann 3. Buch belegen
            try {
                buch1.acquire();
                log(getName() + " hat Buch 1 genommen...");
                buch2.acquire();
                log(getName() + " hat Buch 2 genommen ...");
                buch3.acquire();
                log(getName() + " hat Buch 3 genommen...");

                seminararbeit();
            } catch (InterruptedException ignored) {

            } finally {
                // Seminararbeit erstellen
                // alle Bücher zurück
                buch1.release();
                log(getName() + " legt Buch 1 zurück.");

                buch2.release();
                log(getName() + " legt Buch 2 zurück.");

                buch3.release();
                log(getName() + " legt Buch 3 zurück.");

            }

        }
    }

}



public class StudiDemo {


    public static void main(String[] args) {

        Semaphore buch1 = new Semaphore(1);
        Semaphore buch2 = new Semaphore(1);
        Semaphore buch3 = new Semaphore(2);

        for (int i = 0; i < 20; i++) {
            /**
             * The order the objects will access the Semaphores is determined, (1. buch1, 2. buch2, 3. buch3).
             * thus no deadlock will occur in this case.
             * Uncomment the following lines so that semaphores gets mixed up:
             * Some threads will now access buch2 first instead of buch3, which
             * is essentially the philosophers problem: In some cases, all buch1
             * semaphores will be used by Threads A, and all buch2 semaphores will
             * be used by Threads B. This will yield a deadlock, because Thread A waits for
             * buch2, and Thread B waits for buch1.
             */
            /*if (i > 10) {
                Studi s = new Studi(buch2, buch1, buch3, false);
                continue;
            }*/
            Studi s = new Studi(buch1, buch2, buch3, false);

        }


    }


}