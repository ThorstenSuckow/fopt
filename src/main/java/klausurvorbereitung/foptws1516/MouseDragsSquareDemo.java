package klausurvorbereitung.foptws1516;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class MouseDragsSquareDemo extends Application {

    private Rectangle rectangle;

    private double relX;

    private double paneX;
    private double paneY;

    private double rectangleWidth;
    private double rectangleHeight;

    private double relY;

    public void start(Stage primaryStage) {

        Pane pane = new Pane();
        Scene s = new Scene(pane);

        rectangle = new Rectangle(50, 50, Color.BLACK);
        rectangle.setX(0);
        rectangle.setY(0);

        pane.getChildren().add(rectangle);

        pane.setOnMousePressed(this::onPanePressed);
        pane.setOnMouseDragged(this::onPaneDragged);


        rectangle.setOnMousePressed(this::saveRelPos);
        rectangle.setOnMouseDragged(this::onRectangleDragged);

        primaryStage.setScene(s);
        primaryStage.setWidth(800);
        primaryStage.setHeight(600);

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void saveRelPos(MouseEvent e) {
        relX = e.getX() - rectangle.getX() ;
        relY = e.getY() - rectangle.getY();
    }
    private void onRectangleDragged(MouseEvent evt) {

        double x = evt.getX() - relX;
        double y = evt.getY() - relY;

        // dont bubble up to pane
        // see https://docs.oracle.com/javase/8/javafx/api/javafx/event/Event.html#consume--
        evt.consume();

        rectangle.setX(x);
        rectangle.setY(y);
    }

    private void onPanePressed(MouseEvent e) {
        paneX = e.getX();
        paneY = e.getY();
        rectangleWidth = rectangle.getWidth();
        rectangleHeight = rectangle.getHeight();

    }

    private void onPaneDragged(MouseEvent e) {
        double width = rectangleWidth + e.getX() - paneX;
        double height = rectangleHeight + e.getY() - paneY;
        rectangle.setWidth(width);
        rectangle.setHeight(height);
    }
}

