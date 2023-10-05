package fopt1.uebung4_2;

public class Clerk extends Thread
{

    private Bank bank;

    boolean useLock;

    public Clerk(String name, Bank bank, boolean useLock)
    {
        super(name);
        this.useLock = useLock;
        this.bank = bank;
        start();
    }

    public void run()
    {
        for (int i = 0; i < 10000; i++)
        {
            int accountNumber = (int)Math.random()*100;

            float amount = (int)(Math.random() * 1000) - 500;

            try
            {
                System.out.println((useLock ? "(lock) " : "(sync) ") + getName() + " transfers " + amount + "$");

                if (useLock)
                {
                    bank.lockTransferMoney(accountNumber, amount);
                }
                else
                {
                    bank.syncTransferMoney(accountNumber, amount);
                }

            }
            catch (InterruptedException e)
            {
                throw new RuntimeException(e);
            }

        }

    }



}
