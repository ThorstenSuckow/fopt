package fopt2.sandbox;

public class StaticSynchronizedDemo {

    public StaticSynchronizedDemo() {

        synchronized(this) {
            // ...
        }


    }
    public static synchronized void doSomething() {
        // ...
    }

    public static void main(String[] args) throws InterruptedException {

        Thread t = new Thread(()-> {});
        t.start();
        t.join();
        t.start();
    }

}
