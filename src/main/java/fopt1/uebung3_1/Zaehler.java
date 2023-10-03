package fopt1.uebung3_1;

/**
 * Wie in Kapitel 2.3.4 besprochen, ist synchronized dann notwendig, wenn mehrere Threads auf ein Attribut
 * zugreifen, von denen mindestens ein Zugriff schreibend ist. Um sicherzustellen, dass die Threads auf
 * synchronisierten Daten arbeiten, ist für increment() / decrement() das Schlüsselwort "synchronized" zu verwenden.
 */
public class Zaehler
{
    private int i = 0;


    public /*synchronized*/ int increment()
    {
        if (i == 10)
        {
            return i;
        }

        return ++i;
    }

    public /*synchronized*/ int decrement()
    {
        if (i == 0)
        {
            return i;
        }

        return --i;
    }


}
