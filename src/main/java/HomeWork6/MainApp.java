package HomeWork6;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainApp {

    public static void main(String[] args) {
        System.out.println(Arrays.toString(arrayWithFour(new int[]{1, 2, 4, 4, 2, 3, 4, 1, 7})));
        System.out.println(Arrays.toString(arrayWithFour(new int[]{1, 2, 4, 4, 4, 2, 3, 1})));
        System.out.println(Arrays.toString(arrayWithFour(new int[]{1, 2, 4, 4, 2, 3, 4, 1, 7, 4, 5, 7})));
        System.out.println(Arrays.toString(arrayWithFour(new int[]{1, 2, 4, 4, 2, 3, 4, 1, 7, 4, 5, 4})));
//        arrayWithFour(new int[]{1, 2, 2, 3, 1, 7, 5});

        System.out.println(checkOneFour(new int[]{1,1,1,1,1}));
        System.out.println(checkOneFour(new int[]{4,4}));
        System.out.println(checkOneFour(new int[]{1,1,1,1,1,4}));

    }

    public static int[] arrayWithFour(int[] array) {
        List<Integer> listOfFourNumbers = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            if (array[i] == 4) {
                listOfFourNumbers.add(i);
            }
        }
        if(listOfFourNumbers.isEmpty()){
            throw new RuntimeException("Массив должен содержать хотя бы одну 4");
        }
        int[] modArray = Arrays.stream(Arrays.stream(array).toArray()).skip(listOfFourNumbers.get(listOfFourNumbers.size() - 1) + 1).toArray();

        return modArray;
    }

    public static boolean checkOneFour(int[] array){
        boolean findOne = Arrays.stream(array).anyMatch(z -> z == 1);
        boolean findFour = Arrays.stream(array).anyMatch(z -> z == 4);
        if(findOne && findFour){
            return true;
        }
        return false;
    }
}
