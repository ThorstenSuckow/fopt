package pp.ampel;

public class DeutscheAmpel implements Ampel {

    private boolean gruen;

    private int fahrzeuge;

    private int  naechstes;

    private int tickets;






    @Override
    public synchronized void schalteRot() {
        gruen = false;
    }

    @Override
    public synchronized void schalteGruen() {
        gruen = true;
        notifyAll();
    }


    @Override
    public synchronized void passieren() {

        fahrzeuge++;

        int nummer = tickets++;

        while (!gruen || nummer != naechstes) {
            try {
                this.wait();
            }
            catch (InterruptedException ignored) {
                //ignored
            }
        }

        fahrzeuge--;
        naechstes = nummer + 1;
        notifyAll();
    }


    @Override
    public boolean istGruen() {
        return gruen;
    }

    @Override
    public synchronized int wartendeFahrzeuge() {
        return fahrzeuge;
    }


}
