package fopt1.uebung3_2;

public class Loop2 extends Thread
{

    private int i = 0;


    public Loop2(String name)
    {
        super(name);
    }

    public void run()
    {
        for (i = 1; i <= 100; i++)
        {
            System.out.println(getName() + " (" + i + ")");
        }
    }


}
