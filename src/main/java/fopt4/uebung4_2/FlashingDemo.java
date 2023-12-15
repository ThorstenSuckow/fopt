package fopt4.uebung4_2;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableBooleanValue;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;

class FlashingLabel extends Label {

    private boolean flash;

    private FlashingService flashingService;

    private double delay;
    public boolean getFlash() {
        return flash;
    }
    public void setFlash(boolean flash) {
        this.flash = flash;
    }

    class FlashingTask extends Task<Boolean> {

        FlashingLabel label;
        public FlashingTask(FlashingLabel label) {
            this.label = label;
        }

        @Override
        protected Boolean call() throws Exception {
            label.setFlash(!label.getFlash());
            return label.getFlash();
        }

    }

    class FlashingService extends ScheduledService<Boolean> {

        FlashingLabel label;
        public FlashingService(FlashingLabel label) {
            this.label = label;
        }
        @Override
        protected FlashingTask createTask() {
            return new FlashingTask(label);
        }
    }

    public void startFlashing() {
        if (flashingService == null) {
            flashingService = new FlashingService(this);
            flashingService.valueProperty().addListener((e, o, n) -> {
                if (n != null) {
                    this.visibleProperty().set(n);
                }
            });
        }

        if (!flashingService.isRunning()) {
            flashingService.setPeriod(new Duration(getDelay()));
            flashingService.reset();
            flashingService.start();
        }

    }

    public double getDelay() {
        return delay;
    }

    public void setDelay(double d) {
        if (flashingService != null) {
            flashingService.setPeriod(new Duration(d));
        }
    }

}


public class FlashingDemo extends Application {


    public void start(Stage stage) {


        HBox root = new HBox();
        root.setSpacing(10);
        root.setPadding(new Insets(10));
        root.setFillHeight(true);

        TextField textField = new TextField();
        textField.setText("Hello world");

        VBox flashingCont = new VBox();
        flashingCont.setFillWidth(true);
        flashingCont.setAlignment(Pos.CENTER);
        FlashingLabel flashingLabel = new FlashingLabel();
        flashingLabel.textProperty().bind(textField.textProperty());
        flashingCont.getChildren().add(flashingLabel);

        Slider fontSizeSlider = new Slider();
        fontSizeSlider.setMin(12);
        fontSizeSlider.setMax(100);

        fontSizeSlider.valueProperty().addListener((v, oldValue, newValue) -> {
            flashingLabel.setFont(Font.font((double)newValue));
        });


        Slider delaySlider = new Slider();
        delaySlider.setMin(100);
        delaySlider.setMax(1000);
        delaySlider.setShowTickLabels(true);


        root.getChildren().add(flashingCont);

        VBox controlCont = new VBox();
        controlCont.setPrefWidth(250);
        controlCont.setSpacing(10);
        controlCont.setPadding(new Insets(10));

        controlCont.getChildren().add(textField);
        controlCont.getChildren().add(fontSizeSlider);
        controlCont.getChildren().add(delaySlider);


        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setWidth(400);
        stage.setHeight(300);


        root.getChildren().add(controlCont);
        flashingCont.prefWidthProperty().bind(scene.widthProperty().subtract(controlCont.widthProperty()));

        delaySlider.valueProperty().addListener((e, o , n) -> {
            flashingLabel.setDelay((double) n);
        });

        stage.show();
        stage.setTitle("FlashingDemo");

        flashingLabel.startFlashing();

    }

    public static void main(String[] args) {
        launch(args);
    }


}
