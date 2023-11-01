package fopt2.sandbox;


import java.util.ArrayList;
import java.util.List;

class PipeUser {

    static List<Thread> t = new ArrayList<Thread>();

    public static Thread put(Pipe p, byte[] message) throws InterruptedException {
        Thread t1 = new Thread(()->p.put(message));
        t1.start();
        t.add(t1);

        return t1;
    }

    public static Thread get(Pipe p, int length) throws InterruptedException {
        Thread t1 = new Thread(()->{
            byte[] message = p.get(length);
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



public class PipeDemo {


    public static void main(String[] args) throws InterruptedException {


        Pipe p = new Pipe(3);

        byte[] m = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        PipeUser.put(p, m);

        PipeUser.get(p, 1);
        PipeUser.get(p, 1);
        PipeUser.get(p, 1);
        PipeUser.get(p, 1);
        PipeUser.get(p, 1);
        PipeUser.get(p, 1);
        PipeUser.get(p, 1);
        PipeUser.put(p, m);
        PipeUser.get(p, 1);
        PipeUser.get(p, 1);
        PipeUser.get(p, 1);
        PipeUser.get(p, 1);

        PipeUser.get(p, 1);
        PipeUser.get(p, 1);


        PipeUser.get(p, 1);
        PipeUser.get(p, 1);
        PipeUser.get(p, 1);
        PipeUser.get(p, 1);
        PipeUser.get(p, 1);
        PipeUser.get(p, 1);
        PipeUser.get(p, 1);

        PipeUser.join();



        System.out.println(p);


    }


}
