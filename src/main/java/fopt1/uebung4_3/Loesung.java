package fopt1.uebung4_3;

public class Loesung
{



    public static void run()
    {

        LoopRunnable loopRunnable = new LoopRunnable();

        Thread t1 = new Thread(loopRunnable, "Thread 1");
        Thread t2 = new Thread(loopRunnable, "Thread 2");
        Thread t3 = new Thread(loopRunnable, "Thread 3");

        t1.start();
        t2.start();
        t3.start();
    }


}
