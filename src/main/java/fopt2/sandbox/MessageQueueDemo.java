package fopt2.sandbox;


import java.util.ArrayList;
import java.util.List;

class MessageUser {

    static List<Thread> t = new ArrayList<Thread>();

    public static Thread put(MessageQueue q, byte[] message) throws InterruptedException {
        Thread t1 = new Thread(()->q.put(message));
        t1.start();
        t.add(t1);

        return t1;
    }

    public static Thread get(MessageQueue q) throws InterruptedException {
        Thread t1 = new Thread(()->{
            byte[] message = q.get();
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

public class MessageQueueDemo {


    public static void main (String[] args) throws InterruptedException {

        MessageQueue q = new MessageQueue(5);



        MessageUser.put(q, new byte[]{1, 2, 3});Thread.sleep(100);
        MessageUser.put(q, new byte[]{4, 5, 6});Thread.sleep(100);
        MessageUser.put(q, new byte[]{7, 8});Thread.sleep(100);
        MessageUser.put(q, new byte[]{9});Thread.sleep(100);
        MessageUser.put(q, new byte[]{10, 11, 12});Thread.sleep(100);


        MessageUser.put(q, new byte[]{6});Thread.sleep(100);
        MessageUser.put(q, new byte[]{7});Thread.sleep(100);
        MessageUser.put(q, new byte[]{8});Thread.sleep(100);
        MessageUser.put(q, new byte[]{9});Thread.sleep(100);
        MessageUser.put(q, new byte[]{10});Thread.sleep(100);


        MessageUser.get(q);Thread.sleep(100);
        MessageUser.get(q);Thread.sleep(100);
        MessageUser.get(q);Thread.sleep(100);
        MessageUser.get(q);Thread.sleep(100);
        MessageUser.get(q);Thread.sleep(100);
        MessageUser.get(q);Thread.sleep(100);
        MessageUser.get(q);Thread.sleep(100);
        MessageUser.get(q);Thread.sleep(100);
        MessageUser.get(q);Thread.sleep(100);
        MessageUser.get(q);Thread.sleep(100);

        // User.get(q);
        //  User.get(q);
        //User.get(q);

        User.join();


        System.out.println(q);



    }

}
