package pp.ampel;

public class ItalienischeAmpel implements Ampel {

    private boolean gruen;

    private int fahrzeuge;


    @Override
    public synchronized void schalteRot() {
        gruen = false;
    }

    @Override
    public synchronized void schalteGruen() {
        gruen = true;
        notify();
    }

    @Override
    public synchronized void passieren() {

        fahrzeuge++;

        while (!gruen) {
            try {
                this.wait();
            }
            catch (InterruptedException ignored) {

            }
        }

        fahrzeuge--;
        notify();
    }

    @Override
    public boolean istGruen() {
        return gruen;
    }

    @Override
    public synchronized  int wartendeFahrzeuge() {
        return fahrzeuge;
    }


}
