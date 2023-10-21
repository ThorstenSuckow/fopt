package pp.ampel;


/**
 * Vereinbarung einer Schnittstelle zur Abstraktion einer Ampel.
 */
public interface Ampel {

    /**
     * Schaltet die Ampel auf Rot.
     */
    void schalteRot();


    /**
     * Schaltet die Ampel auf Grün.
     */
    void schalteGruen();


    /**
     * Bildet das Durchfahren eines Autos an der Ampel nach.
     */
    void passieren();


    /**
     * @return die Anzahl der wartenden Fahrzeuge zurück
     */
    int wartendeFahrzeuge();


    /**
     * @return true, wenn die Ampel auf Grün geschaltet ist
     */
    boolean istGruen();

}
