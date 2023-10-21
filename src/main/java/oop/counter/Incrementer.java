package oop.counter;

public class Incrementer extends AbstractCounter {

    public Incrementer(int value) {
        super(value);
    }


    public void increment() {
        value++;
    }

}
