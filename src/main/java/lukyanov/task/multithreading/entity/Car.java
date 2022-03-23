package lukyanov.task.multithreading.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.TimeUnit;

public class Car implements Runnable{
    private static final Logger logger = LogManager.getLogger();
    private final int weight;
    private final int area;
    private final int carId;
    private boolean onBoard;
    private Ferry ferry;

    public Car(int weight, int area, int carId) {
        this.weight = weight;
        this.area = area;
        this.carId = carId;
        this.onBoard = false;
    }

    public int getWeight() {
        return weight;
    }

    public int getArea() {
        return area;
    }

    public int getCarId() {
        return carId;
    }

    public boolean isOnBoard() {
        return onBoard;
    }


    public Ferry getFerry() {
        return ferry;
    }

    public void setFerry(Ferry ferry) {
        this.ferry = ferry;
    }

    @Override
    public void run() {
        while (!onBoard){
            try{
                if(ferry.isSailing()){
                    TimeUnit.SECONDS.sleep(1);
                }else {
                    ferry.addCar(this);
                    TimeUnit.SECONDS.sleep(5);
                }
            } catch (InterruptedException e) {
                logger.error(e.getMessage());
            }
        }
    }

    public void setOnBoard(boolean onBoard) {
        this.onBoard = onBoard;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        if (weight != car.weight) return false;
        if (area != car.area) return false;
        if (carId != car.carId) return false;
        if (onBoard != car.onBoard) return false;
        return ferry != null ? ferry.equals(car.ferry) : car.ferry == null;
    }

    @Override
    public int hashCode() {
        int result = weight;
        result = 31 * result + area;
        result = 31 * result + carId;
        result = 31 * result + (onBoard ? 1 : 0);
        result = 31 * result + (ferry != null ? ferry.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Car{");
        sb.append("weight=").append(weight);
        sb.append(", area=").append(area);
        sb.append(", carId=").append(carId);
        sb.append(", onBoard=").append(onBoard);
        sb.append(", ferry=").append(ferry);
        sb.append('}');
        return sb.toString();
    }
}
