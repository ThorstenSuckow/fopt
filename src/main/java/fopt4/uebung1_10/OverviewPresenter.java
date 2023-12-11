package fopt4.uebung1_10;

import java.util.List;


public class OverviewPresenter
{
    private OverviewView view;
    private MainPresenter mainPresenter;
    private ContactModel contactModel;

    public OverviewPresenter() {
    }

    public void setView(OverviewView view) {
        this.view = view;
    }

    public OverviewView getView() {
        return view;
    }

    public void setMainPresenter(MainPresenter mainPresenter) {
        this.mainPresenter = mainPresenter;
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
        /**
         * Depending on the selection model and the listener implementation,
         * contact could be null upon deselecting items in the list
         */
        if(contact != null) {
            mainPresenter.showDetailView(contact);
        }
    }
}
