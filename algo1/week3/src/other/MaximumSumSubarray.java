package other;

import static java.util.Comparator.comparingInt;

import java.util.stream.Stream;

public final class MaximumSumSubarray {
    private MaximumSumSubarray() {}

    public static Chunk bestChunk(int[] arr) {
        return bestChunk(arr, 0, arr.length - 1);
    }

    public static Chunk bestChunk(int[] arr, int from, int to) {
        if (from == to)
            return new Chunk(from, to, arr[from]);

        int mid = (from + to) >> 1;

        Chunk left = bestChunk(arr, from, mid);
        Chunk crossing = crossingChunk(arr, from, mid, to);
        Chunk right = bestChunk(arr, mid + 1, to);

        return Stream.of(left, crossing, right).max(comparingInt(Chunk::sum)).get();
    }

    private static Chunk crossingChunk(int[] arr, int from, int mid, int to) {
        int leftSum = 0;
        int leftIndex = mid;

        for (int i = mid, sumSoFar = 0; i >= from; i--) {
            sumSoFar += arr[i];

            if (leftSum < sumSoFar) {
                leftSum = sumSoFar;
                leftIndex = i;
            }
        }

        int rightSum = 0;
        int rightIndex = mid + 1;

        for (int i = mid + 1, sumSoFar = 0; i <= to; i++) {
            sumSoFar += arr[i];

            if (rightSum < sumSoFar) {
                rightIndex = i;
                rightSum = sumSoFar;
            }

        }

        return new Chunk(leftIndex, rightIndex, leftSum + rightSum);
    }

    public final static class Chunk {
        private final int fromIdx, toIdx, sum;

        private Chunk(int fromIdx, int toIdx, int sum) {
            this.fromIdx = fromIdx;
            this.toIdx = toIdx;
            this.sum = sum;
        }

        public int sum() {
            return sum;
        }

        public int leftIndex() {
            return fromIdx;
        }

        public int rightIndex() {
            return toIdx;
        }

        @Override
        public String toString() {
            return "from=" + fromIdx + ", to=" + toIdx + ", sum=" + sum;
        }
    }

    public static void main(String[] args) {
        int[] arr = { -2, -3, 4, -1, -2, 1, 5, -3 };
        System.out.println(bestChunk(arr));
    }
}
