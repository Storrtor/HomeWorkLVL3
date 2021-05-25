package HomeWork1;

import java.util.Arrays;

public class TypedBox <T>{
    private final T[] object;

    public TypedBox(T... object) {
        this.object = object;
    }

    public T[] getObject() {
        return object;
    }

    public T[] changePlaces(T... object){
        for (int i = 0; i < object.length; i++) {
            T one = object[i];
            object[i] = object[i+1];
            object[i+1] = one;
        }
        return object;
    }

    @Override
    public String toString() {
        return "TypedBox{" +
                "object=" + Arrays.toString(object) +
                '}';
    }
}
