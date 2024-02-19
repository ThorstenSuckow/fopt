package praktikum.fopt1und2;

public class AmpelDemo {

    private interface Ampel {
        void schalteRot();
        void schalteGruen();

        void passieren();

        int wartendeFahrzeuge();
    }

    private abstract static class AbstractAmpel implements Ampel {
        protected boolean isRot;
        protected int wartendeFahrzeuge;

        protected int ampelId;
        public AbstractAmpel(boolean isRot, int ampelId) {
            this.isRot = isRot;
            this.ampelId = ampelId;
        }
        @Override
        public synchronized void schalteRot() {
            isRot = true;
            // kein notifyAll(); die Autos werden nur benachrichtigt, wenn sie fahren können.
            // die schleifenbedingung wird ohnehin jedes mal geprüft, es reicht also, hier einfach auf rot
            // zu setzen
        }

        @Override
        public synchronized int wartendeFahrzeuge() {
            return wartendeFahrzeuge;
        }

        protected void log(String msg) {
            System.out.println(msg);
        }
    }
    private static class ItalienischeAmpel extends AbstractAmpel {

        public ItalienischeAmpel(boolean isRot, int ampelId) {
            super(isRot, ampelId);
        }

        @Override
        public synchronized void schalteGruen() {
            isRot = false;
            notifyAll(); // alle wartenden dürfen fahren
        }

        @Override
        public synchronized void passieren() {
            wartendeFahrzeuge++;
            while (isRot) {
                try {
                    log("[ampel " + ampelId + "] " + Thread.currentThread().getName() + " waiting... ");
                    this.wait();
                } catch (InterruptedException ignored) {}
            }
            log("[ampel " + ampelId + "] " + Thread.currentThread().getName() + " passing by!");
            wartendeFahrzeuge--;
        }
    }


    private static class DeutscheAmpel extends AbstractAmpel{

        private int counter = 0;
        public DeutscheAmpel(boolean isRot, int ampelId) {
            super(isRot, ampelId);
        }

        @Override
        public synchronized void schalteGruen() {
            isRot = false;
            notifyAll();
        }

        @Override
        public synchronized void passieren() {

            int ticket = counter;
            wartendeFahrzeuge++;
            counter++;


            while (isRot  || counter - wartendeFahrzeuge != ticket) {
                try {
                    log("[ampel " + ampelId + "] " + Thread.currentThread().getName() +
                        " waiting... (rot: " + isRot + "; ticket: " + ticket +"; "
                        + counter + " - "+wartendeFahrzeuge+")"  );
                    this.wait();
                } catch (InterruptedException ignored){}
            }

            log("[ampel " + ampelId + "] " + Thread.currentThread().getName() +  " passing by!");

            wartendeFahrzeuge--;

            try {
                Thread.sleep(500);
            } catch (InterruptedException ignored){}
            notifyAll();
        }
    }

    private static class Auto extends Thread {

        private Ampel[] ampeln;
        public Auto(Ampel[] ampeln) {
            this.ampeln = ampeln;
            start();
        }

        public void run() {

            int i = 0;
            while (true) {
                int ampelIndex =  i++ % (ampeln.length);
                ampeln[ampelIndex].passieren();
                try {
                    Thread.sleep((int)(Math.random() * 1));
                } catch (InterruptedException ignored) {}
            }
        }

        protected void log(String msg) {
            System.out.println(msg);
        }
    }

    private static class AmpelSteuerung extends Thread {

        private Ampel[] ampeln;

        public AmpelSteuerung(Ampel[] ampeln) {
            this.ampeln = ampeln;

        }

        public void schalteGruen() {
            for (int i = 0; i < ampeln.length; i++) {
                log("[ampelsteuerung " + i + "] green :)");
                ampeln[i].schalteGruen();
            }
        }

        public void schalteRot() {
            for (int i = 0; i < ampeln.length; i++) {
                log("[ampelsteuerung " + i + "] rot :(");
                ampeln[i].schalteRot();
            }
        }

        public void run() {

            while (true) {

                for (int i = 0; i < ampeln.length; i++) {
                    if (((int)(Math.random() * 100)) % 2 == 0) {
                        log("[ampelsteuerung " + i + "] green :)");
                        ampeln[i].schalteGruen();
                    } else {

                        log("[ampelsteuerung " + i + "] red :(");
                        ampeln[i].schalteRot();
                    }
                }

                try {
                    Thread.sleep((int)(Math.random() * 10000));
                } catch (InterruptedException ignored) {}
            }
        }

        protected void log(String msg) {
            System.out.println(msg);
        }
    }

    public static void main(String[] args) throws InterruptedException {

        final int ampelMax = 1;
        final int maxAutos = 2;
        Ampel[] ampeln;

        ampeln = new DeutscheAmpel[ampelMax];
       // ampeln = new ItalienischeAmpel[ampelMax];

        for (int i = 0; i < ampelMax; i++) {
            ampeln[i] = new DeutscheAmpel(true, i);
            //ampeln[i] = new ItalienischeAmpel(false, i);
        }

        AmpelSteuerung ampelSteuerung = new AmpelSteuerung(ampeln);
        ampelSteuerung.start();

        for (int i = 0; i < maxAutos; i++) {
            new Auto(ampeln);
        }

    }

}
