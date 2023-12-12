package fopt4.uebung2_3.login.test.view;

import fopt4.uebung2_3.login.Presenter;
import javafx.scene.control.Label;

public class MockPresenter extends Presenter
{
    private Label output;

    public void setOutput(Label output)
    {
        this.output = output;
    }

    @Override
    public void login(String name, String password)
    {
        output.setText("login(\"" + name + "\", \"" +
                       password + "\")");
    }
}
