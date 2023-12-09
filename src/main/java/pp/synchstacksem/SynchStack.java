package pp.synchstacksem;

import java.util.ArrayList;

public class SynchStack {

    private final ArrayList<Object> stack;
    private final Semaphore popSemaphore;
    private final Semaphore pushSemaphore;

    private int head;

    public SynchStack() {
        popSemaphore = new Semaphore(0);
        pushSemaphore = new Semaphore(1);
        stack = new ArrayList<>();
        head = 0;
    }

    public void push(Object o) {

        // lock writing
        pushSemaphore.p();

        stack.add(o);
        head++;

        // release write lock
        pushSemaphore.v();

        // release read lock
        popSemaphore.v();
    }


    public Object pop() {

        Object o;

        // lock read semaphore
        // using a condition isEmpty() and THEN locking it will not work,
        // because isEmpty() may return false, then another thread removes an element
        // we have to lock this method in any case.
        popSemaphore.p();

        // lock writing
        pushSemaphore.p();

        // read out last element
        o = stack.remove(head - 1);
        head--;

        // release write lock
        pushSemaphore.v();

        // return object
        return o;
    }

}
