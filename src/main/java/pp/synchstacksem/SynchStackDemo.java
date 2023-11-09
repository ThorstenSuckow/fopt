package pp.synchstacksem;

class Producer extends Thread {

    SynchStack stack;
    public Producer(SynchStack stack) {
        this.stack = stack;
    }

    public void run() {

        while (true) {

            System.out.println("Producer wants to push...");

            /*try {
                Thread.sleep((int) (Math.random() * 10));
            } catch (InterruptedException ignored) {
            }*/

            Object o = new Object();

            stack.push(o);

            System.out.println(getName() + " pushed " + o + ".");
        }
    }
}
class Consumer extends Thread {

    SynchStack stack;
    public Consumer(SynchStack stack) {
        this.stack = stack;
    }

    public void run() {

        while (true) {
            System.out.println("Consumer wants to pop...");
            Object o = stack.pop();
            if (o == null) {
                throw new RuntimeException("o == null");
            }
            System.out.println("    " + getName() + " popped " + o + ".");

            /*try {
                Thread.sleep((int) (Math.random() * 10));
            } catch (InterruptedException ignored) {
            }*/
        }
    }
}


public class SynchStackDemo {

    public static void main(String[] args) {

        SynchStack stack = new SynchStack();

        Producer p = new Producer(stack);
        Consumer c = new Consumer(stack);

        c.start();
        p.start();


    }

}
