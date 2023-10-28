package fopt2.sandbox.uebung9_2;


class SemaphoreAccessThread extends Thread {

    OrderedSemaphore semaphore;

    int order;

    public SemaphoreAccessThread(OrderedSemaphore semaphore, int order, String name) {
        this.semaphore = semaphore;

        setName(name);

        this.order = order;
    }

    public void run() {

        while (true) {

            semaphore.p(this.order);

            System.out.println(Thread.currentThread().getName() + " in Critical section");

           /*try {
                Thread.sleep((int)(100));
            } catch (InterruptedException ignores) {

            }*/

            semaphore.v();
        }

    }


}

public class OrderedSemaphoreDemo {



    public static void main(String[] args) {

        OrderedSemaphore sem = new OrderedSemaphore(3);

        SemaphoreAccessThread[] ths = {
            new SemaphoreAccessThread(sem, 1, "t1"),
            new SemaphoreAccessThread(sem, 3, "t2"),
            new SemaphoreAccessThread(sem, 2, "t3")
        };

        try {
            ths[0].start();
            Thread.sleep(10);
            ths[1].start();
            Thread.sleep(10);
            ths[2].start();
            Thread.sleep(10);

        } catch (InterruptedException ignored) {

        }
    }


}
