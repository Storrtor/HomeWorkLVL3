package HomeWork6;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class App {

    public static void main(String[] args) {
        arrayWithFour(new int[]{1, 2, 4, 4, 2, 3, 4, 1, 7});
        arrayWithFour(new int[]{1, 2, 4, 4, 4, 2, 3, 1});
        arrayWithFour(new int[]{1, 2, 4, 4, 2, 3, 4, 1, 7, 4, 5, 7});
        arrayWithFour(new int[]{1, 2, 4, 4, 2, 3, 4, 1, 7, 4, 5, 4});
//        arrayWithFour(new int[]{1, 2, 2, 3, 1, 7, 5});

    }

    public static int[] arrayWithFour(int[] array) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            if (array[i] == 4) {
                list.add(i);
            }
        }
        if(list.isEmpty()){
            throw new RuntimeException("Массив должен содержать хотя бы одну 4");
        }
        int[] modArray = Arrays.stream(Arrays.stream(array).toArray()).skip(list.get(list.size() - 1) + 1).toArray();
        System.out.println(Arrays.toString(modArray));

        return modArray;
    }
}
