package fopt3.uebung3_6;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleIntegerProperty;

public class StaticBooleanBindingDemo {

    public static void main(String[] args) {

        SimpleIntegerProperty num1 = new SimpleIntegerProperty();
        SimpleIntegerProperty num2 = new SimpleIntegerProperty();
        SimpleIntegerProperty num3 = new SimpleIntegerProperty();

        // num1 > num2 * num3 && num2 > 0
        BooleanBinding bb1 = Bindings
            .greaterThan(num1, Bindings.multiply(num2, num3))
            .and(Bindings.greaterThan(num2, 0));

        assert(!bb1.getValue());

        num1.set(11);
        num2.set(2);
        num3.set(5);

        assert(bb1.getValue());
    }

}
