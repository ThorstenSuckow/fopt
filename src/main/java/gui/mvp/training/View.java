package gui.mvp.training;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class View extends VBox {

    private ListView<TrainingUnit> listView;

    private Button addButton;

    private Button deleteButton;

    private Label time;
    private Label distance;
    private Label marker;
    private Label meanSpeed;


    public View() {
        initView();
    }


    protected void initView() {
        this.setPadding(new Insets(10));
        this.setSpacing(10);

        this.getChildren().add(createUi());
        this.getChildren().add(createButtons());
    }


    protected HBox createUi() {

        HBox hbox = new HBox();
        hbox.setSpacing(10);

        createListView();

        hbox.getChildren().add(getListView());
        hbox.getChildren().add(createLabelBox());

        return hbox;
    }

    public ListView getListView() {
        return listView;
    }

    public HBox createButtons() {

        HBox box = new HBox(); // ä -> \u00e4
        box.setSpacing(10);
        addButton = new Button("Neue Trainingseinheit hinzuf\u00fcgen"); // ü
        deleteButton = new Button("Trainingseinheit l\u00f6schen"); // ö

        box.getChildren().add(addButton);
        box.getChildren().add(deleteButton);

        return box;
    }

    public Button getDeleteButton() {
        return deleteButton;
    }


    public Button getAddButton() {
        return addButton;
    }

    protected GridPane createLabelBox() {
        GridPane labelBox = new GridPane();
        labelBox.setHgap(10);
        labelBox.setVgap(10);


        Label markerLabel = new Label("Kennung:");
        marker = new Label();

        Label distanceLabel = new Label("Entfernung [km]:");
        distance = new Label();

        Label timeLabel = new Label("Zeit [Minuten]:");
        time = new Label();

        Label meanSpeedLabel = new Label("Durchschnittsgeschwindigkeit [km/h]:");
        meanSpeed = new Label();

        labelBox.add(markerLabel, 0, 0);
        labelBox.add(marker, 1, 0);

        labelBox.add(distanceLabel, 0, 1);
        labelBox.add(distance, 1, 1);

        labelBox.add(timeLabel, 0, 2);
        labelBox.add(time, 1, 2);

        labelBox.add(meanSpeedLabel, 0, 3);
        labelBox.add(meanSpeed, 1, 3);


        return labelBox;
    }

    protected ListView<TrainingUnit> createListView() {
        if (listView != null) {
            return listView;
        }
        listView = new ListView<>();

        return listView;
    }

    public void updateTrainingItemInfo(TrainingUnit unit) {
        if (unit == null) {
            marker.setText("");
            time.setText("");
            distance.setText("");
            meanSpeed.setText("");
            return;
        }

        marker.setText(unit.getMarker());
        time.setText(String.valueOf(unit.getTime()));
        distance.setText(String.valueOf(unit.getDistance()));
        meanSpeed.setText(String.valueOf(unit.getMeanSpeed()));

    }

}
