package sandbox;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.concurrent.atomic.AtomicInteger;

public class ShowAndWaitDemo extends Application {

    public void start(Stage stage) {

        AtomicInteger i = new AtomicInteger();
        VBox root = new VBox();
        Button b = new Button("open()");
        root.getChildren().add(b);
        b.setOnAction((e) -> {
            open(i.getAndIncrement());
        });

        Scene s = new Scene(root);
        stage.setScene(s);

        stage.show();

    }

    private void open(int i) {
        Stage s = new Stage();
        s.setX(i + 20);
        s.setY(i + 20);

        s.setTitle("Stage " + i);
        System.out.println("before show " + i);

        /**
         * creates nested event loops.
         * will return to the caller if the stage is closed
         * if the nested event loop is nested in another nested event loop,
         * all nested loops need to end before the method returns to the
         * origin caller
         * @see https://docs.oracle.com/javase/8/javafx/api/javafx/stage/Stage.html#showAndWait--
         */
        s.showAndWait();

        System.out.println("after show " + i);
    }

    public static void main(String[] args) {

        launch(args);

    }
}
