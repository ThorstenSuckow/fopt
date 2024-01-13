package fopt6.uebung17_2;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

public class RmiSessionImpl extends UnicastRemoteObject implements RmiSession {

    private HashMap<String, Object> data;


    public RmiSessionImpl() throws RemoteException {
        data = new HashMap<>();
    }

    public void setAttribute(String key, Object value) throws RemoteException {
        data.put(key, value);
    }

    public Object  getAttribute(String key) throws RemoteException {
        return data.get(key);
    }



}
