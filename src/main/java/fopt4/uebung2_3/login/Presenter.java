package fopt4.uebung2_3.login;

public class Presenter
{
    private View view;
    private Model model;

    public void setView(View view)
    {
        this.view = view;
    }

    public void setModel(Model model)
    {
        this.model = model;
    }

    public void login(String loginName, String password)
    {
        if(loginName.isEmpty())
        {
            view.showInputError();
        }
        else if(model.isOkay(loginName, password))
        {
            view.resetInput();
            view.showOkayMessage();
        }
        else
        {
            view.showLoginError();
        }
    }
}
