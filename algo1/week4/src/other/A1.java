package other;

public class A1 {
    public static void main(String[] args) {
        double a = Double.NaN, b = a;
        System.out.println(a == b);

        Double x = Double.valueOf(a), y = Double.valueOf(b);
        System.out.println(x.equals(y));
        System.out.println("======================");

        a = 0;
        b = 0;

        System.out.println(a == b);

        x = Double.valueOf(a);
        y = Double.valueOf(-b);
        System.out.println(x.equals(y));

    }
}
