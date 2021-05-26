package HomeWork1.Fruits;

public abstract class Fruit {

    protected static float WEIGHT;

    public Fruit() {
    }

    public float getWEIGHT(Fruit fruit) {
        if(fruit instanceof Apple){
            return ((Apple) fruit).getWEIGHT();
        } else if(fruit instanceof Orange){
            return ((Orange) fruit).getWEIGHT();
        } else {
            throw new RuntimeException("Нет веса данного вида фруктов");
        }
    }
}
