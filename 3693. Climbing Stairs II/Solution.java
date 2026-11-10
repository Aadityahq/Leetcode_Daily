
class Solution {
    public int climbStairs(int n, int[] costs) {

        int[] dp = new int[n + 1];

        // Base condition
        dp[0] = 0;

        for (int i = 1; i <= n; i++) {

            dp[i] = Integer.MAX_VALUE;

            // Try jumping 1, 2, or 3 steps
            for (int jump = 1; jump <= 3; jump++) {

                if (i - jump >= 0) {

                    int currentCost =
                        dp[i - jump] + costs[i - 1] + jump * jump;

                    dp[i] = Math.min(dp[i], currentCost);
                }
            }
        }

        return dp[n];
    }
}
