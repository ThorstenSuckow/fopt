package klausurvorbereitung.uebungen;


public class PhilosophersDemo {

    static class SemaphoreGroup {
        int[] values;
        public SemaphoreGroup (int n) {
            values = new int[n];
            for (int i = 0; i < n; i++) {
                values[i] = 1;
            }
        }

        public synchronized void change(int[] set) {

            while (!canChange(set)) {
                try {
                    wait();
                } catch (InterruptedException ignored) {}
            }

            changeValues(set);
            notifyAll();
        }

        private void changeValues(int[] set) {
            for (int i = 0; i < set.length; i++) {
                values[i] =  values[i] + set[i];
            }
        }
        private boolean canChange(int[] set) {
            for (int i = 0; i < set.length; i++) {
                if (values[i] + set[i] < 0) {
                    return false;
                }
            }
            return true;
        }

    }
    static class Semaphore {
        boolean locked = false;
        public synchronized void acquire()  {
            while (locked) {
                try {
                    wait();
                }catch (InterruptedException ignored) {}
            }
            locked = true;
        }
        public synchronized void release() {
            locked = false;
            notify();
        }

    }
    static class Philosopher extends Thread {
        private Table table;
        private int position;
        private Semaphore eatLock;

        private SemaphoreGroup group;
        private int seats;
        public Philosopher(SemaphoreGroup g, int pos, int s) {
            group = g;
            position = pos;
            seats = s;
            start();
        }
        public Philosopher(Table t, int pos, Semaphore lock) {
            table = t;
            eatLock = lock;
            position = pos;
            start();
        }
        public void run() {
            while (true) {

                try {
                    int[] set = new int[seats];
                    set[position] = -1;
                    set[(position +1) % seats] = -1;
                    group.change(set);

                    System.out.println("[" + position + "] eating...");

                    set[position] = 1;
                    set[(position +1) % seats] = 1;
                    group.change(set);
                    System.out.println("[" + position + "] thinking...");

                } catch (Exception e) {} //InterruptedException ignored) {

            }
        }
    }
    static class Table {
        private final Semaphore[] forks;
        public Table(int howMany) {
            forks = new Semaphore[howMany];
            for (int i = 0; i < howMany; i++) {
                forks[i] = new Semaphore();
            }
        }

        public Table left(int philosopher, boolean release) {
            int forkPos = philosopher;
            forks[forkPos].release();
            if (release) {
                System.out.println("["+philosopher+"] releases " + forkPos);
                forks[forkPos].release();
            } else {
                System.out.println("["+philosopher+"] picks up " + forkPos);
                forks[forkPos].acquire();
            }
            return this;
        }
        public Table right(int philosopher, boolean release) {

            int forkPos = (philosopher + 1) % forks.length;
            if (release) {
                System.out.println("["+philosopher+"] releases " + forkPos);
                forks[forkPos].release();
            } else {
                System.out.println("["+philosopher+"] picks up " + forkPos);
                forks[forkPos].acquire();
            }
            return this;
        }
    }



    public static void main(String[] args) {

        int howMany = 5;
        Philosopher[] philosophers = new Philosopher[howMany];
        Table t = new Table(howMany);
        SemaphoreGroup group = new SemaphoreGroup(howMany);
        Semaphore eatLock = new Semaphore();
        for (int i = 0; i < howMany; i++) {
            philosophers[i] = new Philosopher(group, i, howMany);
        }

    }

}
