package HomeWork4;

public class AbcApp {

    private final Object locker = new Object();
    String current = "C";

    public static void main(String[] args) {

        AbcApp abcApp = new AbcApp();

        Thread t1 = new Thread(() -> abcApp.printA());
        Thread t2 = new Thread(() -> abcApp.printB());
        Thread t3 = new Thread(() -> abcApp.printC());

        t1.start();
        t2.start();
        t3.start();


    }

    public void printA(){
        synchronized (locker){
            for (int i = 0; i < 5; i++) {
                while (!current.equals("C")){
                    try {
                        locker.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.print("A");
                current = "A";
                locker.notifyAll();
            }
        }
    }

    public void printB(){
        synchronized (locker){
            for (int i = 0; i < 5; i++) {
                while (!current.equals("A")){
                    try {
                        locker.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.print("B");
                current = "B";
                locker.notifyAll();
            }
        }
    }

    public void printC(){
        synchronized (locker){
            for (int i = 0; i < 5; i++) {
                while (!current.equals("B")){
                    try {
                        locker.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.print("C");
                current = "C";
                locker.notifyAll();
            }
        }
    }

}
