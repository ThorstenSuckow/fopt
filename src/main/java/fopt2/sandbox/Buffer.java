package fopt2.sandbox;

public class Buffer {

    int data;

    boolean dataAvailable;

    public synchronized void put(int x) {

        while (dataAvailable) {

            try {
                this.wait();
            } catch (InterruptedException ignored) {

            }
        }

        dataAvailable = true;
        data = x;

        notifyAll();

    }

    public synchronized int get() {

        while (!dataAvailable) {
            try {
                this.wait();
            } catch (InterruptedException ignored) {

            }
        }

        dataAvailable = false;
        notifyAll();
        return data;
    }

}
