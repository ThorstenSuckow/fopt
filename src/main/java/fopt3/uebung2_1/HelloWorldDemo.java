package fopt3.uebung2_1;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;

public class HelloWorldDemo extends Application {


    @Override
    public void start(Stage primaryStage) {

        Label l1 = new Label("Hello");
        Label l2 = new Label("World");

        HBox root = new HBox();
        root.getChildren().add(l1);
        root.getChildren().add(l2);

        Scene scene = new Scene(root, 240, 40);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Hello World!");

        primaryStage.show();

    }


    public static void main(String[] args) {

        launch(args);
    }


}
