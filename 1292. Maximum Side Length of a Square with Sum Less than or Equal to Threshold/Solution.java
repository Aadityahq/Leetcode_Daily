class Solution {
    public int maxSideLength(int[][] mat, int threshold) {
        int m = mat.length;
        int n = mat[0].length;

        // Step 1: Build prefix sum matrix
        int[][] pre = new int[m + 1][n + 1];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                pre[i + 1][j + 1] =
                        mat[i][j]
                        + pre[i][j + 1]
                        + pre[i + 1][j]
                        - pre[i][j];
            }
        }

        // Step 2: Binary search on side length
        int left = 0, right = Math.min(m, n);

        while (left < right) {
            int mid = (left + right + 1) / 2;

            if (canFormSquare(pre, mat, threshold, mid)) {
                left = mid;      // try bigger square
            } else {
                right = mid - 1; // reduce size
            }
        }

        return left;
    }

    // Helper function to check if any k x k square is valid
    private boolean canFormSquare(int[][] pre, int[][] mat, int threshold, int k) {
        int m = mat.length;
        int n = mat[0].length;

        for (int i = 0; i <= m - k; i++) {
            for (int j = 0; j <= n - k; j++) {
                int sum =
                        pre[i + k][j + k]
                        - pre[i][j + k]
                        - pre[i + k][j]
                        + pre[i][j];

                if (sum <= threshold) {
                    return true;
                }
            }
        }
        return false;
    }
}
