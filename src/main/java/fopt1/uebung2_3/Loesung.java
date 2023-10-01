package fopt1.uebung2_3;

public class Loesung
{

    private static Runnable getRunnable(String name)
    {
        return () ->
        {
            for (int i = 0; i < 100; i++)
            {
                System.out.println(name + ": " + i);
            }
        };
    }

    public static void run()
    {
        Thread t1 = new Thread(getRunnable("Thread 1"));
        Thread t2 = new Thread(getRunnable("Thread 2"));
        Thread t3 = new Thread(getRunnable("Thread 3"));

        t1.start();
        t2.start();
        t3.start();
    }

}
