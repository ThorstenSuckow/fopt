package pp.ampel;

public class TestAmpel implements Ampel {

    private static int ampelIndex = 0;

    private int myIndex;

    public TestAmpel() {
        myIndex = ampelIndex++;
    }


    @Override
    public void schalteRot() {

    }


    @Override
    public void schalteGruen() {

    }


    @Override
    public synchronized void passieren() {
        System.out.println(Thread.currentThread().getName() + " passiert " + myIndex);

        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException ignored) {

        }

    }


    @Override
    public int wartendeFahrzeuge() {
        return 0;
    }


    @Override
    public boolean istGruen() {
        return false;
    }
}
