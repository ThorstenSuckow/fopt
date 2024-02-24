package klausurvorbereitung.ss19;

public class LogicalTime {


    private int ticks = 0;
    public synchronized void tick() {
        ticks++;
        notifyAll();
    }


    public synchronized void waitTicks(int waitingTicks) {

        int currentTicks = ticks;

        while (ticks - currentTicks != waitingTicks) {
            try {
                System.out.println(Thread.currentThread().getName() + " " + (ticks - currentTicks));
                wait();
            } catch (InterruptedException e) {

            }
        }


        System.out.println(Thread.currentThread().getName() + " done!");
    }


    public static void main (String[] args) throws InterruptedException {

        LogicalTime time = new LogicalTime();

        Thread ticker = new Thread(()->{
            while (true) {
                time.tick();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {

                }
            }
        });

        ticker.start();

        Thread.sleep(1000);

        Thread waiter = new Thread(() -> {
            time.waitTicks(2);
        });

        waiter.start();

    }

}
