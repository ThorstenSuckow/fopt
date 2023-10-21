package pp.ampel;


/**
 * Vereinbarung einer Schnittstelle zur Abstraktion einer Ampel.
 */
public interface Ampel {

    /**
     * Schaltet die Ampel auf Rot.
     */
    public void schalteRot();


    /**
     * Schaltet die Ampel auf Grün.
     */
    public void schalteGruen();


    /**
     * Bildet das Durchfahren eines Autos an der Ampel nach.
     */
    public void passieren();


    /**
     * @return die Anzahl der wartenden Fahrzeuge zurück
     */
    public int wartendeFahrzeuge();


    /**
     * @return true, wenn die Ampel auf Grün geschaltet ist
     */
    public boolean istGruen();

}
