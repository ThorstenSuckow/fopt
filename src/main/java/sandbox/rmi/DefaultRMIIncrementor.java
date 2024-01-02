package sandbox.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

class DefaultRMIIncrementor extends UnicastRemoteObject implements RMIIncrementor {

    private int counter = 0;

    protected DefaultRMIIncrementor() throws RemoteException {
    }

    @Override
    public synchronized int increment() throws RemoteException {
        return ++counter;
    }

    @Override
    public synchronized int reset() throws RemoteException {
        counter = 0;
        return counter;
    }
}
