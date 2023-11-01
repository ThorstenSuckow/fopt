package fopt2.uebung10_2;


import fopt2.sandbox.Semaphore;

/**
 * BufferN w/o synchronize and wait.
 */
public class BufferedSemaphore {

    Semaphore mutexSem = new Semaphore(1);
    Semaphore readSem = new Semaphore(0);

    Semaphore writeSem;

    private int head;

    private int tail;

    private final int[] data;


    public BufferedSemaphore(int size) {
        writeSem = new Semaphore(size);
        data = new int[size];
    }

    public void write(int x) {
        // get access for writing
        writeSem.p();

        // close critical block
        mutexSem.p();

        data[head++] = x;
        head %= data.length;

        // unlock critical block for next reader or writer
        mutexSem.v();

        // data available, release lock for readers
        readSem.v();
    }

    public int read() {

        // get access for reading
        readSem.p();

        // lock critical block
        mutexSem.p();


        int d = data[tail];
        data[tail++] = 0;
        tail %= data.length;

        // unlock critical block
        mutexSem.v();

        // provide write access since reading has cleared some space
        writeSem.v();

        return d;
    }

}