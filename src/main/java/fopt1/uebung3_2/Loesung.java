package fopt1.uebung3_2;

public class Loesung
{



    public static void run()
    {
        run('a');
    }
    public static void run(char aufgabe)
    {
        System.out.println("Teil " + aufgabe);

        switch (aufgabe)
        {
            case 'b':
                aufgabeB();
                break;
            case 'c':
                aufgabeC();
                break;
            case 'd':
                aufgabeD();
                break;
            case 'e':
                aufgabeE();
                break;
            default:
                aufgabeA();
                break;

        }
    }

    /**
     * Bei e) (eigtl. Teil 2 von Aufgabe d) gibt es tatsächlich Probleme, da jetzt eine Objektinstanz von LoopRunnable
     * für jeden Thread genutzt wird, also 3 Threads auf ein und dieselbe Instanzvariable i zugreifen.
     * in LoopRunnable ist der Wert für LEN auf einen entsprechend hohen Wert zu setzen, um einen Fehler zu provozieren.
     *
     */
    public static void aufgabeE()
    {
        LoopRunnable loopRunnable = new LoopRunnable();

        Thread t1 = new Thread(loopRunnable, "Thread 1");
        Thread t2 = new Thread(loopRunnable, "Thread 2");
        Thread t3 = new Thread(loopRunnable, "Thread 3");

        t1.start();
        t2.start();
        t3.start();
    }

    /**
     * Bei d) gibt es keine Probleme durch die Instanzvariable i, die als Laufvariable genutzt wird,
     * da jeder Thread eine eigene Instanz von LoopRunnable nutzt.
     *
     */
    public static void aufgabeD()
    {
        Thread t1 = new Thread(new LoopRunnable("Thread 1"));
        Thread t2 = new Thread(new LoopRunnable("Thread 2"));
        Thread t3 = new Thread(new LoopRunnable("Thread 3"));

        t1.start();
        t2.start();
        t3.start();
    }



    /**
     * Bei c) gibt es keine Probleme mit gemeinsam genutzten Daten. Zwar wird i als Instanzvariable
     * vorgehalten und von einem Thread gelesen und geschrieben, allerdings ist die Variable ein Attribut
     * des Thread-Objektes: Andere Thread-Objekte benutzen das Attribut nicht gemeinsam.
     *  <br />
     * Die Variante mit der lokalen Laufvariable ist allerdings vorzuziehen, da die Instanzvariable
     * für Loop2 keine fachliche / funktionale / desktriptive  Rolle spielt. Sie wird deshalb auch nicht als Attribut
     * benötigt und sollte deshalb in der Methode als lokale Variable verwendet werden; sie ist über den Methodenaufruf
     * hinaus nicht von Interesse.
     * <br />
     * Außerdem kann es bei solchen Implementierunen  bei späteren Erweiterungen der Klasse dazu kommen, dass andere
     * Methoden "i" versehentlich anpassen, wobei es bei der Ausführung von run() zu Problemen kommen kann.
     * Als Beispiel sei "hidden temporal coupling" (siehe "Clean Code", Robert C. Martin) genannt: Sind bspw. zwei
     * Methoden von i abhängig, aber die Zugriffe auf i sind für beide Methoden nicht transparent,
     * kann es zu schwer nachvollziehbaren Seiteneffekten kommen.
     */
    private static void aufgabeC()
    {
        Loop2 t1 = new Loop2("Thread 1");
        Loop2 t2 = new Loop2("Thread 2");
        Loop2 t3 = new Loop2("Thread 3");

        t1.start();
        t2.start();
        t3.start();
    }

    /**
     * Auch wenn die gleiche Runnable-Instanz verwendet wird, gibt es keine Probleme
     * mit gemeinsam genutzten Daten: Die einzige Variable, auf die lesend UND schreibend zugegriffen wird
     * ist eine lokale Laufvariable "i".
     */
    private static void aufgabeB()
    {
        Runnable runner = getRunnable();

        Thread t1 = new Thread(runner, "Thread 1");
        Thread t2 = new Thread(runner, "Thread 2");
        Thread t3 = new Thread(runner, "Thread 3");

        t1.start();
        t2.start();
        t3.start();

    }
    private static void aufgabeA()
    {
        Thread t1 = new Thread(getRunnable(), "Thread 1");
        Thread t2 = new Thread(getRunnable(), "Thread 2");
        Thread t3 = new Thread(getRunnable(), "Thread 3");

        t1.start();
        t2.start();
        t3.start();
    }

    private static Runnable getRunnable()
    {
        return ()->
        {
            for (int i = 1; i <= 100; i++)
            {
                System.out.println(Thread.currentThread().getName() + " (" + i + ")");
            }
        };
    }
}
