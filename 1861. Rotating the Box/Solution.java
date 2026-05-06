class Solution {
    public char[][] rotateTheBox(char[][] boxGrid) {

        int m = boxGrid.length;
        int n = boxGrid[0].length;

        // Step 1: simulate gravity towards right
        for (int i = 0; i < m; i++) {

            int empty = n - 1;

            for (int j = n - 1; j >= 0; j--) {

                // obstacle
                if (boxGrid[i][j] == '*') {
                    empty = j - 1;
                }

                // stone
                else if (boxGrid[i][j] == '#') {

                    // make current empty
                    boxGrid[i][j] = '.';

                    // place stone at empty position
                    boxGrid[i][empty] = '#';

                    empty--;
                }
            }
        }

        // Step 2: rotate matrix clockwise
        char[][] ans = new char[n][m];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                ans[j][m - 1 - i] = boxGrid[i][j];
            }
        }

        return ans;
    }
}