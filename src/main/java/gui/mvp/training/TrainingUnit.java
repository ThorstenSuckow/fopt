package gui.mvp.training;

public class TrainingUnit {

    private String marker;

    private float distance; // in km

    private float time; // in minutes

    public TrainingUnit(String marker, float distance, float time)
    {
        this.marker = marker;
        this.distance = distance;
        this.time = time;
    }

    public String getMarker()
    {
        return marker;
    }

    public float getDistance()
    {
        return distance;
    }

    public float getTime()
    {
        return time;
    }

    public float getMeanSpeed() // km/h
    {
        return distance * 60 / time;
    }

    public String toString()
    {
        return marker;
    }
}
