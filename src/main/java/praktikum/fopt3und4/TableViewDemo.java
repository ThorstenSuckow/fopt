package praktikum.fopt3und4;

import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


import javax.management.StringValueExp;
import java.util.concurrent.atomic.AtomicBoolean;

public class TableViewDemo extends Application {


    private TableView<StringDoublePair> tableView;

    private class ItemDialog extends Stage {
        Button addButton;

        AtomicBoolean addButtonClicked;
        TextField valueTextField;

        TextField nameTextField;
        Button cancelButton;
        public ItemDialog() {
            initView(null);
        }

        public ItemDialog(StringDoublePair sel) {
            initView(sel);
        }

        private void initView(StringDoublePair sel) {

            initModality(Modality.APPLICATION_MODAL);
            VBox root = new VBox();
            root.setPadding(new Insets(10));
            setScene(new Scene(root));

            HBox row1 = new HBox();
            row1.setPadding(new Insets(10));
            row1.getChildren().add(new Label("Entgeldgruppe:"));
            nameTextField = new TextField();
            row1.getChildren().add(nameTextField);

            HBox row2 = new HBox();
            row2.setPadding(new Insets(10));
            row2.getChildren().add(new Label("Gehalt:"));
            valueTextField = new TextField();
            row2.getChildren().add(valueTextField);

            addButton = new Button("Hinzufügen");
            cancelButton = new Button("Abbrechen");

            cancelButton.setOnAction((evt)->close());
            HBox row3 = new HBox();
            row3.getChildren().add(addButton);
            row3.getChildren().add(cancelButton);

            root.getChildren().add(row1);
            root.getChildren().add(row2);
            root.getChildren().add(row3);

            if (sel != null){
                nameTextField.setText(sel.nameProperty().get());
                valueTextField.setText(String.valueOf(sel.valueProperty().get()));
                addButton.setText("Speichern");
            }

            addButtonClicked = new AtomicBoolean(false);
            addButton.setOnAction((evt)-> {

                if (valueTextField.getText().trim().isEmpty()) {
                    Alert al = new Alert(Alert.AlertType.ERROR);
                    al.setTitle("Fehler");
                    al.setHeaderText(null);
                    al.setContentText("Bitte für \"Entgeldgruppe\" einen gültigen Wert eingeben.");
                    al.show();
                    return;
                }

                try {
                    Double.valueOf(valueTextField.getText());
                } catch (NumberFormatException exc) {
                    Alert al = new Alert(Alert.AlertType.ERROR);
                    al.setTitle("Fehler");
                    al.setHeaderText(null);
                    al.setContentText("Bitte für \"Gehalt\" einen gültigen Wert eingeben.");
                    al.show();
                    return;
                }


                addButtonClicked.set(true);
                close();
            });
        }

    }

    private class StringDoublePair {


        private final SimpleStringProperty name;
        private final SimpleDoubleProperty value;

        public StringDoublePair(String name, double value) {
            this.name = new SimpleStringProperty(name);
            this.value = new SimpleDoubleProperty(value);
        }

        public SimpleStringProperty nameProperty() {
            return name;
        }

        public SimpleDoubleProperty valueProperty() {
            return value;
        }

    }


    public void start(Stage primaryStage) {

        VBox root = new VBox();
        root.setPadding(new Insets(10));

        root.getChildren().add(getTableView());
        root.getChildren().add(getToolbar());

        Scene s = new Scene(root);

        primaryStage.setScene(s);

        primaryStage.setWidth(800);
        primaryStage.setHeight(600);
        primaryStage.show();
    }


    private HBox getToolbar() {

        HBox hbox = new HBox();
        hbox.setPadding(new Insets(10));

        Button addButton = new Button("Hinzufügen");
        addButton.setOnAction(this::onAddButtonAction);
        Button editButton = new Button("Ändern");
        editButton.setOnAction(this::onEditButtonAction);
        Button deleteButton = new Button("Löschen");
        deleteButton.setOnAction(this::onDeleteButtonAction);
        Button editAllButton = new Button("Alle anpassen");
        editAllButton.setOnAction(this::onEditAllButton);

        hbox.getChildren().add(addButton);
        hbox.getChildren().add(editButton);
        hbox.getChildren().add(deleteButton);
        hbox.getChildren().add(editAllButton);

        return hbox;
    }

    private void onEditAllButton(ActionEvent e) {

        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        VBox root = new VBox();
        root.setPadding(new Insets(10));
        dialog.setScene(new Scene(root));


        HBox row2 = new HBox();
        row2.setPadding(new Insets(10));
        row2.getChildren().add(new Label("Prozentuale Anpassung:"));
        TextField valueTextField = new TextField();
        row2.getChildren().add(valueTextField);

        Button addButton = new Button("Anpassen");
        Button cancelButton = new Button("Abbrechen");

        cancelButton.setOnAction((evt)->dialog.close());
        HBox row3 = new HBox();
        row3.getChildren().add(addButton);
        row3.getChildren().add(cancelButton);

        root.getChildren().add(row2);
        root.getChildren().add(row3);

        AtomicBoolean addButtonClicked = new AtomicBoolean(false);
        addButton.setOnAction((evt)-> {
            double val = 0;
            try {
                val = Double.valueOf(valueTextField.getText());
            } catch (NumberFormatException exc) {
            }

            if (val < 0) {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle("Fehler");
                al.setHeaderText(null);
                al.setContentText("Bitte für \"Prozentuale Anpassung\" einen gültigen Wert eingeben.");
                al.show();
                return;
            }


            addButtonClicked.set(true);
            dialog.close();
        });

        dialog.showAndWait();

        double value = Double.valueOf(valueTextField.getText());

        ObservableList<StringDoublePair> list = tableView.getItems();

        // 20 Euro
        // 20 * 20 / 100 .... 20 * 100

        for(StringDoublePair pair: list) {
            double base = pair.valueProperty().get();
            pair.valueProperty().set(base + (base * (value / 100)));
        }
    }

    private void onEditButtonAction(ActionEvent evt) {

        TableView<StringDoublePair> tableView = getTableView();

        StringDoublePair sel = tableView.getSelectionModel().getSelectedItem();

        if (sel == null) {
            return;
        }

        ItemDialog dialog = new ItemDialog(sel);

        dialog.showAndWait();

        if (!dialog.addButtonClicked.get()) {
            return;
        }

        String name = dialog.nameTextField.getText();
        Double value = Double.valueOf(dialog.valueTextField.getText());

        sel.valueProperty().set(value);
        sel.nameProperty().set(name);
    }

    private void onDeleteButtonAction(ActionEvent evt) {

        TableView<StringDoublePair> tableView = getTableView();

        StringDoublePair sel = tableView.getSelectionModel().getSelectedItem();

        tableView.getItems().remove(sel);
    }

    private void onAddButtonAction(ActionEvent e) {

        ItemDialog dialog = new ItemDialog();

        dialog.showAndWait();

        if (!dialog.addButtonClicked.get()) {
            return;
        }

        String name = dialog.nameTextField.getText();
        Double value = Double.valueOf(dialog.valueTextField.getText());

        getTableView().getItems().add(new StringDoublePair(name, value));
    }

    private TableView<String> getListView() {

        return null;
    }

    private TableView<StringDoublePair> getTableView() {

        if (tableView != null) {
            return tableView;
        }

        tableView = new TableView<>();

        TableColumn<StringDoublePair, String> col1 = new TableColumn<>("Entgeldgruppe");
        col1.setCellValueFactory(item -> item.getValue().nameProperty());

        TableColumn<StringDoublePair, Number> col2 = new TableColumn<>("Gehalt");
        col2.setCellValueFactory(item -> item.getValue().valueProperty());


        ObservableList<StringDoublePair> data = FXCollections.observableArrayList(
            new StringDoublePair("A", 0.0),
            new StringDoublePair("B", 1.0),
            new StringDoublePair("C", 2.0),
            new StringDoublePair("D", 3.0)
        );

        tableView.getItems().setAll(data);
        tableView.getColumns().add(col1);
        tableView.getColumns().add(col2);

        return tableView;
    }


    public static void main(String[] args) {
        launch(args);
    }

}
