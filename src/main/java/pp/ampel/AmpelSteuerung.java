package pp.ampel;

public class AmpelSteuerung  extends Thread {

    private Ampel[] ampeln;

    public AmpelSteuerung(Ampel[] ampeln) {

        this.ampeln = ampeln;

    }

    public void schalteRot() {
        for (Ampel ampel : ampeln) {
            ampel.schalteRot();
        }
    }

    public void schalteGruen() {
        for (Ampel ampel : ampeln) {
            ampel.schalteGruen();
        }
    }

    public void run() {

        for (Ampel ampel : ampeln) {
            ampel.passieren();
        }
    }

}
