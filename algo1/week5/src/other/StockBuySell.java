package other;

public final class StockBuySell {

    public static BuySellBenifit process(int[] arr) {
        int minIdx = minima(arr), maxIdx = nextMaxima(arr, minIdx);
        int profit = arr[maxIdx] - arr[minIdx];

        int l = minIdx, r = nextMaxima(arr, maxIdx);

        while (r != -1 && l < r) {
            for (int i = l; i < r; i++) {
                if (arr[r] - arr[i] > profit) {
                    profit = arr[r] - arr[i];
                    minIdx = i;
                    maxIdx = r;
                }
            }

            r = nextMaxima(arr, r);
        }

        return new BuySellBenifit(minIdx, maxIdx, profit);
    }

    public static void main(String[] args) {
        System.out.println(process(new int[] { 100, 180, 260, 310, 40, 535, 695 }));
    }

    private static int minima(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++)
            if (arr[i] < arr[i + 1])
                return i;

        return -1;
    }

    private static int nextMaxima(int[] arr, int from) {
        int lastIdx = arr.length - 1;

        for (int i = from; i < lastIdx; i++)
            if (arr[i + 1] > arr[i])
                return i + 1;

        return -1;
    }

    public static class BuySellBenifit {
        final int buyAt, sellAt, profit;

        public BuySellBenifit(int buyAt, int sellAt, int profit) {
            this.buyAt = buyAt;
            this.sellAt = sellAt;
            this.profit = profit;
        }

        @Override
        public String toString() {
            return "buyAt=" + buyAt + ", sellAt=" + sellAt + ", profit=" + profit;
        }

    }
}
