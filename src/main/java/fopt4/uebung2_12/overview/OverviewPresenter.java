package fopt4.uebung2_12.overview;

import java.util.List;
import java.util.Optional;
import fopt4.uebung2_12.detail.*;
import fopt4.uebung2_12.model.Contact;
import fopt4.uebung2_12.model.ContactModel;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.stage.Modality;

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

        /**
         * @uebung2_13 set parameter useSingleDialog to false to make sure multiple
         * dialogs can be shown (will also set Dialog's modality to NONE).
         * Using multiple dialogs with showAndWait will also allow to edit one and the same
         * data at once. However, the ListView will not be refreshed until the last opened dialog was closed (
         * independent from the order in which closing occurs - if the last dialog gets closed first,
         * changes are immediately visible, if the first dialog is closed, and 2 remain open, those two have to be
         * closed first - then the changes from the first opened dialog are reflected):
         *
         *   (informal representation)
         *   open D1 -> event loop E1
         *   open D2 -> event loop E1 -> event loop E2
         *   open D3 -> event loop E1 -> event loop E2 -> event loopE3
         *
         *   close windows D3 D2 D1 -> changes reflect in the contact in this order - D1 changes will be used for contact
         *   close windows D2 D3 D1 -> changes reflect when D2 was closed (with changes in D2), however D1 changes will
         *   make it into the contact.
         *   Thus, event loops have to be resolved from inner to outer. An inner loop will resolve its outer loop if the
         *   inner loop does not contain any nested loops anymore.
         *
         *
         */
        boolean useSingleDialog = true;
        showDetailDialog(contact, useSingleDialog);
    }


    private void showDetailDialog(Contact contact, boolean useSingeDialog) {

        Dialog<Contact> detailDialog = null;

        if (useSingeDialog) {
            detailDialog = this.detailDialog;
        }

        if(detailDialog == null)
        {
            detailDialog = new Dialog<>();

            if (!useSingeDialog) {
                detailDialog.initModality(Modality.NONE);
            }

            DialogDetailView detailView = new DialogDetailView();
            detailDialog.getDialogPane().setContent(detailView);
            detailDialog.getDialogPane().getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);
            /**
             * @uebung2_12 commenting the following line will pass an object of type ButtonType to updateContact.
             * Since casting is not possible, a ClassCastException is thrown.
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
            Contact c = result.get();
            contactModel.updateContact(c);
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
