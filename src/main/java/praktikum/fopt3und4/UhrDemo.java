package praktikum.fopt3und4;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class UhrDemo extends Application {

    private Label clockLabel;

    public void start(Stage primaryStage){


        VBox root = new VBox();

        clockLabel = new Label(getFormattedTime());
        clockLabel.setFont(Font.font(120));

        root.getChildren().add(clockLabel);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setWidth(600);
        primaryStage.setHeight(200);
        primaryStage.setTitle("Uhr");
        primaryStage.show();
        Thread t = new Thread(this::updateTime);
        t.setDaemon(true);
        t.start();

    }


    public static void main(String[] args) {
        launch(args);
    }

    private String getFormattedTime(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime now = LocalTime.now();
        return now.format(formatter);
    }
    private void updateTime() {

        while(true) {
            Platform.runLater(()-> {
                clockLabel.setText(getFormattedTime());
            });
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {

            }
        }

    }


}
