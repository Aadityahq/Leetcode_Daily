class Solution {
    public int largestMagicSquare(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        // Prefix sums for rows and columns
        int[][] rowPrefix = new int[m][n + 1];
        int[][] colPrefix = new int[m + 1][n];

        // Build row prefix sums
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                rowPrefix[i][j + 1] = rowPrefix[i][j] + grid[i][j];
            }
        }

        // Build column prefix sums
        for (int j = 0; j < n; j++) {
            for (int i = 0; i < m; i++) {
                colPrefix[i + 1][j] = colPrefix[i][j] + grid[i][j];
            }
        }

        // Try square sizes from largest to smallest
        for (int k = Math.min(m, n); k >= 2; k--) {
            for (int i = 0; i + k <= m; i++) {
                for (int j = 0; j + k <= n; j++) {
                    if (isMagic(grid, rowPrefix, colPrefix, i, j, k)) {
                        return k;
                    }
                }
            }
        }

        // At least 1x1 is always magic
        return 1;
    }

    private boolean isMagic(int[][] grid, int[][] rowPrefix, int[][] colPrefix,
                            int r, int c, int k) {

        // Target sum from first row
        int target = rowPrefix[r][c + k] - rowPrefix[r][c];

        // Check all rows
        for (int i = r; i < r + k; i++) {
            int rowSum = rowPrefix[i][c + k] - rowPrefix[i][c];
            if (rowSum != target) return false;
        }

        // Check all columns
        for (int j = c; j < c + k; j++) {
            int colSum = colPrefix[r + k][j] - colPrefix[r][j];
            if (colSum != target) return false;
        }

        // Check main diagonal
        int diag1 = 0;
        for (int d = 0; d < k; d++) {
            diag1 += grid[r + d][c + d];
        }
        if (diag1 != target) return false;

        // Check anti-diagonal
        int diag2 = 0;
        for (int d = 0; d < k; d++) {
            diag2 += grid[r + d][c + k - 1 - d];
        }
        if (diag2 != target) return false;

        return true;
    }
}
