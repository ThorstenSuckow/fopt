package fopt4.uebung1_10;


import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import javax.swing.Action;
import javax.swing.event.ChangeEvent;
import java.awt.event.ActionEvent;
import java.util.List;

public class OverviewView extends VBox
{
    private OverviewPresenter presenter;
    private TextField searchField;
    private ListView<Contact> resultsList;

    public OverviewView() {
        initView();
    }

    private void initView() {
        /**
         * uebung1_11
         */
        setPadding(new Insets(10));
        searchField = new TextField();
        searchField.setPrefWidth(200);

        searchField.setOnAction( e ->presenter.search());

        Button searchButton = new Button("Suchen");
        searchButton.setOnAction( e ->presenter.search());

        resultsList = new ListView<>();
        resultsList.getSelectionModel().selectedItemProperty().addListener(
            (ObservableValue<? extends Contact> observable, Contact oldValue, Contact newValue) -> {
                presenter.contactSelected(newValue);
        });


        HBox searchBox = new HBox();
        searchBox.setSpacing(10);
        searchBox.setPadding(new Insets(0 , 0 , 10 ,0));

        searchBox.getChildren().add(new Label("Suchen:"));
        searchBox.getChildren().add(searchField);
        searchBox.getChildren().add(searchButton);

        getChildren().add(searchBox);
        getChildren().add(resultsList);
    }


    public void setPresenter(OverviewPresenter presenter) {
        this.presenter = presenter;
    }

    public String getSearchPhrase() {
        return searchField.getText();
    }

    public void showSearchResults(List<Contact> searchResults) {
        resultsList.getItems().setAll(searchResults);
    }
}
