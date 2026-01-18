## 🧩 Problem Understanding

You are given an `m x n` grid of integers.

A **k × k magic square** means:

* All **row sums** are equal
* All **column sums** are equal
* Both **diagonal sums** are equal
* Numbers **do NOT need to be distinct**
* Every **1 × 1** square is automatically magic

Your task:
👉 **Return the largest possible `k`** such that **some k × k subgrid** inside the grid is a magic square.

---

## 💡 Key Observations

1. Maximum `k` is `min(m, n)`
2. Brute-forcing all sums repeatedly would be too slow
3. We can **optimize sum calculations using prefix sums**
4. We check **larger squares first** so we can return early

---

## 🧠 Strategy (How & Why)

### Why Prefix Sums?

They allow us to calculate:

* Any row sum
* Any column sum
  in **O(1)** time instead of looping every time.

---

### High-Level Steps

1. Build:

   * `rowPrefix[i][j]` → sum of row `i` from column `0` to `j-1`
   * `colPrefix[i][j]` → sum of column `j` from row `0` to `i-1`

2. Try all square sizes `k` from **largest to smallest**

3. For each `k × k` subgrid:

   * Fix the **target sum** using first row
   * Check:

     * All rows
     * All columns
     * Main diagonal
     * Anti-diagonal

4. Return first valid `k`

5. If none found → answer is `1`

---

## ✅ Java Solution

```java
class Solution {
    public int largestMagicSquare(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        // Prefix sums for rows and columns
        int[][] rowPrefix = new int[m][n + 1];
        int[][] colPrefix = new int[m + 1][n];

        // Build row prefix sums
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                rowPrefix[i][j + 1] = rowPrefix[i][j] + grid[i][j];
            }
        }

        // Build column prefix sums
        for (int j = 0; j < n; j++) {
            for (int i = 0; i < m; i++) {
                colPrefix[i + 1][j] = colPrefix[i][j] + grid[i][j];
            }
        }

        // Try square sizes from largest to smallest
        for (int k = Math.min(m, n); k >= 2; k--) {
            for (int i = 0; i + k <= m; i++) {
                for (int j = 0; j + k <= n; j++) {
                    if (isMagic(grid, rowPrefix, colPrefix, i, j, k)) {
                        return k;
                    }
                }
            }
        }

        // At least 1x1 is always magic
        return 1;
    }

    private boolean isMagic(int[][] grid, int[][] rowPrefix, int[][] colPrefix,
                            int r, int c, int k) {

        // Target sum from first row
        int target = rowPrefix[r][c + k] - rowPrefix[r][c];

        // Check all rows
        for (int i = r; i < r + k; i++) {
            int rowSum = rowPrefix[i][c + k] - rowPrefix[i][c];
            if (rowSum != target) return false;
        }

        // Check all columns
        for (int j = c; j < c + k; j++) {
            int colSum = colPrefix[r + k][j] - colPrefix[r][j];
            if (colSum != target) return false;
        }

        // Check main diagonal
        int diag1 = 0;
        for (int d = 0; d < k; d++) {
            diag1 += grid[r + d][c + d];
        }
        if (diag1 != target) return false;

        // Check anti-diagonal
        int diag2 = 0;
        for (int d = 0; d < k; d++) {
            diag2 += grid[r + d][c + k - 1 - d];
        }
        if (diag2 != target) return false;

        return true;
    }
}
```

---

## ⏱ Time & Space Complexity

### Time Complexity

* Square sizes: `O(min(m, n))`
* Positions: `O(m × n)`
* Checks per square: `O(k)`

**Worst Case:**
`O(min(m, n) × m × n × k)`
Given constraints (`≤ 50`), this easily passes.

---

### Space Complexity

* Prefix arrays: `O(m × n)`

---

## 🔍 Example Walkthrough (Example 1)

```
Grid:
7 1 4 5 6
2 5 1 6 4
1 5 4 3 2
1 2 7 3 4
```

Magic square found:

```
5 1 6
5 4 3
2 7 3
```

All rows, columns, diagonals sum to **12**, so answer = **3**

---

