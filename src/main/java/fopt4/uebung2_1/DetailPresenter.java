package fopt4.uebung2_1;


import javafx.stage.Stage;

public class DetailPresenter
{
    private DetailView view;
    private OverviewPresenter overviewPresenter;

    private ContactModel contactModel;

    public DetailPresenter() {
    }

    public void setView(DetailView view) {
        this.view = view;
    }

    public DetailView getView() {
        return view;
    }

    public void setOverviewPresenter(OverviewPresenter overviewPresenter) {
        this.overviewPresenter = overviewPresenter;
    }

    public void setContactModel(ContactModel contactModel) {
        this.contactModel = contactModel;
    }

    public void setContact(Contact contact) {
        view.showContact(contact);
    }

    public void save() {
        Contact updatedContact = view.getContact();
        contactModel.updateContact(updatedContact);
        overviewPresenter.search();
        close();
    }

    public void cancel() {
        overviewPresenter.search();
        close();
    }

    private void close() {
        ((Stage) view.getScene().getWindow()).close();
    }
}
