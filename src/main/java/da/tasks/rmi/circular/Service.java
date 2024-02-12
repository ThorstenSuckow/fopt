package da.tasks.rmi.circular;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Service extends Remote {

    void add(Processor p) throws RemoteException;

    void remove (Processor p) throws RemoteException;

    Processor next(Processor p) throws RemoteException;

}
