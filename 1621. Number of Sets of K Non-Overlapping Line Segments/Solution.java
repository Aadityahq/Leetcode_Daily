class Solution {

    static final long MOD = 1_000_000_007;

    public int numberOfSets(int n, int k) {

        int max = n + k - 1;

        long[][] dp = new long[max + 1][2 * k + 1];

        // C(i, 0) = 1
        for (int i = 0; i <= max; i++) {
            dp[i][0] = 1;
        }

        // Build Pascal's Triangle
        for (int i = 1; i <= max; i++) {
            for (int j = 1; j <= Math.min(i, 2 * k); j++) {
                dp[i][j] = (dp[i - 1][j - 1] + dp[i - 1][j]) % MOD;
            }
        }

        return (int) dp[max][2 * k];
    }
}