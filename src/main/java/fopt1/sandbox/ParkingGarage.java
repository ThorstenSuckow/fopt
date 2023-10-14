package fopt1.sandbox;

class ParkingGarageImpl {

    protected int places = 0;

    public ParkingGarageImpl() {
        System.out.println("constructor 1");
    }

    public ParkingGarageImpl(final int places) {
        this.places = places;
        System.out.println("New garage...");
    }

    public synchronized void enter() {
        while (places == 0);
        System.out.println(Thread.currentThread().getName() + " entering");
        places--;
    }

    public synchronized void leave() {
        System.out.println(Thread.currentThread().getName() + " leaving");
        places++;
    }
}

class ParkingGarageImplSucc extends ParkingGarageImpl {





    public void enter() {
        while (!tryToEnter());
    }

    public synchronized boolean tryToEnter() {

        boolean success = false;

        System.out.println(Thread.currentThread().getName() + " looking for a parking spot...");
        if (places > 0) {

            System.out.println(Thread.currentThread().getName() + " found a parking spot!");
            places--;
            success = true;
        }

        return success;

    }


}


class Car implements Runnable {


    boolean enter;
    ParkingGarageImpl garage;
    public Car(ParkingGarageImpl garage, boolean enter) {
        this.garage = garage;
        this.enter = enter;
    }

    public void run() {
        if (enter) {
            garage.enter();
        } else {
            garage.leave();
        }
    }

}

public class ParkingGarage {


    public static void main(String[] args) throws InterruptedException {

        ParkingGarageImpl garage = new ParkingGarageImplSucc();

        Thread car1 = new Thread(new Car(garage, true));
        Thread car2 = new Thread(new Car(garage, false));

        //car1.start();
      //  car2.start();

        car1.join();

        System.out.println("Exiting...");




    }

}
