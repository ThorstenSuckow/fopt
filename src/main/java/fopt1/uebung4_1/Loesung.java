package fopt1.uebung4_1;

public class Loesung
{

    public static final int UNSYNCED = 0;

    public static final int SYNCED = 1;

    public static final int INDIVIDUAL = 2;


    public static void run()
    {
        run(0);
    }

    public static void run(int mode)
    {

        Rest rest1 = new Rest();

        Rest rest2;

        if (mode != INDIVIDUAL)
        {
            rest2 = rest1;
        }
        else
        {
            rest2 = new Rest();
        }

        boolean useSync = mode > UNSYNCED;

        Thread t1 = new Thread(()->
        {
            try
            {
                if (useSync)
                {
                    rest1.syncSleep();
                }
                else
                {
                    rest1.sleep();
                }
            }
            catch (InterruptedException e)
            {
                throw new RuntimeException(e);
            }
        });
        Thread t2 = new Thread(()->
        {
            try
            {
                if (useSync)
                {
                    rest2.syncSleep();
                }
                else
                {
                    rest2.sleep();
                }
            }
            catch (InterruptedException e)
            {
                throw new RuntimeException(e);
            }
        });

        t1.start();
        t2.start();

    }




}
