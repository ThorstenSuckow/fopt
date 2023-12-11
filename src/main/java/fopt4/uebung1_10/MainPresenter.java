package fopt4.uebung1_10;



public class MainPresenter
{
    private MainView view;
    private OverviewPresenter overviewPresenter;
    private DetailPresenter detailPresenter;

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

    public void setDetailPresenter(DetailPresenter detailPresenter) {
        this.detailPresenter = detailPresenter;
    }

    public void showOverviewView() {
        overviewPresenter.search();
        view.setContent(overviewPresenter.getView());
    }

    public void showDetailView(Contact contact) {
        detailPresenter.setContact(contact);
        view.setContent(detailPresenter.getView());
    }
}

