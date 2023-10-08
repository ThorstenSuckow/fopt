package fopt1.sandbox;

class StopThread extends Thread {

    private boolean stopped = false;


    public StopThread() {
        start();
    }

    public synchronized void stopThread() {
        stopped = true;
    }

    public synchronized boolean isStopped() {
        return stopped;
    }

    public void run() {
        int i = 0;
        System.out.println("run: " + Thread.currentThread());

        try {
            System.out.println("sleep");
Thread.sleep(5000);
            System.out.println(Thread.currentThread());
        } catch (InterruptedException e) {
            System.out.println("INTERRUPTED");
        }
        while (!isStopped()) {
            i++;
            // System.out.print(i++);
        }
    }

}

public class Main {


    public static void main(String[] args) {
        System.out.println(Thread.currentThread());
        StopThread t1 = new StopThread();
        System.out.println(Thread.currentThread());

        System.out.println("Stop");
        t1.interrupt();
        System.out.println(Thread.currentThread());
    }


}
