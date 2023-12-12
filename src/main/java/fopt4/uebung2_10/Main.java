package fopt4.uebung2_10;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.Optional;

public class Main extends Application {


    public void start(Stage stage) {

        HBox root = new HBox();
        root.setPadding(new Insets(10));
        root.setSpacing(10);

        Button alert = new Button("alert");
        alert.setOnAction(this::onAlert);

        Button exit = new Button("exit");
        exit.setOnAction(this::onExit);

        root.getChildren().addAll(alert, exit);

        Scene scene = new Scene(root);
        stage.setScene(scene);

        stage.show();

    }

    private void onExit(ActionEvent e) {

        Alert a = new Alert(Alert.AlertType.CONFIRMATION);

        a.setTitle("[title] Bestätigung erforderlich");
        a.setHeaderText(null);
        a.setContentText("[content] Wollen Sie das Programm wirklich verlassen");

        // custom button types using setAll in this case.
        // addAll will throw an exception since ButtonType.CANCEL
        // already exists in the list of ButtonTypes

        ButtonType systemExit = new ButtonType("System.exit()");
        ButtonType platformExit = new ButtonType("Platform.exit()");
        a.getButtonTypes().setAll(systemExit, platformExit, ButtonType.CANCEL);

        Optional<ButtonType> choice = a.showAndWait();

        if (choice.isPresent()) {

            if (choice.get() == systemExit) {
                System.exit(0);
            }
            if (choice.get() == platformExit) {
                Platform.exit();
            }

        }

    }

    private void onAlert(ActionEvent e) {
        Alert a = new Alert(Alert.AlertType.WARNING);
        a.setTitle("[title] Warnung!");
        a.setHeaderText("[header] Das ist ein Alert-Dialog");
        a.setContentText("[content] Das ist der Content-Text");
        a.show();
    }

    public static void main(String[] args) {
        System.out.println(
                "using the Platform.exit()-button will show a message on the console."
        );
        launch(args);
        System.out.println(
                "if Platform.exit() was called, you will see this message. " +
                "If System.exit was called, this message will not be displayed."
        );
    }

}
