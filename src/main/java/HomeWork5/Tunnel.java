package HomeWork5;

import java.util.concurrent.Semaphore;

public class Tunnel extends Stage{

    Semaphore semaphore = new Semaphore(Car.getCarsCount() / 2);

    public Tunnel() {
        this.length = 80;
        this.description = "Тоннель " + length + " метров";
    }

    @Override
    public void go(Car car) {
        try {
            try {
                System.out.println(car.getName() + " готовится к этапу (ждет): " + description);
                semaphore.acquire();  //даем палочку, заходит Car.getCarsCount()/2
                System.out.println(car.getName() + " начал этап: " + description);
                Thread.sleep(length / car.getSpeed() * 1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            } finally {
                semaphore.release(); //освобождаем палочку
                System.out.println(car.getName() + " закончил этап: " + description);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
