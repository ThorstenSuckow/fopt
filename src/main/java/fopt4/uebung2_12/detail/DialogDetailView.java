package fopt4.uebung2_12.detail;

import fopt4.uebung2_12.model.Contact;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class DialogDetailView extends GridPane
{
    private Long contactId;
    private Label firstNameLabel;
    private Label lastNameLabel;
    private TextField mailAddressField;

    public DialogDetailView()
    {
        initView();
    }

    private void initView()
    {
        setHgap(10);
        setVgap(10);

        add(new Label("Vorname:"), 0, 0);
        firstNameLabel = new Label();
        add(firstNameLabel, 1, 0);

        add(new Label("Nachname:"), 0, 1);
        lastNameLabel = new Label();
        add(lastNameLabel, 1, 1);

        add(new Label("Mail-Adresse:"), 0, 2);
        mailAddressField = new TextField();
        mailAddressField.setPrefColumnCount(30);
        add(mailAddressField, 1, 2);

        setPadding(new Insets(10));
    }

    public Contact getContact()
    {
        return new Contact
        (
            contactId,
            firstNameLabel.getText(),
            lastNameLabel.getText(),
            mailAddressField.getText().trim()
        );
    }

    public void showContact(Contact contact)
    {
        if(contact != null)
        {
            contactId = contact.getId();
            firstNameLabel.setText(contact.getFirstName());
            lastNameLabel.setText(contact.getLastName());
            mailAddressField.setText(contact.getMailAddress());
        }
        else
        {
            contactId = null;
            firstNameLabel.setText("");
            lastNameLabel.setText("");
            mailAddressField.setText("");
        }
    }
}
