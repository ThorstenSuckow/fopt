package fopt4.uebung1_2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Source code from [Oec22].
 *
 * This implementation advises the presenter to load the model once the
 * stage was shown. A message indicates whether the translation file
 * or the simple vocabulary is being used.
 */
public class Main extends Application  {

    public void start(Stage primaryStage) {

        Presenter p = new Presenter();
        View v = new View(p);
        Model m = new Model();
        p.setModelAndView(m, v);

        primaryStage.setOnShown((event) -> {
            p.loadModel();
        });

        Scene scene = new Scene(v.getUI());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Vokabel-Training");
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
