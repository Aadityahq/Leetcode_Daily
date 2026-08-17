class Solution {

    public int stoneGameV(int[] stoneValue) {

        int n = stoneValue.length;

        // 1. Prefix Sum
        int[] prefix = new int[n + 1];

        for (int i = 0; i < n; i++) {
            prefix[i + 1] = prefix[i] + stoneValue[i];
        }

        // 2. DP
        // dp[l][r] = maximum score Alice can get
        // from subarray l to r
        int[][] dp = new int[n][n];

        // Try all subarray lengths
        for (int len = 2; len <= n; len++) {

            // Starting index
            for (int l = 0; l + len - 1 < n; l++) {

                int r = l + len - 1;

                // Try every possible split
                for (int k = l; k < r; k++) {

                    // Sum of left part
                    int leftSum = prefix[k + 1] - prefix[l];

                    // Sum of right part
                    int rightSum = prefix[r + 1] - prefix[k + 1];

                    if (leftSum < rightSum) {

                        // Right part is removed.
                        // Alice keeps the left part.
                        dp[l][r] = Math.max(
                                dp[l][r],
                                leftSum + dp[l][k]
                        );

                    } else if (leftSum > rightSum) {

                        // Left part is removed.
                        // Alice keeps the right part.
                        dp[l][r] = Math.max(
                                dp[l][r],
                                rightSum + dp[k + 1][r]
                        );

                    } else {

                        // Both parts have equal sum.
                        // Alice can choose either side.

                        dp[l][r] = Math.max(
                                dp[l][r],
                                Math.max(
                                        leftSum + dp[l][k],
                                        rightSum + dp[k + 1][r]
                                )
                        );
                    }
                }
            }
        }

        return dp[0][n - 1];
    }
}