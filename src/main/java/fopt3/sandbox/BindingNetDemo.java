package fopt3.sandbox;

import javafx.beans.binding.NumberBinding;
import javafx.beans.property.SimpleIntegerProperty;

public class BindingNetDemo {


    public static void main(String[] args) {

        SimpleIntegerProperty p1 = new SimpleIntegerProperty(1);
        SimpleIntegerProperty p2 = new SimpleIntegerProperty(2);

        NumberBinding b = p1.add(p2).multiply(2).divide(2).add(1).subtract(1);

        NumberBinding b2Mid = p1.multiply(p2);
        NumberBinding b2 = p1.add(b2Mid);

        // bidirectional binding not allowed
        // p1.bindBidirectional(b2);

        // results in a StackOverflowError
        // p1.bind(b2); // pi binds to b2. b2 adds p1 with p1 multiplied with p2. b2 is updated,
                        // updates p1, p1 triggers NumberBindingUpdate,
                        // triggers p1 update, triggers NumberBinding-update...

        System.out.println("b: " + b.getValue());
        System.out.println("b2: " + b2.getValue());

        p2.set(4);

        System.out.println("b: " + b.getValue());
        System.out.println("b2: " + b2.getValue());

        p1.set(8);

        System.out.println("b: " + b.getValue());
        System.out.println("b2: " + b2.getValue());

    }

}
