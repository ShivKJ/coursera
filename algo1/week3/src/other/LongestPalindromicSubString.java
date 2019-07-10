package other;

import static java.lang.Math.min;
import static java.lang.String.copyValueOf;
import static java.util.Arrays.copyOfRange;

public final class LongestPalindromicSubString {
    public static String search(String string) {
        return search(string.toCharArray());
    }

    public static String search(char[] str) {
        int[] arr = { 0, 0 };

        for (int i = 1; i < str.length - 1; i++) {
            int[] a = search(str, i);

            if (a[1] - a[0] > arr[1] - arr[0])
                arr = a;
        }

        return copyValueOf(copyOfRange(str, arr[0], arr[1] + 1));
    }

    public static int[] search(char[] str, int idx) {
        int n = str.length, w = min(idx, n - idx - 1);

        int a = search(str, idx, idx, w);

        int left = idx - a, right = idx + a;

        if (str[idx] == str[idx + 1]) {
            w = min(n - idx - 2, w);
            int b = search(str, idx, idx + 1, w);

            if (a < b) {
                left = idx - b;
                right = idx + b + 1;
            }
        }

        return new int[] { left, right };
    }

    public static int search(char[] arr, int left, int right, int w) {
        int expansion = 0;

        for (int i = 1; i <= w; i++)
            if (arr[left - i] == arr[right + i])
                expansion++;
            else
                break;

        return expansion;
    }

    public static void main(String[] args) {
        System.out.println(search("abcddc"));
    }
}
