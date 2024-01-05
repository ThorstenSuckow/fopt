package sandbox.rmi.chat;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class ChatView extends VBox {

    private ListView<String> chat;

    private Button sendButton;

    private TextField textField;


    private Label status;
    public ChatView(Presenter p) {
        p.init(this);

    }

    public TextField getTextField() {
        return textField;
    }

    public Button getSendButton() {
        return sendButton;
    }



    public void initView() {

        setPadding(new Insets(10, 10, 10, 10 ));
        setSpacing(10);

        chat = new ListView<>();
        chat.setPrefWidth(400);
        chat.setPrefHeight(300);

        getChildren().add(chat);

        HBox controls = new HBox();
        controls.setPrefWidth(400);
        controls.setSpacing(10);
        controls.setSpacing(10);

        textField = new TextField();
        HBox.setHgrow(textField, Priority.ALWAYS);
        sendButton = new Button("send");

        controls.getChildren().add(textField);
        controls.getChildren().add(sendButton);

        getChildren().add(controls);

        status = new Label();
        getChildren().add(status);

    }

    public void addMessage(String message) {
        chat.getItems().add(message);
    }

    public void showStatus(String message) {
        status.setText(message);
    }


}
