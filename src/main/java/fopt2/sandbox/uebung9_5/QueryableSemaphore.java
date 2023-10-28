package fopt2.sandbox.uebung9_5;


import fopt2.sandbox.Semaphore;

import java.util.ArrayList;
import java.util.List;


/**
 * Key-Takeaways:
 *
 * 1) External access on the threadList should be synchronized to make sure only one thread can access the current list.
 *
 * 2) A new list (copy) should be returned when querying the list to make sure THIS list cannot be changed from outside.
 *
 * 3) in #pInterruptable(), the final statement should be the removal of the current thread from the list, in
 * case an InterruptedException is thrown.
 *
 *
 *
 */
public class QueryableSemaphore extends Semaphore {


    ArrayList<Thread> threadList = new ArrayList<Thread>();

    public QueryableSemaphore(int value) {
        super(value);
    }

    /**
     * Returns a copy of the list to make sure the threadList this instance
     * operates on is not altered.
     *
     * @return
     */
    public List<Thread> getWaitingList() {
        return new ArrayList<Thread>(threadList);
    }

    /**
     * Synchronize access to make sure list is not altered during access.
     *
     * @return
     */
    public synchronized int getWaitingListSize() {
        return threadList.size();
    }


    public synchronized void p() {

        threadList.add(Thread.currentThread());

        while (value == 0) {

            try {
                this.wait();
            } catch (InterruptedException ignored) {

            }
        }

        threadList.remove(Thread.currentThread());
        value--;
    }

    /**
     * The interruptable implementation (see uebung9_4) makes sure that removing the Thread from the list
     * happens in any case, even if an exception was thrown.
     *
     * @throws InterruptedException
     */
    public synchronized void pInterruptable() throws InterruptedException {

        threadList.add(Thread.currentThread());

        try {
            while (value == 0) {
                this.wait();
            }

            value--;
        } finally {
            threadList.remove(Thread.currentThread());
        }

    }


}
