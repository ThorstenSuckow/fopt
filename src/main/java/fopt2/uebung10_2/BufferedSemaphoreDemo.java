package fopt2.uebung10_2;

import java.util.ArrayList;
import java.util.List;

class BufferedSemaphoreUser {

    static List<Thread> t = new ArrayList<Thread>();

    public static Thread write(BufferedSemaphore b, int n) throws InterruptedException {
        Thread t1 = new Thread(()->b.write(n));
        t1.start();
        t.add(t1);

        return t1;
    }

    public static Thread read(BufferedSemaphore b) throws InterruptedException {
        Thread t1 = new Thread(()->{
            int n = b.read();
        });
        t1.start();

        t.add(t1);

        return t1;
    }

    public static void join() throws InterruptedException {

        for (Thread t1:t) {
            t1.join();
        }
    }

}


/**
 * BufferN w/o synchronize and wait.
 */
public class BufferedSemaphoreDemo {

    public static void main(String[] args) throws InterruptedException {

        BufferedSemaphore b = new BufferedSemaphore(5);


        BufferedSemaphoreUser.write(b,1);
        BufferedSemaphoreUser.write(b,2);
        BufferedSemaphoreUser.write(b,3);
        BufferedSemaphoreUser.write(b,4);
        BufferedSemaphoreUser.write(b,5);
        BufferedSemaphoreUser.write(b,6);

        BufferedSemaphoreUser.read(b);
        BufferedSemaphoreUser.read(b);
        BufferedSemaphoreUser.read(b);

        BufferedSemaphoreUser.join();

    }

}