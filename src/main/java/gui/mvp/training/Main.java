package gui.mvp.training;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {


    @Override
    public void start(Stage stage) throws Exception {


        View v = new View();
        Model m = new Model();
        addSampleData(m);
        Presenter p = new Presenter();
        p.setView(v);
        p.setModel(m);

        Scene scene = new Scene(v);
        stage.setScene(scene);

        stage.setTitle("Trainingseinheiten");
        stage.show();

    }


    private void addSampleData(Model m) {

        for (int i = 0; i < 10; i++) {

            m.addTrainingUnit(new TrainingUnit(
                "Einheit " + (i + 1),
                    (float) (Math.random() * 10),
                    (float) (Math.random() * 10)
            ));
        }


    }


    public static void main (String[] args) {
        launch(args);
    }
}
