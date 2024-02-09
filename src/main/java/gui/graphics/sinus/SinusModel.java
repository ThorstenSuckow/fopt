package gui.graphics.sinus;

public class SinusModel {


    public double getY(double x, double amplitude, double frequency, double phase) {

        return amplitude * Math.sin(frequency * x + phase) ;


    }


}
