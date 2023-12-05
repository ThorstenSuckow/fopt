package fopt3.uebung2_10;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Omitting fx:id in the fxml-file of renaming the label-property in the Controller-class without
 * changing the fx:id will throw a RuntimeException if any of the methods actively accessing "label"
 * are called.
 *
 */
public class FxmlDemo extends Application {



    @Override
    public void start(Stage stage) throws Exception {

        VBox root = FXMLLoader.load(getClass().getClassLoader().getResource("uebung2_10/demo.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("FxmlDemo");
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
