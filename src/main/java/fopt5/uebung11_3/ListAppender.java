package fopt5.uebung11_3;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ListAppender extends Remote {

    void append(List l, int n) throws RemoteException;

}
