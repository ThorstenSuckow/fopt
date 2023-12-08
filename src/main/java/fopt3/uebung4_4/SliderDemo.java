package fopt3.uebung4_4;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Slider;

/**
 * rightUniSlider is bound to leftUniSlider.
 * Changing the leftUniSlider's value changes the rightUniSlider's value.
 * Trying to set the value of a right-hand-side's unidirectional binding throws an exception
 * (in this case: "A bound value cannot be set"). Thus, since bound values cannot be set,
 * the right-hand-slider cannot be updated.
 *
 * The bidirectional binding, however, works in two directions: Changing the left slider
 * updates the right slider and vice versa.
 *
 *
 */
public class SliderDemo extends Application {

    Slider leftUniSlider;

    Slider rightUniSlider;

    Slider leftBiSlider;

    Slider rightBiSlider;

    public void start(Stage stage) {

        VBox root = new VBox();

        root.setSpacing(10);
        root.setPadding(new Insets(10));

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        leftUniSlider = new Slider();

        rightUniSlider = new Slider();

        rightUniSlider.valueProperty().bind(leftUniSlider.valueProperty());

        leftBiSlider = new Slider();
        rightBiSlider = new Slider();

        leftBiSlider.valueProperty().bindBidirectional(rightBiSlider.valueProperty());

        gridPane.add(leftUniSlider, 0, 0);
        gridPane.add(rightUniSlider, 1, 0);

        gridPane.add(leftBiSlider, 0, 1);
        gridPane.add(rightBiSlider, 1, 1);

        root.getChildren().add(gridPane);

        Scene scene = new Scene(root);
        stage.setScene(scene);

        stage.show();


        // exception: "a bound value cannot be set"
        // rightUniSlider.valueProperty().set(10);

    }

    public static void main(String[] args) {
        launch(args);
    }

}
