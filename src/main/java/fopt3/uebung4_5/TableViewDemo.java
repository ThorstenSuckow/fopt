package fopt3.uebung4_5;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class TableViewDemo extends Application {

    ObservableList<SimpleDoubleProperty[]> valueList;


    public void start(Stage stage) {

        VBox root = new VBox();
        root.setPadding(new Insets(10));

        valueList = FXCollections.observableArrayList(
            new SimpleDoubleProperty[]{new SimpleDoubleProperty(1), new SimpleDoubleProperty(2)},
            new SimpleDoubleProperty[]{new SimpleDoubleProperty(3), new SimpleDoubleProperty(4)},
            new SimpleDoubleProperty[]{new SimpleDoubleProperty(5), new SimpleDoubleProperty(6)},
            new SimpleDoubleProperty[]{new SimpleDoubleProperty(7), new SimpleDoubleProperty(8)}
        );

        TableView<SimpleDoubleProperty[]> tableView = new TableView<>(valueList);
        tableView.setTableMenuButtonVisible(true);

        TableColumn<SimpleDoubleProperty[], Number> value1Column = new TableColumn<>("x");
        value1Column.setCellValueFactory(item -> item.getValue()[0]);
        value1Column.setPrefWidth(100);
        tableView.getColumns().add(value1Column);

        TableColumn<SimpleDoubleProperty[], Number> value2Column = new TableColumn<>("y");
        value2Column.setCellValueFactory(item -> item.getValue()[1]);
        value2Column.setPrefWidth(100);
        tableView.getColumns().add(value2Column);

        TableColumn<SimpleDoubleProperty[], Number> value3Column = new TableColumn<>("y");
        // Bindings.multiply() creates a binding with the two properties  - setting either will update the value for the
        // returned ObservableValue<Number>
        value3Column.setCellValueFactory(item -> Bindings.multiply(item.getValue()[0], item.getValue()[1]));
        value3Column.setPrefWidth(100);
        tableView.getColumns().add(value3Column);


        Button shuffleButton = new Button("Shuffle!");
        shuffleButton.setOnAction(this::shuffleValues);

        root.getChildren().add(tableView);
        root.getChildren().add(shuffleButton);

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("TableViewDemo");
        stage.show();


    }


    private void shuffleValues(ActionEvent e) {

        for (SimpleDoubleProperty[] doubles: valueList) {
            doubles[0].set((int)(Math.random() * 100));
            doubles[1].set((int)(Math.random() * 100));
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
