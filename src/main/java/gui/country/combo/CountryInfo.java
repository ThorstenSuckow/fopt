package gui.country.combo;

import javafx.application.Application;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.LongStringConverter;

import java.util.regex.Pattern;


public class CountryInfo extends Application {

    Label landLabel;

    Label hauptstadtLabel;
    Label einwohnerLabel;

    Label flaecheLabel;

    Label bevoelkerungsdichteLabel;

    CheckBox exactValuesCheckBox;

    GridPane labelGridPane;

    TextField countryField;

    TextField capitalField;

    TextField populationField;

    TextField areaField;

    Button addButton;

    Button deleteButton;


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
            updateLabels(newValue, exactValuesCheckBox.selectedProperty().get());
        }
    }

    private String format(long value, boolean exact) {

        if (!exact) {
            if (value >= 1_000_000) {
                return (value / 1_000_000) + " Mill.";
            }

            if (value >= 1_000) {
                return (value / 1_000) + ".000";
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


        return p.reverse().toString();
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
        einwohnerLabel.setId("populationField");

        // fläche
        Label flaeche = new Label("Fl\u00e4che (in qkm):");
        flaecheLabel = new Label();
        einwohnerLabel.setId("areaField");

        // bevölkerungsdichte
        Label bevoelkerungsdichte = new Label("Bev\u00f6lkerungsdichte (in Personen pro qkm):");
        bevoelkerungsdichteLabel = new Label();

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
                new Country("Kanada", "Ottawa", 34_278_406, 9984670),
            new Country("Deutschland", "Berlin", 80_000_000, 357592),
            new Country("Belgien", "Brüssel", 40_000_000, 357592),
            new Country("Holland", "Amsterdam", 20_000_000, 357592)
        );

        return countries;
    }


    private ComboBox<Country> getCountryComboBox() {

        if (countryComboBox != null) {
            return countryComboBox;
        }

        countryComboBox = new ComboBox<>();

        countryComboBox.getSelectionModel().selectedItemProperty().addListener(new CountryComboBoxChangeListener());

        System.out.println(countryComboBox.getConverter());
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
        ComboBox<Country> countryComboBox = getCountryComboBox();
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
        deleteButton.setId("del");
        root.getChildren().add(deleteButton);
        deleteButton.setOnAction(this::deleteCountry);

        Scene scene = new Scene(root, 400, 320);

        primaryStage.setScene(scene);

        primaryStage.setTitle("Country");
        primaryStage.show();


        // select the first item in the combobox
        countryComboBox.getSelectionModel().select(0);;

    }

    private void deleteCountry(ActionEvent actionEvent) {

        Country c = countryComboBox.getValue();

        countryComboBox.getItems().remove(c);

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
        populationField.setPromptText("Bev\u00f6lkerung");
        populationField.setId("populationField");
        formPane.getChildren().add(populationField);

        areaField = new TextField();
        areaField.setPromptText("Fl\u00e4nche");
        areaField.setId("populationField");
        formPane.getChildren().add(areaField);

        addButton = new Button("Hinzufügen");
        addButton.setId("add");
        formPane.getChildren().add(addButton);

        areaField.setTextFormatter(getNumberFormatter());
        populationField.setTextFormatter(getNumberFormatter());

        addButton.disableProperty().bind(
            countryField.textProperty().isEmpty()
            .or(capitalField.textProperty().isEmpty())
            .or(populationField.textProperty().isEmpty())
            .or(areaField.textProperty().lessThanOrEqualTo(String.valueOf(0)))
            .or(populationField.textProperty().lessThanOrEqualTo(String.valueOf(0)))
        );


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


    public TextFormatter<Long> getNumberFormatter() {

        Pattern validNumberText = Pattern.compile("[0-9]*");
        return new TextFormatter<Long>(new LongStringConverter(), null,
                change -> {
                    String newText = change.getControlNewText();
                    if (validNumberText.matcher(newText).matches()
                        //&& !newText.equals("")
                        // && Long.valueOf(newText) > 0
                    ) {
                        return change ;
                    } else return null ;
                });

    }

    public static void main(String[] args) {
        launch(args);
    }


}
