package pp.ampel;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DeutscheAmpelTest {


    @SuppressWarnings("checkstyle:MagicNumber")
    @Test
    @DisplayName("testDeutscheAmpel")
    void testDeutscheAmpel() throws InterruptedException {

        DeutscheAmpel ampel = new DeutscheAmpel();
        Assertions.assertFalse(ampel.istGruen());
        Assertions.assertEquals(0, ampel.wartendeFahrzeuge());

        ampel = new DeutscheAmpel(2);

        Assertions.assertFalse(ampel.istGruen());
        Assertions.assertEquals(2, ampel.wartendeFahrzeuge());
    }


    @SuppressWarnings("checkstyle:MagicNumber")
    @Test
    @DisplayName("NoCar")
    void testNoCar() throws InterruptedException {

        DeutscheAmpel ampel = new DeutscheAmpel();

        Thread t = new Thread(ampel::passieren);

        t.start();

        Assertions.assertEquals(0, ampel.wartendeFahrzeuge());
        ampel.schalteGruen();

        t.join(10);

        Assertions.assertEquals(0, ampel.wartendeFahrzeuge());
    }


    @SuppressWarnings("checkstyle:MagicNumber")
    @Test
    @DisplayName("OneCar")
    void testOneCar() throws InterruptedException {

        DeutscheAmpel ampel = new DeutscheAmpel(2);

        Thread t = new Thread(ampel::passieren);

        t.start();

        Assertions.assertEquals(2, ampel.wartendeFahrzeuge());
        ampel.schalteGruen();

        t.join();

        Assertions.assertEquals(1, ampel.wartendeFahrzeuge());
    }


    @SuppressWarnings("checkstyle:MagicNumber")
    @Test
    @DisplayName("TwoCars")
    void testTwoCars() throws InterruptedException {

        DeutscheAmpel ampel = new DeutscheAmpel(2);

        Thread t1 = new Thread(ampel::passieren);
        Thread t2 = new Thread(ampel::passieren);

        t1.start();
        t2.start();

        Assertions.assertEquals(2, ampel.wartendeFahrzeuge());
        ampel.schalteGruen();

        t1.join();
        t2.join();

        Assertions.assertEquals(0, ampel.wartendeFahrzeuge());
    }

    @Test
    @DisplayName("concurrency")
    void testAmpelWithConcurrency() throws InterruptedException {

        final int numberofCars = 1000;

        ExecutorService service = Executors.newFixedThreadPool(numberofCars);

        CountDownLatch latch = new CountDownLatch(numberofCars);

        DeutscheAmpel ampel = new DeutscheAmpel(numberofCars);

        for (int i = 0; i < numberofCars; i++) {
            service.execute(() -> {
                ampel.passieren();
                latch.countDown();
            });
        }

        ampel.schalteGruen();
        latch.await();

        Assertions.assertEquals(0, ampel.wartendeFahrzeuge());
    }

}
