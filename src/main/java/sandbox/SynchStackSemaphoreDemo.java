package sandbox;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SynchStackSemaphoreDemo {
    private static class MutexSemaphore {

        private boolean mutex;
        public MutexSemaphore(boolean initial) {
            mutex = initial;
        }

        public synchronized void p() {
            while (mutex) {
                try {
                    wait();
                } catch (InterruptedException ignored){}
            }
            mutex = true;
        }

        public synchronized void v() {
            mutex = false;
            notify();
        }
    }

    private static class SynchStack {
        private List<Integer> stack;
        private MutexSemaphore isEmpty;
        private MutexSemaphore isAccessed;

        public SynchStack() {
            this(new MutexSemaphore(true));
            stack = new ArrayList<>();
        }

        public SynchStack(MutexSemaphore sem) {
            stack = new ArrayList<>();
            isEmpty = sem;
            isAccessed = new MutexSemaphore(false);
        }

        public int pop() {

            isEmpty.p();
            isAccessed.p();
            int i = stack.removeLast();
            isAccessed.v();
            return i;
        }

        public void push(int i) {
            isAccessed.p();
            stack.add(i);
            isAccessed.v();
            isEmpty.v();
        }
    }



    public static void main(String... args) throws InterruptedException {

        SynchStack stack = new SynchStack();

        for (int i = 0 ; i < 100; i++) {
            new Thread(() -> {
                while (true) {
                    stack.push((new Random()).nextInt(10));
                }
            }).start();
            new Thread(() -> {
                while (true) {
                    System.out.print(stack.pop());
                    System.out.print("\r");
                }
            }).start();
        }




    }


}
