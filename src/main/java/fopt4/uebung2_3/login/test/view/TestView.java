package fopt4.uebung2_3.login.test.view;

import fopt4.uebung2_3.login.View;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class TestView
{
    private VBox vbox;
    private Label output;

    public TestView(View view)
    {
        vbox = new VBox(10);
        HBox buttons = new HBox(10);
        Button b;
        b = new Button("resetInput()");
        b.setOnAction(e -> view.resetInput());
        buttons.getChildren().add(b);
        b = new Button("showInputError()");
        b.setOnAction(e -> view.showInputError());
        buttons.getChildren().add(b);
        b = new Button("showLoginError()");
        b.setOnAction(e -> view.showLoginError());
        buttons.getChildren().add(b);
        b = new Button("showOkayMessage()");
        b.setOnAction(e -> view.showOkayMessage());
        buttons.getChildren().add(b);
        vbox.getChildren().add(buttons);

        output = new Label();
        vbox.getChildren().add(output);
    }

    public Pane getPane()
    {
        return vbox;
    }

    public Label getOutput()
    {
        return output;
    }
}
