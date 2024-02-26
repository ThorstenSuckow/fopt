package sandbox;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SynchStackConditionedDemo {

    private static class SynchStack {

        private List<Integer> stack;
        private Lock lock;

        private Condition isEmpty;

        public SynchStack() {
            stack = new ArrayList<>();
            lock = new ReentrantLock();
            isEmpty = lock.newCondition();
        }

        public int pop() {

            try {
                lock.lock();

                if (stack.isEmpty()) {
                    try {
                        System.out.print(0);
                        System.out.print("\r");
                        isEmpty.await();
                    } catch (InterruptedException ignored) {
                    }
                }
                System.out.print(stack.size());
                System.out.print("\r");
                return stack.removeLast();
            } finally {
                lock.unlock();
            }
        }
        public void push(int i) {
            try {
                lock.lock();
                stack.add(i);
                isEmpty.signal();
            } finally {
                lock.unlock();
            }
        }
    }



    public static void main(String... args) {

        SynchStack stack = new SynchStack();
        Thread t1 = new Thread(() -> {
            while (true) {
                stack.push((new Random()).nextInt());
            }
        });
        Thread t2 = new Thread(() -> {
            while (true) {
                stack.pop();
            }
        });

        t1.start();
        t2.start();

    }

}
