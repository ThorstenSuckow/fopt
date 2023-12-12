package fopt2.uebung11_1;

abstract class Accessor extends Thread {

    Target target;

    public Accessor(Target target) {
        this.target = target;
    }

}

class Reader extends Accessor {
    public Reader(Target target) {
        super(target);
        this.start();
    }

    public void run() {
        while (true) {
            target.read();
            /*try {
                Thread.sleep((long)(Math.random() * 1));
            } catch (InterruptedException ignored) {

            }*/
        }
    }

}

class Writer extends Accessor {

    public Writer(Target target) {
        super(target);
        this.start();
    }

    public void run() {
        while (true) {
            int data = (int) (Math.random() * 100);
            target.write(data);
           /* try {
                Thread.sleep((long)(Math.random() * 1));
            } catch (InterruptedException ignored) {

            }*/
        }
    }

}


class Target {

    int data;
    int activeReaders;
    int activeWriters;

    int nextWaitingNumber;

    int nextEntryNumber;

    private synchronized void beforeRead() {

        int myTicket = nextWaitingNumber++;

        while (myTicket != nextEntryNumber || activeWriters > 0) {
            try {
                System.out.println("  [reader] " + Thread.currentThread().getName() + " waiting...");
                this.wait();
            } catch (InterruptedException ignored) {

            }
        }


        System.out.println("  [reader]  can read.");
        activeReaders++;
        nextEntryNumber++;
        // required if there is a Thread with a larger waitingNumber in queue -
        // notifying makes sure an activeReader is added if this threads waitingNumber
        // equals to nextEntryNumber
        notifyAll();
    }

    public synchronized void afterRead() {
        activeReaders--;
        notifyAll();
    }

    private synchronized void beforeWrite() {

        int myTicket = nextWaitingNumber++;

        while (myTicket != nextEntryNumber || activeWriters > 0 || activeReaders > 0) {
            try {
                System.out.println("[writer] " + Thread.currentThread().getName() + " waiting...");
                this.wait();
            } catch (InterruptedException ignored) {

            }
        }

        activeWriters++;
        nextEntryNumber++;
    }

    public synchronized void afterWrite() {
        activeWriters--;
        notifyAll();
    }

    public int read() {

        beforeRead();

        System.out.println("  [reader] " + Thread.currentThread().getName() + " reads " + data);

        afterRead();
        return data;
    }


    public void write(int data) {

        beforeWrite();

        System.out.println("[writer] " + Thread.currentThread().getName() + " writes " + data);

        this.data = data;

        afterWrite();
    }


}


public class FairReaderWriterDemo {

    public static void main (String[] args) {

        Target t = new Target();

        Reader r1 = new Reader(t);
        Reader r2 = new Reader(t);
        Reader r3 = new Reader(t);
        Reader r4 = new Reader(t);

        Writer w1 = new Writer(t);
        Writer w2 = new Writer(t);
        Writer w3 = new Writer(t);
        Writer w4 = new Writer(t);
        Writer w5 = new Writer(t);
        Writer w6 = new Writer(t);

    }

}
