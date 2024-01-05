package sandbox.rmisemaphor;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class SemaphorImpl extends UnicastRemoteObject implements Semaphor {

    private int v = 1;

    protected SemaphorImpl() throws RemoteException {
    }

    @Override
    public synchronized void p() throws RemoteException {

        while (v == 0) {
            try {
                System.out.println(Thread.currentThread().getName() + " waiting...");
                wait();
            } catch (InterruptedException ignored) {

            }
        }

        System.out.println(Thread.currentThread().getName() + " obtaining the lock!");
        v--;
    }

    @Override
    public synchronized void v() throws RemoteException {

        v++;
        notify();
    }
}
