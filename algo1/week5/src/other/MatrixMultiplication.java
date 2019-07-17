package other;

import static java.lang.System.out;

public final class MatrixMultiplication {
    public static long numberOfParamthesis(int n) {
        long[] p = new long[n];
        p[0] = 1;

        for (int i = 1; i < n; i++)
            for (int j = 0; j < i; j++)
                p[i] += p[j] * p[i - j - 1];

        return p[n - 1];
    }

    public static void main(String[] args) {
        out.println(numberOfParamthesis(4));
    }
}
