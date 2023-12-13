package fopt4.uebung3_5;

import fopt4.uebung3_5.model.Contact;
import fopt4.uebung3_5.model.ContactModel;

public class SaveCommand implements UndoRedoAction {

    Contact oldContact;
    Contact newContact;

    ContactModel contactModel;

    OverviewPresenter presenter;
    public SaveCommand(ContactModel contactModel, Contact oldContact, Contact newContact, OverviewPresenter presenter) {
        this.contactModel = contactModel;
        this.oldContact = oldContact;
        this.newContact = newContact;
        this.presenter = presenter;
    }


    public void undo() {
        contactModel.updateContact(oldContact);
        this.presenter.search();
    }

    public void redo() {
        contactModel.updateContact(newContact);
        this.presenter.search();
    }



}
