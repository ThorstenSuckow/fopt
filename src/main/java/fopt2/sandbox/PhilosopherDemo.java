package fopt2.sandbox;

class Philosopher implements Runnable {

    Table table;

    int seat;

    public Philosopher(Table table, int seat) {
        this.table = table;
        this.seat = seat;
    }

    public void run() {

        while (true) {

            table.eat(seat);
            System.out.println("Philosopher " + seat + " eats!");


            try {
                Thread.sleep((int)(Math.random() * 1000));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            table.think(seat);
            System.out.println("Philosopher " + seat + " thinks...");

            try {
                Thread.sleep((int)(Math.random() * 1000));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }


}

abstract class Table {

    abstract void eat(int seat);
    abstract void think(int seat);

    public int leftFork(int seat) {
        return seat;
    }

    public int rightFork(int seat) {
        return (seat + 1) % getSeats();
    }

    abstract int getSeats();
}

class SemaphoredTable extends Table {
    private Semaphore[] forks;
    public SemaphoredTable(int seats) {

        forks = new Semaphore[seats];

        for (int i = 0; i < seats; i++) {
            forks[i] = new Semaphore(1);
        }

    }

    public int getSeats() {
        return forks.length;
    }

    @Override
    void eat(int seat) {

        Semaphore left = forks[leftFork(seat)];
        Semaphore right = forks[rightFork(seat)];

        // note: if all threads obtain the left fork first, no-one can access
        // the right fork, since its obtained by the next thread (the right philosopher)
        left.p();
        right.p();

    }

    @Override
    void think(int seat) {
        Semaphore left = forks[leftFork(seat)];
        Semaphore right = forks[rightFork(seat)];

        left.v();
        right.v();
    }
}

class SynchronizedTable extends Table {

    protected byte[] forks;

    public SynchronizedTable(int seats) {
        forks = new byte[seats];
    }

    public int getSeats() {
        return forks.length;
    }

    public synchronized void eat(int seat) {

        int left = leftFork(seat);
        int right = rightFork(seat);

        while (forks[left] != 0 || forks[right] != 0) {

            try {
                System.out.println ("Philosopher " + seat + " waiting for forks " + left + ", " + right);
                this.wait();
            } catch (InterruptedException ignored) {

            }

        }

        forks[left] = 1;
        forks[right] = 1;

        notifyAll();

    }

    @Override
    public synchronized void think(int seat) {

        int left = leftFork(seat);
        int right = rightFork(seat);

        forks[left] = 0;
        forks[right] = 0;

        notifyAll();
    }


}

public class PhilosopherDemo {

    public static void main (String[] args) {

        //Table t = new SynchronizedTable(4);
        Table t = new SemaphoredTable(4);

        Thread p1 = new Thread(new Philosopher(t, 0));
        Thread p2 = new Thread(new Philosopher(t, 1));
        Thread p3 = new Thread(new Philosopher(t, 2));
        Thread p4 = new Thread(new Philosopher(t, 3));

        p1.start();
        p2.start();
        p3.start();
        p4.start();

    }



}
