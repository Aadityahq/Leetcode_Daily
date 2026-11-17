
class Solution {

    public int minimumChanges(String s, int k) {

        int n = s.length();

        int[][] cost = new int[n][n];

        // Step 1: Precompute substring costs
        for (int l = 0; l < n; l++) {

            for (int r = l + 1; r < n; r++) {

                int len = r - l + 1;
                int minCost = Integer.MAX_VALUE;

                // Try every proper divisor
                for (int d = 1; d < len; d++) {

                    if (len % d != 0) {
                        continue;
                    }

                    int changes = 0;

                    // Check all groups
                    for (int start = 0; start < d; start++) {

                        int left = l + start;

                        // Corrected right pointer
                        int right = l + start
                                + ((len - 1 - start) / d) * d;

                        while (left < right) {

                            if (s.charAt(left) != s.charAt(right)) {
                                changes++;
                            }

                            left += d;
                            right -= d;
                        }
                    }

                    minCost = Math.min(minCost, changes);
                }

                cost[l][r] = minCost;
            }
        }

        // Step 2: Dynamic Programming
        int INF = 1_000_000_000;

        int[][] dp = new int[n + 1][k + 1];

        for (int[] row : dp) {
            java.util.Arrays.fill(row, INF);
        }

        dp[0][0] = 0;

        for (int i = 1; i <= n; i++) {

            for (int j = 1; j <= k; j++) {

                for (int p = 0; p < i; p++) {

                    if (i - p < 2) {
                        continue;
                    }

                    if (dp[p][j - 1] == INF) {
                        continue;
                    }

                    dp[i][j] = Math.min(
                        dp[i][j],
                        dp[p][j - 1] + cost[p][i - 1]
                    );
                }
            }
        }

        return dp[n][k];
    }
}
