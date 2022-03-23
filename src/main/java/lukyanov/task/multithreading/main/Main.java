package lukyanov.task.multithreading.main;

import lukyanov.task.multithreading.TalkThread;
import lukyanov.task.multithreading.WalkRunnable;
import lukyanov.task.multithreading.entity.Car;
import lukyanov.task.multithreading.entity.Ferry;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {

        List<Car> cars = new ArrayList<>();
        Ferry ferry = Ferry.getInstance();
        cars.add(new Car(1000, 50, 1));
        cars.add(new Car(1000, 50, 2));
        cars.add(new Car(1000, 50, 3));
        cars.add(new Car(1000, 50, 4));
        cars.add(new Car(1000, 50, 6));
        cars.add(new Car(1000, 50, 7));
        cars.add(new Car(1000, 50, 8));
        cars.add(new Car(1000, 50, 9));

        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(ferry);

        for (Car car : cars) {
            car.setFerry(ferry);
            executorService.execute(car);
        }

        executorService.shutdown();
    }
}
