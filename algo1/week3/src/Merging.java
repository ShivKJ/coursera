import static java.lang.Integer.MAX_VALUE;
import static java.lang.System.arraycopy;
import static java.util.Arrays.copyOfRange;

import java.util.Arrays;

public final class Merging {

    public static int[] mergingSortedArray(int[] a, int[] b) {
        int[] c = new int[a.length + b.length];

        for (int i = 0, j = 0, idx = 0; idx < c.length;) {
            c[idx++] = a[i] < b[j] ? a[i++] : b[j++];

            if (i == a.length) {
                arraycopy(b, j, c, idx, b.length - j);
                idx += b.length - j;
            }

            if (j == b.length) {
                arraycopy(a, i, c, idx, a.length - i);
                idx += a.length - i;
            }
        }

        return c;
    }

    public static int[] mergingSortedArrayDirty(int[] a, int p, int q, int r) {
        int[] b = copyOfRange(a, p, r + 1);

        for (int idx = p, i1 = 0, i2 = q + 1 - p; idx <= r; idx++) {
            int index;

            if (i1 == q - p + 1)
                index = i2++;
            else if (i2 == r - p + 1)
                index = i1++;
            else if (b[i1] <= b[i2])
                index = i1++;
            else
                index = i2++;

            a[idx] = b[index];
        }

        return a;
    }

    public static int[] mergingSortedArrayClean(int[] a, int p, int q, int r) {
        int[] b = new int[r - p + 3];

        arraycopy(a, p, b, 0, q - p + 1);
        arraycopy(a, q + 1, b, q - p + 2, r - q);

        b[q - p + 1] = MAX_VALUE;
        b[r - p + 2] = MAX_VALUE;

        for (int idx = p, i1 = 0, i2 = q - p + 1; idx <= r; idx++)
            a[idx] = b[i1] < b[i2] ? b[i1++] : b[i2++];

        return a;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(mergingSortedArray(new int[] { -1, 2, 10, 15 }, new int[] { -1, 4, 5, 10, 20 })));
        int[] c = { -1, 2, 10, 15, -1, 4, 5, 10, 20 };
        System.out.println(Arrays.toString(mergingSortedArrayDirty(c, 0, 3, c.length - 1)));
        c = new int[] { -1, 2, 10, 15, -1, 4, 5, 10, 20 };
        mergingSortedArrayClean(c, 0, 3, c.length - 1);
    }
}
