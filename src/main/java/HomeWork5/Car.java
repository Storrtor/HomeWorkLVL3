package HomeWork5;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Car implements Runnable {

    private static int CARS_COUNT = 4;

    private static int CAR_NUMBER = 0;

    private final CountDownLatch latch;

    private static Lock lock = new ReentrantLock();

    private static boolean isPrinted = false;

    private static boolean isWinner = false;


    public static int getCarsCount() {
        return CARS_COUNT;
    }

    private Race race;
    private int speed;
    private String name;

    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    public Car(CountDownLatch latch, Race race, int speed) {
        this.latch = latch;
        this.race = race;
        this.speed = speed;
        CAR_NUMBER++;
        this.name = "Участник №" + CAR_NUMBER;
    }

    @Override
    public void run() {

        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int) (Math.random() * 800));
            System.out.println(this.name + " готов");
            latch.countDown();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        lock.lock();
        try {
            if (!isPrinted) {
                System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
                isPrinted = true;
            }
        } finally {
            lock.unlock();
        }



        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this);
        }

        lock.lock();
        try {
            if (isWinner == false) {
                System.out.println(this.name + " VICTORY!!!");
                isWinner = true;
            }
        } finally {
            lock.unlock();
        }


//        System.out.println(Thread.activeCount());

    }


}
