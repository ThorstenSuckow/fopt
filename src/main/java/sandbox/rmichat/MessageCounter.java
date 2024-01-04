package sandbox.rmichat;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MessageCounter extends Remote {


    void updateMessageCount() throws RemoteException;

    int getMessageCount() throws RemoteException;

}
