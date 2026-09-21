class Solution {
    public long[] resultArray(int[] nums, int k) {

        long[] result = new long[k];

        // dp[r] = number of subarrays ending at previous index
        // whose product % k == r
        long[] dp = new long[k];

        for (int num : nums) {

            long[] newDp = new long[k];

            int value = num % k;

            // 1. Start a new subarray with nums[i]
            newDp[value]++;

            // 2. Extend all previous subarrays
            for (int r = 0; r < k; r++) {

                if (dp[r] > 0) {
                    int newRemainder = (r * value) % k;

                    newDp[newRemainder] += dp[r];
                }
            }

            // Add all subarrays ending at current index
            // to the final answer
            for (int r = 0; r < k; r++) {
                result[r] += newDp[r];
            }

            dp = newDp;
        }

        return result;
    }
}