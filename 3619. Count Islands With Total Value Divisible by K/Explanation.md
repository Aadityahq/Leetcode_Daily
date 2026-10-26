# 3619. Count Islands With Total Value Divisible by K

**Difficulty:** Medium  
**Topics:** Depth-First Search (DFS), Matrix, Graph

## Problem Explanation

You are given an `m x n` grid and a positive integer `k`.

- A positive integer represents land.
- `0` represents water.
- An island is a group of positive cells connected horizontally or vertically.
- Diagonal connections do not count.
- The total value of an island is the sum of all its cells.

Return the number of islands whose total value is divisible by `k`.

In simple words, find every island, calculate its sum, and check whether `sum % k == 0`.

## Java Solution

```java
class Solution {
    public int countIslands(int[][] grid, int k) {
        int m = grid.length;
        int n = grid[0].length;

        boolean[][] visited = new boolean[m][n];
        int count = 0;

        int[][] directions = {
            {1, 0},   // Down
            {-1, 0},  // Up
            {0, 1},   // Right
            {0, -1}   // Left
        };

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                if (grid[i][j] > 0 && !visited[i][j]) {
                    long sum = 0;
                    java.util.Stack<int[]> stack = new java.util.Stack<>();

                    stack.push(new int[]{i, j});
                    visited[i][j] = true;

                    while (!stack.isEmpty()) {
                        int[] cell = stack.pop();

                        int row = cell[0];
                        int col = cell[1];

                        sum += grid[row][col];

                        for (int[] dir : directions) {
                            int nr = row + dir[0];
                            int nc = col + dir[1];

                            if (nr >= 0 && nr < m &&
                                nc >= 0 && nc < n &&
                                grid[nr][nc] > 0 &&
                                !visited[nr][nc]) {

                                visited[nr][nc] = true;
                                stack.push(new int[]{nr, nc});
                            }
                        }
                    }

                    if (sum % k == 0) {
                        count++;
                    }
                }
            }
        }

        return count;
    }
}
```

## Approach: DFS (Depth-First Search)

We use DFS to explore all the cells belonging to an island.

### Step 1: Traverse the Grid

Use two nested loops to visit every cell.

If a cell is positive and has not been visited, it is the starting point of a new island.

### Step 2: Explore the Island

Use a stack to perform DFS.

- Mark the starting cell as visited.
- Remove a cell from the stack.
- Add its value to the island sum.
- Check its four neighbours.
- Add every valid, positive, unvisited neighbour to the stack.

Continue until the stack becomes empty. At this point, the complete island has been explored.

### Step 3: Check Divisibility

After exploring the island, check:

```java
if (sum % k == 0) {
    count++;
}
```

If the remainder is zero, the island's total is divisible by `k`. Therefore, increment the count.

### Step 4: Return the Answer

After traversing the entire grid, return `count`.

## Example Walkthrough

**Input:**

```text
grid = [
    [3, 0, 3, 0],
    [0, 3, 0, 3],
    [3, 0, 3, 0]
]
k = 3
```

Each positive cell is a separate island because the cells are connected only diagonally.

| Island | Sum | Sum % 3 |
|---|---:|---:|
| 1 | 3 | 0 |
| 2 | 3 | 0 |
| 3 | 3 | 0 |
| 4 | 3 | 0 |
| 5 | 3 | 0 |
| 6 | 3 | 0 |

Every island has a sum divisible by `3`.

**Output:**

```text
6
```

## Why Do We Use a `visited` Array?

Without a `visited` array, DFS could process the same cell multiple times.

The array ensures that:
- Each cell is processed at most once.
- Cells are not added repeatedly to the stack.
- Each island is counted only once.

## Why Only Four Directions?

The problem allows connections only in four directions:

- Up: `(row - 1, col)`
- Down: `(row + 1, col)`
- Left: `(row, col - 1)`
- Right: `(row, col + 1)`

Diagonal connections are not allowed.

## Complexity Analysis

Let `m` be the number of rows and `n` be the number of columns.

- **Time Complexity:** `O(m × n)` because every cell is visited at most once, and each cell has four neighbours to check.
- **Space Complexity:** `O(m × n)` for the `visited` array and the DFS stack in the worst case.

## Key Takeaways

1. Use DFS to find connected groups of positive cells.
2. Calculate the sum of every island.
3. Use `sum % k == 0` to check divisibility.
4. Count each qualifying island once.
5. Remember that diagonal cells are not connected.

**Final Approach:** DFS + Visited Array + Island Sum Calculation.