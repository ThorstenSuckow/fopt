package klausurvorbereitung.ss19;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CheckBoxDemo extends Application {


    public void start(Stage stage) {

        VBox root = new VBox();


        CheckBox cb = new CheckBox("Demo CheckBox");

        root.getChildren().add(cb);

        cb.selectedProperty().addListener((ob, oldValue, newValue) -> {
            System.out.println(newValue ? "checked" : "unchecked");
        });

        Scene s = new Scene(root);

        stage.setScene(s);

        stage.show();

    }


    public static void main(String[] args) {

        launch(args);

    }

}
