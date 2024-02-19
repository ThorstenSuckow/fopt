package praktikum.fopt1und2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SynchStackDemo {

    private static class SynchStack {

        List<Object> stackList;

        public SynchStack() {
            stackList = new ArrayList<>();
        }

        public synchronized void push(Object obj) {

            log("[push] " + Thread.currentThread().getName() + " push " + obj);
            stackList.add(obj);
            notify();
        }

        public synchronized Object pop() {

            while (stackList.isEmpty()) {
                try {
                    log("[pop] " + Thread.currentThread().getName() + " waiting...");
                    this.wait();
                } catch (InterruptedException ignored) {}
            }

            log("[pop] " + Thread.currentThread().getName() + " popping " + stackList.get(stackList.size() - 1));
            Object el = stackList.remove(stackList.get(stackList.size() - 1));

            if (!stackList.isEmpty()) {
                notify();
            }

            return el;
        }

        private void log(String msg) {
            System.out.println(msg);
        }

    }



    public static void main(String[]args) {
        SynchStack  stack = new SynchStack();

        final int maxThreads = 2000;

        for (int i = 0; i < maxThreads; i++) {

            if (i == 2) {
                new Thread(()->{
                    while (true) {
                        Random r = new Random();
                        stack.push(r.nextInt(maxThreads));
                    }
                }).start();
            } else {
                new Thread(()->{while(true){stack.pop();}}).start();
            }
        }

    }

}
