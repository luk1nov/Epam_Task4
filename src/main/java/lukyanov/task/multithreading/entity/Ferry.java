package lukyanov.task.multithreading.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class Ferry implements Runnable{
    private static final Logger logger = LogManager.getLogger();
    private static final int MAX_AREA = 100;
    private static final int MAX_WEIGHT = 10000;
    private static final List<Car> ferryCars = new ArrayList<>();
    private static final ConcurrentLinkedQueue<Car> carQueue = new ConcurrentLinkedQueue<>();
    private static final ReentrantLock lock = new ReentrantLock();
    private AtomicInteger loadedArea = new AtomicInteger();
    private AtomicInteger loadedWeight = new AtomicInteger();
    private static Ferry instance;
    private boolean ready;
    private boolean isSailing;

    private Ferry (){}

    public static Ferry getInstance(){
        lock.lock();
        try {
            if (instance == null){
                instance = new Ferry();
            }
        } finally {
            lock.unlock();
        }
        return instance;
    }


    public boolean isSailing() {
        return isSailing;
    }

    public void setSailing(boolean sailing) {
        isSailing = sailing;
    }

    public void addCar(Car car){
        lock.lock();
        logger.info(loadedArea + " " + loadedWeight);
        if (loadedWeight.get() <= MAX_WEIGHT && loadedArea.get() <= MAX_AREA) {
            logger.warn("Car "+ car.getCarId() + " drove to the ferry");
            car.setOnBoard(true);
            loadedWeight.addAndGet(car.getWeight());
            loadedArea.addAndGet(car.getArea());
            ferryCars.add(car);
        } else {
            carQueue.add(car);
            logger.info("Car "+ car.getCarId() + " drove to queue");
        }
        lock.unlock();
    }

    public void unloadFerry(){
        ferryCars.clear();
        loadedArea.set(0);
        loadedWeight.set(0);
    }

    @Override
    public void run() {
        //in process
/*        while (carQueue.size() < 1) {
            logger.info("Ferry waiting for loading");
            try {
                TimeUnit.SECONDS.sleep(2);
                if (ferryCars.size() >= 2) {
                    isSailing = true;
                    while (!ferryCars.isEmpty()) {
                        logger.info("The ferry delivers cars with a total weight " + loadedWeight);
                        TimeUnit.SECONDS.sleep(2);
                        unloadFerry();
                        logger.info("The ferry deliver the cars");
                        isSailing = false;
                        TimeUnit.SECONDS.sleep(1);
                    }
                }
            } catch (InterruptedException e) {
                logger.error(e.getMessage());
            }
        }
        logger.info("Cars in the queue " + carQueue.size());*/
    }

    //equal & hashcode
}

