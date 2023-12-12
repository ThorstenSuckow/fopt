package fopt4.uebung2_7;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    public void start(Stage stage) {

        stage.initStyle(StageStyle.UNDECORATED);
        stage.setHeight(300);
        stage.setWidth(300);
        VBox root = new VBox();
        root.setAlignment(Pos.TOP_RIGHT);
        Button b = new Button("Close");
        b.setOnAction((e) -> stage.hide());
        root.getChildren().add(b);
        Scene scene = new Scene(root);

        // scene's immediate width/height is 0d
        // stage returns NaN for width / height before the window is rendered
        stage.setScene(scene);
        System.out.println("[scene] Before show: " + scene.getWidth() + " " + scene.getHeight());
        System.out.println("[stage] Before show: " + stage.getWidth() + " " + stage.getHeight());

        stage.show();

        // height / width automatically set by application.
        // since stage is UNDECORATED, the stage's dimensions equal to the scene's dimension
        // (no title bar taken into account)
        System.out.println("[scene] After show: " + scene.getWidth() + " " + scene.getHeight());
        System.out.println("[stage] After show: " + stage.getWidth() + " " + stage.getHeight());
    }


    public static void main(String[] args) {

        launch(args);

    }
}
