package sandbox;

public class InterruptDemo extends Thread {


    public void run() {

        while (true) {


            try {

                /**
                 * Sleep bubbles InterruptedEception up.
                 * From the Thread.sleep() docs:
                 * "Throws:
                 * InterruptedException – if any thread has interrupted the current thread. The interrupted status of
                 * the current thread is cleared when this exception is thrown."
                 */
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println("[InterruptedException]");
            }
        }


    }

    public static void main(String[] args) throws InterruptedException {

        InterruptDemo demo = new InterruptDemo();

        demo.start();
        System.out.println("[1] demo.isInterrupted()" + demo.isInterrupted());
        /**
         * from the docs (Thread.interrupt()):
         * "If this thread is blocked in an invocation of the wait(), wait(long), or wait(long, int) methods
         * of the Object class, or of the join(), join(long), join(long, int), sleep(long), or sleep(long, int),
         * methods of this class, then its interrupt status will be cleared and it will receive an
         * InterruptedException."
         */
        demo.interrupt();
        // InterruptedException handled in Thread, next line most likely called before flag gets reset,
        // thus yielding "true"
        System.out.println("[2] demo.isInterrupted()" + demo.isInterrupted());
        System.out.println("[3] demo.isInterrupted()" + demo.isInterrupted());

    }

}
