package fopt4.uebung2_1;

import javafx.scene.Scene;
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
        /**
         * @uebung2_9 setting the DetailView's Modality to APPLICATION_MODAL will make
         * sure that no additional DetailView's can be opened - the user has to close
         * the DetailView (either through cancelling or saving any changes made to the
         * contact being edited) to proceed with the OverviewView.
         * Setting the Modality to WINDOW_MODAL makes the DetailView modal relative to its owning
         * window (in this case the primary stage).
         * Trying to focus the owning window then will create an effect on the focused window
         * indicating that the modal prevents the owning window from being focused.
         */
        //stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();

    }
}
