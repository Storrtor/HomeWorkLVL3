package HomeWork7;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class MainTestClass {
    int Bflag = 0;
    int Aflag = 0;

    public static void start(Class obj) throws Exception {
        MainTestClass tc = new MainTestClass();

        Method[] methods = obj.getDeclaredMethods();
        ArrayList<Method> arr = new ArrayList<>();
        for (Method m : methods) {
            if (m.isAnnotationPresent(BeforeSuite.class)) {
                tc.Bflag++;
            }
            if (m.isAnnotationPresent(AfterSuite.class)) {
                tc.Aflag++;
            }
        }
        if ((tc.Aflag | tc.Bflag) > 1) throw new RuntimeException();

        for (Method m : methods) {
            if (m.isAnnotationPresent(BeforeSuite.class)) {
                m.invoke(null);
            }
            if (m.isAnnotationPresent(Test.class)) {
                arr.add(m);
            }
        }

        arr.sort((o1, o2) -> {
            return o1.getAnnotation(Test.class).priority() - o2.getAnnotation(Test.class).priority();
        });

        for (int i = arr.size() - 1; i >= 0; i--) {
            System.out.print("Приоритет: " + arr.get(i).getAnnotation(Test.class).priority() + " Тест: ");
            arr.get(i).invoke(null);
        }

        for (Method m : methods) {
            if (m.isAnnotationPresent(AfterSuite.class)) {
                m.invoke(null);
            }
        }
    }
}







