package other;

public final class BasicFunctions {
    public static int nextMinima(int[] arr, int fromExclusive, int toInclusive) {
        for (int i = fromExclusive + 1; i < toInclusive; i++)
            if (arr[i] < arr[i + 1])
                return i;

        return fromExclusive == toInclusive ? -1 : toInclusive;
    }

    public static int nextMinima(int[] arr, int a) {
        return nextMinima(arr, a, arr.length - 1);
    }

    public static int nextMinima(int[] arr) {
        return nextMinima(arr, -1, arr.length - 1);
    }

    public static void main(String[] args) {
        int[] arr = {};

        System.out.println(nextMinima(arr, 0));
    }
}
