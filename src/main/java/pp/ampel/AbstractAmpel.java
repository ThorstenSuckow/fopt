package pp.ampel;

public abstract class AbstractAmpel implements Ampel {


    protected boolean gruen;

    protected int fahrzeuge;


    public AbstractAmpel() {

    }


    public AbstractAmpel(int fahrzeuge) {

        if (fahrzeuge < 0) {
            throw new IllegalArgumentException("\"fahrzeuge\" must be >= 0");
        }
        this.fahrzeuge = fahrzeuge;
    }

    @Override
    public boolean istGruen() {
        return gruen;
    }

    @Override
    public int wartendeFahrzeuge() {
        return fahrzeuge;
    }

}
