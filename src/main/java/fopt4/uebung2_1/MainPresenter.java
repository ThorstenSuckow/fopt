package fopt4.uebung2_1;


public class MainPresenter
{
    private MainView view;
    private OverviewPresenter overviewPresenter;

    public MainPresenter() {
    }

    public void setView(MainView view) {
        this.view = view;
    }

    public MainView getView() {
        return view;
    }

    public void setOverviewPresenter(OverviewPresenter overviewPresenter) {
        this.overviewPresenter = overviewPresenter;
    }

    public void showOverviewView() {
        overviewPresenter.search();
        view.setContent(overviewPresenter.getView());
    }

}

