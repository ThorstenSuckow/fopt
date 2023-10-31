package fopt2.sandbox;


import java.util.ArrayList;
import java.util.List;

class User {

    static List<Thread> t = new ArrayList<Thread>();

    public static Thread put(BufferN q, int value) throws InterruptedException {
        Thread t1 = new Thread(()->q.put(value));
        t1.start();
        t.add(t1);

        return t1;
    }

    public static Thread get(BufferN q) throws InterruptedException {
        Thread t1 = new Thread(()->{
            int i = q.get();
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

public class BufferNDemo {


    public static void main (String[] args) throws InterruptedException {

        BufferN q = new BufferN(7);



        User.put(q,1);Thread.sleep(100);
        User.put(q,2);Thread.sleep(100);
        User.put(q,3);Thread.sleep(100);
        User.put(q,4);Thread.sleep(100);
        User.put(q,5);Thread.sleep(100);


        User.put(q, 6);Thread.sleep(100);
        User.put(q, 7);Thread.sleep(100);
        User.put(q, 8);Thread.sleep(100);
        User.put(q, 9);Thread.sleep(100);
        User.put(q, 10);Thread.sleep(100);


        User.get(q);Thread.sleep(100);
        User.get(q);Thread.sleep(100);
        User.get(q);Thread.sleep(100);
        User.get(q);Thread.sleep(100);
        User.get(q);Thread.sleep(100);
        User.get(q);Thread.sleep(100);
        User.get(q);Thread.sleep(100);
        User.get(q);Thread.sleep(100);
        User.get(q);Thread.sleep(100);
        User.get(q);Thread.sleep(100);

       // User.get(q);
      //  User.get(q);
        //User.get(q);

        User.join();


        System.out.println(q);



    }

}
