package pp.counter;

public class BoundedCounterDemo {

    static class UpDownThread extends Thread {
        private BoundedCounter bc;
        private int n;
        private boolean up;
        public UpDownThread(BoundedCounter bc, int n, boolean up) {
            this.bc = bc;
            this.n = n;
            this.up = up;
            this.start();

        }

        public void run() {
            for (int i = 0; i < n; i++) {
                if (up) {
                    bc.up();
                    System.out.println("+" + bc.get());
                } else {
                    bc.down();
                    System.out.println("-" + bc.get());
                }
            }
        }
    }


    public static void main(String[] args) {

        BoundedCounter bc = new BoundedCounter(1, 2);

        for (int i = 0; i < 6; i++) {
            new UpDownThread(bc,10000, (i % 2 == 0));
        }


    }
}