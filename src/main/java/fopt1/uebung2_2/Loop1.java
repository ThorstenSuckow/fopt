package fopt1.uebung2_2;

public class Loop1 implements Runnable
{
    private String myName;

    public Loop1(String name)
    {
        myName = name;
    }


    public void run()
    {
        for (int i = 1; i <= 100; i++)
        {
            System.out.println(myName + " ( " + i + ")");
        }
    }



}
