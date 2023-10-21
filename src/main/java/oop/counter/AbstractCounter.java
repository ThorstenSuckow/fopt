package oop.counter;

public abstract class AbstractCounter {

    protected int value;

    public AbstractCounter(int value) {
        this.value = value;
    }


    public int getValue() {
        return value;
    }

}
