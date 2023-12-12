package fopt4.uebung2_6;


import javafx.application.Application;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {

    public void start(Stage stage) {
        stage.setWidth(300);
        stage.setHeight(300);

        // compute width of primary screen
        stage.setX(Screen.getPrimary().getBounds().getWidth() - stage.getWidth());
        stage.setY(0);
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
