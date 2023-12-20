package gui.mvp.training;

import java.util.ArrayList;
import java.util.HashMap;

public class Model {

    private final ArrayList<TrainingUnit> trainingUnits = new ArrayList<>();


    public Model() {
    }

    public void addTrainingUnit(TrainingUnit unit) {
        for (TrainingUnit t: trainingUnits) {
            if (t.getMarker().equals(unit.getMarker())) {
                throw new IllegalArgumentException();
            }
        }

        trainingUnits.add(unit);
    }

    public TrainingUnit getTrainingUnit(String unit) {
        for (TrainingUnit t: trainingUnits) {
            if (t.getMarker().equals(unit)) {
                return t;
            }
        }
        return null;
    }
    public void removeTrainingUnit(String marker) {
        for (TrainingUnit t: trainingUnits) {
            if (t.getMarker().equals(marker)) {
                trainingUnits.remove(t);
                return;
            }
        }
    }

    public String[] getAllMarkers() {
        String[] markers = new String[trainingUnits.size()];
        int i = 0;
        for (TrainingUnit t: trainingUnits) {
            markers[i++] = t.getMarker();
        }
        return markers;
    }


}
