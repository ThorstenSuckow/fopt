package sandbox.rmi.chat;

import javafx.application.Platform;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ChatClientImpl extends UnicastRemoteObject implements ChatClient {

    private ChatView chatView;

    private final String name;

    public ChatClientImpl(String name, ChatView chatView) throws RemoteException {
        this.name = name;
        this.chatView = chatView;
    }

    @Override
    public String getName() throws RemoteException {
        return name;
    }

    @Override
    public void print(String msg) throws RemoteException {
        Platform.runLater(()->chatView.addMessage(msg));
    }
}
