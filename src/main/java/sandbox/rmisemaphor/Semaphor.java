package sandbox.rmisemaphor;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Semaphor extends Remote {


    void p() throws RemoteException;

    void v() throws RemoteException;


}
