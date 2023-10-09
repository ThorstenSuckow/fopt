package fopt1.uebung5_1;


/**
 * Wenn wir ein Objekt mit StopThreadByInterrupt(true) ("false",  um die Original-Implementierung aus dem
 * Buch zu verwenden(, wird das sleep mit dem try/catch-Block innerhalb der Schleife
 * aufgerufen.
 * <br />
 * Die run()-Methode führt die while()-Schleife aus, die solange durchlaufen wird, wie isInterrupted()
 * false zurückliefert. Allerdings wird der Thread in jeder Iteration mit sleep() schlafen gelegt, d.h. die Schleife -
 * und folglich die isInterrupted()-Abfrage - findet garnicht statt.
 * <br />
 * sleep() wird mit hoher Wahrscheinlichkeit durch den Aufruf von interrupt() unterbrochen - diese Stelle wirft dann die
 * Exception, und wenn nun kein neuer Aufruf von interrupt() innerhalb des catch-Blockes stattfindet,
 * geht der Thread in einen neuen State über, und bei einem erneuten Schleifendurchlauf ist isInterrupted() wieder
 * "false".
 *
 */
public class Loesung {


    public static void main(String[] args)  {
        StopThreadByInterrupt t1 = new StopThreadByInterrupt(true);

        try {
            Thread.sleep(9100);
        } catch (InterruptedException e) {
        }

        t1.interrupt();

    }

}
