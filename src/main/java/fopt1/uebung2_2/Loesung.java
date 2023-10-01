package fopt1.uebung2_2;

public class Loesung
{

    public static void run()
    {
        Thread t1 = new Thread(new Loop1("Thread 1"));
        Thread t2 = new Thread(new Loop1("Thread 2"));
        Thread t3 = new Thread(new Loop1("Thread 2"));

        t1.start();
        t2.start();
        t3.start();
    }

}
