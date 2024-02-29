package gui.graphics.sinus;

import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polyline;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class SinusPresenter {


    private final SinusModel sinusModel;

    private SinusView sinusView;

    public SinusPresenter(SinusModel sinusModel) {
        this.sinusModel = sinusModel;
    }

    void setSinusView(SinusView sinusView) {
        this.sinusView = sinusView;
        this.sinusView.initView();

        sinusView.getSinusCanvas().heightProperty().addListener(this::drawSinusCurve);
        sinusView.getZoomSlider().valueProperty().addListener(this::drawSinusCurve);
        sinusView.getAmplitudeSlider().valueProperty().addListener(this::drawSinusCurve);
        sinusView.getFrequencySlider().valueProperty().addListener(this::drawSinusCurve);
        sinusView.getPhaseSlider().valueProperty().addListener(this::drawSinusCurve);
        drawSinusCurve();
    }

    private void drawSinusCurve(ObservableValue<? extends Number> observableValue, Number number, Number number1) {
        drawSinusCurve();
    }


    private void drawSinusCurve() {

        new Thread(()-> {
            Pane sinusCanvas = sinusView.getSinusCanvas();
            double width = sinusCanvas.widthProperty().get();
            double height = sinusCanvas.heightProperty().get();

            double zoom = sinusView.getZoomSlider().valueProperty().get();
            double phase = sinusView.getPhaseSlider().valueProperty().get();
            double frequency = sinusView.getFrequencySlider().valueProperty().get();
            double amplitude = sinusView.getAmplitudeSlider().valueProperty().get();

            double start = -(width / 2);

            List<Double> tmpPoints = new ArrayList<>();

            for (double i = 0; i < width; i += 1 / zoom) {

                double sinX = (start + i);
                double sinY = sinusModel.getY(sinX, amplitude, frequency, phase);

                double x = (sinX * zoom - start);
                double y = (sinY * zoom + (height / 2));

                tmpPoints.add(x);
                tmpPoints.add(y);
            }

            Platform.runLater(()->{
                ObservableList<Double> points = sinusView.getSinusLine().getPoints();
                points.clear();
                points.addAll(tmpPoints);
            });
        }).start();


    }

}
