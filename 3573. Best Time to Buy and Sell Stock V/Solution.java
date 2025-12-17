class Solution {
    public long maximumProfit(int[] prices, int k) {
        long NEG = Long.MIN_VALUE / 4;

        long[] free = new long[k + 1];
        long[] holdLong = new long[k + 1];
        long[] holdShort = new long[k + 1];

        for (int t = 0; t <= k; t++) {
            free[t] = 0;
            holdLong[t] = NEG;
            holdShort[t] = NEG;
        }

        for (int price : prices) {
            long[] newFree = new long[k + 1];
            long[] newLong = new long[k + 1];
            long[] newShort = new long[k + 1];

            for (int t = 0; t <= k; t++) {
                // stay free
                newFree[t] = free[t];

                // close positions → consume transaction
                if (t > 0) {
                    newFree[t] = Math.max(newFree[t], holdLong[t - 1] + price);
                    newFree[t] = Math.max(newFree[t], holdShort[t - 1] - price);
                }

                // open or keep long
                newLong[t] = Math.max(holdLong[t], free[t] - price);

                // open or keep short
                newShort[t] = Math.max(holdShort[t], free[t] + price);
            }

            free = newFree;
            holdLong = newLong;
            holdShort = newShort;
        }

        long ans = 0;
        for (int t = 0; t <= k; t++) {
            ans = Math.max(ans, free[t]);
        }
        return ans;
    }
}
