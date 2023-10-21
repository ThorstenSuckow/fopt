package pp.ampel;

public class ItalienischeAmpel extends AbstractAmpel{


    public ItalienischeAmpel() {
        super();

    }


    public ItalienischeAmpel(int fahrzeuge) {
        super(fahrzeuge);

    }


    @Override
    public void schalteRot() {
        gruen = false;
    }

    @Override
    public synchronized void schalteGruen() {
        gruen = true;
        notify();
    }

    @Override
    public synchronized void passieren() {

        while (!gruen || fahrzeuge == 0) {
            try {
                this.wait();
            }
            catch (InterruptedException ignored) {

            }
        }

        notify();
        fahrzeuge--;
    }



}
