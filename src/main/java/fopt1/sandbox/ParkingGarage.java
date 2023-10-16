package fopt1.sandbox;

class ParkingGarageFair extends ParkingGarageImpl {


    private int places;
    private int nextWaitingNumber;
    private int nextEnteringNumber;

    public ParkingGarageFair (int places) {
        this.places = places;
    }

    public synchronized void enter() {

        int myNumber = nextWaitingNumber++;

        System.out.println(Thread.currentThread().getName() + " entering. Number: " + myNumber);


        while (myNumber != nextEnteringNumber || places == 0) {

            try {
                System.out.println(Thread.currentThread().getName() + " waiting: " + myNumber + " is my number, but next to enter is  " + nextEnteringNumber);
                wait();
            } catch (InterruptedException ignored) {

            }
        }

        places--;
        nextEnteringNumber++;

        System.out.println(Thread.currentThread().getName() + " entered! My number was " + myNumber + "! next is " + nextEnteringNumber);

        //notify();
         notifyAll();
    }

    public synchronized void leave() {
        places++;

        System.out.println(Thread.currentThread().getName() + " leaving. Byebye!");
        //notify();
        notifyAll();
    }


}


class ParkingGarageImpl {

    protected int places = 0;

    public ParkingGarageImpl() {
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

        garage.enter();

        try {
            Thread.sleep((int) (Math.random() * 100));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        garage.leave();


    }

}




public class ParkingGarage {


    public static void main(String[] args) throws InterruptedException {

        ParkingGarageFair garage = new ParkingGarageFair(20);

        for (int i = 0; i < 100; i++) {
            Thread t1 = new Thread(new Car(garage, i % 2 == 0));
            t1.start();
        }

        System.out.println("Exiting...");


    }

}
