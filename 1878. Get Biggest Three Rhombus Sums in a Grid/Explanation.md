# 🔷 First: Problem Understanding (Rhombus Sum)

### What is a rhombus here?

A rhombus is a **diamond shape (tilted square)** like this:

```
   a
  b c
 d   e
  f g
   h
```

👉 But **only the border counts**, not inside values.

👉 Also, a rhombus of size 0 = just a single cell.

---

## 💡 Key Observations

1. Every cell can be the **center (top corner)** of a rhombus.
2. We expand rhombus layer by layer.
3. We only add:

   * left diagonal
   * right diagonal
   * bottom edges

---

# 🔷 How to Solve Efficiently

Brute force would be too slow because:

* Grid size = up to 50×50
* Many rhombus shapes

So we optimize using **diagonal prefix sums**.

---

## 🔑 Trick: Diagonal Prefix Sums

We build two prefix arrays:

1. `diag1` → top-left → bottom-right
2. `diag2` → top-right → bottom-left

These allow us to compute diagonal sums in **O(1)** instead of O(n).

---

# 🔷 Algorithm Steps

### Step 1: Precompute diagonals

```java
diag1[i][j] = grid[i][j] + diag1[i-1][j-1]
diag2[i][j] = grid[i][j] + diag2[i-1][j+1]
```

---

### Step 2: Try every center

For each cell `(i, j)`:

* Add **size 0 rhombus** → just `grid[i][j]`

---

### Step 3: Expand rhombus

For size `k`:

* Top = `(i, j)`
* Left = `(i+k, j-k)`
* Right = `(i+k, j+k)`
* Bottom = `(i+2k, j)`

👉 Check bounds before computing

---

### Step 4: Compute border sum

Using diagonals:

* Top → Left (diag2)
* Top → Right (diag1)
* Left → Bottom (diag1)
* Right → Bottom (diag2)

Then subtract corners (because counted twice).

---

### Step 5: Store top 3 distinct sums

Use:

```java
TreeSet<Integer> set = new TreeSet<>(Collections.reverseOrder());
```

---

# 🔷 Full Correct Code

```java
import java.util.*;

class Solution {
    public int[] getBiggestThree(int[][] grid) {
        int m = grid.length, n = grid[0].length;

        int[][] diag1 = new int[m][n];
        int[][] diag2 = new int[m][n];

        // Build diagonals
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                diag1[i][j] = grid[i][j];
                if (i > 0 && j > 0)
                    diag1[i][j] += diag1[i - 1][j - 1];

                diag2[i][j] = grid[i][j];
                if (i > 0 && j < n - 1)
                    diag2[i][j] += diag2[i - 1][j + 1];
            }
        }

        TreeSet<Integer> set = new TreeSet<>(Collections.reverseOrder());

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                // size 0 rhombus
                set.add(grid[i][j]);

                // expand rhombus
                for (int k = 1; i + 2 * k < m && j - k >= 0 && j + k < n; k++) {

                    int sum = 0;

                    // 4 edges
                    sum += getDiag2(diag2, i, j, i + k, j - k);
                    sum += getDiag1(diag1, i, j, i + k, j + k);
                    sum += getDiag1(diag1, i + k, j - k, i + 2 * k, j);
                    sum += getDiag2(diag2, i + k, j + k, i + 2 * k, j);

                    // subtract corners (counted twice)
                    sum -= grid[i][j];
                    sum -= grid[i + k][j - k];
                    sum -= grid[i + k][j + k];
                    sum -= grid[i + 2 * k][j];

                    set.add(sum);
                }
            }
        }

        int size = Math.min(3, set.size());
        int[] res = new int[size];

        int idx = 0;
        for (int val : set) {
            if (idx == size) break;
            res[idx++] = val;
        }

        return res;
    }

    private int getDiag1(int[][] d, int x1, int y1, int x2, int y2) {
        if (x1 > 0 && y1 > 0)
            return d[x2][y2] - d[x1 - 1][y1 - 1];
        return d[x2][y2];
    }

    private int getDiag2(int[][] d, int x1, int y1, int x2, int y2) {
        if (x1 > 0 && y1 < d[0].length - 1)
            return d[x2][y2] - d[x1 - 1][y1 + 1];
        return d[x2][y2];
    }
}
```

---

# 🔷 Why This Works

### ❌ Without optimization:

* Each rhombus = O(k)
* Total = O(n⁴) → TLE

### ✅ With diagonals:

* Each rhombus = O(1)
* Total = **O(n³)** → Works!

---

# 🔷 Key Intuition (VERY IMPORTANT)

👉 Instead of walking edges manually
👉 We **pre-store diagonal sums**
👉 Then we “cut out” segments instantly

---

# 🔷 Mistake in Your Code

Your code is for **another problem**:

👉 `Largest Submatrix With Rearrangements`

That problem uses:

* histogram heights
* sorting rows

❌ It has **nothing to do with rhombus**

---

# 🔥 Interview Tip

If interviewer asks:

👉 “Why diagonal prefix?”

Say:

> Because rhombus edges lie along diagonals, so prefix sums allow O(1) edge calculation instead of traversing each edge.

---
