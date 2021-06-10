package HomeWork5;

import java.util.concurrent.CountDownLatch;

public class Car implements Runnable{

    private static int CARS_COUNT = 4;

    private static int CAR_NUMBER = 0;

    CountDownLatch latch = new CountDownLatch(4);

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

    public Car(Race race, int speed) {
        this.race = race;
        this.speed = speed;
        CAR_NUMBER++;
        this.name = "Участник №" + CAR_NUMBER;
    }


    @Override
    public void run() {
        try{
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int)(Math.random() * 800));
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



        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this);
        }

    }




}
