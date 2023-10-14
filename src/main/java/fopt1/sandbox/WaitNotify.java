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

    public boolean checkAccess() {
        System.out.println("checking access to slots: " + slots);
        return true;
    }

    public synchronized void dropIn()  {

        System.out.println(Thread.currentThread().getName() + " entering dropIn()");


        // change "while" to "if" to see how threads can accidentally concur when not checking against
        // slots again
        while (slots == 0 & checkAccess()) {

            try {
                System.out.println(Thread.currentThread().getName() + " waiting for free slots! Free slots now:  " + slots);
                this.wait();
                System.out.println(Thread.currentThread().getName() + " resuming; slots are " + slots);

            } catch (InterruptedException ignored) {
                System.out.println("Unexpected InterruptedException");
            }

        }

        slots--;
        if (slots < 0) {
            throw new RuntimeException("i was less than 0");
        }
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

        Client[] clients = new Client[1000];

        for (int i = 0; i < clients.length; i++) {
            clients[i] = new Client(target);
        }

        for (Client client: clients) {
            client.start();
        }


      /*  try {
            c1.join();
            c2.join();
            c3.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
*/

        System.out.println("Exiting");
    }

}
