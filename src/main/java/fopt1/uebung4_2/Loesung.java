package fopt1.uebung4_2;


/**
 * Die CPU-Auslastung ist bei useLock (der Variante, die die while-Schleife mit dem Sperrattribute verwendet, also
 * ein polling durchführt) wesentlich höher, als wenn die synchronisierte Variante verwendet wird. Hier werden die
 * Sperren (Threads) durch die JVM verwaltet, während das Sperrattribut eine Lösung im Userland-Code ist; die Sperre wird
 * durch die Abarbeitung der while-schleife überprüft, wodurch unnötig Ressourcen verbraucht werden.
 */
public class Loesung
{

    public static void run()
    {
        Bank bank = new Bank();

        boolean useLock = false;

        Clerk c1 = new Clerk("t1", bank, useLock);
        Clerk c2 = new Clerk("t2", bank, useLock);
        Clerk c3 = new Clerk("t3", bank, useLock);
        Clerk c4 = new Clerk("t4", bank, useLock);
        Clerk c5 = new Clerk("t5", bank, useLock);
        Clerk c6 = new Clerk("t6", bank, useLock);

    }

}
