package fopt2.sandbox;


/**
 * Semaphore / Additive Semaphore implementation.
 */
public class Semaphore {

    protected int value = 0;

    public Semaphore() {
        value = 1;
    }

    public Semaphore(int value) {

        if (value < 0) {
            throw new IllegalArgumentException();
        }

        this.value = value;

    }

    public synchronized void p() {
        while (value == 0) {
            try {
                this.wait();
            } catch (InterruptedException ignored) {

            }
        }

        value--;
    }


    public synchronized void p(int x) {

        if (x < 0) {
            throw new IllegalArgumentException();
        }

        while (value - x < 0) {
            try {
                this.wait();
            } catch (InterruptedException ignored) {

            }
        }

        value -= x;
    }


    public synchronized void v() {
        this.value++;
        notify();
    }


    public synchronized void v(int x) {

        if (x < 0) {
            throw new IllegalArgumentException();
        }

        this.value += x;
        notifyAll();
    }

}
