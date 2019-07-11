package other;

import static java.lang.String.copyValueOf;
import static java.util.Arrays.copyOfRange;

public final class LongestPalindromicSubString {
    public static String longestPalindrome(String string) {
        return string.isEmpty() ? string : search(string.toCharArray());
    }

    private static String search(char[] str) {
        int[] arr = { 0, 0 };

        for (int i = 0; i < str.length; i++) {
            int[] a = largestPelindromeAround(str, i);

            if (isGreater(a, arr))
                arr = a;
        }

        return copyValueOf(copyOfRange(str, arr[0], arr[1] + 1));
    }

    private static boolean isGreater(int[] a, int[] b) {
        return a[1] - a[0] > b[1] - b[0];
    }

    private static int[] largestPelindromeAround(char[] str, int idx) {
        int[] a = pelindromeExpansion(str, idx, idx);

        if (idx < str.length - 1 && str[idx] == str[idx + 1]) {
            int[] b = pelindromeExpansion(str, idx, idx + 1);

            if (isGreater(b, a))
                a = b;

        }

        return a;
    }

    private static int[] pelindromeExpansion(char[] arr, int left, int right) {
        int n = arr.length;

        while (left > 0 && right < n - 1 && arr[left - 1] == arr[right + 1]) {
            left--;
            right++;
        }

        return new int[] { left, right };
    }

    public static void main(String[] args) {
        System.out.println(longestPalindrome("babad"));
    }
}
