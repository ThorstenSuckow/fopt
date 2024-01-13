package fopt6.uebung17_2;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RmiSession extends Remote {


    Object getAttribute(String key) throws RemoteException;

    void setAttribute(String key, Object value) throws RemoteException;


}
