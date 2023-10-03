package fopt1.uebung3_1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

class ZaehlerTest {

    @Test
    @DisplayName("Zahler breaks w/o being synchronized")
    void zaehlerOverflow() throws InterruptedException {

        AtomicInteger e = new AtomicInteger();

        Thread.UncaughtExceptionHandler handler = (t, ex) -> {
            e.set(1);
        };

        Zaehler zaehler = new Zaehler();

        Thread t1 = new Thread(getRunnable("Thread 1", zaehler));
        Thread t2 = new Thread(getRunnable("Thread 2", zaehler));
        t1.setUncaughtExceptionHandler(handler);
        t2.setUncaughtExceptionHandler(handler);

        t1.start();
        t2.start();

        t1.join();
        t2.join();


        org.junit.jupiter.api.Assertions.assertSame(e.get(), 1);
    }


    private static Runnable getRunnable(String name, Zaehler zaehler)
    {
        return () ->
        {
            for (int i = 0; i < 1000; i++)
            {
                if (i % 2 == 0)
                {
                    int a = zaehler.increment();
                    if (a > 10)
                    {
                        throw new RuntimeException(name + "++ (" + i + ")" + a);
                    }
                }
                else
                {
                    int b =  zaehler.decrement();
                    if (b < 0)
                    {
                        throw new RuntimeException(name + "-- (" + i + ")" + b);
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