package sandbox.rmichat;

import sandbox.rmicallbyvalue.Dummy;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Iterator;

public class ChatServerImpl extends UnicastRemoteObject implements ChatServer {

    private ArrayList<ChatClient> clients;

    private boolean simDelay;

    private MessageCounter messageCounter;

    public ChatServerImpl(boolean simDelay) throws RemoteException {

        this.simDelay = simDelay;
        clients = new ArrayList<>();

        messageCounter = new MessageCounterImpl();
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
        sendMessage(client.getName() + " enters the chat!");
        return true;
    }

    @Override
    public synchronized void removeClient(ChatClient client) throws RemoteException {
        clients.remove(client);
        sendMessage(client.getName() + " says goodbye!");
    }


    private synchronized void sendMessage(String msg) throws RemoteException {

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
                cc.print(msg);
            } catch (RemoteException exc) {
                iter.remove();
            }

        }

    }

    @Override
    public synchronized void sendMessage(String name, String msg) throws RemoteException {
        sendMessage(name + ": " + msg);
    }

    @Override
    public MessageCounter getMessageCounter() throws RemoteException {
        return messageCounter;
    }

    public void printMessageCount() {
        System.out.println("[MessageCounter] " + messageCounter);
    }

}


