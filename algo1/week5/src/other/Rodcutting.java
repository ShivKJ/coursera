package other;

import static java.lang.Math.max;
import static java.lang.System.out;

public class Rodcutting {
    public static int rodcutting(int[] p) {
        int n = p.length;
        int[] r = new int[n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++)
                r[i] = max(r[i - j - 1], p[j]);
            r[i] = max(r[i], p[i]);
        }

        return r[n - 1];
    }

    public static void main(String[] args) {
        out.println(rodcutting(new int[] { 1, 5, 8, 9, 10, 17, 17, 20, 24, 30 }));
    }
}
