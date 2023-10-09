package fopt1.uebung5_1;

public class StopThreadByInterrupt extends Thread {

    private int i = 0;

    private boolean inLoop = false;
    public StopThreadByInterrupt(boolean inLoop) {
        this.inLoop = inLoop;
        start();
    }

    public void run() {


        if (inLoop) {

            while (!isInterrupted()) {

                i++;
                System.out.println("Hallo Welt (" + i + "; isInterrupted():" + isInterrupted() +")");

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage()+ "; isInterrupted():" + isInterrupted());
                    // this.interrupt(); // einkommentieren, damit auch die while-schleife abgebrochen wird
                }

            }
        } else {

            try {
                while (!isInterrupted()) {
                    i++;
                    System.out.println("Hallo Welt (" + i + ")");
                    Thread.sleep(3000);
                }
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }

        }


        System.out.println("Thread terminating; isInterrupted():" + isInterrupted());
    }

}
