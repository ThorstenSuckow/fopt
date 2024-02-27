package sandbox;

import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class UiDemo extends Application {


    private double lastX;
    private double lastY;
    private Rectangle activeRectangle;
    private CheckBox cb;

    private Pane pane;

    public void start(Stage primaryStage) {

        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));

        pane = new Pane();
        cb = new CheckBox();
        cb.setText("Rot");

        cb.selectedProperty().addListener(this::onCheckBoxChange);

        VBox.setVgrow(pane, Priority.ALWAYS);
        vbox.getChildren().addAll(cb, pane);


        pane.setOnMousePressed(this::onMousePressed);
        pane.setOnMouseDragged(this::onMouseDragged);
        pane.setOnMouseReleased(this::onMouseReleased);

        Scene scene = new Scene(vbox);
        primaryStage.setScene(scene);

        primaryStage.setWidth(800);
        primaryStage.setHeight(600);
        primaryStage.setTitle("UiDemo");
        primaryStage.show();
    }

    private void onCheckBoxChange(ObservableValue<? extends Boolean> obs, boolean oldValue, boolean newValue) {
        if (activeRectangle == null) {
            return;
        }
        changeColor(newValue);
    }

    private Rectangle createRectangle(double x, double y) {

        Rectangle r = new Rectangle();

        r.setFill(null);
        r.setX(x);
        r.setY(y);
        r.setStroke(Color.BLACK);

        return r;
    }


    private void onMousePressed(MouseEvent e) {
        lastX = e.getX();
        lastY = e.getY();

        activeRectangle = createRectangle(lastX, lastY);
        pane.getChildren().add(activeRectangle);
    }


    private void onMouseDragged(MouseEvent e) {

        if (activeRectangle == null) {
            return;
        }

        double x = e.getX();
        double y = e.getY();


        activeRectangle.setX(Math.min(lastX, x));
        activeRectangle.setY(Math.min(lastY, y));

        activeRectangle.setWidth(Math.abs(lastX - x));
        activeRectangle.setHeight(Math.abs(lastY - y));

    }

    private void changeColor(boolean red) {
        if (activeRectangle == null) {
            return;
        }
        activeRectangle.setFill(red ? Color.RED : null);

    }


    private void onMouseReleased(MouseEvent e) {
        changeColor(cb.selectedProperty().get());
    }


}
