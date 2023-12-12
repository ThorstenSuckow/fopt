package fopt4.uebung2_3.login.test.view;

import fopt4.uebung2_3.login.View;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ManualViewTest extends Application
{
    public void start(Stage primaryStage)
    {
        MockPresenter p = new MockPresenter();
        View v = new View(p);
        v.initView();

        // test stage
        TestView testView = new TestView(v);
        p.setOutput(testView.getOutput());
        VBox testRoot = new VBox();
        testRoot.getChildren().add(testView.getPane());

        Scene testScene = new Scene(testRoot);
        Stage testStage = new Stage();
        testStage.setScene(testScene);

        HBox box = new HBox(20);
        box.getChildren().addAll(v.getUI());

        Scene scene = new Scene(box);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Login Test");
        primaryStage.show();

        testStage.initOwner(primaryStage);
        testStage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
