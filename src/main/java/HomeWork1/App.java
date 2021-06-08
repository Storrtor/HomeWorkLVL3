package HomeWork1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class App {
    public static void main(String[] args) {

        TypedBox<Integer> typedBox1 = new TypedBox<>(1,2,3,4);
        System.out.println(typedBox1);
        System.out.println(Arrays.toString(typedBox1.changePlaces()));

        TypedBox<String> boxString = new TypedBox<>("A","D","E","G");
        System.out.println(boxString);
        System.out.println(Arrays.toString(boxString.changePlaces()));

        System.out.println("------");
        System.out.println(typedBox1.getClass());
        System.out.println(typedBox1.changeToArrayList().getClass());
        System.out.println(typedBox1);
        System.out.println(boxString.changeToArrayList().getClass());
        System.out.println(boxString);

        System.out.println("-------");




    }
}
