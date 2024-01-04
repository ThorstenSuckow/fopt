package fopt5.uebung11_3;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface List extends Remote {

    void add(int n) throws RemoteException;

    String asString() throws RemoteException;
}
