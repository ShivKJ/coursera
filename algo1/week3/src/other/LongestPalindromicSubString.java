package other;

import static java.lang.Math.min;
import static java.util.Arrays.copyOfRange;

public final class LongestPalindromicSubString {
    public static String search(String string) {
        return search(string.toCharArray());
    }

    public static String search(char[] str) {
        return null;
    }

    public static char[] search(char[] str, int idx) {
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

        return copyOfRange(str, left, right + 1);
    }

    public static int search(char[] arr, int left, int right, int w) {
        int expansion = 0;

        for (int i = 1; i <= w; i++)
            if (arr[left - i] == arr[right + i])
                expansion++;
            else
                break;

        return expansion;
//        return copyOfRange(arr, left - expansion, right + expansion + 1);
    }

    public static void main(String[] args) {
//        System.out.println(search("abcdcba".toCharArray(), 3));
//        System.out.println(search("abcddcba".toCharArray(), 3));
//        System.out.println(search("abcdefgabcddcba".toCharArray(), 10));
        System.out.println(search("abcddc".toCharArray(), 3));
    }
}
