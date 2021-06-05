package HomeWork1.Fruits;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FruitApp {
    public static void main(String[] args) {

        Orange orange = new Orange();
        Apple apple = new Apple();
        Apple apple1 = new Apple();
        Apple apple2 = new Apple();

        Box<Apple> appleBox = new Box<>();
        appleBox.add(apple, apple1, apple2);

        Box<Apple> appleBox1 = new Box<>();
        appleBox1.add(new Apple(),new Apple(),new Apple(), new Apple(), new Apple());

        Box<Orange> orangeBox = new Box<>();
        orangeBox.add(orange,new Orange(), new Orange());

        Box<Orange> orangeBox1 = new Box<>();
        orangeBox1.add(new Orange(), new Orange(), new Orange());

        System.out.println("Вес яблок = " + appleBox.getWeight());
        System.out.println("Вес апельсинов = " + orangeBox.getWeight());
        System.out.println(appleBox.compare(orangeBox));
        System.out.println("-----------");
        System.out.println("-----------");

        System.out.println("-----------");

        System.out.println(orangeBox);
        orangeBox.remove(orange);
        System.out.println(orangeBox);

        System.out.println("----");

        System.out.println(orangeBox1);
        orangeBox.changeBox(orangeBox1);
        System.out.println(orangeBox);
        System.out.println(orangeBox1);

        System.out.println(appleBox);
        System.out.println(appleBox1);

        System.out.println("-----------");

        appleBox.changeBox(appleBox1);
        System.out.println(appleBox);
        System.out.println(appleBox1);


    }

}
