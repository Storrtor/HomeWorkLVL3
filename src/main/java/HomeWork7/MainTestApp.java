package HomeWork7;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;


public class MainTestApp {

    public static void main(String[] args) {
        TestClass1 testClass1 = new TestClass1();
        TestClass2 testClass2 = new TestClass2();

//        start(testClass1.getClass());
        start(testClass2.getClass());

    }

    static void start(Class<?> clazz) {
        boolean isUniqBefore = true;
        boolean isUniqAfter = true;
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            Annotation[] declaredAnnotations = method.getDeclaredAnnotations();

            for (Annotation annotation : declaredAnnotations) {


                if(annotation.annotationType().equals(BeforeSuite.class) && isUniqBefore) {
                    System.out.println(method.toString());
                    System.out.println("BeforeSuite");
                    isUniqBefore = false;
                } else if (annotation.annotationType().equals(BeforeSuite.class) && !isUniqBefore){
                    throw new RuntimeException("Метод BeforeSuite должен быть только один");
                }
                if (annotation.annotationType().equals(Test.class)) {
                    System.out.println(method.toString());
                    System.out.println("Test");
                }
                if (annotation.annotationType().equals(AfterSuite.class) && isUniqAfter) {
                    System.out.println(method.toString());
                    System.out.println("AfterSuite");
                    isUniqAfter = false;
                } else if (annotation.annotationType().equals(AfterSuite.class) && !isUniqAfter) {
                    throw new RuntimeException("Метод AfterSuite должен быть только один");
                }
            }
        }
    }



}
