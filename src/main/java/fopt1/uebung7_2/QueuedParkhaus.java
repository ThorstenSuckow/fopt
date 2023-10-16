package fopt1.uebung7_2;

import org.junit.jupiter.api.Order;

import java.util.ArrayList;
import java.util.List;

class Parkhaus {

    private int places = 0;

    private List<Thread> queue = new ArrayList<>();

    public List<String> orderIn = new ArrayList<String>();
    public List<String> orderOut = new ArrayList<>();

    public Parkhaus(int places) {

        if (places <= 0) {
            throw new IllegalArgumentException("places must be > 0");
        }

        this.places = places;

    }

    public synchronized void enter() {

        queue.add(Thread.currentThread());
        orderIn.add(Thread.currentThread().getName());

        while (places == 0 || queue.getFirst() != Thread.currentThread()) {

            try {
                wait();
            } catch (InterruptedException ignored) {

            }
        }

        places--;
        orderOut.add(queue.removeFirst().getName());
        notifyAll();
    }


    public synchronized void leave() {

        places++;
        notifyAll();
    }

}

class Car extends Thread {

    Parkhaus parkhaus;

    public Car(Parkhaus parkhaus) {
        this.parkhaus = parkhaus;
    }

    public void run() {

        try {
            Thread.sleep((int) (Math.random() * 100));
            parkhaus.enter();

            Thread.sleep((int) (Math.random() * 100));
            parkhaus.leave();

        } catch (InterruptedException ignored) {

        }
    }


}



public class QueuedParkhaus {

    public static void main(String[] args) {

        Parkhaus parkhaus = new Parkhaus(1);

        List<Car> cars = new ArrayList<>();

        for (int i = 0; i< 50; i++) {
            Car car = new Car(parkhaus);
            car.start();
            cars.add(car);
        }

        for (Car car: cars) {
            try {
                car.join();
            } catch (InterruptedException ignored) {

            }
        }
    }


}
