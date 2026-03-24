# 🔍 Problem Understanding

You are given a matrix `grid (n x m)`.

You need to build another matrix `p` such that:

👉 `p[i][j] = product of ALL elements in grid except grid[i][j]`
👉 Then take `% 12345`

---

## ⚠️ Brute Force (Wrong Approach)

For every cell:

* Multiply all other elements → O((nm)²)

❌ Too slow (since n*m can be up to 10⁵)

---

# 💡 Key Idea (Optimal)

This is same as:

> **"Product of array except self"** but in 2D

---

## 🔄 Step 1: Flatten the matrix

Convert grid into 1D array:

```
grid = [[1,2],[3,4]]
→ arr = [1,2,3,4]
```

---

## 🔄 Step 2: Use Prefix & Suffix Products

Instead of dividing (not safe with modulo), we use:

* `prefix[i] = product of elements before i`
* `suffix[i] = product of elements after i`

Then:

```
result[i] = prefix[i] * suffix[i] % 12345
```

---

## 🧠 Why this works?

For index `i`, we want:

```
product of all except arr[i]
= (product before i) * (product after i)
```

So:

```
prefix[i] → left side
suffix[i] → right side
```

---

# ✅ Java Solution

```java
class Solution {
    public int[][] constructProductMatrix(int[][] grid) {
        int mod = 12345;
        int n = grid.length;
        int m = grid[0].length;
        int size = n * m;

        // Step 1: Flatten
        int[] arr = new int[size];
        int idx = 0;
        for (int[] row : grid) {
            for (int val : row) {
                arr[idx++] = val % mod;
            }
        }

        // Step 2: Prefix
        int[] prefix = new int[size];
        prefix[0] = 1;
        for (int i = 1; i < size; i++) {
            prefix[i] = (prefix[i - 1] * arr[i - 1]) % mod;
        }

        // Step 3: Suffix
        int[] suffix = new int[size];
        suffix[size - 1] = 1;
        for (int i = size - 2; i >= 0; i--) {
            suffix[i] = (suffix[i + 1] * arr[i + 1]) % mod;
        }

        // Step 4: Build result
        int[][] result = new int[n][m];
        idx = 0;

        for (int i = 0; i < size; i++) {
            int val = (prefix[i] * suffix[i]) % mod;
            result[i / m][i % m] = val;
        }

        return result;
    }
}
```

---

# ⏱️ Complexity

* Time: **O(n * m)** ✅
* Space: **O(n * m)** (can be optimized further)

---

# 🔥 Example Walkthrough

Input:

```
grid = [[1,2],[3,4]]
arr = [1,2,3,4]
```

### Prefix:

```
[1, 1, 2, 6]
```

### Suffix:

```
[24, 12, 4, 1]
```

### Result:

```
[24, 12, 8, 6]
```

---

# ⚠️ Important Edge Case

### When element = 12345 multiple

Since:

```
x % 12345 = 0
```

So product may become 0 → correct behavior

---

# 🧠 Intuition Summary

👉 Don’t try to recompute product for every cell
👉 Don’t use division (mod issues)
👉 Use prefix + suffix trick

---

# 🚀 Bonus (Optimization Idea)

You can reduce space by:

* Using only suffix array
* Computing prefix on the fly

---

