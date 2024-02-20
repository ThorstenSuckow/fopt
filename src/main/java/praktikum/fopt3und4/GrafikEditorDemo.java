package praktikum.fopt3und4;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

public class GrafikEditorDemo extends Application {


    Pane canvas;

    Shape activeShape;

    private double lastX;

    private double lastY;

    private ToggleGroup toggleGroup;
    private RadioButton lineRadio;
    private RadioButton rectangleRadio;
    private RadioButton circleRadio;

    private SimpleIntegerProperty lineCountProperty;
    private SimpleIntegerProperty rectangleCountProperty;
    private SimpleIntegerProperty circleCountProperty;


    @Override
    public void start(Stage stage) throws Exception {

        lineCountProperty = new SimpleIntegerProperty();
        rectangleCountProperty = new SimpleIntegerProperty();
        circleCountProperty = new SimpleIntegerProperty();

        VBox root = new VBox();
        root.setPadding(new Insets(10));


        canvas = new Pane();
        Rectangle clip = new Rectangle();
        clip.widthProperty().bind(canvas.widthProperty());
        clip.heightProperty().bind(canvas.heightProperty());
        canvas.setClip(clip);
        canvas.setPrefHeight(600);
        canvas.setPrefWidth(800);
        VBox.setVgrow(canvas, Priority.ALWAYS);

        installListeners();

        root.getChildren().add(getTopToolbar());
        root.getChildren().add(canvas);
        root.getChildren().add(getBottomToolbar());

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Deluxe Paint");
        stage.show();
    }


    private void installListeners() {

        canvas.setOnMousePressed(e -> {
            activeShape = getShape();
            canvas.getChildren().add(activeShape);
            lastX = e.getX();
            lastY = e.getY();

            if (activeShape instanceof Line) {
                Line line = initLine((Line)activeShape);
                drawLine(line, lastX, lastY, lastX, lastY);
            }

            if (activeShape instanceof Rectangle) {
                Rectangle rectangle = initRectangle((Rectangle)activeShape);
                drawRectangle(rectangle, lastX, lastY, lastX, lastY);
            }

            if (activeShape instanceof Circle) {
                Circle circle = initCircle((Circle)activeShape);
                drawCircle(circle, lastX, lastY, lastX, lastY);
            }
        });

        canvas.setOnMouseDragged(e -> {
            if (activeShape == null) {
                return;
            }
            if (activeShape instanceof Line) {
                drawLine((Line)activeShape, lastX, lastY, e.getX(), e.getY());
            }
            if (activeShape instanceof Rectangle) {
                drawRectangle((Rectangle)activeShape, lastX, lastY, e.getX(), e.getY());
            }
            if (activeShape instanceof Circle) {
                drawCircle((Circle)activeShape, lastX, lastY, e.getX(), e.getY());
            }
        });

    }

    private void drawCircle(Circle circle, double startX, double startY, double endX, double endY) {
        circle.setCenterX(startX);
        circle.setCenterY(startY);
        circle.setRadius(Math.sqrt(Math.pow(startX - endX , 2) + Math.pow(startY - endY, 2)));
    }

    private Circle initCircle(Circle circle) {
        circleCountProperty.set(circleCountProperty.get() + 1);
        circle.setFill(Color.TRANSPARENT);
        circle.setStrokeWidth(1);
        circle.setStroke(Color.BLACK);

        return circle;
    }

    private Rectangle initRectangle(Rectangle rectangle) {

        rectangleCountProperty.set(rectangleCountProperty.get() + 1);
        rectangle.setFill(Color.TRANSPARENT);
        rectangle.setStrokeWidth(1);
        rectangle.setStroke(Color.BLACK);
        return rectangle;
    }

    private Line initLine(Line line) {
        lineCountProperty.set(lineCountProperty.get() + 1);
        return line;
    }

    private void drawRectangle(Rectangle rectangle, double startX, double startY, double endX, double endY) {
        rectangle.setX(Math.min(startX, endX));
        rectangle.setY(Math.min(startY, endY));
        rectangle.setWidth(Math.abs(endX - startX));
        rectangle.setHeight(Math.abs(endY - startY));
    }

    private void drawLine(Line line, double startX, double startY, double endX, double endY) {
        line.startXProperty().set(startX);
        line.startYProperty().set(startY);
        line.endXProperty().set(endX);
        line.endYProperty().set(endY);
    }
    private Shape getShape() {

        RadioButton radio = (RadioButton) toggleGroup.getSelectedToggle();

        if (radio == lineRadio) {
            return new Line();
        }
        if (radio == rectangleRadio) {
            return new Rectangle();
        }
        if (radio == circleRadio) {
            return new Circle();
        }
        return null;
    }


    private HBox getTopToolbar() {

        HBox hbox = new HBox();
        hbox.setPadding(new Insets(10));
        hbox.setSpacing(10);

        lineRadio = new RadioButton("Linie");
        lineRadio.setSelected(true);
        rectangleRadio = new RadioButton("Rechteck");
        circleRadio = new RadioButton("Kreis");

        toggleGroup = new ToggleGroup();
        toggleGroup.getToggles().addAll(lineRadio, rectangleRadio, circleRadio);
        hbox.getChildren().addAll(lineRadio, rectangleRadio, circleRadio);

        return hbox;
    }

    private HBox getBottomToolbar() {

        HBox hbox = new HBox();

        hbox.setPadding(new Insets(10));
        hbox.setSpacing(10);

        Label lineCountLabel = new Label();
        Label rectangleCountLabel = new Label();
        Label circleCountLabel = new Label();
        Label totalLabel = new Label();

        lineCountLabel.textProperty().bind(Bindings.convert(lineCountProperty));
        rectangleCountLabel.textProperty().bind(Bindings.convert(rectangleCountProperty));
        circleCountLabel.textProperty().bind(Bindings.convert(circleCountProperty));

        totalLabel.textProperty().bind(
            Bindings.convert(lineCountProperty.add(rectangleCountProperty).add(circleCountProperty))
        );

        hbox.getChildren().addAll(
            new Label("Linien:"),
            lineCountLabel,
            new Label("Rechtecke:"),
            rectangleCountLabel,
            new Label("Kreise:"),
            circleCountLabel,
            new Label("Gesamt:"),
            totalLabel
        );

        return hbox;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
