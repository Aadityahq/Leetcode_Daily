# LeetCode 3882: Minimum XOR Path in a Grid

Difficulty: Medium\
Topic: Dynamic Programming (DP), Bitwise XOR\
Language: Java

## 1. Understand the problem

You are given a 2D grid. You start at the top-left cell `(0, 0)` and must reach the bottom-right cell `(m - 1, n - 1)`.

You can move in only two directions:

- Right: Move to the next column.
- Down: Move to the next row.

The cost of a path is the XOR of every cell value visited, including the starting and ending cells. Your task is to find the minimum XOR value among all possible paths.

### Example

Input: `grid = [[1,2],[3,4]]`

# 1

Start

# 2

# 3

# 4

Destination

You must visit the top-left cell first and the bottom-right cell last.

There are two possible paths:

Path 1: Right, then down

\\[ 1 \oplus 2 \oplus 4 = 7 \\]

Path 2: Down, then right

\\[ 1 \oplus 3 \oplus 4 = 6 \\]

Here, \\(\oplus\\) represents the bitwise XOR operation.

The minimum of `7` and `6` is `6`.

Output: `6`

## 2. Important concept: How XOR works

XOR compares the binary bits of two numbers:

- Same bits → `0`
- Different bits → `1`

For example:

```
  1 = 001
  3 = 011
---------
1^3 = 010 = 2
```

So, `1 ^ 3 = 2`.

Remember these two useful properties:

- `a ^ a = 0`
- `a ^ 0 = a`

Important: XOR is not the same as addition. Also, choosing the smallest cell value does not necessarily produce the smallest path XOR.

## 3. Approach: Dynamic Programming

We need to consider all possible paths, but we do not need to store every complete path.

Instead, we can store all XOR values that are possible when reaching each cell.

Because every grid value is between `0` and `1023`, every XOR value will also be between `0` and `1023`. Therefore, there are only 1024 possible XOR values to track at each cell.

### Step 1: Define the DP state

```
dp[i][j][x]
```

This is `true` if it is possible to reach cell `(i, j)` with XOR value `x`. Otherwise, it is `false`.

For example, if we can reach a cell with XOR values `4` and `7`, then:

```
dp[i][j][4] = true
dp[i][j][7] = true
```

### Step 2: Initialize the starting cell

We start at `(0, 0)`, so the XOR value is simply the value of that cell.

```
dp[0][0][grid[0][0]] = true;
```

We do not initialize any other XOR value at the starting cell.

### Step 3: Process each cell

To reach a cell `(i, j)`, we can come from:

1. The cell above: `(i - 1, j)`
2. The cell on the left: `(i, j - 1)`

Suppose the current cell value is `grid[i][j]`, and a previous cell has a reachable XOR value `x`.

The new XOR value becomes:

\\[ \text{newXOR} = x \oplus grid[i][j] \\]

We mark this new value as reachable.

We repeat this process for all reachable XOR values from both directions.

### Step 4: Find the minimum XOR

After processing the grid, check the possible XOR values at the destination, starting from `0` and going up to `1023`.

The first reachable XOR value is the answer.

## 4. Java solution

```

class Solution {
    public int minXorPath(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        boolean[][][] dp = new boolean[m][n][1024];

        // Starting cell
        dp[0][0][grid[0][0]] = true;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                // Skip the starting cell
                if (i == 0 && j == 0) {
                    continue;
                }

                int value = grid[i][j];

                // Come from the cell above
                if (i > 0) {
                    for (int x = 0; x < 1024; x++) {
                        if (dp[i - 1][j][x]) {
                            dp[i][j][x ^ value] = true;
                        }
                    }
                }

                // Come from the cell on the left
                if (j > 0) {
                    for (int x = 0; x < 1024; x++) {
                        if (dp[i][j - 1][x]) {
                            dp[i][j][x ^ value] = true;
                        }
                    }
                }
            }
        }

        // Find the minimum reachable XOR
        for (int x = 0; x < 1024; x++) {
            if (dp[m - 1][n - 1][x]) {
                return x;
            }
        }

        return -1;
    }
}

```

Note: If LeetCode displays a different method signature for this problem, keep its required signature and use the same method body.

## 5. Dry run with an example

Input:

```
grid = new int[][] {
    {1, 2},
    {3, 4}
};
```

Initially, we can reach the starting cell with XOR value `1`.

### Step-by-step traversal

# 1

Cell `(0, 0)`

Starting XOR = 1

# 2

Cell `(0, 1)`

1 XOR 2 = 3

# 3

Cell `(1, 0)`

1 XOR 3 = 2

# 4

Destination `(1, 1)`

From above: 3 XOR 4 = 7

From left: 2 XOR 4 = 6

## Minimum = 6

At the destination, the DP state contains both reachable values:

```
XOR = 6  → reachable
XOR = 7  → reachable
```

The final loop checks values in increasing order. It finds `6` first and returns it.

Output:

```
6
```

## 6. Why does this solution work?

The solution works because every valid path must enter a cell either from above or from the left.

- Base case: The starting cell has exactly one possible XOR value: its own value.
- Transition: For every XOR value reachable at a previous cell, XOR it with the current cell value to get a valid XOR value at the current cell.
- Completeness: Every valid path is made up of right and down moves, so the DP considers every possible way to reach each cell.
- Minimum: At the destination, checking XOR values in ascending order returns the smallest reachable value.

We do not need to store the actual paths because we only need their XOR values.

## 7. Complexity analysis

Let \\(K = 1024\\), the number of possible XOR values.

- Time complexity: \\(O(m \times n \times K)\\), or \\(O(mn \times 1024)\\).
- Space complexity: \\(O(m \times n \times K)\\), or \\(O(mn \times 1024)\\).

Since \\(m \times n \leq 1000\\), this approach is efficient for the given constraints.

## 8. Key takeaways for interviews

1. Do not use greedy selection. The smallest cell value does not necessarily give the smallest XOR.
2. Use DP when the result depends on possible states from previous cells.
3. XOR values are bounded by `1023`, so a Boolean array can track every possible state.
4. The most important transition is:
   ```
   dp[i][j][x ^ grid[i][j]] = true;
   ```

Remember the core idea: Instead of finding the minimum XOR at every intermediate cell, track all possible XOR values. Only choose the minimum at the destination.