package HomeWork7;

public class TestClass2 {

    TestClass2 testClass2;

    @BeforeSuite
    void init(){
        testClass2 = new TestClass2();
        System.out.println("Создали тест класс");
    }

    @AfterSuite
    void destruct(){
        System.out.println("Закрыли ресурсы");
    }

    @Test
    void printTest2A(){
        System.out.println("A");
    }

    @Test
    void printTest2B(){
        System.out.println("B");
    }
}
