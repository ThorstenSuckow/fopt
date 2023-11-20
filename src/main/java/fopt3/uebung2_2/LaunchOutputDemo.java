package fopt3.uebung2_2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * launch() is called in the main-Thread, start is being called in a different thread.
 * launch() is blocking until Stage is closed - the output appears on the console afterwards.
 */
public class LaunchOutputDemo extends Application {


    @Override
    public void start(Stage primaryStage) {

        primaryStage.show();

    }


    public static void main(String[] args) {

        launch(args);

        System.out.println("Exit.");
    }


}
