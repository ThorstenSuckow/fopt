package gui.calculator;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

/**
 * Code provided by olat-system / ASB-Zusatzaufgaben
 */
public class Computation {
    public static double evaluate(String expression) {
        try {
            final Expression expr = new ExpressionBuilder(expression).build();
            return expr.evaluate();
        } catch(Exception e) {
            throw new IllegalArgumentException();
        }
    }
}