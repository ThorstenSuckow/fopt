package sandbox;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RunLaterDemo extends Application {

    Label label;

    public void start(Stage stage) {

        VBox root = new VBox();
        label = new Label("Text");

        root.getChildren().add(label);

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setWidth(400);
        stage.setHeight(300);
        stage.show();

        updateText();
    }

    /**
     * Creates a new Thread that adds a request to update a label text every 10 seconds.
     * The request is handled by the JavaFX application Thread.
     */
    public void updateText() {
        Thread t = new Thread(()-> {
            for (int i = 0; i < 10; i++) {
                int finalI = i;
                /**
                 * From the docs:
                 * "Patform.runLater(): Run the specified Runnable on the JavaFX Application Thread at some
                 * unspecified time in the future. This method, which may be called from any thread, will post
                 * the Runnable to an event queue and then return immediately to the caller."
                 */
                Platform.runLater(()->label.setText("Text " + finalI));
                System.out.println("in thread " + Thread.currentThread().getName());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) {

                }
            }
        });

        t.start();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
