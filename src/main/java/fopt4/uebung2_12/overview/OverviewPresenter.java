package fopt4.uebung2_12.overview;

import java.util.List;
import java.util.Optional;
import fopt4.uebung2_12.detail.*;
import fopt4.uebung2_12.model.Contact;
import fopt4.uebung2_12.model.ContactModel;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

public class OverviewPresenter
{
    private OverviewView view;
    private Dialog<Contact> detailDialog;
    private ContactModel contactModel;

    public OverviewPresenter()
    {
    }

    public void setView(OverviewView view)
    {
        this.view = view;
    }

    public OverviewView getView()
    {
        return view;
    }

    public void setContactModel(ContactModel contactModel)
    {
        this.contactModel = contactModel;
    }

    public void search()
    {
        String searchPhrase = view.getSearchPhrase();
        final String[] keywords = searchPhrase.split("\\s+");
        List<Contact> hits = contactModel.searchContacts(keywords);
        view.showSearchResults(hits);
    }

    public void contactSelected(Contact contact)
    {
        if(contact == null)
        {
            return;
        }
        if(detailDialog == null)
        {
            detailDialog = new Dialog<>();
            DialogDetailView detailView = new DialogDetailView();
            detailDialog.getDialogPane().setContent(detailView);
            detailDialog.getDialogPane().getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);
            /**
             * @uebung2_12 commenting the following line will pass an object of type ButtonType to updateContact.
             * Since casting is not possible, an exception is thrown.
             * Note: The return type must have the same type as the type parameter of the Dialog-class.
             */
            detailDialog.setResultConverter((ButtonType bt) -> makeContact(bt, detailView));
        }

        DialogDetailView detailView = (DialogDetailView)detailDialog.getDialogPane().getContent();
        detailView.showContact(contact);
        detailDialog.setTitle("Kontaktsystem - Hochschule Trier (" + contact.getLastName() + ")");
        Optional<Contact> result = detailDialog.showAndWait();
        if(result.isPresent())
        {
            contactModel.updateContact(result.get());
            search();
        }
    }

    private Contact makeContact(ButtonType b, DialogDetailView detailView)
    {
        if(b == ButtonType.OK)
        {
            return detailView.getContact();
        }
        return null;
    }
}
