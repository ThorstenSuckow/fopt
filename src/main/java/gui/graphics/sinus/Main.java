package gui.graphics.sinus;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {


    public void start(Stage primaryStage) {

        SinusView sinusView = new SinusView(new SinusPresenter(new SinusModel()));

        Scene scene = new Scene(sinusView);

        primaryStage.setScene(scene);

        primaryStage.setTitle("Sinus");
        primaryStage.setWidth(800);
        primaryStage.setHeight(600);

        primaryStage.show();
    }


    public static void main(String[] args) {

        launch(args);

    }
}
