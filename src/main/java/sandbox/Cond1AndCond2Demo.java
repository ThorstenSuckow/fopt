package sandbox;

import java.util.Random;

public class Cond1AndCond2Demo {

    private static class Cond1AndCond2 {

        private boolean cond1;
        private boolean cond2;

        public synchronized void setCond1(boolean c) {
            cond1 = c;
            // check if c && cond2 before notifyAll
            notifyAll();
        }

        public synchronized void setCond2(boolean c) {
            cond2 = c;
            // check if c && cond1 before notifyAll
            notifyAll();
        }

        public synchronized void cond1AndCond2() {

            /*
            //wrong:
            while(!cond1) {
                try {
                    wait();
                } catch(InterruptedException e) { }
            }

            while(!cond2) {
                try {
                    wait();
                } catch(InterruptedException e) {}
            }*/

            while(!cond1 || !cond2) {
                try {
                    wait();
                } catch(InterruptedException e) { }
            }

            if (!cond1 && cond2) {
                System.out.println("cond1 and cond2:" + cond1 + " " + cond2);
            }
        }
    }


    public static void main(String... args) {

        Cond1AndCond2 target = new Cond1AndCond2();

        new Thread(()->{
            Random r = new Random();
            while (true) {
                target.setCond1(r.nextBoolean());
            }
        }).start();
        new Thread(()->{
            Random r = new Random();
            while (true) {
                target.setCond2(r.nextBoolean());
            }
        }).start();

        new Thread(()->{
            Random r = new Random();
            while (true) {
                target.cond1AndCond2();
            }
        }).start();
    }
}
