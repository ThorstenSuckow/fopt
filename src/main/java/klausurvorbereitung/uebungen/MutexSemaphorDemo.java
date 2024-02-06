package klausurvorbereitung.uebungen;

public class MutexSemaphorDemo {

    static class MutexSemaphor {

        private int n = 1;

        public synchronized void p() {
            while (n < 1) {
                try {
                    wait();
                } catch (InterruptedException ignored) {}
            }

            n--;
        }

        public synchronized void v() {
            n=1;
            notify();
        }
    }

    static class Waiter extends Thread{
        MutexSemaphor sem;
        String name;
        public Waiter(MutexSemaphor s, String n) {
            sem = s;
            name = n;
            start();
        }

        public void enter() {
                String currentThread = Thread.currentThread().getName();
                sem.p();
                System.out.println(currentThread + " enters critical section of [" + name + "]!");
                try {
                    Thread.currentThread().sleep((int) (Math.random() * 100));
                } catch (InterruptedException ignored) {}
                System.out.println(currentThread + " exits [" + name + "]");
                sem.v();
        }

    }

    public static void main(String[] args) throws InterruptedException {
        MutexSemaphor sem = new MutexSemaphor();
        Waiter waiter1 = new Waiter(sem, "w1");
        Waiter waiter2 = new Waiter(sem, "w2");
        Waiter waiter3 = new Waiter(sem, "w3");
        Thread t1 = new Thread(waiter1::enter);
        Thread t2 = new Thread(waiter2::enter);
        Thread t3 = new Thread(waiter3::enter);

        t1.start();t2.start();t3.start();
        t1.join();t2.join();t3.join();
    }

}
