package klausurvorbereitung.uebungen;

import java.util.function.Consumer;
/**
 * This code demonstrates how to pass a function call to a thread,
 * then use the executed lambda-method's return value as the argument
 * for the specvified Consumer.
 *
 * @see https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/function/Consumer.html
 *
 * see also [Oec22, 320 Listing 6.5]
 */
public class ConsumerDemo {
    interface Supplier<T> {
        T execute();
    }

    public <T> void asyncCall(Supplier<T> s, Consumer<T> c) {
        startThread(s, c);
    }

    public <T> void startThread(Supplier<T> s, Consumer<T> c) {
        Thread t = new Thread(() -> c.accept(s.execute()));
        t.start();
    }


    public static void main(String[] args) {
        ConsumerDemo c = new ConsumerDemo();
        c.asyncCall(
            () -> Math.random() * 100,
            (r) -> System.out.println("Done: " + r)
        );
    }

}
