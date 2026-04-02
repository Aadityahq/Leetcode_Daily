# 🧠 Problem Understanding

You have a grid:

* Start → `(0,0)`
* End → `(m-1, n-1)`
* Moves allowed → **Right OR Down**

Each cell:

* `+ve` → gain coins
* `-ve` → lose coins (robber)
* You can **neutralize robbers in at most 2 cells** → meaning you can ignore up to 2 negative values

👉 Goal: **Maximize total coins**

---

# 🚨 Key Insight

Normal grid DP would be:

```
dp[i][j] = max(dp[i-1][j], dp[i][j-1]) + coins[i][j]
```

But here:
👉 You can **skip negative values up to 2 times**

So we need **one extra dimension**:

```
k = number of neutralizations used (0, 1, 2)
```

---

# 💡 DP Definition

```
dp[i][j][k] = max coins reaching cell (i,j) using k neutralizations
```

---

# 🔄 Transition

From top `(i-1, j)` or left `(i, j-1)`:

### Case 1: Take the cell normally

```
dp[i][j][k] = prev + coins[i][j]
```

### Case 2: If cell is negative and we can neutralize

```
dp[i][j][k] = prev (ignore negative)  // use 1 neutralization
```

---

# 🧩 Final Answer

```
max(dp[m-1][n-1][0], dp[m-1][n-1][1], dp[m-1][n-1][2])
```

---

# ✅ Java Solution

```java
class Solution {
    public int maximumAmount(int[][] coins) {
        int m = coins.length;
        int n = coins[0].length;

        // dp[i][j][k] = max coins at (i,j) using k neutralizations
        int[][][] dp = new int[m][n][3];

        // Initialize with very small value
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < 3; k++) {
                    dp[i][j][k] = Integer.MIN_VALUE;
                }
            }
        }

        // Base case (0,0)
        if (coins[0][0] >= 0) {
            dp[0][0][0] = coins[0][0];
        } else {
            dp[0][0][0] = coins[0][0]; // take loss
            dp[0][0][1] = 0; // neutralize
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                for (int k = 0; k <= 2; k++) {
                    if (dp[i][j][k] == Integer.MIN_VALUE) continue;

                    // Move Right
                    if (j + 1 < n) {
                        int val = coins[i][j + 1];

                        // take normally
                        dp[i][j + 1][k] = Math.max(dp[i][j + 1][k],
                                dp[i][j][k] + val);

                        // neutralize if negative
                        if (val < 0 && k < 2) {
                            dp[i][j + 1][k + 1] = Math.max(dp[i][j + 1][k + 1],
                                    dp[i][j][k]);
                        }
                    }

                    // Move Down
                    if (i + 1 < m) {
                        int val = coins[i + 1][j];

                        // take normally
                        dp[i + 1][j][k] = Math.max(dp[i + 1][j][k],
                                dp[i][j][k] + val);

                        // neutralize if negative
                        if (val < 0 && k < 2) {
                            dp[i + 1][j][k + 1] = Math.max(dp[i + 1][j][k + 1],
                                    dp[i][j][k]);
                        }
                    }
                }
            }
        }

        int ans = Integer.MIN_VALUE;
        for (int k = 0; k <= 2; k++) {
            ans = Math.max(ans, dp[m - 1][n - 1][k]);
        }

        return ans;
    }
}
```

---

# ⚙️ Complexity

* Time: **O(m × n × 3)** ≈ `O(mn)`
* Space: **O(m × n × 3)**

---

# 🧠 Why This Works (Interview Explanation)

👉 The tricky part is **decision making at negative cells**:

At every negative cell, you have 2 choices:

1. Take loss
2. Neutralize (if available)

So instead of greedy, we:

* Track all possibilities using DP
* Keep best result for each `(i,j,k)`

👉 This ensures we **don’t waste neutralizations early**

---

# 🔥 Intuition Summary

* Without robber power → simple DP
* With robber power → add dimension (`k`)
* Always try:

  * **take value**
  * **skip negative if possible**

---
