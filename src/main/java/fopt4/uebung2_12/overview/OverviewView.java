package fopt4.uebung2_12.overview;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.util.List;
import fopt4.uebung2_12.model.Contact;

public class OverviewView extends BorderPane
{
    private OverviewPresenter presenter;
    private TextField searchField;
    private ListView<Contact> resultsList;

    public OverviewView()
    {
        initView();
    }

    private void initView()
    {
        VBox topArea = new VBox(5);
        topArea.setPadding(new Insets(10));

        Label titleLabel = new Label("Kontaktsystem der HS Trier");
        topArea.getChildren().add(titleLabel);
        Label tagLine = new Label("Implementiert mit JavaFX");
        topArea.getChildren().add(tagLine);

        setTop(topArea);

        VBox centerArea = new VBox();
        centerArea.setSpacing(10);
        HBox searchBar = new HBox(10);

        searchBar.getChildren().add(new Label("Suchen:"));
        searchField = new TextField();
        searchField.setPrefColumnCount(20);
        searchField.setOnAction(e->presenter.search());
        searchBar.getChildren().add(searchField);

        Button searchButton = new Button("Suchen");
        searchButton.setOnAction(e->presenter.search());
        searchBar.getChildren().add(searchButton);

        centerArea.getChildren().add(searchBar);

        resultsList = new ListView<Contact>();
        resultsList.getSelectionModel().
                    selectedItemProperty().
                    addListener
        (
            (obs, oldValue, newValue) -> Platform.runLater(()->presenter.contactSelected(newValue))
        );
        BorderPane.setMargin(resultsList, new Insets(10, 10, 10, 10));
        VBox.setVgrow(resultsList, Priority.ALWAYS);
        centerArea.getChildren().add(resultsList);

        setCenter(centerArea);
        setMargin(centerArea, new Insets(20, 20, 20, 20));
    }

    public void setPresenter(OverviewPresenter presenter)
    {
        this.presenter = presenter;
    }

    public String getSearchPhrase()
    {
        return searchField.getText();
    }

    public void showSearchResults(List<Contact> searchResults)
    {
        resultsList.getItems().setAll(searchResults);
    }
}
