package HomeWork5;

import java.util.concurrent.CountDownLatch;

public class MainClass {

    public static void main(String[] args) throws InterruptedException {

        CountDownLatch latch = new CountDownLatch(Car.getCarsCount());

        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");

        Race race = new Race(new Road(60), new Tunnel(), new Road(40));
        Car[] cars = new Car[Car.getCarsCount()];

        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(latch, race, 20 + (int) (Math.random() * 10));
        }

        for (int i = 0; i < cars.length; i++) {
            new Thread(cars[i]).start();
        }

        while (Thread.activeCount() > 2) {
        }
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");


    }

}
