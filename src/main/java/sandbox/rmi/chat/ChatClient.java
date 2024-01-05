package sandbox.rmi.chat;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChatClient extends Remote {

    String getName() throws RemoteException;

    void print(String msg) throws RemoteException;

}
