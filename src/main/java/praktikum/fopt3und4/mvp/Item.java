package praktikum.fopt3und4.mvp;

import javafx.beans.property.SimpleBooleanProperty;

public class Item {

    private final String description;

    private final double weight;

    private boolean selected;

    public Item(String description, double weight) {
        this.description = description;
        this.weight = weight;
    }

    public void setSelected(boolean sel) {
        selected = sel;
    }
    public boolean isSelected() {
        return selected;
    }

    public String getDescription() {
        return description;
    }

    public double getWeight() {
        return weight;
    }



}
