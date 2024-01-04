package sandbox.rmichat;

import javafx.application.Platform;
import javafx.event.ActionEvent;

import java.rmi.Naming;

public class Presenter {

    private ChatServer chatServer;

    private ChatClient chatClient;

    private ChatView chatView;


    public void init(ChatView chatView) {
        this.chatView = chatView;
        chatView.initView();
        chatView.showStatus("disconnected");
        initListeners();
    }

    private void initListeners() {
        chatView.getTextField().setOnAction(this::onTextFieldAction);
        chatView.getSendButton().setOnAction(this::onSendButtonAction);
    }

    private void sendMessage() {

        Platform.runLater( () -> disableControls(true));

        String msg = chatView.getTextField().getText();

        try {
            if (msg.trim().isEmpty()) {
                return;
            }
            chatServer.sendMessage(chatClient.getName(), msg);
            Platform.runLater( () -> {
                chatView.getTextField().setText("");
            });

        } catch (Exception e) {
            chatView.showStatus("error while sending message: " + e);
        } finally {
            Platform.runLater( () -> {
                disableControls(false);
                chatView.getTextField().requestFocus();
            });
        }
    }

    private void disableControls(boolean disable) {
        chatView.getTextField().setDisable(disable);
        chatView.getSendButton().setDisable(disable);
    }

    public void onSendButtonAction(ActionEvent e) {
        new Thread(this::sendMessage).start();
    }

    public void onTextFieldAction(ActionEvent e) {
        new Thread(this::sendMessage).start();
    }

    public void setChatClient(ChatClient chatClient) {
        this.chatClient = chatClient;
        initConnection();
    }

    private void initConnection() {

        chatView.showStatus("connecting...");

        try {
            chatServer = (ChatServer) Naming.lookup("rmichatserver");
            chatServer.addClient(chatClient);
            chatView.showStatus("Ready.");
        } catch (Exception e) {
            chatView.showStatus("[presenter] " + e);
            System.err.println("[presenter] " + e);

        }
    }

}