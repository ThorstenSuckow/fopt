package pp.ampel;

public class Auto extends Thread {

    private Ampel[] ampeln;

    public Auto(Ampel[] ampeln) {

        this.ampeln = ampeln;
    }

    public void run() {

        int idx = 0;

        while (true) {

            if (idx >= ampeln.length) {
                idx = 0;
            }

            this.ampeln[idx++].passieren();

        }

    }

}
