package HomeWork6;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainApp {

    public static void main(String[] args) {
        MainApp mainApp = new MainApp();

        System.out.println(Arrays.toString(mainApp.arrayWithFour(new int[]{1, 2, 4, 4, 2, 3, 4, 1, 7})));
        System.out.println(mainApp.checkOneFour(new int[]{1, 4, 4, 1, 1, 4, 3}));

    }

    /**
     * №2. Метод, получающий не пустой одномерный целочисленный массив.
     * @return новый массив, полученный путем вытаскивания из исходного массива элементов, идущих после последней четверки.
     * Входной массив должен содержать хотя бы одну четверку, иначе в методе необходимо выбросить RuntimeException.
     * Написать набор тестов для этого метода (по 3-4 варианта входных данных). Вх: [ 1 2 4 4 2 3 4 1 7 ] -> вых: [ 1 7 ].
     */
    public int[] arrayWithFour(int[] array) {
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

    /**
     * №3. Проверяет состав массива из чисел 1 и 4.
     * Если в нем нет хоть одной четверки или единицы, то метод вернет false;
     * Написать набор тестов для этого метода (по 3-4 варианта входных данных).
     */
    public boolean checkOneFour(int[] array){
        boolean findOne = Arrays.stream(array).anyMatch(z -> z == 1);
        boolean findFour = Arrays.stream(array).anyMatch(z -> z == 4);
        int[] findAnotherNum = Arrays.stream(array).filter(z -> z != 4 && z != 1).toArray();
        if(findOne && findFour && findAnotherNum.length == 0){
            return true;
        }
        return false;
    }
}
