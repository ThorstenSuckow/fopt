package sandbox.rmichat;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ChatDemo extends Application {

    public void start(Stage stage) {

        try {


            String userName = getParameters().getUnnamed().get(0);

            Presenter presenter = new Presenter();
            ChatView chatView = new ChatView(presenter);
            ChatClient client = new ChatClientImpl(userName, chatView);
            presenter.setChatClient(client);

            Scene scene = new Scene(chatView);

            stage.setScene(scene);

            stage.setWidth(800);

            stage.setTitle("RMI ChatDemo");
            stage.show();

        } catch (Exception e) {
            System.err.println("Unexpected exception: " + e);
        }
    }


    public static void main(String[] args) {

        String[] opts = new String[1];
        if (args.length < 1) {
            System.out.println("Usage: java ChatDemo [UserName]");
            System.out.println("setting random username...");
            opts[0] = "user_" + ((int)(Math.random() * 100));
        } else {
            opts = args;
        }

        launch(opts);
    }

}
