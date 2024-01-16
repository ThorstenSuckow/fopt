import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

import java.io.Serializable;

@Named
@ApplicationScoped
public class Counter {

    private int value;
    public Counter() {
        System.out.println("Booted Counter into @ApplicationScoped");
        value = 0;
    }

    public synchronized int getValue() {
        return value;
    }

    public synchronized void increment() {
        value++;
    }

    public synchronized void reset() {
        value = 0;
    }

    public String handleIncrement() {
        increment();
        return "counter";
    }

    public String handleReset() {
        reset();
        return "counter";
    }
}
