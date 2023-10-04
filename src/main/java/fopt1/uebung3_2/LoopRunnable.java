package fopt1.uebung3_2;

import java.sql.Array;

public class LoopRunnable implements Runnable
{

    private int i = 1;

    private final int LEN = 100;


    private final boolean[] USED = new boolean[LEN];

    private String name = null;

    public LoopRunnable()
    {
    }

    public LoopRunnable(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public void run()
    {
        if (USED[i - 1])
        {
            throw new RuntimeException(i + "");
        }

        for (i = 1; i <= LEN; i++)
        {
            USED[i - 1] = true;
            System.out.println(((getName() != null) ? getName() : Thread.currentThread().getName()) + " (" + i + ")");
        }
    }


}
