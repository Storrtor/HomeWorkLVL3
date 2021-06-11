import HomeWork6.MainApp;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ArrayWithFourTest {

    @BeforeEach
    void init() {
//        int[] actual = MainApp.arrayWithFour();
    }

    @DisplayName("Проверяет то что входящий массив Not Null")
    @Test
    void testNotNull(int[] array) {
        assertNotNull(array.length);
    }

}
