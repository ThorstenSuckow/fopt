package fopt2.sandbox;



public class GroupedSemaphore {

    private byte[] values;

    public GroupedSemaphore (int size) {
        values = new byte[size];
    }

    private boolean canUpdate(byte[] values) {

        for (int i = 0; i < values.length; i++) {

            if (this.values[i] - values[i] < 0) {
                return false;
            }
        }

        return true;
    }

    public void update (byte[] values) {

        for (int i = 0; i < values.length; i++) {
            this.values[i] -= values[i];
        }

    }


    public synchronized void p(byte[] values) {

        if (values.length != this.values.length) {
            throw new IllegalArgumentException();
        }

        while (!canUpdate(values)) {
            try {
                this.wait();
            } catch (InterruptedException ignored) {

            }
        }

        update(values);

        notifyAll();
    }


    public synchronized void v(byte[] values) {

        for (int i = 0; i < values.length; i++) {
            this.values[i] += values[i];
        }

        notifyAll();
    }


}
