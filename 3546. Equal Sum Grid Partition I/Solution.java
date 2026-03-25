class Solution {
    public boolean canPartitionGrid(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        long total = 0;

        // 🔹 Calculate total sum
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                total += grid[i][j];
            }
        }

        // If total is odd → not possible
        if (total % 2 != 0) return false;

        long target = total / 2;

        // 🔹 Check horizontal cut
        long curr = 0;
        for (int i = 0; i < m - 1; i++) { // ensure bottom part is non-empty
            for (int j = 0; j < n; j++) {
                curr += grid[i][j];
            }
            if (curr == target) return true;
        }

        // 🔹 Compute column sums
        long[] colSum = new long[n];
        for (int j = 0; j < n; j++) {
            for (int i = 0; i < m; i++) {
                colSum[j] += grid[i][j];
            }
        }

        // 🔹 Check vertical cut
        curr = 0;
        for (int j = 0; j < n - 1; j++) { // ensure right part is non-empty
            curr += colSum[j];
            if (curr == target) return true;
        }

        return false;
    }
}