# 🧠 Key Idea (Why normal DP fails?)

If all numbers were positive → just keep max product.

But here:

* Negative × Negative = **Positive**
* Positive × Negative = **Negative**

👉 So at every cell, we must track:

* **Maximum product till here**
* **Minimum product till here** (important because it can become max later)

---

# 🔥 DP State

Let:

```
maxDp[i][j] = maximum product reaching (i, j)
minDp[i][j] = minimum product reaching (i, j)
```

---

# ⚙️ Transition (Core Logic)

From top `(i-1, j)` and left `(i, j-1)`:

We consider **4 possibilities**:

```
grid[i][j] * maxDp[i-1][j]
grid[i][j] * minDp[i-1][j]
grid[i][j] * maxDp[i][j-1]
grid[i][j] * minDp[i][j-1]
```

Then:

```
maxDp[i][j] = max(all 4)
minDp[i][j] = min(all 4)
```

---

# 🚧 Base Case

```
maxDp[0][0] = grid[0][0]
minDp[0][0] = grid[0][0]
```

---

# 🎯 Final Answer

At `(m-1, n-1)`:

* If `maxDp[m-1][n-1] < 0 → return -1`
* Else return `maxDp % (1e9+7)`

---

# 💻 Java Solution

```java
class Solution {
    public int maxProductPath(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        
        long[][] maxDp = new long[m][n];
        long[][] minDp = new long[m][n];
        
        maxDp[0][0] = grid[0][0];
        minDp[0][0] = grid[0][0];
        
        // First column
        for (int i = 1; i < m; i++) {
            maxDp[i][0] = maxDp[i - 1][0] * grid[i][0];
            minDp[i][0] = maxDp[i][0];
        }
        
        // First row
        for (int j = 1; j < n; j++) {
            maxDp[0][j] = maxDp[0][j - 1] * grid[0][j];
            minDp[0][j] = maxDp[0][j];
        }
        
        // Fill DP
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                long val = grid[i][j];
                
                long a = val * maxDp[i - 1][j];
                long b = val * minDp[i - 1][j];
                long c = val * maxDp[i][j - 1];
                long d = val * minDp[i][j - 1];
                
                maxDp[i][j] = Math.max(Math.max(a, b), Math.max(c, d));
                minDp[i][j] = Math.min(Math.min(a, b), Math.min(c, d));
            }
        }
        
        long result = maxDp[m - 1][n - 1];
        
        if (result < 0) return -1;
        
        return (int)(result % 1000000007);
    }
}
```

---

# 🧪 Example Walkthrough (Important)

### Input:

```
grid = [[1,-2,1],
        [1,-2,1],
        [3,-4,1]]
```

Path:

```
1 → 1 → -2 → -4 → 1
```

Product:

```
1 × 1 × (-2) × (-4) × 1 = 8
```

👉 Notice:

* Two negatives turned result positive
* That’s why we tracked **minDp**

---

# 💡 Why This Works

| Situation                             | Why we need minDp    |
| ------------------------------------- | -------------------- |
| Negative number comes                 | flips sign           |
| Small negative becomes large positive | after multiplication |
| Zero exists                           | resets product       |

👉 Without `minDp`, you would miss optimal paths.

---

# ⏱️ Complexity

* **Time:** `O(m × n)`
* **Space:** `O(m × n)` (can optimize to `O(n)`)

---

# 🚀 Optimization Tip (Interview Bonus)

You can reduce space to **1D arrays**:

* Keep only current row
* Use temp variables

---

# 🧠 One-Line Intuition

👉 *“Track both best and worst because worst can become best after a negative.”*

---

