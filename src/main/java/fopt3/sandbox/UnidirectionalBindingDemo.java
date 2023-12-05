package fopt3.sandbox;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * To enable assertions, compile with VM options "-ea".
 */
public class UnidirectionalBindingDemo {

    public static void main(String[] args) {

        SimpleIntegerProperty left = new SimpleIntegerProperty();

        SimpleIntegerProperty right = new SimpleIntegerProperty();

        right.bind(left);

        left.set(1);
        assert(right.get() == 1);

        try {
            right.set(2);
        } catch (Exception e) {
            assert(e instanceof RuntimeException);
        }
    }

}
