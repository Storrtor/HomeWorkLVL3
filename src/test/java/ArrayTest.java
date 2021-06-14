import HomeWork6.MainApp;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class ArrayTest {

    MainApp mainApp;

    @BeforeEach
    void init() {
        System.out.println("Создание обьекта");
        mainApp = new MainApp();
    }

    @AfterEach
    void destruct() {
        System.out.println("Чистка ресурсов");
    }

    @Test
    @DisplayName("Входной массив без четверок для метода написания чисел после 4")
    void withoutFour() {
        int[] arr = new int[]{1, 2, 3, 5};
        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> mainApp.arrayWithFour(arr));
        assertEquals("Массив должен содержать хотя бы одну 4", runtimeException.getMessage());
    }

    @DisplayName("Проверка корректности написания чисел после 4")
    @ParameterizedTest
    @MethodSource
    void testArray(int[] arr, int[] resArr) {
        assertArrayEquals(resArr, mainApp.arrayWithFour(arr), "The result should be " + resArr);
    }

    private static Stream<Arguments> testArray() {
        List<Arguments> list = new ArrayList<>();
        list.add(Arguments.arguments(new int[]{1, 2, 5, 4, 3, 7, 4, 5, 8, 1, 8, 1}, new int[]{5, 8, 1, 8, 1}));
        list.add(Arguments.arguments(new int[]{1, 4, 5}, new int[]{5}));
        list.add(Arguments.arguments(new int[]{1, 2, 4, 4, 2, 3, 4, 1, 7}, new int[]{1, 7}));
        list.add(Arguments.arguments(new int[]{4}, new int[]{}));
        list.add(Arguments.arguments(new int[]{1, 4, 7, 8, 9, 4, 5, 7, 9, 3, 5}, new int[]{5, 7, 9, 3, 5}));
        return list.stream();
    }

    private static Stream<Arguments> testArrayOneFour() {
        List<Arguments> list = new ArrayList<>();
        list.add(Arguments.arguments(new int[]{1, 1, 1, 4, 4, 1, 4, 4}, true));
        list.add(Arguments.arguments(new int[]{1, 1, 1, 1, 1, 1}, false));
        list.add(Arguments.arguments(new int[]{4, 4, 4, 4}, false));
        list.add(Arguments.arguments(new int[]{1, 4, 4, 1, 1, 4, 3}, false));
        return list.stream();
    }

    @DisplayName("Проверка корректности boolean метода наличия 4 и 1")
    @ParameterizedTest
    @MethodSource
    void testArrayOneFour(int[] array, boolean bool) {
        assertEquals(bool, mainApp.checkOneFour(array), "The result should be " + bool);
    }


}
