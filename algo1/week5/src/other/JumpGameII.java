package other;

import static java.lang.Math.min;
import static java.util.Arrays.fill;

public class JumpGameII {

    public static int jumpDpFromRightToLeft(int[] jumps) {
        int n = jumps.length;

        int[] out = new int[n];

        fill(out, 0, n - 1, n);

        for (int i = n - 2; i >= 0; i--)
            for (int k = min(n - 1, i + jumps[i]); k > i; k--)
                out[i] = min(out[i], 1 + out[k]);

        return out[0];
    }

    public static int jumpDpFromLeftToRight(int[] jumps) {
        int n = jumps.length;

        int[] out = new int[n];

        fill(out, 1, n, n);

        for (int i = 1; i < n; i++)
            for (int k = 0; k < i; k++)
                if (jumps[k] + k >= i)
                    out[i] = min(out[i], 1 + out[k]);

        return out[n - 1];

    }

}
