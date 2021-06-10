package HomeWork5;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MainClass {

    public static void main(String[] args) throws InterruptedException {

        Lock lock = new ReentrantLock();
        System.out.println("Важное объявление >>> Подготовка!!!");


        Race race = new Race(new Road(60), new Tunnel(), new Road(40));
        Car[] cars = new Car[Car.getCarsCount()];

        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10));
        }

        for (int i = 0; i < cars.length; i++) {
            new Thread(cars[i]).start();
        }

        System.out.println("Важное обьявление >>> Гонка началась!!!");

        System.out.println("Важное обьявление >>> Гонка закончилась!!!");


    }

}
