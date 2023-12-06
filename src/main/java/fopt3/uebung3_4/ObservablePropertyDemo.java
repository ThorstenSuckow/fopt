package fopt3.uebung3_4;



import java.util.ArrayList;
import java.util.List;

public class ObservablePropertyDemo<T> {


    interface PropertyChangeListener<T> {
        public void propertyChanged(Property<T> p, T oldValue, T newValue);
    }
    static class Property<T> {

        T value;

        List<PropertyChangeListener<T>> changeListener = new ArrayList<>();

        public void addListener(PropertyChangeListener<T> listener) {
            changeListener.add(listener);
        }

        public void removeListener(PropertyChangeListener<T> listener) {
            changeListener.remove(listener);
        }
        public void set(T v) {

            T oldValue = value;

            value = v;

            if (oldValue != value) {
                for (PropertyChangeListener listener: changeListener) {
                    listener.propertyChanged(this, oldValue, value);
                }
            }
        }

        public T get() {
            return value;
        }
    }



    public static void main(String[] args) {

        Property<String> p = new Property<>();

        p.addListener((property, oldValue, newValue)-> System.out.println("property changed: " + property + "; oldValue=" + oldValue + "; newValue=" + newValue));

        p.set("1");
        p.set("2");
        p.set("2");
        p.set("3");
    }


}
