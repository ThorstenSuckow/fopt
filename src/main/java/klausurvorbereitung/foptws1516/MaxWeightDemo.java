package klausurvorbereitung.foptws1516;

public class MaxWeightDemo {

    static class WeightServer {

        private boolean[] booleanField;

        private int weight;

        private int currentWeight;
        public WeightServer(int[] f, int maxWeight) {
            if (maxWeight < 0) {
                throw new IllegalArgumentException();
            }

            weight = maxWeight;
            currentWeight = 0;

            booleanField = new boolean[f.length];
        }

        public synchronized void use(int index) {

            System.out.println("[use] index: " + index + "; weight: " + currentWeight);
            while (booleanField[index] || currentWeight == weight) {
                try {
                    System.out.println("    > zzZZ index: " + index + "; weight: " + currentWeight);
                    this.wait();
                } catch (InterruptedException ignored) {

                }

            }

            System.out.println("  [access] index: " + index + "; weight: " + currentWeight);
            booleanField[index] = true;
            currentWeight++;
        }

        public synchronized void dontUse(int index) {

            if (!booleanField[index] || currentWeight == 0) {
                System.err.println("-- dontUse has no effect on index " + index + " (currentWeight = " + currentWeight + ")");
                return;
            }


            booleanField[index] = false;
            currentWeight--;

            notifyAll();
        }

    }

    static class AccessThread extends Thread {

        private int index;

        private WeightServer server;
        public AccessThread(WeightServer srv, int idx) {
            index = idx;
            server = srv;
            start();
        }

        public void run() {

            while (true) {

                server.use(index);

                try {
                    Thread.sleep(1);
                } catch (InterruptedException ignored) {

                }

                server.dontUse(index);
            }


        }

    }


    public static void main(String[] args) throws InterruptedException {

        WeightServer w = new WeightServer(new int[4], 2);


        new AccessThread(w, 0);
        new AccessThread(w, 1);
        new AccessThread(w, 2);
        new AccessThread(w, 3);


    }

}
