package fopt6.uebung17_2;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

public class RmiSessionContextImpl extends UnicastRemoteObject implements RmiSessionContext {

    private final HashMap<String, RmiSession> sessionStore;

    protected RmiSessionContextImpl() throws RemoteException {
        sessionStore = new HashMap<>();
    }


    public RmiSession getSession(String key) throws RemoteException {
        if (sessionStore.get(key) == null) {
            sessionStore.put(key, new RmiSessionImpl());
        }

        return sessionStore.get(key);
    }

}
