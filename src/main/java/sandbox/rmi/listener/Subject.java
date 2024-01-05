package sandbox.rmi.listener;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Subject extends Remote {

    void addObserver(Observer ob) throws RemoteException;

    void notifyAll(String msg) throws RemoteException;

}
