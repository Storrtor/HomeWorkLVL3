package HomeWork7;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class MainTestClass {
    int Bflag = 0;
    int Aflag = 0;

    public static void start(Class clazz) throws Exception {
        MainTestClass mainTestClass = new MainTestClass();

        Method[] methods = clazz.getDeclaredMethods();
        ArrayList<Method> methodList = new ArrayList<>();
        for (Method m : methods) {
            if (m.isAnnotationPresent(BeforeSuite.class)) {
                mainTestClass.Bflag++;
            }
            if (m.isAnnotationPresent(AfterSuite.class)) {
                mainTestClass.Aflag++;
            }
        }
        if ((mainTestClass.Aflag | mainTestClass.Bflag) > 1) throw new RuntimeException();

        for (Method m : methods) {
            if (m.isAnnotationPresent(BeforeSuite.class)) {
                m.invoke(null);
            }
            if (m.isAnnotationPresent(Test.class)) {
                methodList.add(m);
            }
        }

        methodList.sort((o1, o2) -> {
            return o1.getAnnotation(Test.class).priority() - o2.getAnnotation(Test.class).priority();
        });

        for (int i = methodList.size() - 1; i >= 0; i--) {
            System.out.print("Приоритет: " + methodList.get(i).getAnnotation(Test.class).priority() + " Тест: ");
            methodList.get(i).invoke(null);
        }

        for (Method m : methods) {
            if (m.isAnnotationPresent(AfterSuite.class)) {
                m.invoke(null);
            }
        }
    }
}







