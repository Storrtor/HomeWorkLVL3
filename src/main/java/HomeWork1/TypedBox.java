package HomeWork1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TypedBox <T>{
    private final T[] object;

    public TypedBox(T... object) {
        this.object = object;
    }

    public T[] getObject() {
        return object;
    }

    @Override
    public String toString() {
        return "TypedBox{" +
                "object=" + Arrays.toString(object) +
                '}';
    }

    /**
     * 1. Написать метод, который меняет два элемента массива местами.
     * (массив может быть любого ссылочного типа);
     */
    public T[] changePlaces(){
        for (int i = 0; i < object.length - 1; i++) {
            T one = object[i];
            object[i] = object[i+1];
            object[i+1] = one;
        }
        return object;
    }

    /**
     * 2. Написать метод, который преобразует массив в ArrayList;
     * @return ArrayList
     */
    public ArrayList changeToArrayList(){
        List<T> list = new ArrayList<>();
        for (int i = 0; i < object.length; i++) {
            list.add(object[i]);
        }
        return (ArrayList) list;
    }




}
