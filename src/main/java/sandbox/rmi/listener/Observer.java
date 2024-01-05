package sandbox.rmi.listener;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Observer extends Remote {

    void notify(String msg) throws RemoteException;

}
