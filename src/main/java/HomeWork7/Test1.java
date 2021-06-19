package HomeWork7;


public class Test1 {

    @BeforeSuite
    public static void before(){
        System.out.println("Before");
    }

    public static void NotTest(){
        System.out.println("NotTest");
    }


    @Test (priority = 5)
    public static void addTest1(){
        System.out.println("test 1");
    }

    @Test (priority = 10)
    public static void addTest2(){
        System.out.println("test 2");
    }

    @Test (priority = 7)
    public static void addTest3(){
        System.out.println("test 3");
    }

    @Test (priority = 6)
    public static void addTest4(){
        System.out.println("test 4");
    }

    @AfterSuite
    public static void after(){
        System.out.println("After");
    }

//    @AfterSuite
//    public static void after1(){
//        System.out.println("After1");
//    }
}
