package fopt1.uebung3_1;

public class Loesung
{
    private static Runnable getRunnable(String name, Zaehler zaehler)
    {
        return () ->
        {
            for (int i = 0; i < 1000000; i++)
            {
                if (i % 2 == 0)
                {
                    int a = zaehler.increment();
                    if (a > 10)
                    {
                        System.out.println(name + "++ (" + i + ")" + a);
                    }
                }
                else
                {
                    int b =  zaehler.decrement();
                    if (b < 0)
                    {
                        System.out.println(name + "-- (" + i + ")" + b);
                    }

                }

            }
        };
    }

    public static void run()
    {
        Zaehler zaehler = new Zaehler();

        Thread t1 = new Thread(getRunnable("Thread 1", zaehler));
        Thread t2 = new Thread(getRunnable("Thread 2", zaehler));

        t1.start();
        t2.start();
    }


}
