package fopt3.uebung2_8;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;
import java.util.Map;

/**
 * Includes Übung 2.9.
 *
 * run with the following cli args:
 *
 * --hallo=welt "das ist ein test" -ea --hallo=welt -ea --test=1 --test=2
 *
 * getNamed(): multiple equal values ("name" key!) will be reduced to the last value that
 * was added to the HashMap (unique keys in hasmap!)
 * getUnnamed(): all arguments not in the form of "--name=value", multiple equal entries possible
 * getRaw(): list of all arguments, multiple entries possible
 */
public class LabeledCommandLineArgumentsDemo extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        VBox root = new VBox();

        Parameters params = getParameters();

        Map<String, String> namedParams = params.getNamed();
        List<String> unnamedParams = params.getUnnamed();

        List<String> rawParams = params.getRaw();

        root.getChildren().add(new Label("Raw:"));
        for(String param: rawParams) {
            root.getChildren().add(new Label(param));
        }

        root.getChildren().add(new Label("Named:"));
        for(Map.Entry<String, String> param: namedParams.entrySet()) {
            root.getChildren().add(new Label(param.getKey() + " " + param.getValue()));
        }

        root.getChildren().add(new Label("Unnamed:"));
        for(String param: unnamedParams) {
            root.getChildren().add(new Label(param));
        }

        Scene scene = new Scene(root);

        stage.setScene(scene);

        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
