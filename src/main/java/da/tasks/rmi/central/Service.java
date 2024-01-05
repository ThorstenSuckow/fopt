package da.tasks.rmi.central;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Service extends Remote {

    Data open() throws RemoteException;

    Data get() throws RemoteException;

    Data close() throws RemoteException;

    boolean isOpen() throws RemoteException;

}
