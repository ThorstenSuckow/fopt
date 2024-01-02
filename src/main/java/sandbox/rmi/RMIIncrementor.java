package sandbox.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIIncrementor extends Remote {

    int increment() throws RemoteException;

    int reset() throws RemoteException;

}
