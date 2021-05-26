package HomeWork1.Fruits;

public class Apple extends Fruit{

    private static final float WEIGHT =  1.0f;

    public Apple() {
    }

    public static float getWEIGHT() {
        return WEIGHT;
    }

    @Override
    public String toString() {
        return "Apple{}";
    }
}
