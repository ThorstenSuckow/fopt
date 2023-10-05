package fopt1.uebung4_2;

public class Bank
{
    final private Account[] accounts;

    private boolean locked = false;

    private boolean useLock = false;

    public Bank()
    {
        accounts = new Account[100];

        for (int i = 0; i < accounts.length; i++)
        {
            accounts[i] = new Account();
        }
    }


    public synchronized void syncTransferMoney(int accountNumber, float amount) throws InterruptedException
    {
        Thread.sleep(1000);
        float oldBalance = accounts[accountNumber].getBalance();
        System.out.println("(sync) current balance is " + oldBalance+ "$");
        float newBalance = oldBalance + amount;
        accounts[accountNumber].setBalance(newBalance);
        System.out.println("(sync) new balance is " + newBalance + "$");
    }

    public void lockTransferMoney(int accountNumber, float amount) throws InterruptedException
    {
        while (locked);

        locked = true;

        Thread.sleep(1000);
        float oldBalance = accounts[accountNumber].getBalance();
        System.out.println("(lock) current balance is " + oldBalance+ "$");
        float newBalance = oldBalance + amount;
        accounts[accountNumber].setBalance(newBalance);
        System.out.println("(lock) new balance is " + newBalance + "$");

        locked = false;

    }

}
