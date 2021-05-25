package HomeWork1;

public class App {
    public static void main(String[] args) {
        TypedBox<Integer> typedBox1 = new TypedBox<>(1,2,3,4);
        System.out.println(typedBox1);
        System.out.println(typedBox1.changePlaces());
    }
}
