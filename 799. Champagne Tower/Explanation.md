## 🥂 799. Champagne Tower — Explanation + Java Solution

### 🔎 Problem Understanding (Simple Words)

We pour `poured` cups of champagne into the top glass `(0,0)`.

Rules:

* Each glass holds **maximum 1 cup**.
* If a glass gets **more than 1 cup**, the extra (overflow) is split **equally (50%-50%)** to:

  * The glass below-left
  * The glass below-right
* This continues row by row.
* There are at most **100 rows**.

We must return how full the glass at:

```
(query_row, query_glass)
```

is after pouring.

---

### 🧠 Key Idea

We simulate the pouring process using **Dynamic Programming (DP)**.

Instead of thinking “fill each glass”, think like this:

> Each glass passes down only its overflow.

If a glass has `x` cups:

* If `x <= 1` → no overflow
* If `x > 1` → overflow = `(x - 1)`

  * Left child gets `(x - 1) / 2`
  * Right child gets `(x - 1) / 2`

---

### 💡 Why DP Works

Each glass depends only on:

* The glass above-left
* The glass above-right

So we build row by row.

Since max rows = 100, we can safely use a 2D array:

```
double[101][101]
```

Time Complexity:

```
O(row²)  → at most 100² = 10000 (very small)
```

Space Complexity:

```
O(100²)
```

---

## ✅ Java Solution (LeetCode Ready)

```java
class Solution {
    public double champagneTower(int poured, int query_row, int query_glass) {
        
        double[][] dp = new double[101][101];
        dp[0][0] = poured;
        
        for (int row = 0; row < 100; row++) {
            for (int col = 0; col <= row; col++) {
                
                if (dp[row][col] > 1) {
                    double overflow = (dp[row][col] - 1) / 2.0;
                    
                    dp[row + 1][col] += overflow;
                    dp[row + 1][col + 1] += overflow;
                    
                    dp[row][col] = 1; // glass can only hold 1
                }
            }
        }
        
        return Math.min(1, dp[query_row][query_glass]);
    }
}
```

---

## 🔥 Step-by-Step Example

### Example:

```
poured = 2
query_row = 1
query_glass = 1
```

Step 1:

```
dp[0][0] = 2
```

Step 2:
Top glass can hold 1 → overflow = 1

Each child gets:

```
1 / 2 = 0.5
```

So:

```
dp[1][0] = 0.5
dp[1][1] = 0.5
```

Answer:

```
dp[1][1] = 0.5
```

✔ Output = `0.50000`

---

## 🎯 Important Observations

* We don’t simulate infinite pouring — only up to 100 rows.
* If poured is extremely large (like 100000009), many glasses will just become full (1.0).
* That’s why we return:

```java
Math.min(1, dp[query_row][query_glass]);
```

Because glass cannot hold more than 1.

---

## 🧩 Why This Is Medium Difficulty?

Because:

* Requires understanding overflow simulation.
* Must use floating point carefully.
* Requires bottom-up DP thinking.

---

