package fopt6.sandbox.util;


public class Counter {

    private int value = 0;

    public synchronized void increment() {
        value++;
    }

    public synchronized void reset() {
        value = 0;
    }


    public String toString() {
        return super.toString() + " " + value;
    }

}
