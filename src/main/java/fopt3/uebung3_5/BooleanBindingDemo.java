package fopt3.uebung3_5;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleIntegerProperty;

public class BooleanBindingDemo {


    public static void main(String[] args) {


        SimpleIntegerProperty num1 = new SimpleIntegerProperty();
        SimpleIntegerProperty num2 = new SimpleIntegerProperty();
        SimpleIntegerProperty num3 = new SimpleIntegerProperty();

        BooleanBinding bb1 = num1.greaterThan(num2.multiply(num3)).and(num2.greaterThan(0));

        try {
            assert bb1.getValue();
        } catch (AssertionError e) {
            System.out.println("boolean binding is false");
        }

        num1.set(5);
        num2.set(2);
        num3.set(2);

        assert bb1.getValue();
        System.out.println("boolean binding is true");




    }

}
