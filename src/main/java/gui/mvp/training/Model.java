package gui.mvp.training;

import java.util.ArrayList;
import java.util.HashMap;

public class Model {

    private HashMap<String, TrainingUnit> trainingUnits = new HashMap<>();


    public Model() {



    }

    public void addTrainingUnit(TrainingUnit unit) {

        if (trainingUnits.get(unit.getMarker()) != null) {
            throw new IllegalArgumentException();
        }

        trainingUnits.put(unit.getMarker(), unit);
    }

    public TrainingUnit getTrainingUnit(String unit) {
        return trainingUnits.get(unit);
    }
    public void removeTrainingUnit(String marker) {
        trainingUnits.remove(marker);
    }

    public String[] getAllMarkers() {
        return trainingUnits.keySet().toArray(new String[0]);
    }


}
