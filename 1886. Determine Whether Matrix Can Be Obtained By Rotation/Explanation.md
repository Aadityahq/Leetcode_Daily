# 🧠 Problem Understanding

You are given two matrices:

* `mat` → original matrix
* `target` → desired matrix

👉 You can rotate `mat` by **90° clockwise** multiple times.

### 🔄 Possible rotations:

* 0° (no rotation)
* 90°
* 180°
* 270°

👉 Your task:
Check if **any of these rotations** makes `mat == target`.

---

# 💡 Key Idea

Instead of trying complex transformations:

👉 Just **rotate the matrix at most 4 times** and compare each time.

Because:

* After 4 rotations → matrix returns to original
* So only 4 cases exist

---

# 🔁 How Rotation Works (Important)

For a 90° clockwise rotation:

```
mat[i][j] → mat[j][n - 1 - i]
```

👉 This is the core transformation.

---

# ✅ Approach

1. Repeat 4 times:

   * Check if `mat == target`
   * If yes → return `true`
   * Else → rotate `mat` by 90°
2. If none matched → return `false`

---

# 💻 Java Solution

```java
class Solution {
    public boolean findRotation(int[][] mat, int[][] target) {
        for (int k = 0; k < 4; k++) {
            if (isEqual(mat, target)) return true;
            mat = rotate(mat);
        }
        return false;
    }

    private boolean isEqual(int[][] a, int[][] b) {
        int n = a.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (a[i][j] != b[i][j]) return false;
            }
        }
        return true;
    }

    private int[][] rotate(int[][] mat) {
        int n = mat.length;
        int[][] rotated = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                rotated[j][n - 1 - i] = mat[i][j];
            }
        }
        return rotated;
    }
}
```

---

# 🔍 Step-by-Step Example

### Input:

```
mat =
0 1
1 0

target =
1 0
0 1
```

### Rotation 1 (90°):

```
1 0
0 1
```

✅ Matches target → return true

---

# 🤔 Why This Works

* There are only **4 possible orientations**
* Brute force is efficient since `n ≤ 10`
* Rotation is **O(n²)**, done 4 times → still fast

---

# ⏱ Complexity

* Time: `O(4 × n²) ≈ O(n²)`
* Space: `O(n²)` (for rotated matrix)

---

# ⚡ Alternative (Smart Observation)

You could also:

* Check all 4 rotations **without actually modifying matrix**
* But that becomes harder to code

👉 Current approach = **clean + interview friendly**

---

# 🚀 Key Takeaway

👉 Whenever rotation is involved:

* Think in terms of **fixed number of transformations**
* Try all possibilities (usually 4 for square matrix)

---

