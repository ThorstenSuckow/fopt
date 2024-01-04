package sandbox.rmichat;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChatServer extends Remote {

    public boolean addClient(ChatClient client) throws RemoteException;


    public void removeClient(ChatClient client) throws RemoteException;

    void sendMessage(String name, String msg) throws RemoteException;

}
