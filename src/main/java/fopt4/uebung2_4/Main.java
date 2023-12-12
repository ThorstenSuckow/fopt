package fopt4.uebung2_4;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public void start(Stage stage) {

        // stage returns NaN for width / height before the window is rendered
        System.out.println("Before show: " + stage.getWidth() + " " + stage.getHeight());
        stage.show();
        System.out.println("After show: " + stage.getWidth() + " " + stage.getHeight());
    }


    public static void main(String[] args) {

        launch(args);

    }
}
