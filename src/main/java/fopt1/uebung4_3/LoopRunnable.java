package fopt1.uebung4_3;

public class LoopRunnable implements Runnable
{

    private int i = 0;

    final private int LEN = 100;


    /**
     * Es kann nur jeweils in Thread gleichzeitig i inkrementieren.
     *
     */
    public synchronized int incrementAndGet()
    {
        i++;
        return i;
    }

    /**
     * run() sollte nicht synchronized sein, weil ein Thread dann die komplette Schleifenausführung blockt, und
     * andere Threads die Methode erst betreten können, wenn i bereits LEN ist - die Schleife wird dann nicht mehr
     * betreten. Somit zählt dann nur ein Thread i hoch.
     */
    public synchronized void run()
    {

        int j;

        while ((j = incrementAndGet()) < LEN)
        {
            System.out.println(Thread.currentThread().getName() + " (" + j + ")");
        }
    }


}
