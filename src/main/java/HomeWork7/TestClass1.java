package HomeWork7;

public class TestClass1 {

    TestClass1 testClass1;

    @BeforeSuite
    void init(){
        testClass1 = new TestClass1();
        System.out.println("Создали тест класс");
    }

    @BeforeSuite
    void init2(){
        testClass1 = new TestClass1();
        System.out.println("Создали тест класс");
    }

    @AfterSuite
    void destruct(){
        System.out.println("Закрыли ресурсы");
    }

    @Test
    void printA(){
        System.out.println("A");
    }

    @Test
    void printB(){
        System.out.println("B");
    }


}
