package pp.ampel;

public class DeutscheAmpel extends AbstractAmpel {


    private int wartend;

    private int naechstes;


    public DeutscheAmpel() {
        super();
    }


    public DeutscheAmpel(int fahrzeuge) {
        super(fahrzeuge);
    }


    @Override
    public void schalteRot() {
        gruen = false;
    }

    @Override
    public synchronized void schalteGruen() {
        gruen = true;
        notifyAll();
    }

    @Override
    public synchronized void passieren() {

        int current = wartend++;

        while (!gruen || current != naechstes || fahrzeuge == 0) {

            try {
                this.wait();
            }
            catch (InterruptedException ignored) {
                //ignored
            }
        }

        notifyAll();
        naechstes++;
        fahrzeuge--;
    }


}
