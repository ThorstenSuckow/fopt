package klausurvorbereitung.foptws23;


import java.util.Arrays;
import java.util.Random;

class Zahlenschloss {

    private int[] kombination;

    private int[] state;

    private boolean opened = false;

    public Zahlenschloss(int[] kombination) {
        this.kombination = kombination;
        this.state = new int[kombination.length];
    }

    public int anzahlRaedchen() {
        return kombination.length;
    }

    public synchronized int lesen(int radnummer) {
        return state[radnummer];
    }

    public synchronized void drehen(int radnummer, int zahl) {

        state[radnummer] = zahl;
        System.out.print("[" + Thread.currentThread().getName() + "]combination is now " + Arrays.toString(state));
        System.out.print("\r");

        opened = true;
        for (int i = 0; i < anzahlRaedchen(); i++) {
            if (lesen(i) != kombination[i]) {
                opened = false;
                break;
            }
        }

        if (opened) {
            this.notify();
        }
    }

    public synchronized void warten() {

        while (!opened) {
            try {
                this.wait();
            } catch (InterruptedException ignored) {}
        }

        System.out.println(Thread.currentThread().getName() + " saw the correct combination " + Arrays.toString(kombination));

        this.notify();
    }


}

public class ZahlenschlossDemo {


    public static void main(String[] args) {

        final int numGuessingThreads = 50;
        final int numWaitingThreads = 10;

        final int[] kombination = {1, 2, 3};
        Zahlenschloss z = new Zahlenschloss(kombination);


        for (int i = 0; i < numWaitingThreads; i++) {
            new Thread(() -> {
                z.warten();
            }).start();
        }

        for (int i = 0; i < numGuessingThreads; i++) {
            int finalI = i;
            new Thread(() -> {
               while (true) {
                   Random rand = new Random();
                   int radnummer = rand.nextInt(kombination.length);
                   int zahl = rand.nextInt(10);
                   z.drehen(radnummer, zahl);
               }
            }).start();
        }


    }


}