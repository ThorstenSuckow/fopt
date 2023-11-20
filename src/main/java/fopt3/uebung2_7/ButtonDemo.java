package fopt3.uebung2_7;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;



public class ButtonDemo extends Application {

    Label label;
    int i;
    class Incrementor implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent actionEvent) {
            increment();
        }
    }

    class Decrementor implements EventHandler<ActionEvent>  {

        @Override
        public void handle(ActionEvent actionEvent) {
            decrement();
        }

    }

    private void reset() {
        i = 0;
        label.setText("value: "+ i);
    }

    private void increment() {
        i++;
        label.setText("value: " + i);
    }

    private void decrement() {
        i--;
        label.setText("value: " + i);
    }


    public void start(Stage primaryStage) {



        VBox root = new VBox();

        Button inc = new Button("increment()");
        Button dec = new Button("decrement()");
        Button res = new Button("reset()");
        res.setOnAction((e)->reset());
        label = new Label();

        inc.setOnAction(new Incrementor());
        dec.setOnAction(new Decrementor());

        reset();

        root.getChildren().add(inc);
        root.getChildren().add(dec);
        root.getChildren().add(res);
        root.getChildren().add(label);


        Scene scene = new Scene(root, 400, 120);

        primaryStage.setScene(scene);

        primaryStage.show();
    }


    public static void main(String[] args) {

        launch(args);

    }


}
