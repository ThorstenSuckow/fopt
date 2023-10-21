package oop.counter;

public class Decrementer extends AbstractCounter {

    public Decrementer(int value) {
        super(value);
    }


    public void decrement() {
        value--;
    }

}
