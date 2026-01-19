# 🔷 Problem Explanation (WHAT)

You are given:

* A matrix `mat` of size `m × n`
* An integer `threshold`

You must find:

> **The largest possible side length `k` of a square submatrix**
> such that the **sum of all elements inside the `k × k` square is ≤ threshold**

If no valid square exists, return `0`.

---

## Why this problem is tricky

* Matrix size can be as large as **300 × 300**
* Checking every square naively would be too slow
* We need:

  * **Fast submatrix sum calculation**
  * **Efficient way to try different square sizes**

---

# 🔷 Core Idea (WHY THIS WORKS)

### 1️⃣ Prefix Sum → fast square sum in O(1)

Instead of recalculating sums repeatedly, we **precompute cumulative sums**.

### 2️⃣ Binary Search on side length

If a square of size `k` works, then **all smaller sizes also work**
➡️ This monotonic property allows **binary search**

---

# 🔷 Step 1: Prefix Sum Matrix (HOW)

We create a prefix sum array `pre` where:

```
pre[i][j] = sum of all elements from (0,0) to (i-1,j-1)
```

This lets us calculate the sum of **any k × k square in O(1)**.

### Square sum formula

For a square starting at `(r, c)` with side `k`:

```
sum =
pre[r+k][c+k]
- pre[r][c+k]
- pre[r+k][c]
+ pre[r][c]
```

This is why prefix sums are powerful.

---

# 🔷 Step 2: Binary Search on Side Length (HOW)

* Minimum side length = `0`
* Maximum side length = `min(m, n)`

For each `mid`:

* Check if **any square of size `mid × mid`** has sum ≤ threshold
* If yes → try larger size
* If no → reduce size

---

# ✅ Java Solution

```java
class Solution {
    public int maxSideLength(int[][] mat, int threshold) {
        int m = mat.length;
        int n = mat[0].length;

        // Step 1: Build prefix sum matrix
        int[][] pre = new int[m + 1][n + 1];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                pre[i + 1][j + 1] =
                        mat[i][j]
                        + pre[i][j + 1]
                        + pre[i + 1][j]
                        - pre[i][j];
            }
        }

        // Step 2: Binary search on side length
        int left = 0, right = Math.min(m, n);

        while (left < right) {
            int mid = (left + right + 1) / 2;

            if (canFormSquare(pre, mat, threshold, mid)) {
                left = mid;      // try bigger square
            } else {
                right = mid - 1; // reduce size
            }
        }

        return left;
    }

    // Helper function to check if any k x k square is valid
    private boolean canFormSquare(int[][] pre, int[][] mat, int threshold, int k) {
        int m = mat.length;
        int n = mat[0].length;

        for (int i = 0; i <= m - k; i++) {
            for (int j = 0; j <= n - k; j++) {
                int sum =
                        pre[i + k][j + k]
                        - pre[i][j + k]
                        - pre[i + k][j]
                        + pre[i][j];

                if (sum <= threshold) {
                    return true;
                }
            }
        }
        return false;
    }
}
```

---

# 🔷 Dry Run (HOW IT THINKS)

Example:

```
threshold = 4
```

Binary search tries:

* `k = 2` → finds a valid square ✔️
* `k = 3` → no valid square ❌

➡️ Final answer = `2`

---

# ⏱️ Time & Space Complexity

### Time Complexity

* Prefix sum: `O(m × n)`
* Binary search: `O(log(min(m, n)))`
* Each check: `O(m × n)`

✅ **Total:**

```
O(m × n × log(min(m, n)))
```

---

### Space Complexity

* Prefix sum matrix: `O(m × n)`

---

# 🔷 Why this solution is optimal

✔ Avoids recalculating sums
✔ Uses mathematical properties (monotonicity)
✔ Efficient for large inputs
✔ Very common **LeetCode + interview pattern**

---

