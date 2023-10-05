package fopt1.uebung4_1;

public class Rest
{

    /**
     * Thread-1 und Thread-2 rufen beide die Methode auf. Die Instanz wird für den Zugriff zwei unterschiedlicher
     * Threads nicht gesperrt, weshalb durch die Threads die Methode "parallel" abarbeiten.
     *
     * @throws InterruptedException
     */
    public void sleep() throws InterruptedException
    {
        System.out.println(Thread.currentThread().getName()+ ": before sleep");
        Thread.sleep(1000);
        System.out.println(Thread.currentThread().getName()+ ": after sleep");

    }


    /**
     * Wenn die Methode synchronized ist, sperrt Thread-1 den Zugriff auf die Methode (der geteilten Instanz dieser
     * Klasse) und Thread-t2 erhält erst Zugriff, wenn die Sperre von Thread-1  gelöst wurde.
     * <br />
     * Werden zwei unterschiedliche Instanzen von Rest verwendet für T1 und T2, ist die Ausführung ähnlich der
     * unsynchronisierten Methode.
     *
     * @throws InterruptedException
     */
    public synchronized void syncSleep() throws InterruptedException
    {
        System.out.println(Thread.currentThread().getName()+ ": before sleep");
        Thread.sleep(1000);
        System.out.println(Thread.currentThread().getName()+ ": after sleep");

    }

}
