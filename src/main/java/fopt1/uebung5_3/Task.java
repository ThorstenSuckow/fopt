package fopt1.uebung5_3;

import java.util.List;

interface Task<T> {
    public boolean isDivisible();
    public List<Task<T>> split();
    public T execute();
    public T combine(List<T> results);
}