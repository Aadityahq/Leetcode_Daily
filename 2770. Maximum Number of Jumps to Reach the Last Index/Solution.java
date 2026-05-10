class Solution {
    public int maximumJumps(int[] nums, int target) {
        int n = nums.length;

        int[] dp = new int[n];

        // Mark all indices unreachable initially
        for (int i = 0; i < n; i++) {
            dp[i] = -1;
        }

        // Starting index
        dp[0] = 0;

        // Try to reach every index
        for (int i = 1; i < n; i++) {

            // Check all previous indices
            for (int j = 0; j < i; j++) {

                // Jump condition
                if (dp[j] != -1 &&
                    Math.abs(nums[i] - nums[j]) <= target) {

                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
        }

        return dp[n - 1];
    }
}