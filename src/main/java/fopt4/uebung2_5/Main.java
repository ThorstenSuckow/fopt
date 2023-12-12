package fopt4.uebung2_5;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    public void start(Stage stage) {

        VBox root = new VBox();
        Scene scene = new Scene(root);

        // scene's immediate width/height is 0d
        // stage returns NaN for width / height before the window is rendered
        stage.setScene(scene);
        System.out.println("[scene] Before show: " + scene.getWidth() + " " + scene.getHeight());
        System.out.println("[stage] Before show: " + stage.getWidth() + " " + stage.getHeight());

        stage.show();

        // height / width automatically set by application.
        System.out.println("[scene] After show: " + scene.getWidth() + " " + scene.getHeight());
        System.out.println("[stage] After show: " + stage.getWidth() + " " + stage.getHeight());
    }


    public static void main(String[] args) {

        launch(args);

    }
}
