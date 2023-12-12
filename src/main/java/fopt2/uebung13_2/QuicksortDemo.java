package fopt2.uebung13_2;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

class Quicksort<E extends Comparable<E>> extends RecursiveAction {

    List<E> list;

    int start;

    int end;


    public Quicksort(List<E> data) {
        this(data, 0, data.size() - 1);

    }

    public Quicksort(List<E> data, int start, int end) {
        this.list = data;
        this.start = start;
        this.end = end;
    }


    @Override
    protected void compute() {

        if (start < end) {
            int pivotIndex = start + ((end - start) / 2);

            pivotIndex = partition(pivotIndex);

            Quicksort<E> leftSort = new Quicksort<E>(list, start, pivotIndex - 1);

            Quicksort<E> rightSort = new Quicksort<E>(list, pivotIndex + 1, end);

            invokeAll(leftSort, rightSort);
        }


    }

    private int partition(int pivotIndex) {

        E pivotEl = list.get(pivotIndex);

        swap(pivotIndex, end);

        int index = start;

        for (int i = start; i < end; i++) {
            if (list.get(i).compareTo(pivotEl) < 0) {
                swap(i, index);
                index++;
            }
        }

        swap(index, end);

        return index;

    }

    private void swap(int i, int j) {

        if (i == j) {
            return;
        }

        E value = list.get(i);
        list.set(i, list.get(j));
        list.set(j, value);

    }

}

public class QuicksortDemo {


    public static void main (String[] args) {

        List<Integer> list = new ArrayList<Integer>();

        for (int i = 0; i < 10; i++) {
            list.add((int) (Math.random() * 1000));
        }

        ForkJoinPool pool = new ForkJoinPool();

        Quicksort sort = new Quicksort(list);

        long start = System.currentTimeMillis();

        System.out.println("Sorting " + list + "...");

        pool.invoke(sort);

        long end = System.currentTimeMillis();
        System.out.println(list + " sorted in " + (end - start) + "ms.");

    }

}
