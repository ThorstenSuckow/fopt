package pp.counter;

import java.lang.InterruptedException;

public class BoundedCounter {


    private int min;
    private int max;
    private int value;


    public BoundedCounter(int min, int max) {

        if (max <= min) {
            throw new IllegalArgumentException();
        }

        this.min = min;
        this.max = max;
        this.value = min;
    }

    public synchronized void down() {

        while (value - 1 < min) {
            try {
                this.wait();
            } catch (InterruptedException ignored) {}
        }

        value--;
        notifyAll();
    }

    public synchronized void up() {

        while (value + 1 > max) {
            try {
                this.wait();
            } catch (InterruptedException ignored) {}
        }

        value++;
        notifyAll();
    }

    public synchronized int get() {
        return value;
    }

}