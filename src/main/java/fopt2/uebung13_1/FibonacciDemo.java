package fopt2.uebung13_1;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

class Fibonacci extends RecursiveTask<Integer> {

    int n;

    public Fibonacci(int n) {

        if (n < 0) {
            throw new IllegalArgumentException();
        }

        this.n = n;
    }

    @Override
    protected Integer compute() {

        if (n == 0) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return 1;
        }

        Fibonacci bin1 = new Fibonacci(n - 1);
        Fibonacci bin2 = new Fibonacci(n - 2);
        bin1.fork();
        bin2.fork();

        int l = bin1.join();
        int r = bin2.join();

        //System.out.print(Thread.currentThread().getName() + " computing fib(" + (n - 1) + "): " + l);
        //System.out.print(Thread.currentThread().getName() + " computing fib(" + (n - 2) + "): " + r);

        return l + r;

    }
}


public class FibonacciDemo {

    public static void main(String[] args) {

        int n = 37;

        ForkJoinPool pool = new ForkJoinPool();
        Fibonacci fib = new Fibonacci(n);

        long start = System.currentTimeMillis();

        int result = pool.invoke(fib);

        long end = System.currentTimeMillis();


        System.out.println("The result r=" + result + " for n="+ n +" was computed in " + (end - start) + "ms.");


    }

}
