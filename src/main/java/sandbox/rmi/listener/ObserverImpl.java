package sandbox.rmi.listener;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ObserverImpl extends UnicastRemoteObject implements Observer {


    protected ObserverImpl() throws RemoteException {
    }

    @Override
    public void notify(String msg) throws RemoteException {
        System.out.println("[" + this + "] notified: " + msg);
    }
}
