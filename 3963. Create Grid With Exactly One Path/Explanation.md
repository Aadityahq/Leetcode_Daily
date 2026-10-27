# LeetCode 3963: Create Grid With Exactly One Path

Difficulty: Easy\
Topic: Greedy / Grid Construction\
Language: Java

## 1. Understanding the problem

You are given two integers, `m` and `n`, representing the number of rows and columns in a grid.

You need to create an `m × n` grid containing only two characters:

- `'.'` means the cell is free.
- `'#'` means the cell is blocked.

The path must follow three rules:

1. Start at the top-left cell `(0, 0)`.
2. Reach the bottom-right cell `(m - 1, n - 1)`.
3. Move only right or down.

Your task is to construct a grid in which exactly one valid path exists from the start to the destination.

You can return any grid that satisfies these conditions.

## 2. Approach: How can we create exactly one path?

The simplest idea is to create one path along the top row and the rightmost column.

- Make every cell in the top row `'.'`.
- Make every cell in the rightmost column `'.'`.
- Make all other cells `'#'`.

For example, if `m = 3` and `n = 4`, the grid will look like this:

## .

## .

## .

## .

## \#

## \#

## \#

## .

## \#

## \#

## \#

## .

Green cells are free; dark cells are obstacles.

The only valid path is:

`(0,0) → (0,1) → (0,2) → (0,3) → (1,3) → (2,3)`

Why is there exactly one path?

- From the top row, we can move right.
- To reach the bottom-right cell, we then move down along the last column.
- Every other cell is blocked, so no alternative route is possible.

This construction works even when `m = 1` or `n = 1`.

## 3. Java solution

```

class Solution {
    public String[] specialGrid(int m, int n) {
        char[][] grid = new char[m][n];

        // Fill every cell with an obstacle
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = '#';
            }
        }

        // Make the entire top row free
        for (int j = 0; j < n; j++) {
            grid[0][j] = '.';
        }

        // Make the rightmost column free
        for (int i = 0; i < m; i++) {
            grid[i][n - 1] = '.';
        }

        // Convert each row into a String
        String[] result = new String[m];

        for (int i = 0; i < m; i++) {
            result[i] = new String(grid[i]);
        }

        return result;
    }
}

```

## 4. Dry run with an example

Input: `m = 3, n = 4`

Step 1: Fill the entire grid with `'#'`.

```
####
####
####
```

Step 2: Make the top row free.

```
....
####
####
```

Step 3: Make the rightmost column free.

```
....
###.
###.
```

Step 4: Convert each row into a string and return the array.

Output:

```
["....", "###.", "###."]
```

The unique path is:

### S

### →

### →

### ↓

### \#

### \#

### \#

### ↓

### \#

### \#

### \#

### E

S = Start, E = End, arrows = path, # = blocked cell.

## 5. How and why does the code work?

Let's understand the important parts.

A. Initialize the grid

```
char[][] grid = new char[m][n];
```

This creates a two-dimensional character array with `m` rows and `n` columns.

B. Block every cell

```
for (int i = 0; i < m; i++) {
    for (int j = 0; j < n; j++) {
        grid[i][j] = '#';
    }
}
```

We start by blocking everything. This prevents unwanted paths from existing.

C. Open the top row

```
for (int j = 0; j < n; j++) {
    grid[0][j] = '.';
}
```

Here, `grid[0][j]` means a cell in the first row. We make all these cells free so we can move right.

D. Open the rightmost column

```
for (int i = 0; i < m; i++) {
    grid[i][n - 1] = '.';
}
```

The last column's index is `n - 1` because array indexing starts at zero. We open this column so we can move down to the destination.

E. Convert rows into strings

```
String[] result = new String[m];

for (int i = 0; i < m; i++) {
    result[i] = new String(grid[i]);
}
```

The problem expects an array of strings, not a `char[][]`. So we convert every row into a `String`.

## 6. Complexity analysis

- Time complexity: \\(O(m \times n)\\), because we initialize every cell and traverse the rows and columns.
- Auxiliary space complexity: \\(O(m \times n)\\), for the character grid and the resulting strings.

## 7. Key takeaway

The main trick is that we do not need to search for a path; we construct the grid so that only one path can exist.

By opening only the top row and rightmost column, and blocking every other cell, we guarantee exactly one valid path.