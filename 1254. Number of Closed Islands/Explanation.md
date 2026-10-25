# 1254. Number of Closed Islands

**Difficulty:** Medium  
**Topics:** Depth-First Search (DFS), Breadth-First Search (BFS), Matrix

## Approach: DFS (Depth-First Search)

### Intuition

We are given a grid where:

- `0` represents **land**.
- `1` represents **water**.
- An island is a group of connected `0`s in four directions: up, down, left, and right.
- A closed island is an island that does not touch the boundary of the grid.

**Key idea:** First, explore every island connected to the boundary and mark it as visited. These islands cannot be closed. Then count the remaining connected groups of `0`s.

### Java Solution

```java
class Solution {
    private int rows, cols;

    private void dfs(int[][] grid, int r, int c) {
        if (r < 0 || c < 0 || r >= rows || c >= cols
                || grid[r][c] == 1) {
            return;
        }

        // Mark the land as visited
        grid[r][c] = 1;

        dfs(grid, r + 1, c); // Down
        dfs(grid, r - 1, c); // Up
        dfs(grid, r, c + 1); // Right
        dfs(grid, r, c - 1); // Left
    }

    public int closedIsland(int[][] grid) {
        rows = grid.length;
        cols = grid[0].length;

        // Remove all land connected to the boundary
        for (int r = 0; r < rows; r++) {
            dfs(grid, r, 0);
            dfs(grid, r, cols - 1);
        }

        for (int c = 0; c < cols; c++) {
            dfs(grid, 0, c);
            dfs(grid, rows - 1, c);
        }

        int count = 0;

        // Count the remaining islands
        for (int r = 1; r < rows - 1; r++) {
            for (int c = 1; c < cols - 1; c++) {
                if (grid[r][c] == 0) {
                    count++;
                    dfs(grid, r, c);
                }
            }
        }

        return count;
    }
}
```

### Dry Run

Consider Example 2:

```text
grid = [
    [0, 0, 1, 0, 0],
    [0, 1, 0, 1, 0],
    [0, 1, 1, 1, 0]
]
```

**Step 1:** Start DFS from every boundary cell containing `0`. All boundary-connected land is marked as `1`.

**Step 2:** Scan the inner cells. The land at `(1, 2)` is not connected to the boundary and forms one closed island.

**Step 3:** No other closed islands remain.

**Output:** `1`

### Complexity Analysis

- **Time Complexity:** \(O(m \times n)\), because each cell is visited at most a constant number of times.
- **Space Complexity:** \(O(m \times n)\) in the worst case due to the recursive DFS call stack.

Here, `m` is the number of rows and `n` is the number of columns.

### Important Note

The DFS changes visited land cells from `0` to `1`. This is intentional and avoids needing a separate visited array.

The crucial observation is that **any island touching the boundary cannot be closed**. By removing all such islands first, we only need to count the remaining islands.