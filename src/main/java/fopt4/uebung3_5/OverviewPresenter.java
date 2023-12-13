package fopt4.uebung3_5;

import fopt4.uebung3_5.detail.DialogDetailView;
import fopt4.uebung3_5.model.Contact;
import fopt4.uebung3_5.model.ContactModel;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.stage.Modality;

import java.util.List;
import java.util.Optional;

public class OverviewPresenter
{
    private OverviewView view;
    private Dialog<Contact> detailDialog;
    private ContactModel contactModel;

    private UndoRedoManager undoRedoManager;

    public OverviewPresenter()
    {
    }

    public UndoRedoManager getUndoRedoManager() {
        return undoRedoManager;
    }
    public void setUndoRedoManager(UndoRedoManager manager) {
        undoRedoManager = manager;
    }

    public void undo() {
        undoRedoManager.undo();
    }

    public void redo() {
        undoRedoManager.redo();
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

        showDetailDialog(contact, true);
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
            undoRedoManager.addAction(new SaveCommand(contactModel, contact, c, this));
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
