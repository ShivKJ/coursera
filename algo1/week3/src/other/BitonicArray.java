package other;

import static java.util.Arrays.binarySearch;

public final class BitonicArray {
    private BitonicArray() {}

    public static int search(int[] arr, int k) {
        return search(arr, k, 0, arr.length - 1);
    }

    public static int search(int[] arr, int k, int from, int to) {

        if (from > to)// base case
            return -1;

        int mid = (from + to) >> 1;
        int e = arr[mid];

        if (k < e) {
            if (arr[mid - 1] < arr[mid]) {

                int index = binarySearch(arr, from, mid, k);// mid is exclusive here

                if (index > -1)
                    return index;

                return search(arr, k, mid + 1, to);
            } else {
                int index = reverseBinarySearch(arr, k, mid + 1, to);

                if (index > -1)
                    return index;

                return search(arr, k, from, mid - 1);
            }

        } else if (k > e) {
            if (arr[mid - 1] < arr[mid])
                return search(arr, k, mid + 1, to);

            return search(arr, k, from, mid - 1);
        } else
            return mid;

    }

    public static int reverseBinarySearch(int[] arr, int k, int lo, int high) {

        while (lo <= high) {
            int mid = (lo + high) >> 1;

            int e = arr[mid];

            if (e < k)
                high = mid - 1;
            else if (e > k)
                lo = mid + 1;
            else
                return mid;
        }

        return -1;
    }

}
