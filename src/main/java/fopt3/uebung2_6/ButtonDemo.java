package fopt3.uebung2_6;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

class Incrementor implements EventHandler<ActionEvent> {

    int i = 0;

    @Override
    public void handle(ActionEvent actionEvent) {
        Button b1 = (Button)actionEvent.getSource();

        b1.setText("increment(" + i++ +")");
    }
}

public class ButtonDemo extends Application {


    public void start(Stage primaryStage) {

        VBox root = new VBox();

        Button b1 = new Button();
        b1.setText("increment()");
        b1.setOnAction(new Incrementor());
        b1.fire();
        root.getChildren().add(b1);

        Scene scene = new Scene(root, 400, 120);

        primaryStage.setScene(scene);

        primaryStage.show();
    }


    public static void main(String[] args) {

        launch(args);

    }


}
