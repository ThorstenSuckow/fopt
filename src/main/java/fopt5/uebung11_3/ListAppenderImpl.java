package fopt5.uebung11_3;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ListAppenderImpl extends UnicastRemoteObject implements ListAppender {
    protected ListAppenderImpl() throws RemoteException {
    }

    @Override
    public void append(List l, int n) throws RemoteException {
        l.add(n);
    }
}
