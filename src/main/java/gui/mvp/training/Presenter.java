package gui.mvp.training;

import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.scene.control.ListView;

import java.util.concurrent.atomic.AtomicReference;

public class Presenter {

    private Model model;

    private EditorDialog editorDialog;
    private View view;

    public Presenter() {

    }

    public void setModel(Model m) {
        this.model = m;

        for (String marker: m.getAllMarkers()) {
            this.view.getListView().getItems().add(model.getTrainingUnit(marker));
        }
    }

    public void setView(View v) {
        view = v;

        view.getListView().getSelectionModel().selectedItemProperty().addListener(this::onListViewItemChange);
        view.getDeleteButton().setOnAction(this::onDeleteAction);
        view.getAddButton().setOnAction(this::onAddAction);
    }

    private void onDeleteAction(ActionEvent e) {

        TrainingUnit t = (TrainingUnit) view.getListView().getSelectionModel().getSelectedItem();

        if (t == null) {
            return;
        }

        model.removeTrainingUnit(t.getMarker());
        view.getListView().getItems().remove(t);

    }

    private void onAddAction(ActionEvent e) {

        TrainingUnit t = showDialog();

        System.out.println(t);


    }

    private TrainingUnit showDialog() {


        if (editorDialog == null) {
            editorDialog = new EditorDialog();
            editorDialog.setModel(model);
            editorDialog.initOwner(view.getScene().getWindow());
        }

        editorDialog.showAndWait();

        return editorDialog.getTrainingUnit();
    }


    private void onListViewItemChange(Observable observable) {

        TrainingUnit t = null;

        ListView<TrainingUnit> listView = view.getListView();

        if (listView.getSelectionModel().getSelectedItem() != null) {
            t = listView.getSelectionModel().getSelectedItem();
        }

        view.updateTrainingItemInfo(t);

    }


}
