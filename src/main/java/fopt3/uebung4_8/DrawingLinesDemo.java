package fopt3.uebung4_8;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * Source code taken directly from Listing 4.6 in [Oec22]
 */
public class DrawingLinesDemo extends Application {
    private Pane graphicsPane;
    private double x, y;

    private ChoiceBox choiceBox;

    private int lineThickness = 1;

    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        graphicsPane = new Pane();
        root.setCenter(graphicsPane);
        HBox hbox = new HBox(20);
        Button b = new Button("Löschen");
        hbox.getChildren().add(b);
        hbox.getChildren().add(new Label("Irgendein unwichtiger Text"));

        hbox.getChildren().add(getChoiceBox());


        hbox.setPadding(new Insets(10));
        root.setBottom(hbox);

        graphicsPane.setOnMousePressed(
                e -> mousePressed(e.getX(), e.getY())
        );
        graphicsPane.setOnMouseDragged(
            e -> mouseDragged(e.getX(), e.getY())
        );
        b.setOnAction(
            e -> clear()
        );

        primaryStage.setTitle("Freihandzeichnen");
        primaryStage.setScene(new Scene(root, 400, 150));
        primaryStage.show();

        Rectangle clipRect = new Rectangle();
        clipRect.widthProperty().bind(graphicsPane.widthProperty());
        clipRect.heightProperty().bind(graphicsPane.heightProperty());
        graphicsPane.setClip(clipRect);
    }

    private void mousePressed(double newX, double newY) {
        x = newX;
        y = newY;
        mouseDragged(x, y);
    }

    private void mouseDragged(double newX, double newY) {
        Line line = new Line(x, y, newX, newY);
        line.setStrokeWidth(lineThickness);

        graphicsPane.getChildren().add(line);
        x = newX;
        y = newY;
    }

    private void clear() {
        graphicsPane.getChildren().clear();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private ChoiceBox getChoiceBox() {

        ChoiceBox<Integer> lineChoice = new ChoiceBox<>();
        lineChoice.getItems().addAll(1, 2, 3);

        lineChoice.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->  {
            lineThickness = newValue;
        });

        lineChoice.setValue(lineThickness);

        return lineChoice;
    }
}
