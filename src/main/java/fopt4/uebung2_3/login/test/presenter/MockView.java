package fopt4.uebung2_3.login.test.presenter;

import fopt4.uebung2_3.login.Presenter;
import fopt4.uebung2_3.login.View;

public class MockView extends View
{
    private int resetInputCalls, showOkayMessageCalls,
                showInputErrorCalls, showLoginErrorCalls;

    public MockView(Presenter p)
    {
        super(p);
    }

    public void resetInput()
    {
        resetInputCalls++;
    }

    public void showOkayMessage()
    {
        showOkayMessageCalls++;
    }

    public void showInputError()
    {
        showInputErrorCalls++;
    }

    public void showLoginError()
    {
        showLoginErrorCalls++;
    }

    public int getResetInputCalls()
    {
        return resetInputCalls;
    }

    public int getShowOkayMessageCalls()
    {
        return showOkayMessageCalls;
    }

    public int getShowInputErrorCalls()
    {
        return showInputErrorCalls;
    }

    public int getShowLoginErrorCalls()
    {
        return showLoginErrorCalls;
    }
}
