package gui.graphics.sinus;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.concurrent.Callable;

public class SinusView extends VBox {

    private Pane sinusCanvas;
    private SinusPresenter sinusPresenter;

    private Polyline sinusLine;

    private Slider frequencySlider;
    private Slider amplitudeSlider;
    private Slider phaseSlider;
    private Slider zoomSlider;

    public SinusView(SinusPresenter sinusPresenter) {
        this.sinusPresenter = sinusPresenter;
        sinusPresenter.setSinusView(this);
    }

    void initView() {

        this.setPadding(new Insets(10));

        this.initCanvasAndControls();
    }

    private void initCanvasAndControls() {

        VBox.setVgrow(getSinusCanvas(), Priority.ALWAYS);

        getChildren().add(new HBox(10, getInfoLabel()));
        getChildren().add(getSinusCanvas());

        for (Parent c: getControlRows()) {
            this.getChildren().add(c);
        }
    }


    public Pane getSinusCanvas() {
        if (sinusCanvas == null) {
            sinusCanvas = new Pane();

            Rectangle clip = new Rectangle();
            clip.widthProperty().bind(sinusCanvas.widthProperty());
            clip.heightProperty().bind(sinusCanvas.heightProperty());
            sinusCanvas.setClip(clip);

            sinusCanvas.getChildren().add(getSinusLine());

            drawLines();
        }

        return sinusCanvas;
    }

    private Label getInfoLabel() {

        Label info = new Label();

        info.textProperty().bind(Bindings.createStringBinding(
            ()-> getAmplitudeSlider().valueProperty().get() +
                    " * sin(" + getFrequencySlider().valueProperty().get() +
                    " * x + " + getPhaseSlider().valueProperty().get() + ")",
            getAmplitudeSlider().valueProperty(),
            getFrequencySlider().valueProperty(),
            getPhaseSlider().valueProperty()
        ));

        return info;
    }

    private Parent[] getControlRows() {


        HBox amplitudeRow = new HBox(10);
        HBox frequencyRow = new HBox(10);
        HBox phaseRowRow = new HBox(10);
        HBox zoomRow = new HBox(10);

        amplitudeRow.getChildren().add(createLabel("Amplitude"));
        amplitudeRow.getChildren().add(getAmplitudeSlider());

        frequencyRow.getChildren().add(createLabel("Frequenz"));
        frequencyRow.getChildren().add(getFrequencySlider());

        phaseRowRow.getChildren().add(createLabel("Phase"));
        phaseRowRow.getChildren().add(getPhaseSlider());

        zoomRow.getChildren().add(createLabel("Zoom"));
        zoomRow.getChildren().add(getZoomSlider());

        HBox.setHgrow(getAmplitudeSlider(), Priority.ALWAYS);
        HBox.setHgrow(getFrequencySlider(), Priority.ALWAYS);
        HBox.setHgrow(getPhaseSlider(), Priority.ALWAYS);
        HBox.setHgrow(getZoomSlider(), Priority.ALWAYS);

        return new Parent[]{
            amplitudeRow,
            frequencyRow,
            phaseRowRow,
            zoomRow
        };
    }

    public Slider getAmplitudeSlider() {
        if (amplitudeSlider == null) {
            amplitudeSlider = new Slider(-6, 6, 0);
            amplitudeSlider.setId("amplitude");
            amplitudeSlider.setShowTickMarks(true);
            amplitudeSlider.setShowTickLabels(true);
            amplitudeSlider.setMajorTickUnit(2f);
            amplitudeSlider.setMinorTickCount(1);
        }

        return amplitudeSlider;
    }


    public Slider getFrequencySlider() {
        if (frequencySlider == null) {
            frequencySlider = new Slider(0, 40, 20);
            frequencySlider.setId("frequency");
            frequencySlider.setShowTickMarks(true);
            frequencySlider.setShowTickLabels(true);
            frequencySlider.setMajorTickUnit(10f);
            frequencySlider.setMinorTickCount(5);
        }

        return frequencySlider;
    }

    public Slider getPhaseSlider() {
        if (phaseSlider == null) {
            phaseSlider = new Slider(-10, 10, 0);
            phaseSlider.setId("phase");
            phaseSlider.setShowTickMarks(true);
            phaseSlider.setShowTickLabels(true);
            phaseSlider.setMajorTickUnit(5f);
            phaseSlider.setMinorTickCount(1);
        }

        return phaseSlider;
    }

    public Slider getZoomSlider() {
        if (zoomSlider == null) {
            zoomSlider = new Slider(10, 210, 110);
            zoomSlider.setId("zoom");
            zoomSlider.setShowTickMarks(true);
            zoomSlider.setShowTickLabels(true);
            zoomSlider.setMajorTickUnit(100);
            zoomSlider.setMinorTickCount(100);
        }

        return zoomSlider;
    }


    private Label createLabel(String text) {
        Label l = new Label(text);
        l.setPrefWidth(120);
        return l;
    }


    Polyline getSinusLine() {
        if (sinusLine == null) {
            sinusLine = new Polyline(0);
        }
        return sinusLine;
    }

    void drawLines() {
        Line vert = new Line();
        DoubleBinding xBinding = Bindings.createDoubleBinding(
            () -> sinusCanvas.widthProperty().divide(2).get(),
            sinusCanvas.widthProperty()
        );
        DoubleBinding yBinding = Bindings.createDoubleBinding(
            () -> sinusCanvas.heightProperty().divide(2).get(),
            sinusCanvas.heightProperty()
        );
        vert.startYProperty().set(0);
        vert.endYProperty().bind(sinusCanvas.heightProperty());
        vert.startXProperty().bind(xBinding);
        vert.endXProperty().bind(xBinding);

        Line hor = new Line();
        hor.startXProperty().set(0);
        hor.endXProperty().bind(sinusCanvas.widthProperty());
        hor.startYProperty().bind(yBinding);
        hor.endYProperty().bind(yBinding);

        sinusCanvas.getChildren().add(vert);
        sinusCanvas.getChildren().add(hor);
    }

}
