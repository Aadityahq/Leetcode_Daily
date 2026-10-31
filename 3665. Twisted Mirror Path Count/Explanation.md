# LeetCode 3665 – Twisted Mirror Path Count



Medium

Dynamic Programming

2D DP



Let's understand this problem from the basics, then develop the logic step by step and implement it in Java.

## 1. Understanding the problem

We are given a binary grid containing `0` and `1`.

- `0` means an empty cell.
- `1` means a mirror.

Our robot starts at `(0, 0)` and needs to reach `(m-1, n-1)`.

The robot can normally move in only two directions:

1. Right `(i, j+1)`
2. Down `(i+1, j)`

However, there is a twist: when the robot tries to enter a mirror, its direction changes.

### Mirror rules

There are two possible situations:

Case 1: Robot moves RIGHT into a mirror

The robot gets reflected DOWN and enters the cell directly below the mirror.

Right → Mirror → Down

Case 2: Robot moves DOWN into a mirror

The robot gets reflected RIGHT and enters the cell directly to the right of the mirror.

Down → Mirror → Right

Important: If the robot gets reflected into another mirror, the same process happens again immediately.

If the robot goes outside the grid, that path is invalid.

## 2. What approach should we use?

We can solve this problem using Dynamic Programming (DP).

But before writing the code, we need to understand one important thing.

### Why can't we use a simple 2D DP array?

In a normal unique-path problem, we use:

```
dp[i][j] = dp[i-1][j] + dp[i][j-1];
```

This works because we only need to know how many ways we can reach a particular cell.

But here, the direction in which we enter a cell matters.

Consider a mirror:

- Entering from the left means moving RIGHT, so the robot moves DOWN.
- Entering from above means moving DOWN, so the robot moves RIGHT.

Therefore, we cannot combine both incoming directions at a mirror.

Our solution: Maintain two DP arrays instead of one.

## 3. DP states

We will create two 2D arrays:

```
right[i][j]
down[i][j]
```

Their meanings are:

| DP state      | Meaning                                                  |
| ------------- | -------------------------------------------------------- |
| `right[i][j]` | Number of ways to enter cell `(i,j)` while moving RIGHT. |
| `down[i][j]`  | Number of ways to enter cell `(i,j)` while moving DOWN.  |

Why do we need two arrays?

Because a mirror behaves differently depending on the incoming direction.

For example:

When the current cell is a mirror (1)

RIGHT

Incoming direction

DOWN

Outgoing direction

DOWN

Incoming direction

RIGHT

Outgoing direction

## 4. Understanding the transitions

Now let's understand the most important part.

We have two situations depending on whether the current cell is empty or a mirror.

### Case A: Current cell is empty (0)

If the robot reaches an empty cell, it can choose either direction.

The total number of ways reaching this cell is:

\\[ ways = right[i][j] + down[i][j] \\]

From this cell, we can move in two directions.

Move RIGHT:

\\[ right[i][j+1] += ways \\]

Move DOWN:

\\[ down[i+1][j] += ways \\]

We must check the grid boundaries before updating.

### Case B: Current cell is a mirror (1)

Here, we cannot combine the two DP values.

Instead, we process them separately.

If the robot entered moving RIGHT:

It gets reflected DOWN.

\\[ down[i+1][j] += right[i][j] \\]

If the robot entered moving DOWN:

It gets reflected RIGHT.

\\[ right[i][j+1] += down[i][j] \\]

This automatically handles consecutive mirrors because the reflected movement reaches the next cell with its new direction.

## 5. Java Solution

```

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

```

## 6. Dry Run with Example 1

Input:

```
grid = {
    {0, 1, 0},
    {0, 0, 1},
    {1, 0, 0}
};
```

We have a `3 × 3` grid.

Initially:

```
right[0][0] = 1;
```

All other values are zero.

Let's process the cells.

Step 1: Cell (0,0)

It is an empty cell.

We have:

```
ways = 1
```

We can move right or down.

```
right[0][1] = 1
down[1][0] = 1
```

Step 2: Cell (0,1)

It is a mirror.

We entered it while moving RIGHT.

Therefore, the robot must move DOWN.

```
down[1][1] = 1
```

Step 3: Cell (0,2)

It is an empty cell, but no path reaches it.

So we do nothing.

Step 4: Cell (1,0)

It is empty.

We have:

```
down[1][0] = 1
```

We can move right or down.

```
right[1][1] = 1
down[2][0] = 1
```

Step 5: Cell (1,1)

It is empty.

Two paths reach this cell:

```
right[1][1] = 1
down[1][1] = 1
```

Therefore:

```
ways = 1 + 1 = 2
```

We can move right or down.

```
right[1][2] = 2
down[2][1] = 2
```

Step 6: Cell (1,2)

It is a mirror.

We entered it while moving RIGHT.

Therefore, the robot moves DOWN.

```
down[2][2] = 2
```

Step 7: Cell (2,0)

It is a mirror.

We entered it while moving DOWN.

Therefore, the robot moves RIGHT.

```
right[2][1] = 1
```

Step 8: Cell (2,1)

It is empty.

Two directions bring paths here:

```
right[2][1] = 1
down[2][1] = 2
```

Total:

```
ways = 1 + 2 = 3
```

It moves right:

```
right[2][2] = 3
```

Step 9: Destination (2,2)

It is an empty cell.

We have:

```
right[2][2] = 3
down[2][2] = 2
```

Therefore:

\\[ Answer = 3 + 2 = \boxed{5} \\]

Output: 5

## 7. Why do we process row by row?

This is another important concept.

We use nested loops:

```
for (int i = 0; i < m; i++) {
    for (int j = 0; j < n; j++) {
        // Process current cell
    }
}
```

Why does this work?

Because the robot can only move:

- Right: `(i, j+1)`
- Down: `(i+1, j)`

It can never move backward.

Therefore, every cell receives its contributions from:

- The cell on its left.
- The cell above it.

Both are processed before the current cell in row-major order.

Even mirror reflections only move right or down, so the same processing order works.

## 8. Complexity Analysis

Let `m` be the number of rows and `n` be the number of columns.

| Complexity | Explanation         |
| ---------- | ------------------- |
| Time       | \\(O(m \times n)\\) |
| Space      | \\(O(m \times n)\\) |

Time Complexity: We visit every grid cell exactly once and perform constant-time operations.

Space Complexity: We maintain two 2D DP arrays, each of size `m × n`.

Given the constraints of `m, n ≤ 500`, this solution is efficient.

## 9. Important concepts to remember

| Concept             | What you learned                                                      |
| ------------------- | --------------------------------------------------------------------- |
| Dynamic Programming | Store previously calculated path counts.                              |
| 2D DP               | Maintain information for every grid cell.                             |
| Directional DP      | Track the direction in which a cell is entered.                       |
| State transition    | Change the next state based on whether the cell is empty or a mirror. |
| Modulo              | Prevent the answer from becoming too large.                           |

The most important takeaway:

In normal grid DP, we usually track only the number of ways to reach a cell.

But in this problem, the incoming direction changes the next movement. Therefore, we maintain two states: `right[][]` and `down[][]`.

That is the main observation that makes this problem solvable.

## 10. Test your understanding

Before moving to the next problem, try answering these three questions.

Quick knowledge check

1/3 answered

1\. Why do we need two DP arrays instead of one?

To reduce time complexity

Because mirror behavior depends on incoming direction

Because the grid has two dimensions

2\. What happens when the robot enters a mirror while moving RIGHT?

It moves LEFT

It moves UP

It gets reflected DOWN

3\. Why can we process the grid row by row?

Because movement is only RIGHT or DOWN

Because mirrors cannot repeat

Because all cells are empty

Check answers