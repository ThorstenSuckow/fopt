package fopt1.uebung7_2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class ParkhausQueueTest {

    @Test
    @DisplayName("proper order")
    void testFifo() throws InterruptedException {

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

        Assertions.assertEquals(parkhaus.orderIn, parkhaus.orderOut);
    }


}