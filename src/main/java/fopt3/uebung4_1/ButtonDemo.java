package fopt3.uebung4_1;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;



public class ButtonDemo extends Application {

    public void start(Stage stage) {

        Pane pane = new Pane();

        final int BUTTON_HEIGHT = 30;
        final int BUTTON_WIDTH = 120;



        for (int i = 0; i < 10; i++) {

            Button btn = new Button();
            btn.setPrefHeight(BUTTON_HEIGHT);
            btn.setPrefWidth(BUTTON_WIDTH);
            btn.setText("Button " + i + " "+ btn.getPrefHeight());

            if (i >= 1 && i <= 8) {
                btn.layoutYProperty().bind(
                    Bindings.max(0, pane.heightProperty().divide(8).multiply(i).subtract(btn.getPrefHeight()))
                );
                btn.layoutXProperty().bind(
                    Bindings.max(0, pane.widthProperty().divide(8).multiply(i).subtract(btn.getPrefWidth()))
                );
            }

            pane.getChildren().add(btn);
        }

        pane.getChildren().get(0).setLayoutX(0);
        pane.getChildren().get(0).setLayoutY(0);

        pane.getChildren().getLast().layoutXProperty().bind(pane.widthProperty().subtract(((Button)pane.getChildren().getLast()).getPrefWidth()));
        pane.getChildren().getLast().layoutYProperty().bind(pane.heightProperty().subtract(((Button)pane.getChildren().getLast()).getPrefHeight()));

        Scene scene = new Scene(pane);

        stage.setScene(scene);

        stage.show();

    }


    public static void main(String[] args) {

        launch(args);

    }

}
