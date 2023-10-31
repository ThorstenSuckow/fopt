package fopt2.uebung9_4;

import fopt2.sandbox.Semaphore;


/**
 * There are two key-takeaways fot his implementation:
 *
 * 1) it is enough to add the Exception to the method signature instead of providing
 * an implementation in the method directly. The way the API handles InterruptedExceptions
 * should be an implementation detail of the ExceptionHandler, not of the Semaphore.
 *
 * 2) When checking for a timeout, the method must constantly check for the remaining time
 * in the while loop - using the passed argument is not sufficient and indeed erroneous,
 * since each while-iteration would reset the waiting-timer to the passed argument. Instead,
 * an remaining delta must be computed, i.e. the difference between expected endTime and current
 * Time, the used as the argument for wait().
 *
 */
public class ExtendedSemaphore extends Semaphore {


    public synchronized void pInterrubtable() throws InterruptedException {

        while (value == 0) {

            this.wait();

            /*
            This should be part of the exception handler
            try {
                this.wait();
            } catch (InterruptedException e) {
                notify();
                return;
            }*/
        }

        value--;
    }

    public synchronized boolean pWait(int milis) {

        if (milis < 0) {
            throw new IllegalArgumentException();
        }

        final long end = System.currentTimeMillis() + milis;

        // if we have waited long enough, we cancel the loop
        while (value == 0 && System.currentTimeMillis() < end) {
            try {
                // it is important to recompute the remaining time
                // in each step, otherwise each step will reset the
                // waiting time to milis, whereas we must consider
                // the time we have already been waiting
                this.wait(end - System.currentTimeMillis());
            } catch (InterruptedException ignored) {

            }
        }

        if (value == 0) {
            return false;
        }

        value--;
        return true;
    }

    public synchronized boolean pWaitInterruptable(int milis) throws InterruptedException {

        if (milis < 0) {
            throw new IllegalArgumentException();
        }

        final long end = System.currentTimeMillis() + milis;

        while (value == 0 && System.currentTimeMillis() < end) {
            // it is important to recompute the remaining time
            // in each step, otherwise each step will reset the
            // waiting time to milis, whereas we must consider
            // the time we have already been waiting
            this.wait(end - System.currentTimeMillis());
        }

        if (value == 0) {
            return false;
        }

        value--;
        return true;
    }

}
