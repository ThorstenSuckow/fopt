package fopt3.uebung4_6;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;



public class CubicCurveDemo extends Application {

    double deltaX;
    double deltaY;

    public void start(Stage stage) {

        VBox root = new VBox();
        root.setPadding(new Insets(20));

        Pane drawingPane = new Pane();
        Rectangle drawingRect = new Rectangle();
        drawingPane.setClip(drawingRect);
        drawingRect.widthProperty().bind(drawingPane.widthProperty());
        drawingRect.heightProperty().bind(drawingPane.heightProperty());

        Group startPoint = createStartPoint();
        Group endPoint = createEndPoint();
        Group magnetA = createMagnetA();
        Group magnetB = createMagnetB();

        /**
         * The line will be placed ad the layoutX/layoutY of the individual groups.
         * Circles have by default their center set to 0,0, which denotes the location
         * of the center(!) - NOT the top-left coordinate of the circle. This is why the
         * line seems to start start / end in the circles of the individual groups, whereas
         * this is really the layoutX / layoutY of the group.
         */
        CubicCurve curve = new CubicCurve();
        curve.setFill(Color.TRANSPARENT);
        curve.setStroke(Color.BLACK);
        curve.startXProperty().bind(startPoint.layoutXProperty());
        curve.startYProperty().bind(startPoint.layoutYProperty());
        curve.endXProperty().bind(endPoint.layoutXProperty());
        curve.endYProperty().bind(endPoint.layoutYProperty());
        curve.controlX1Property().bind(magnetA.layoutXProperty());
        curve.controlY1Property().bind(magnetA.layoutYProperty());
        curve.controlX2Property().bind(magnetB.layoutXProperty());
        curve.controlY2Property().bind(magnetB.layoutYProperty());

        Line lineA = new Line();
        lineA.getStrokeDashArray().addAll(6d);
        lineA.setStroke(Color.TURQUOISE);

        Line lineB = new Line();
        lineB.getStrokeDashArray().addAll(6d);
        lineB.setStroke(Color.TURQUOISE);

        Line lineC = new Line();
        lineC.getStrokeDashArray().addAll(6d);
        lineC.setStroke(Color.TURQUOISE);

        Line lineD = new Line();
        lineD.getStrokeDashArray().addAll(6d);
        lineD.setStroke(Color.TURQUOISE);

        lineA.startXProperty().bind(startPoint.layoutXProperty());
        lineA.startYProperty().bind(startPoint.layoutYProperty());
        lineA.endXProperty().bind(magnetA.layoutXProperty());
        lineA.endYProperty().bind(magnetA.layoutYProperty());

        lineB.startXProperty().bind(magnetA.layoutXProperty());
        lineB.startYProperty().bind(magnetA.layoutYProperty());
        lineB.endXProperty().bind(endPoint.layoutXProperty());
        lineB.endYProperty().bind(endPoint.layoutYProperty());

        lineC.startXProperty().bind(endPoint.layoutXProperty());
        lineC.startYProperty().bind(endPoint.layoutYProperty());
        lineC.endXProperty().bind(magnetB.layoutXProperty());
        lineC.endYProperty().bind(magnetB.layoutYProperty());

        lineD.startXProperty().bind(magnetB.layoutXProperty());
        lineD.startYProperty().bind(magnetB.layoutYProperty());
        lineD.endXProperty().bind(startPoint.layoutXProperty());
        lineD.endYProperty().bind(startPoint.layoutYProperty());


        drawingPane.getChildren().addAll(curve, lineA, lineB, lineC, lineD, startPoint, endPoint, magnetA, magnetB);

        root.getChildren().add(drawingPane);
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setWidth(800);
        stage.setHeight(600);
        stage.setTitle("CubicCurveDemo");
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }


    private Group createStartPoint() {
        return createCurvePoint(100, 400, "Start");
    }
    private Group createEndPoint() {
        return createCurvePoint(400, 100, "End");
    }

    private Group createMagnetA() {
        return createMagnet(100, 100, "A");
    }

    private Group createMagnetB() {
        return createMagnet(400, 400, "B");
    }

    private void onPointPress(MouseEvent e) {
        Group g = (Group) e.getSource();

        deltaX = g.getLayoutX() - e.getSceneX();
        deltaY = g.getLayoutY() - e.getSceneY();

        g.setCursor(Cursor.MOVE);
    }

    private void onPointRelease(MouseEvent e) {
        Group g = (Group) e.getSource();
        g.setCursor(Cursor.DEFAULT);
    }


    private void onPointDrag(MouseEvent e) {

        Group g = (Group) e.getSource();

        g.setLayoutX(e.getSceneX() + deltaX);
        g.setLayoutY(e.getSceneY() + deltaY);
    }


    private Group createCurvePoint(final double x, final double y, final String title) {
        Group g = new Group();
        g.setLayoutX(x);
        g.setLayoutY(y);

        Circle point = new Circle(5);
        point.setLayoutX(0);
        point.setLayoutY(0);

        Text text = new Text(title);
        text.setLayoutX(-10);
        text.setLayoutY(20);

        point.setFill(Color.TURQUOISE);

        g.getChildren().addAll(point, text);

        g.setOnMousePressed(this::onPointPress);
        g.setOnMouseReleased(this::onPointRelease);
        g.setOnMouseDragged(this::onPointDrag);

        return g;
    }


    private Group createMagnet(final double x, final double y, String title) {
        Group g = new Group();
        g.setLayoutX(x);
        g.setLayoutY(y);

        Circle point = new Circle(5);
        point.setLayoutX(0);
        point.setLayoutY(0);

        Text text = new Text(title);
        text.setLayoutX(-10);
        text.setLayoutY(20);

        point.setFill(Color.TRANSPARENT);
        point.setStroke(Color.DARKTURQUOISE);


        g.getChildren().addAll(point, text);

        g.setOnMousePressed(this::onPointPress);
        g.setOnMouseReleased(this::onPointRelease);
        g.setOnMouseDragged(this::onPointDrag);

        return g;
    }


}
