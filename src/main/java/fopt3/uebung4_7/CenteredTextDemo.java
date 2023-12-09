package fopt3.uebung4_7;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.stage.Stage;

public class CenteredTextDemo extends Application {

    public void start(Stage stage) {

        VBox root = new VBox();
        root.setPadding(new Insets(10));

        Pane pane = new Pane();

        Rectangle rect = new Rectangle();
        rect.setStroke(Color.BLACK);
        rect.setFill(Color.TRANSPARENT);
        pane.getChildren().add(rect);
        rect.widthProperty().bind(pane.widthProperty());
        rect.heightProperty().bind(pane.heightProperty());

        Rectangle clipRect = new Rectangle();
        clipRect.widthProperty().bind(pane.widthProperty());
        clipRect.heightProperty().bind(pane.heightProperty());
        pane.setClip(clipRect);

        Text text = new Text("Hello World!");
        pane.getChildren().add(text);

        Rectangle textRectangle = new Rectangle();
        textRectangle.setStroke(Color.BLACK);
        textRectangle.setFill(Color.TRANSPARENT);
        textRectangle.setWidth(text.getLayoutBounds().getWidth());
        textRectangle.setHeight(text.getLayoutBounds().getHeight());

        text.layoutXProperty().bind(textRectangle.layoutXProperty());
        text.layoutYProperty().bind(textRectangle.layoutYProperty().add(text.layoutBoundsProperty().getValue().getHeight()));
        pane.getChildren().add(textRectangle);


        textRectangle.layoutXProperty().bind(
            Bindings.subtract(
                pane.widthProperty().divide(2),
                textRectangle.widthProperty().divide(2)
            )
        );

        textRectangle.layoutYProperty().bind(
            Bindings.subtract(
                pane.heightProperty().divide(2),
                textRectangle.heightProperty().divide(2)
            )
        );


        root.getChildren().add(pane);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {

        launch(args);

    }

}
