package fopt3.uebung2_10;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
public class Controller {

    private int counter;

    @FXML
    private Label label;

    // can be set to private with @FXML annotation
    //@FXML
    public /*private*/ void increment(ActionEvent e) {
        counter++;
        label.setText("" + counter);
    }

    // can be set to private with @FXML annotation
    //@FXML
    public /*private*/ void reset() {
        counter = 0;
        label.setText("" + counter);
    }
}
