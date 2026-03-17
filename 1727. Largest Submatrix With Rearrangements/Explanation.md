# 🧠 Key Idea (Intuition)

We are allowed to **rearrange columns** → this is the main twist.

👉 That means:

* In each row, we can reorder columns however we want.
* But **column rearrangement applies to all rows simultaneously**.

---

# 🔍 Step 1: Convert Matrix → Heights (Histogram)

We build a new matrix `height[i][j]`:

* If `matrix[i][j] == 1` → increase height from above
* Else → reset to 0

### Example:

```
matrix:
[0,0,1]
[1,1,1]
[1,0,1]

heights:
[0,0,1]
[1,1,2]
[2,0,3]
```

👉 Each row now represents a **histogram of consecutive 1s ending at that row**

---

# 🔍 Step 2: Why Sorting Works?

Since we can rearrange columns:

👉 For each row, we can **sort heights in descending order**

Why?

Because:

* Taller columns give larger rectangles
* Sorting helps us group large heights together

---

# 🔍 Step 3: Compute Max Area per Row

After sorting each row:

For each column `j`:

```
area = height[j] * (j + 1)
```

Why `(j + 1)`?

* Because width = number of columns taken
* We take first `j+1` columns (largest heights)

---

# ✨ Full Algorithm

1. Build height matrix
2. For each row:

   * Sort in descending order
   * Compute max area
3. Return maximum

---

# 💻 Java Solution

```java
import java.util.*;

class Solution {
    public int largestSubmatrix(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;

        // Step 1: Build heights
        for (int i = 1; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 1) {
                    matrix[i][j] += matrix[i - 1][j];
                }
            }
        }

        int maxArea = 0;

        // Step 2 & 3
        for (int i = 0; i < m; i++) {
            int[] row = matrix[i].clone();

            // Sort descending
            Arrays.sort(row);

            // Traverse from largest
            for (int j = n - 1; j >= 0; j--) {
                int height = row[j];
                int width = n - j;
                maxArea = Math.max(maxArea, height * width);
            }
        }

        return maxArea;
    }
}
```

---

# ⚡ Why This Works (Core Logic)

### 💡 Insight 1: Column Rearrangement = Freedom

We don’t care about original column positions anymore.

---

### 💡 Insight 2: Fix Bottom Row

For each row:

* Treat it as **base of rectangle**
* Heights tell how far up we can go

---

### 💡 Insight 3: Greedy Choice

Sort heights → always try:

* largest height × possible width

---

# 🧪 Walkthrough (Example 1)

```
Row: [1,1,2]
Sorted: [1,1,2]

Check:
2 × 1 = 2
1 × 2 = 2
1 × 3 = 3
```

Next row:

```
[2,0,3] → [0,2,3]

3 × 1 = 3
2 × 2 = 4  ← answer
```

---

# ⏱ Complexity

* Build heights: **O(m × n)**
* Sorting each row: **O(m × n log n)**

✅ Total: **O(m × n log n)**

---

# 🧩 Pattern You Should Remember

This problem teaches:

👉 **"Histogram + Sorting Trick"**

Used in:

* Largest rectangle problems
* Matrix rearrangement problems
* Greedy + prefix heights

---

# 🚀 Interview Tip

If you see:

* Binary matrix
* "Rearrange columns"
* "Max rectangle/submatrix"

👉 Think:
**"Convert to heights + sort each row"**

---

