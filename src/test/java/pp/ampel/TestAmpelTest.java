package pp.ampel;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestAmpelTest {


    @Test
    @DisplayName("testTestAmpel")
    void testTestAmpel() {

        TestAmpel ta1 = new TestAmpel();
        TestAmpel ta2 = new TestAmpel();

        Ampel[] ampeln = {ta1, ta2};

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
