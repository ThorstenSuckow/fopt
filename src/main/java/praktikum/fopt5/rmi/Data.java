package praktikum.fopt5.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Data extends Remote {

    int getValue() throws RemoteException;

    void setValue(int value) throws RemoteException;

}