import java.util.ArrayDeque;
import java.util.Deque;

class Solution {
    public int countIslands(int[][] grid, int k) {
        int m = grid.length;
        int n = grid[0].length;

        boolean[][] visited = new boolean[m][n];
        int islands = 0;

        int[][] directions = {
            {1, 0},   // down
            {-1, 0},  // up
            {0, 1},   // right
            {0, -1}   // left
        };

        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                // Start exploring a new island.
                if (grid[r][c] > 0 && !visited[r][c]) {
                    long islandSum = 0;
                    Deque<int[]> stack = new ArrayDeque<>();

                    stack.push(new int[]{r, c});
                    visited[r][c] = true;

                    // Explore every cell in this island.
                    while (!stack.isEmpty()) {
                        int[] cell = stack.pop();
                        int row = cell[0];
                        int col = cell[1];

                        islandSum += grid[row][col];

                        for (int[] dir : directions) {
                            int newRow = row + dir[0];
                            int newCol = col + dir[1];

                            if (newRow >= 0 && newRow < m &&
                                newCol >= 0 && newCol < n &&
                                grid[newRow][newCol] > 0 &&
                                !visited[newRow][newCol]) {

                                visited[newRow][newCol] = true;
                                stack.push(new int[]{newRow, newCol});
                            }
                        }
                    }

                    // Count this island if its sum is divisible by k.
                    if (islandSum % k == 0) {
                        islands++;
                    }
                }
            }
        }

        return islands;
    }
}