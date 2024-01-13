package fopt6.uebung17_2;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RmiSessionContext extends Remote {


    /**
     * This implementation allows for mapping a Session-Object to a string.
     * This string would have to be computed and send to the client, or specified by the
     * client itself, mimicking s Session-Id.
     *
     * @param key
     * @return
     * @throws RemoteException
     */
    RmiSession getSession(String key) throws RemoteException;

}
