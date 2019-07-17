package other;

public final class FibonacciSequence {

    public static int fib(int n) {
        if (n < 2)
            return n;

        int[] f = new int[n];
        f[1] = 1;

        for (int i = 2; i < n; i++)
            f[i] = f[i - 1] + f[i - 2];
        
        return f[n - 1];
    }

}
