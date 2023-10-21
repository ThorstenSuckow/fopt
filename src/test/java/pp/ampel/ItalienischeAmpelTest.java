package pp.ampel;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ItalienischeAmpelTest {


    @SuppressWarnings("checkstyle:MagicNumber")
    @Test
    @DisplayName("testItalienischeAmpel")
    void testItalienischeAmpel() throws InterruptedException {

        ItalienischeAmpel ampel = new ItalienischeAmpel();
        Assertions.assertFalse(ampel.istGruen());
        Assertions.assertEquals(0, ampel.wartendeFahrzeuge());

        ampel = new ItalienischeAmpel(2);
        Assertions.assertFalse(ampel.gruen);
        Assertions.assertEquals(2, ampel.wartendeFahrzeuge());
    }


    @SuppressWarnings("checkstyle:MagicNumber")
    @Test
    @DisplayName("NoCar")
    void testNoCar() throws InterruptedException {

        ItalienischeAmpel ampel = new ItalienischeAmpel();

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

        ItalienischeAmpel ampel = new ItalienischeAmpel(2);

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

        ItalienischeAmpel ampel = new ItalienischeAmpel(2);

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

        final int numberofCars = 10000;

        ExecutorService service = Executors.newFixedThreadPool(numberofCars);

        CountDownLatch latch = new CountDownLatch(numberofCars);

        ItalienischeAmpel ampel = new ItalienischeAmpel(numberofCars);

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
