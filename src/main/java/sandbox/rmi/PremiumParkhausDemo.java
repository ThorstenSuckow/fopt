package sandbox.rmi;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class PremiumParkhausDemo {

    private static class PremiumParkhaus {

        private int slots;
        private int premiumWaiting = 0;
        private int normalWaiting = 0;
        private ReentrantLock einfahrt;

        private Condition premiumKunde;

        private Condition normalKunde;

        public PremiumParkhaus(int slots) {
            einfahrt = new ReentrantLock();
            premiumKunde = einfahrt.newCondition();
            normalKunde = einfahrt.newCondition();
            this.slots = slots;
        }
        public void leave() {

            try {
                einfahrt.lock();
                System.out.println("leaving:: " + premiumWaiting + " " + normalWaiting + " " + slots);
                slots++;

                if (premiumWaiting != 0) {
                    premiumKunde.signal();
                } else if (normalWaiting != 0){
                    normalKunde.signal();
                }
            } finally {
                einfahrt.unlock();
            }
        }

        public void enter() {

            try {
                einfahrt.lock();

                normalWaiting++;
                while (slots == 0 || premiumWaiting != 0) {
                    System.out.println("[normal] " + Thread.currentThread().getName() + " waiting...");
                    normalKunde.awaitUninterruptibly();
                }
                System.out.println("    [normal] " + Thread.currentThread().getName() + " entering!");
                slots--;
                normalWaiting--;
            } finally {
                einfahrt.unlock();
            }
        }

        public void enterPremium() {

            try {
                einfahrt.lock();

                premiumWaiting++;

                while (slots == 0) {
                    System.out.println("[premium] " + Thread.currentThread().getName() + " waiting...");
                    premiumKunde.awaitUninterruptibly();
                }
                System.out.println("    [premium] " + Thread.currentThread().getName() + " entering!");
                premiumWaiting--;
                slots--;

                if (premiumWaiting == 0 && slots != 0 && normalWaiting >= 1) {
                    System.out.println("    [premium] " + Thread.currentThread().getName() + " signaling " + normalWaiting);
                    if (normalWaiting == 1) {
                        normalKunde.signal();
                    } else {
                        normalKunde.signalAll();
                    }
                }

            } finally {
                einfahrt.unlock();
            }
        }

    }

    public static void main(String... args) throws InterruptedException {

        PremiumParkhaus p = new PremiumParkhaus(5);

        Runnable premium = p::enterPremium;
        Runnable normal = p::enter;

        new Thread(premium).start();
        Thread.sleep(100);
        new Thread(premium).start();
        Thread.sleep(100);

        new Thread(premium).start();
        Thread.sleep(100);
        new Thread(premium).start();
        Thread.sleep(100);
        Thread leaver = new Thread(premium);
        leaver.start();
        Thread.sleep(100);

        new Thread(premium).start();
        Thread.sleep(100);

        new Thread(p::leave).start();
        Thread.sleep(100);
        new Thread(p::leave).start();
        Thread.sleep(100);

        new Thread(normal).start();
        Thread.sleep(100);
        new Thread(normal).start();
        Thread.sleep(100);

        new Thread(p::leave).start();
        Thread.sleep(100);


    }

}
