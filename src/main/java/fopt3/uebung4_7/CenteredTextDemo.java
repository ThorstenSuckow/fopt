package fopt3.uebung4_7;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
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

        // x-y coordinates computed from left top instead bottom-left (default)
        text.setTextOrigin(VPos.TOP);
        text.xProperty().bind(textRectangle.xProperty());
        text.yProperty().bind(textRectangle.yProperty());
        pane.getChildren().add(textRectangle);


        textRectangle.xProperty().bind(
            Bindings.subtract(
                pane.widthProperty(),
                textRectangle.widthProperty()
            ).divide(2)
        );

        textRectangle.yProperty().bind(
            Bindings.subtract(
                pane.heightProperty(),
                textRectangle.heightProperty()
            ).divide(2)
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
