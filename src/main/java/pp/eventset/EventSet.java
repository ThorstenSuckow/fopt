package pp.eventset;

public class EventSet {

    final private boolean[] set;

    public EventSet(int n) {
        if (n < 0) {
            throw new IllegalArgumentException();
        }

        set = new boolean[n];
    }

    public synchronized void set(int pos, boolean value) {
        if (pos < 0 || pos >= set.length) {
            throw new IllegalArgumentException();
        }

        this.set[pos] = value;
        if (value) {
            notifyAll();
        }
    }

    public synchronized void waitAND() {

        while(!allTrue()) {
            try {
                this.wait();
            } catch (InterruptedException ignored) {}
        }

        notifyAll();
    }

    public synchronized void waitOR() {

        while (!oneTrue()) {
            try {
                this.wait();
            } catch (InterruptedException ignored) {}
        }
        notifyAll();
    }


    private boolean allTrue() {
        for (boolean b : set) {
            if (!b) {
                return false;
            }
        }

        return true;
    }

    private boolean oneTrue() {
        for (boolean b : set) {
            if (b) {
                return true;
            }
        }

        return false;
    }
}
