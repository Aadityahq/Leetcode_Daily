class Solution {

    public boolean hasValidPath(char[][] grid) {

        int m = grid.length;
        int n = grid[0].length;

        // Total number of cells in every possible path
        int length = m + n - 1;

        // A valid parentheses string must have even length
        if (length % 2 != 0) {
            return false;
        }

        // Maximum possible balance is length
        boolean[][][] dp = new boolean[m][n][length + 1];

        // Starting cell
        int startBalance = grid[0][0] == '(' ? 1 : -1;

        // If first character is ')', it is already invalid
        if (startBalance < 0) {
            return false;
        }

        dp[0][0][startBalance] = true;

        // Traverse the grid
        for (int i = 0; i < m; i++) {

            for (int j = 0; j < n; j++) {

                // Skip starting cell
                if (i == 0 && j == 0) {
                    continue;
                }

                // Current cell contribution
                int change = grid[i][j] == '(' ? 1 : -1;

                for (int balance = 0; balance <= length; balance++) {

                    int newBalance = balance + change;

                    // Balance cannot become negative
                    if (newBalance < 0 || newBalance > length) {
                        continue;
                    }

                    // Come from the top
                    if (i > 0 && dp[i - 1][j][balance]) {
                        dp[i][j][newBalance] = true;
                    }

                    // Come from the left
                    if (j > 0 && dp[i][j - 1][balance]) {
                        dp[i][j][newBalance] = true;
                    }
                }
            }
        }

        // Valid parentheses string must end with balance 0
        return dp[m - 1][n - 1][0];
    }
}