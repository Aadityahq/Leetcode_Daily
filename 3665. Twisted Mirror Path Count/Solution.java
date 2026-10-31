
class Solution {
    public int uniquePaths(int[][] grid) {

        int m = grid.length;
        int n = grid[0].length;

        int MOD = 1_000_000_007;

        int[][] right = new int[m][n];
        int[][] down = new int[m][n];

        // Robot starts at (0, 0)
        right[0][0] = 1;

        // Process the grid row by row
        for (int i = 0; i < m; i++) {

            for (int j = 0; j < n; j++) {

                // Case 1: Empty cell
                if (grid[i][j] == 0) {

                    int ways = (int) (
                        ((long) right[i][j] + down[i][j]) % MOD
                    );

                    // Move RIGHT
                    if (j + 1 < n) {
                        right[i][j + 1] = (int) (
                            ((long) right[i][j + 1] + ways) % MOD
                        );
                    }

                    // Move DOWN
                    if (i + 1 < m) {
                        down[i + 1][j] = (int) (
                            ((long) down[i + 1][j] + ways) % MOD
                        );
                    }

                }

                // Case 2: Mirror cell
                else {

                    // Entered from LEFT (moving RIGHT)
                    // Reflection changes direction to DOWN
                    if (i + 1 < m) {
                        down[i + 1][j] = (int) (
                            ((long) down[i + 1][j] + right[i][j]) % MOD
                        );
                    }

                    // Entered from ABOVE (moving DOWN)
                    // Reflection changes direction to RIGHT
                    if (j + 1 < n) {
                        right[i][j + 1] = (int) (
                            ((long) right[i][j + 1] + down[i][j]) % MOD
                        );
                    }
                }
            }
        }

        // Total ways to reach the destination
        return (int) (
            ((long) right[m - 1][n - 1]
            + down[m - 1][n - 1]) % MOD
        );
    }
}
