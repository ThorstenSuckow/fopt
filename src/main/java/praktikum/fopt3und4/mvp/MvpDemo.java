package praktikum.fopt3und4.mvp;

import javafx.application.Application;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class MvpDemo extends Application {


    @Override
    public void start(Stage stage) throws Exception {

        View view = new View(stage);
        new Presenter(view);

        List<String> descriptions = new ArrayList<>();
        List<Double> weights = new ArrayList<>();

        descriptions.add("Waschzeug");
        descriptions.add("Ersatzkleidung");
        descriptions.add("Bücher");
        descriptions.add("Essenspaket");
        descriptions.add("Schnorchel");
        descriptions.add("Flossen");
        descriptions.add("ne Taucherbrille");
        descriptions.add("Vitamine für Raucherpille");

        weights.add(1d);
        weights.add(2d);
        weights.add(8d);
        weights.add(7d);
        weights.add(5d);
        weights.add(6d);
        weights.add(7d);
        weights.add(0.1);

        view.init(
            descriptions,
            weights,
            30
        );

        view.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

}
