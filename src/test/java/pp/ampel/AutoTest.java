package pp.ampel;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AutoTest {


    @Test
    @DisplayName("testAuto")
    void testAuto() {

        final int ampelCount = 100;
        final int jedesNIstDeutscheAmpel = 5;

        Ampel[] ampeln = new Ampel[ampelCount];

        for (int j = 0; j < ampelCount; j++) {
            ampeln[j] = (j % jedesNIstDeutscheAmpel == 0) ? new DeutscheAmpel(2) : new ItalienischeAmpel(2);
        }

        Auto a1 = new Auto(ampeln);
        Auto a2 = new Auto(ampeln);

        a1.start();
        a2.start();

        try {
            Thread.sleep(3000);
            a1.interrupt();
            a2.interrupt();
        } catch (InterruptedException ignored) {

        }

    }
}
