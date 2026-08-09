class Solution {
    private int[][] memo;
    private int[] suffix;
    private int n;

    public int stoneGameII(int[] piles) {
        n = piles.length;

        // suffix[i] = total stones from i to end
        suffix = new int[n + 1];
        for (int i = n - 1; i >= 0; i--) {
            suffix[i] = suffix[i + 1] + piles[i];
        }

        memo = new int[n][n + 1];
        return dfs(0, 1);
    }

    private int dfs(int i, int m) {
        // no piles left
        if (i >= n) return 0;

        // if we can take all remaining piles
        if (i + 2 * m >= n) {
            return suffix[i];
        }

        if (memo[i][m] != 0) {
            return memo[i][m];
        }

        int best = 0;

        // try taking X piles (1 to 2M)
        for (int x = 1; x <= 2 * m; x++) {
            int opponent = dfs(i + x, Math.max(m, x));
            best = Math.max(best, suffix[i] - opponent);
        }

        return memo[i][m] = best;
    }
}