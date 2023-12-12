package fopt4.uebung2_1;


import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class DetailView extends GridPane
{
    private DetailPresenter presenter;
    private Long contactId;
    private Label firstNameLabel;
    private Label lastNameLabel;
    private TextField mailAddressField;

    public DetailView() {
        initView();
    }

    private void initView() {

        firstNameLabel = new Label();
        lastNameLabel = new Label();
        mailAddressField = new TextField();

        setHgap(10);
        setVgap(10);

        add(firstNameLabel, 0, 0);
        add(lastNameLabel, 1, 0);
        add(mailAddressField, 0, 1, 2, 1);

        HBox bbar = new HBox();
        Button saveButton = new Button("Speichern");
        saveButton.setOnAction(e -> presenter.save());
        Button cancelButton = new Button("Abbrechen");
        cancelButton.setOnAction(e -> presenter.cancel());
        bbar.getChildren().add(saveButton);
        bbar.getChildren().add(cancelButton);
        bbar.setSpacing(10);
        bbar.setPadding(new Insets(10));
        add(bbar, 0, 2, 2, 1);
    }

    public void setPresenter(DetailPresenter presenter) {
        this.presenter = presenter;
    }

    public Contact getContact() {
        return new Contact
        (
            contactId,
            firstNameLabel.getText(),
            lastNameLabel.getText(),
            mailAddressField.getText().trim()
        );
    }

    public void showContact(Contact contact) {
        contactId = contact.getId();
        firstNameLabel.setText(contact.getFirstName());
        lastNameLabel.setText(contact.getLastName());
        mailAddressField.setText(contact.getMailAddress());
    }
}
