package gui.country.combo;

import com.sun.javafx.collections.ObservableListWrapper;
import javafx.application.Application;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.When;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.converter.LongStringConverter;

import java.text.NumberFormat;
import java.util.regex.Pattern;


public class CountryInfo extends Application {

    private Label landLabel;

    private Label hauptstadtLabel;
    private Label einwohnerLabel;

    private Label flaecheLabel;

    private Label bevoelkerungsdichteLabel;

    private CheckBox exactValuesCheckBox;

    private GridPane labelGridPane;

    private TextField countryField;

    private TextField capitalField;

    private TextField populationField;

    private TextField areaField;


    private ComboBox<Country> countryComboBox;

    class CheckboxChangeListener implements ChangeListener<Boolean> {

        @Override
        public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
            updateLabels(
                countryComboBox.getSelectionModel().getSelectedItem(),
                newValue
            );
        }
    }

    class CountryComboBoxChangeListener implements ChangeListener<Country>  {
        @Override
        public void changed(ObservableValue<? extends Country> observableValue, Country oldValue, Country newValue) {

            if (newValue != null && !newValue.isValid()) {
                return;
            }

            updateLabels(newValue, exactValuesCheckBox.selectedProperty().get());
        }
    }

    private String format(long value, boolean exact) {

        boolean isMill = false;
        boolean isThous = false;

        if (!exact) {
            if (value >= 1_000_000) {
                isMill = true;
                value = Math.round(value / 1_000_000d);
            } else if (value >= 1_000) {
                isThous = true;
                value = (long) Math.round(value / 1_000d) * 1000;
            }
        }

        String v = String.valueOf(value);
        int i = v.length() - 1;
        int pos;
        StringBuffer p = new StringBuffer();
        do  {
            pos = Math.abs(i - (v.length() - 1));
            if (pos != 0 && pos % 3 == 0) {
                p.append(".");
            }
            p.append(v.charAt(i));
        } while (i-- != 0);


        String res = p.reverse().toString();

        if (isMill) {
            return res + " Mill.";
        }

        return res;
    }

    private void updateLabels(Country country, boolean exact) {
        landLabel.setText(country != null ? country.getName() : "");
        hauptstadtLabel.setText(country != null ? country.getCapital() : "");
        einwohnerLabel.setText(country != null ? format(country.getPeople(), exact) : "");
        flaecheLabel.setText(country != null ? format(country.getArea(), exact) : "");
        bevoelkerungsdichteLabel.setText(country != null ? format(country.getPopulationDensity(), exact) : "");
    }

    private GridPane getLabelGridPane () {

        if (labelGridPane != null) {
            return labelGridPane;
        }

        labelGridPane = new GridPane();
        labelGridPane.setVgap(10);
        labelGridPane.setHgap(10);


        // land
        Label land = new Label("Land:");
        landLabel = new Label();
        landLabel.setId("countryName");

        // hauptstadt
        Label hauptstadt = new Label("Hauptstadt:");
        hauptstadtLabel = new Label();
        hauptstadtLabel.setId("capital");

        // einwohner
        Label einwohner = new Label("Einwohner:");
        einwohnerLabel = new Label();
        einwohnerLabel.setId("population");

        // fläche
        Label flaeche = new Label("Fl\u00e4che (in qkm):");
        flaecheLabel = new Label();
        flaecheLabel.setId("area");

        // bevölkerungsdichte
        Label bevoelkerungsdichte = new Label("Bev\u00f6lkerungsdichte (in Personen pro qkm):");
        bevoelkerungsdichteLabel = new Label();
        bevoelkerungsdichteLabel.setId("density");

        labelGridPane.add(land, 1, 0);
        labelGridPane.add(landLabel, 2, 0);

        labelGridPane.add(hauptstadt, 1, 1);
        labelGridPane.add(hauptstadtLabel, 2, 1);

        labelGridPane.add(einwohner, 1, 2);
        labelGridPane.add(einwohnerLabel, 2, 2);

        labelGridPane.add(flaeche, 1, 3);
        labelGridPane.add(flaecheLabel, 2, 3);

        labelGridPane.add(bevoelkerungsdichte, 1, 4);
        labelGridPane.add(bevoelkerungsdichteLabel, 2, 4);

        return labelGridPane;
    }

    private ObservableList<Country> buildCountries() {

        ObservableList<Country> countries = FXCollections.observableArrayList(
                new Country("Kanada", "Ottawa", 18631, 242),
            new Country("Deutschland", "Berlin", 80_000_000, 357592),
            new Country("Belgien", "Brüssel", 40_000_000, 357592),
            new Country("Holland", "Amsterdam", 20_000_000, 357592),
                new Country ("China", "Peking", 1349585838 , 9571302)
        );

        return countries;
    }


    private ComboBox<Country> getCountryComboBox() {

        if (countryComboBox != null) {
            return countryComboBox;
        }

        countryComboBox = new ComboBox<>();
        countryComboBox.setPromptText("Keine L\u00e4nder vorhanden");

        countryComboBox.getSelectionModel().selectedItemProperty().addListener(new CountryComboBoxChangeListener());

        countryComboBox.setId("countrySelector");
        ObservableList<Country> countries = buildCountries();

        countryComboBox.getItems().addAll(countries);

        return countryComboBox;
    }


    private CheckBox getExactValuesCheckBox() {

        if (exactValuesCheckBox != null) {
            return exactValuesCheckBox;
        }

        exactValuesCheckBox = new CheckBox();
        exactValuesCheckBox.selectedProperty().addListener(new CheckboxChangeListener());
        exactValuesCheckBox.setId("exactValues");
        exactValuesCheckBox.setText("exakte Angaben");
        exactValuesCheckBox.setTextAlignment(TextAlignment.RIGHT);

        return exactValuesCheckBox;
    }


    @Override
    public void start(Stage primaryStage) throws Exception {

        VBox root = new VBox();
        root.setSpacing(10);
        root.setPadding(new Insets(10));

        // combobox
        countryComboBox = getCountryComboBox();
        root.getChildren().add(countryComboBox);

        // checkbox
        exactValuesCheckBox = getExactValuesCheckBox();
        root.getChildren().add(exactValuesCheckBox);

        // gridpane
        GridPane gridPane = getLabelGridPane();
        root.getChildren().add(gridPane);

        // form
        HBox formPane = getFormPane();
        root.getChildren().add(formPane);

        // delete button
        Button deleteButton = new Button("L\u00f6schen");
        deleteButton.setId("delete");
        root.getChildren().add(deleteButton);
        deleteButton.setOnAction(this::deleteCountry);

        Scene scene = new Scene(root, 400, 320);

        primaryStage.setScene(scene);

        primaryStage.setTitle("L\u00e4nder-Informationen");
        primaryStage.show();


        // select the first item in the combobox
        countryComboBox.getSelectionModel().select(0);;



    }

    private void deleteCountry(ActionEvent actionEvent) {

        Country c = countryComboBox.getValue();
        int pos = countryComboBox.getSelectionModel().getSelectedIndex();
        countryComboBox.getItems().remove(c);

        int next = Math.max(countryComboBox.getItems().size() - 1, pos);

        if (next >= 0) {
            countryComboBox.getSelectionModel().select(next);
        }

    }

    private HBox getFormPane() {
        HBox formPane = new HBox();
        formPane.setSpacing(10);

        countryField = new TextField();
        countryField.setPromptText("Land");
        countryField.setId("countryField");
        formPane.getChildren().add(countryField);

        capitalField = new TextField();
        capitalField.setPromptText("Hauptstadt");
        capitalField.setId("capitalField");
        formPane.getChildren().add(capitalField);

        populationField = new TextField();
        populationField.setPromptText("Einwohner");
        populationField.setId("populationField");
        formPane.getChildren().add(populationField);

        areaField = new TextField();
        areaField.setPromptText("Fl\u00e4che");
        areaField.setId("areaField");
        formPane.getChildren().add(areaField);

        Button addButton = new Button("Hinzufügen");
        addButton.setId("add");
        formPane.getChildren().add(addButton);


        BooleanBinding invalidFieldsBinding = Bindings.createBooleanBinding(
            () -> !populationField.textProperty().getValue().matches("[0-9]+")
                || !areaField.textProperty().getValue().matches("[0-9]+"),
            populationField.textProperty(), areaField.textProperty()
            ).or(countryField.textProperty().isEmpty())
            .or(capitalField.textProperty().isEmpty());

        addButton.disableProperty().bind(invalidFieldsBinding);

        addButton.setOnAction(this::onAddAction);

        return formPane;
    }

    private void onAddAction(ActionEvent actionEvent) {


        Country c = new Country(
                countryField.getText(),
                capitalField.getText(),
                Long.valueOf(populationField.getText()),
                Long.valueOf(areaField.getText())
        );

        countryComboBox.getItems().add(c);
        countryComboBox.getSelectionModel().select(c);

        countryField.clear();
        capitalField.clear();
        populationField.clear();
        areaField.clear();
    }


    public static void main(String[] args) {
        launch(args);
    }


}
