package fopt4.uebung2_1;

import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;


public class OverviewPresenter
{
    private OverviewView view;

    private ContactModel contactModel;

    public OverviewPresenter() {
    }

    public void setView(OverviewView view) {
        this.view = view;
    }

    public OverviewView getView() {
        return view;
    }

    public void setContactModel(ContactModel contactModel) {
        this.contactModel = contactModel;
    }

    public void search() {
        String searchPhrase = view.getSearchPhrase();
        final String[] keywords = searchPhrase.split("\\s+");
        List<Contact> hits = contactModel.searchContacts(keywords);
        view.showSearchResults(hits);
    }

    public void contactSelected(Contact contact) {

        if(contact == null) {
            return;
        }

        DetailPresenter detailPresenter = new DetailPresenter();
        DetailView detailView = new DetailView();
        detailView.setPrefWidth(260);
        detailPresenter.setView(detailView);
        detailPresenter.setContactModel(contactModel);
        detailPresenter.setOverviewPresenter(this);
        detailView.setPresenter(detailPresenter);

        detailPresenter.setContact(contact);

        Stage stage = new Stage();
        Scene scene = new Scene(detailView);
        stage.setScene(scene);
        stage.setTitle("Kontakt: " + contact.getLastName());
        /**
         * @uebung2_8 adding this view's window as the owner will make sure that
         * subsequent DetailViews are closed if the owner is closed
         */
        // stage.initOwner(view.getScene().getWindow());

        stage.show();

    }
}
