package fopt4.uebung1_2;

import javafx.event.*;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.control.*;

public class View {
    private Presenter presenter;

    private GridPane pane;
    private Label question;
    private TextField solution;
    private Label status;

    public View(Presenter presenter) {
        this.presenter = presenter;
        initView();
    }

    private void initView() {
        pane = new GridPane();
        pane.setPadding(new Insets(6));
        pane.setHgap(10);
        pane.setVgap(10);
        pane.add(new Label("Bitte Übersetzen Sie:"), 0, 0);
        question = new Label();
        pane.add(question, 0, 1);
        solution = new TextField();
        pane.add(solution, 1, 1);
        HBox buttons = new HBox(8);
        Button okay = new Button("Überpüfen");
        buttons.getChildren().add(okay);
        Button next = new Button("Weiter");
        buttons.getChildren().add(next);
        pane.add(buttons, 0, 2, 2, 1);
        status = new Label();
        pane.add(status, 0, 3, 2, 1);

        EventHandler<ActionEvent> h = e ->
                    presenter.check(question.getText(),
                                    solution.getText());
        solution.setOnAction(h);
        okay.setOnAction(h);
        next.setOnAction(e -> presenter.choose());

    }

    public Pane getUI() {
        return pane;
    }

    public void showNewWord(String word) {
        question.setText(word);
        solution.setText("");
    }

    public void eraseMessage() {
        status.setText("");
    }

    public void showOkayMessage() {
        status.setText("Die Lösung war richtig.");
    }

    public void showContinuationErrorMessage(int tries) {
        if(tries <= 1) {
            status.setText("Die Lösung war falsch. Sie können es nochmals versuchen.");
        } else {
            status.setText("Die Lösung war zum " + tries + ". Mal falsch. Sie können es nochmals versuchen.");
        }
    }

    public void showFinalErrorMessage(int tries) {
        status.setText("Die Lösung war zum " + tries + ". Mal falsch. Es geht weiter mit dem nächsten Wort.");
    }

    public void showNoInputMessage() {
        status.setText("Es wurde keine Lösung angegeben.");
    }

    public void setStatus(String txt) {
        System.out.println(txt);
        status.setText(txt);
    }
}
