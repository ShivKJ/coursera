package other;

import static java.lang.Integer.MAX_VALUE;
import static java.lang.Math.min;
import static java.lang.String.format;
import static java.util.Arrays.fill;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;

public final class MatrixMultiplication {
    private static final int UNASSIGNED = MAX_VALUE;

    public static long numberOfParamthesis(int n) {
        long[] p = new long[n];
        p[0] = 1;

        for (int i = 1; i < n; i++)
            for (int j = 0; j < i; j++)
                p[i] += p[j] * p[i - j - 1];

        return p[n - 1];
    }

    public static int operations(int[] dim) {
        int n = dim.length - 1;
        int[][] dp = new int[n][n];

        stream(dp).forEach(p -> fill(p, UNASSIGNED));

        int out = operations(dim, dp, 0, n - 1);

        print(dp);

        return out;
    }

    private static int matrixMult(int[] dim, int i, int k, int j) {
        return dim[i] * dim[k + 1] * dim[j + 1];
    }

    public static int operations(int[] dim, int[][] dp, int i, int j) {
        if (dp[i][j] != UNASSIGNED)
            return dp[i][j];

        int out;

        if (i == j)
            out = 0;

        else {
            out = MAX_VALUE;

            for (int k = i; k < j; k++)
                out = min(out, operations(dim, dp, i, k) + operations(dim, dp, k + 1, j) + matrixMult(dim, i, k, j));

        }

        return dp[i][j] = out;
    }

    public static void main(String[] args) {
        System.out.println(operations(new int[] { 30, 35, 15, 5, 10, 20, 25 }));
    }

    private static void print(int[][] arr) {
        arr = stream(arr).map(int[]::clone).toArray(int[][]::new);

        for (int i = 0; i < arr.length; i++)
            for (int j = 0; j < arr.length; j++)
                if (arr[i][j] == UNASSIGNED)
                    arr[i][j] = 0;

        System.out.println(stream(arr).map(MatrixMultiplication::print).collect(joining("\n")));;
    }

    private static String print(int[] p) {
        return stream(p).mapToObj(MatrixMultiplication::print)
                        .collect(joining(""));
    }

    private static String print(int i) {
        return format("%-10d", i);
    }

}
