package sandbox.rmichat;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Iterator;

public class ChatServerImpl extends UnicastRemoteObject implements ChatServer {

    private ArrayList<ChatClient> clients;

    private boolean simDelay;

    public ChatServerImpl(boolean simDelay) throws RemoteException {

        this.simDelay = simDelay;
        clients = new ArrayList<>();
    }

    @Override
    public synchronized boolean addClient(ChatClient client) throws RemoteException {

       String name = client.getName();

        for (Iterator<ChatClient> iter = clients.iterator(); iter.hasNext();) {
            ChatClient cc = iter.next();

            try {
                if (cc.getName().equals(name)) {
                    return false;
                }
            } catch (RemoteException exc) {
                iter.remove();
            }
        }

        clients.add(client);
        return true;
    }

    @Override
    public synchronized void removeClient(ChatClient client) throws RemoteException {
        clients.remove(client);
    }

    @Override
    public synchronized void sendMessage(String name, String msg) throws RemoteException {

        if (simDelay) {
            try {
                Thread.sleep((long) (Math.random() * 1000));
            } catch (InterruptedException ignored) {

            }
        }

        Iterator<ChatClient> iter = clients.iterator();
        while (iter.hasNext()) {
            ChatClient cc = iter.next();
            try {
                cc.print(name + ": " + msg);
            } catch (RemoteException exc) {
                iter.remove();
            }

        }

    }
}
