package HomeWork1.Fruits;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class Box <T extends Fruit>{

    private final ArrayList<T> someFruits = new ArrayList<>();

    public void add(T... fruit){
        someFruits.addAll(Arrays.asList(fruit));
    }

    public void remove(T... fruit){
        someFruits.remove(fruit);
    }

    @Override
    public String toString() {
        return "Box{" +
                "someFruits=" + someFruits +
                '}';
    }

    /**
     * Метод высчитывает вес коробки (апельсинов или яблок)
     * @return weight
     */
    public float getWeight(){
        float weight = 0;
            for (T someFruit : someFruits) {
                weight += someFruit.getWEIGHT(someFruit);
            }
        return weight;
    }

    /**
     * Сравнивает вес коробок
     * @param box
     * @return true - если вес равен, false - если же нет
     */
    public boolean compare(Box<?> box){
        if(this.getWeight() == box.getWeight()){
            return true;
        }
        return false;
    }

    /**
     * Пересыпает все фрукты из текущей коробки в другую
     * @return
     */
    public void changeBox(Box<T> box){
        this.someFruits.stream().forEach(f -> box.add(f));
        //Жень, не поняла как сделать так, чтобы в одном цикле/стриме и добавлять из одного в другой и удалять.
        //При прохождении выкидывает ошибку ConcurrentModificationException
        //Я выкрутилась просто вставив цикл ниже, но мне каежтся это не самое лучшее решение
        for (int i = 0; this.someFruits.size() != 0; i++) {
            this.someFruits.remove(0);
        }
    }
}
