package gui.mvp.training;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;



public class EditorDialog extends Stage {


    private TextField markerField;

    private TextField timeField;

    private TextField distanceField;

    private Button addButton;

    private Button cancelButton;

    private TrainingUnit trainingUnit;

    private Label errorLabel;




    public EditorDialog() {
        initView();
    }

    private void initView() {
        initModality(Modality.APPLICATION_MODAL);
        setWidth(600);
        setHeight(300);

        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(10);

        HBox markerBox = new HBox();
        markerBox.setSpacing(10);
        markerBox.getChildren().add(new Label("Kennung (nicht leer):"));
        markerField = new TextField();
        markerField.setId("markerTF");
        markerBox.getChildren().add(markerField);


        HBox distanceBox = new HBox();
        distanceBox.setSpacing(10);
        distanceBox.getChildren().add(new Label("Entfernung (in km):"));
        distanceField = new TextField();
        distanceField.setId("distanceTF");
        distanceBox.getChildren().add(distanceField);


        HBox timeBox = new HBox();
        timeBox.setSpacing(10);
        timeBox.getChildren().add(new Label("Zeit (in Minuten):"));
        timeField = new TextField();
        timeField.setId("timeTF");
        timeBox.getChildren().add(timeField);

        HBox buttonBox = new HBox();
        buttonBox.setSpacing(10);
        addButton = new Button("Hinzuf\u00fcgen");

        addButton.setOnAction(this::onAddAction);

        cancelButton = new Button("Abbrechen");
        buttonBox.getChildren().addAll(addButton, cancelButton);
        cancelButton.setOnAction(e -> close());

        errorLabel = new Label("");
        errorLabel.setId("errMsgLabel");

        vbox.getChildren().addAll(markerBox, distanceBox, timeBox, buttonBox, errorLabel);


        setScene(new Scene(vbox));

        setTitle("hinzuf\u00fcgen");
    }

    private void onAddAction(ActionEvent actionEvent) {


    }

    public Button getAddButton() {
        return addButton;
    }

    public Label getErrorLabel() {
        return errorLabel;
    }

    public TrainingUnit getTrainingUnit() {

        String time = timeField.textProperty().get();
        String marker = markerField.textProperty().get().trim();
        String distance = distanceField.textProperty().get();

        float timeValue;
        float distanceValue;

        if (marker.isEmpty()) {
            errorLabel.setText("Kennung: ung\u00fcltige Eingabe");
            return null;
        }

        try {
            distanceValue = Float.parseFloat(distance);
        } catch (NumberFormatException e) {
            errorLabel.setText("Entfernung: ung\u00fcltige Eingabe");
            return null;
        }

        if (distanceValue <= 0) {
            errorLabel.setText("Entfernung: ung\u00fcltige Eingabe");
            return null;
        }

        try {
            timeValue = Float.parseFloat(time);
        } catch (NumberFormatException e) {
            errorLabel.setText("Zeit: ung\u00fcltige Eingabe");
            return null;
        }

        if (timeValue <= 0) {
            errorLabel.setText("Zeit: ung\u00fcltige Eingabe");
            return null;
        }


        errorLabel.setText("");

        return new TrainingUnit(
            marker,
            distanceValue,
            timeValue
        );

    }

}
