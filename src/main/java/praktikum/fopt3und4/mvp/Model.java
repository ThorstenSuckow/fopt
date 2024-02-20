package praktikum.fopt3und4.mvp;

import java.util.ArrayList;
import java.util.List;

public class Model /*Backpack*/ {

    List<Item> itemList;

    private double maxWeight;

    public Model(String[] descriptions, double[] weights, double maxWeight) {

        if (descriptions.length != weights.length || maxWeight <= 0) {
            throw new IllegalArgumentException();
        }

        this.maxWeight = maxWeight;
        itemList = new ArrayList<>();

        for (int i = 0; i < descriptions.length; i++) {
            if (weights[i] > maxWeight) {
                throw new IllegalArgumentException();
            }
            if (contains(descriptions[i])) {
                throw new IllegalArgumentException();
            }

            itemList.add(new Item(descriptions[i], weights[i]));
        }
    }

    public boolean contains(String name) {
        for (Item item: itemList) {
            if (item.getDescription().equals(name)) {
                return true;
            }
        }

        return false;
    }

    public void setSelected(String name, boolean selected) {
        for (Item item: itemList) {
            if (item.getDescription().equals(name)) {
                item.setSelected(selected);
            }
        }
    }

    public double getMaxWeight() {
        return maxWeight;
    }

    public List<String> getEnabledItems() {
        List<String> list = new ArrayList<>();

        double total = getSumOfUsedWeights();

        for (Item item: itemList) {
            if (!item.isSelected() && item.getWeight() + total <= maxWeight) {
                list.add(item.getDescription());
            }
        }

        return list;
    }

    public double getSumOfUsedWeights() {
        double total = 0;
        for (Item item: itemList) {
            total += item.isSelected() ? item.getWeight() : 0;
        }

        return total;
    }

    public List<String> getAllItemNames() {
        List<String> list = new ArrayList<>();

        for (Item item: itemList) {
            list.add(item.getDescription());
        }
        return list;
    }


    public List<Double> getAllItemWeights() {
        List<Double> list = new ArrayList<>();

        for (Item item: itemList) {
            list.add(item.getWeight());
        }
        return list;
    }

}
