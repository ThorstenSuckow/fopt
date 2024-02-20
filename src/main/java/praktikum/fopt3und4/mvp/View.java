package praktikum.fopt3und4.mvp;

import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.event.ActionEvent;
import java.io.FilterOutputStream;
import java.util.List;

public class View {

    private Stage stage;

    private VBox checkboxContainer;

    private Label totalWeight;

    private Pane fxPane;
    private Rectangle fillometer;
    private Label maxWeight;
    private Presenter presenter;

    public View(Stage stage) {
        stage.setTitle("fill-O-meter©");
        this.stage = stage;
    }


    private void initView() {
        stage.setHeight(300);
        stage.setWidth(500);

        HBox root = new HBox();
        root.setPadding(new Insets(10));

        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.getChildren().add(getInfoText());
        vbox.getChildren().add(getCheckboxContainer());

        root.getChildren().add(vbox);
        root.getChildren().add(getFxPane());

        Scene scene = new Scene(root);
        stage.setScene(scene);

    }

    private double currentWeight;

    public void updateRelativeWeight(double weight) {
        updateRelativeWeight(weight, true);
    }

    protected void updateRelativeWeight(double weight, boolean animate) {

        Duration duration = Duration.millis(250);

        currentWeight = weight;
        double newHeight = fxPane.getHeight() * weight;

        if (fillometer.getHeight() == 0) {
            fillometer.setHeight(1);
        }

        fillometer.setY(fxPane.getHeight());

        double scaleY = newHeight / fillometer.getHeight();
        double translateY = -newHeight/2;

        if (animate) {
            ScaleTransition scaleTransition = new ScaleTransition(duration, fillometer);
            scaleTransition.setToY(scaleY);
            scaleTransition.setCycleCount(1);

            TranslateTransition translateTransition = new TranslateTransition(duration, fillometer);
            translateTransition.setToY(translateY);

            ParallelTransition st = new ParallelTransition();
            st.getChildren().addAll(scaleTransition, translateTransition);
            st.play();
        } else {
            fillometer.setScaleY(scaleY);
            fillometer.setTranslateY(translateY);
        }


    }

    public void updateTotalWeight(double value) {
        getTotalWeightLabel().setText(value+ " kg");
    }


    public void updateEnabledItems(List<String> enabledItems) {

        for (Node node : getCheckboxContainer().getChildren()) {
            CheckBox cb = (CheckBox) node;
            if (!cb.selectedProperty().get()) {
                cb.setDisable(true);
            }
        }

        for (String name : enabledItems) {
            CheckBox cb = findCheckBox(name);
            cb.setDisable(false);
        }
    }


    protected CheckBox findCheckBox(String name) {
        ObservableList<Node> bs = getCheckboxContainer().getChildren();

        for (Node node : bs) {
            CheckBox cb = (CheckBox) node;
            if (cb.getId().equals(name)) {
                return cb;
            }
        }

        return null;
    }

    private Pane getFxPane() {

        fxPane = new Pane();
        fxPane.setBorder(new Border(new BorderStroke(
            Color.BLUE,
            BorderStrokeStyle.SOLID,
            CornerRadii.EMPTY,
            BorderWidths.DEFAULT
        )));
        fillometer = new Rectangle();
        fillometer.setFill(Color.BLUE);
        fxPane.setPrefWidth(100);
        fillometer.widthProperty().bind(fxPane.widthProperty());
        fillometer.setX(0);

        fxPane.heightProperty().addListener((observableValue, oldValue, newValue) -> {
            if (fillometer.getHeight() == 0) {
                return;
            }
            updateRelativeWeight(currentWeight, false);
        });
        fxPane.getChildren().add(fillometer);

        return fxPane;

    }

    public Label getMaxWeightLabel() {
        return maxWeight;
    }

    public Label getTotalWeightLabel() {
        return totalWeight;
    }

    public void init(List<String> name, List<Double> weights, double maxWeight) {
        presenter.initItemList(name, weights, maxWeight);
    }

    protected VBox getCheckboxContainer() {
        if (checkboxContainer != null) {
            return checkboxContainer;
        }

        checkboxContainer = new VBox();


        return checkboxContainer;
    }

    protected VBox getInfoText() {

        VBox vbox = new VBox();

        vbox.setPadding(new Insets(10));

        HBox row1 = new HBox();
        row1.setPadding(new Insets(10));
        Label totalWeightLabel = new Label("Gesamtgewicht: ");
        totalWeight = new Label();
        row1.getChildren().addAll(totalWeightLabel, totalWeight);

        HBox row2 = new HBox();
        row1.setPadding(new Insets(10));
        Label maxWeightLabel = new Label("maximalesGewicht: ");
        maxWeight = new Label();
        row2.getChildren().addAll(maxWeightLabel, maxWeight);

        vbox.getChildren().addAll(row1, row2);

        return vbox;
    }


    protected void setPresenter(Presenter presenter) {
        this.presenter = presenter;
        initView();
    }


    public void show() {

        stage.show();
    }


}
