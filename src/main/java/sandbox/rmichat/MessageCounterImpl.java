package sandbox.rmichat;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Change to implements Serializable, MessageCounter to see effects when MessageCounter is returned by value,
 * not by reference.
 */
public class MessageCounterImpl extends UnicastRemoteObject implements MessageCounter {

    private int messages = 0;

    protected MessageCounterImpl() throws RemoteException {
    }

    @Override
    public void updateMessageCount() throws RemoteException {
        messages++;
    }

    public int getMessageCount() {
        return messages;
    }

    public String toString() {
        return String.valueOf(messages);
    }
}
