package fopt4.uebung2_11;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.Optional;

public class Main extends Application {


    public void start(Stage stage) {

        HBox root = new HBox();
        root.setPadding(new Insets(10));
        root.setSpacing(10);

        Button input = new Button("input");
        input.setOnAction(this::onInput);

        root.getChildren().addAll(input);

        Scene scene = new Scene(root);
        stage.setScene(scene);

        stage.show();

    }

    private void onInput(ActionEvent e) {

        TextInputDialog t = new TextInputDialog();
        t.setTitle("Eingabe");
        t.setHeaderText(null);
        t.setContentText("Bitte Text eingeben");

        Optional<String> res = t.showAndWait();

        if (res.isPresent()) {

            Alert a = new Alert(Alert.AlertType.INFORMATION);

            a.setTitle("Ergebnis");
            a.setHeaderText("Die Eingabe lautet:");
            a.setContentText(res.get());

            a.show();

        }

    }



    public static void main(String[] args) {
        launch(args);
    }

}
