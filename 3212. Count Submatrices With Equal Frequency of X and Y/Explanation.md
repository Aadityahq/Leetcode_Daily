# 🔍 Problem Understanding

We need to count submatrices that:

1. ✅ **Include (0,0)** → top-left corner must be part of submatrix
2. ✅ **Equal number of 'X' and 'Y'**
3. ✅ **At least one 'X'**

---

# 💡 Key Observation (MOST IMPORTANT)

Since every submatrix **must include (0,0)**, that means:

👉 Every valid submatrix is of the form:

```
(0,0) → (i,j)
```

So instead of checking all submatrices ❌ (O(n⁴)),
we only check **prefix submatrices** ✅ (O(n²))

---

# ⚡ Idea

For each cell `(i, j)`:

We compute:

* count of `'X'` from `(0,0)` → `(i,j)`
* count of `'Y'` from `(0,0)` → `(i,j)`

Then check:

```
X_count == Y_count
AND X_count > 0
```

---

# 🧠 How to Compute Efficiently?

We use **2D Prefix Sum**:

Let:

* `px[i][j]` → number of 'X'
* `py[i][j]` → number of 'Y'

Formula:

```
px[i][j] = px[i-1][j] + px[i][j-1] - px[i-1][j-1] + (grid[i][j] == 'X' ? 1 : 0)
```

Same for `py`.

---

# ✅ Java Solution

```java
class Solution {
    public int numberOfSubmatrices(char[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        int[][] px = new int[m][n]; // prefix count of X
        int[][] py = new int[m][n]; // prefix count of Y

        int result = 0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                int x = (grid[i][j] == 'X') ? 1 : 0;
                int y = (grid[i][j] == 'Y') ? 1 : 0;

                px[i][j] = x;
                py[i][j] = y;

                if (i > 0) {
                    px[i][j] += px[i - 1][j];
                    py[i][j] += py[i - 1][j];
                }

                if (j > 0) {
                    px[i][j] += px[i][j - 1];
                    py[i][j] += py[i][j - 1];
                }

                if (i > 0 && j > 0) {
                    px[i][j] -= px[i - 1][j - 1];
                    py[i][j] -= py[i - 1][j - 1];
                }

                // Check condition
                if (px[i][j] == py[i][j] && px[i][j] > 0) {
                    result++;
                }
            }
        }

        return result;
    }
}
```

---

# 🧠 Why This Works

### ✅ 1. Only Top-left Submatrices

Because of constraint **must include (0,0)** → reduces complexity drastically

---

### ✅ 2. Prefix Sum = Instant Count

Instead of counting each time → we reuse previous results

---

### ✅ 3. Equal Frequency Condition

```
px[i][j] == py[i][j]
```

---

### ✅ 4. At Least One X

```
px[i][j] > 0
```

---

# ⏱️ Complexity

| Type  | Complexity |
| ----- | ---------- |
| Time  | O(m × n)   |
| Space | O(m × n)   |

---

# 🚀 Optimization (IMPORTANT FOR INTERVIEW)

You can **remove extra space** and use only variables:

```java
class Solution {
    public int numberOfSubmatrices(char[][] grid) {
        int m = grid.length, n = grid[0].length;
        
        int[][] px = new int[m][n];
        int[][] py = new int[m][n];

        int res = 0;

        for (int i = 0; i < m; i++) {
            int rowX = 0, rowY = 0;

            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 'X') rowX++;
                if (grid[i][j] == 'Y') rowY++;

                px[i][j] = rowX + (i > 0 ? px[i - 1][j] : 0);
                py[i][j] = rowY + (i > 0 ? py[i - 1][j] : 0);

                if (px[i][j] == py[i][j] && px[i][j] > 0) {
                    res++;
                }
            }
        }

        return res;
    }
}
```

---
