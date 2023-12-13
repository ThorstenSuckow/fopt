package fopt4.uebung3_5;

import fopt4.uebung3_5.model.Contact;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.List;

public class OverviewView extends BorderPane
{
    private OverviewPresenter presenter;
    private TextField searchField;
    private ListView<Contact> resultsList;

    public OverviewView()
    {
        initView();
    }

    MenuItem undoItem;

    MenuItem redoItem;

    private MenuBar createMenuBar() {

        undoItem = new MenuItem("Undo");
        undoItem.setAccelerator(KeyCombination.keyCombination("Ctrl+Z"));
        undoItem.setOnAction(e -> presenter.undo());

        redoItem = new MenuItem("Redo");
        redoItem.setOnAction(e -> presenter.redo());
        redoItem.setAccelerator(KeyCombination.keyCombination("Ctrl+Shift+Z"));

        Menu undoRedoMenu = new Menu("Edit");
        undoRedoMenu.getItems().addAll(undoItem, redoItem);
        return new MenuBar(undoRedoMenu);


    }

    private void initView()
    {
        VBox topArea = new VBox(5);
        topArea.setPadding(new Insets(10));


        topArea.getChildren().add(createMenuBar());


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

    public void setPresenter(OverviewPresenter presenter) {
        if (presenter.getUndoRedoManager() == null) {
            throw new RuntimeException("Please set UndoRedoManager for OverviewPresenter first.");
        }
        undoItem.disableProperty().bind(presenter.getUndoRedoManager().canUndoProperty().not());
        redoItem.disableProperty().bind(presenter.getUndoRedoManager().canRedoProperty().not());

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
