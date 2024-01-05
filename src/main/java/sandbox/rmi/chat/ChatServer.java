package sandbox.rmi.chat;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChatServer extends Remote {

    boolean addClient(ChatClient client) throws RemoteException;


    void removeClient(ChatClient client) throws RemoteException;

    void sendMessage(String name, String msg) throws RemoteException;

    MessageCounter getMessageCounter() throws  RemoteException;

    void printMessageCount() throws  RemoteException;

}
