package praktikum.fopt3und4.mvp;

import javafx.event.ActionEvent;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;


import java.util.List;

public class Presenter {

    private View view;

    private Model model;

    public Presenter(View view) {
        this.view = view;
        view.setPresenter(this);
    }



    protected void initItemList(List<String> names, List<Double> weights, double maxWeight) {

        model = new Model(
            names.toArray(String[]::new),
            weights.stream().mapToDouble(Double::doubleValue).toArray(),
            maxWeight
        );

        VBox checkboxContainer = view.getCheckboxContainer();
        checkboxContainer.getChildren().removeAll();

        for (Item item : model.itemList) {
            CheckBox checkBox = new CheckBox(item.getDescription() + "( "+ item.getWeight() + " kg)");
            checkBox.setId(item.getDescription());
            checkBox.setOnAction(this::onCheckBoxSelected);
            checkboxContainer.getChildren().add(checkBox);
        }

        view.getMaxWeightLabel().setText(maxWeight+" kg");
        view.getTotalWeightLabel().setText("0 kg");
    }

    private void onCheckBoxSelected(ActionEvent e) {

        CheckBox cb = (CheckBox) e.getSource();
        model.setSelected(cb.getId(), cb.isSelected());

        view.updateRelativeWeight(model.getSumOfUsedWeights() / model.getMaxWeight() );
        view.updateTotalWeight(model.getSumOfUsedWeights());
        view.updateEnabledItems(model.getEnabledItems());

    }

    private Item findItem(String name) {
        for (Item item : model.itemList) {
            if (item.getDescription().equals(name)) {
                return item;
            }
        }
        return null;
    }

}
