package gui.calculator;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

public class Calculator extends Application {


    private boolean clearOnInput = true;


    public void start(Stage primaryStage) throws IOException {

        Pane root;
        try { // Maven hack - expected location for maven is resources folder,
              // asb might differ in this regard
            root = FXMLLoader.load(getClass().getResource("mainui.fxml"));
        } catch (Exception e) {
            root = FXMLLoader.load(getClass().getResource("/calculator/mainui.fxml"));
        }

        installListeners(root);


        Scene scene = new Scene(root);
        primaryStage.setScene(scene);

        primaryStage.show();
    }


    private void installListeners(Parent parent) {
        Set<Node> buttons = parent.lookupAll(".button");

        for (Node button : buttons) {
            Button b = (Button) button; // casting is required since
                                        // Node does not provide a setOnAction-method
            b.setOnAction(this::onButtonAction);
        }
    }

    private void onButtonAction(ActionEvent evt) {
        Button b = (Button) evt.getSource();
        handleInput(b.getText(), (TextField) ((Button) evt.getSource()).getScene().getRoot().lookup("#display"));
    }

    private void handleInput(String btnText, TextField display) {

        if (clearOnInput) {
            display.setText("");
            clearOnInput = false;
        }

        String displayText = display.getText();

        switch (btnText) {
            case "CLEAR":
                display.setText("");
                return;
            case "DELETE":
                display.setText(displayText.length() > 0 ? displayText.substring(0, display.getText().length() - 1) : "");
                return;
            case "=":
                try {
                    display.setText(displayText + "=" + Computation.evaluate(displayText));
                } catch (IllegalArgumentException e) {
                    display.setText(displayText + "=>FEHLER");
                }
                clearOnInput = true;
                return;
            default:
                display.setText(display.getText() + btnText);
        }
    }


    public static void main(String[] args) {
        launch(args);
    }

}
