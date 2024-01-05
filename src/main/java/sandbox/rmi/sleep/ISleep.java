package sandbox.rmi.sleep;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ISleep extends Remote {

    void sleep(int seconds) throws RemoteException;

    void sleepAsync(int seconds) throws RemoteException;
}
