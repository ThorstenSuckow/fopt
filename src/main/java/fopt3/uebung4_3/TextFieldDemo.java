package fopt3.uebung4_3;

import javafx.application.Application;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;



public class TextFieldDemo extends Application {

    Label textFieldValueLabel;

    Label boundLabel;

    Label enterKeyLabel;

    TextField textField;

    public void start(Stage stage) {

        VBox root = new VBox();
        root.setPadding(new Insets(10));
        root.setSpacing(10);

        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.setHgap(10);



        textField = new TextField();

        textFieldValueLabel = new Label();

        boundLabel = new Label();
        boundLabel.textProperty().bind(textField.textProperty());

        enterKeyLabel = new Label();

        textField.setOnAction(this::onTextFieldAction);

        root.getChildren().add(textField);

        root.getChildren().add(gridPane);

        gridPane.add(new Label("ChangeListener:"), 0, 0);
        gridPane.add(textFieldValueLabel, 1, 0);

        gridPane.add(new Label("Bound:"), 0, 1);
        gridPane.add(boundLabel, 1, 1);

        gridPane.add(new Label("Enter key:"), 0, 2);
        gridPane.add(enterKeyLabel, 1, 2);


        textField.textProperty().addListener(this::onTextFieldChange);

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("TextFieldDemo");
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    private void onTextFieldChange(Observable field, String oldValue, String newValue) {
        textFieldValueLabel.setText(newValue);
    }

    private void onTextFieldAction(ActionEvent e) {
        enterKeyLabel.setText(((TextField) e.getSource()).getText());
    }

}
