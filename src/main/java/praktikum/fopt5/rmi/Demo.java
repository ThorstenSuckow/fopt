package praktikum.fopt5.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Demo extends Remote {

    int increment(Data data) throws RemoteException;


    int increment(PlainData data) throws RemoteException;


    int getOperations() throws RemoteException;

}
