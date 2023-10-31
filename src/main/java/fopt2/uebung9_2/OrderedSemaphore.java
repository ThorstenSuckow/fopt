package fopt2.uebung9_2;

import fopt2.sandbox.Semaphore;

/**
 * Implementation of an OrderedSemaphore where p() is called with the
 * preferred order in which a thread requires to access a critical block.
 *
 * This implementation does not consider Threads that may be processed parallel, as in Listing 3.3
 * in the book.
 *
 */
public class OrderedSemaphore extends Semaphore {

    int tickets = 0;
    int nextTicket = -1;

    public OrderedSemaphore(int length) {
        super();
        tickets = length;
    }


    public synchronized void p(int orderRequest) {

        if (nextTicket == -1) {
            nextTicket = orderRequest;
        }


        while (value == 0 || orderRequest != nextTicket) {

            try {
                this.wait();
            } catch (InterruptedException ignored) {

            }


        }

        nextTicket = (nextTicket % tickets) + 1;
        value--;
        notifyAll();
    }


    public synchronized  void v() {

        value++;
        notifyAll();

    }

}
