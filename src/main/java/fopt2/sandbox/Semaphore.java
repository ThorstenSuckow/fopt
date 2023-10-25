package fopt2.sandbox;


final class Semaphore {

    private int value = 0;


    public Semaphore(int value) {

        if (value < 0) {
            throw new IllegalArgumentException();
        }

        this.value = value;

    }

    public synchronized void p() {

        while (this.value == 0) {
            try {
                this.wait();
            } catch (InterruptedException ignored) {

            }
        }

        this.value--;

    }

    public synchronized void v() {
        this.value++;
        notify();
    }

}
