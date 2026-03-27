This is a **pattern observation problem**, not a simulation problem (that’s the key insight).

---

# 🧠 Core Idea (Very Important)

Instead of actually shifting the matrix **k times**, we observe:

* A row of length `n` returns to original after **n cyclic shifts**
* So performing `k` shifts is same as doing `k % n`

👉 This reduces unnecessary work.

---

# 🔄 What happens to rows?

For each row `i`:

* If `i` is **even** → left shift by `k % n`
* If `i` is **odd** → right shift by `k % n`

Now we check:

👉 After shifting, does each element come back to its original position?

---

# 🎯 Key Condition

Instead of building a new matrix, we check:

For every element `(i, j)`:

### Even row (left shift)

Original position:

```
mat[i][j]
```

After shift:

```
mat[i][(j + shift) % n]
```

So condition:

```
mat[i][j] == mat[i][(j + shift) % n]
```

---

### Odd row (right shift)

```
mat[i][j] == mat[i][(j - shift + n) % n]
```

---

# ⚡ Why this works

We are verifying:

👉 “After k shifts, does every element land back to same value?”

Instead of moving elements → we check positions mathematically.

This makes solution:

* Time: **O(m × n)**
* Space: **O(1)**

---

# ✅ Java Solution

```java
class Solution {
    public boolean areSimilar(int[][] mat, int k) {
        int m = mat.length;
        int n = mat[0].length;

        int shift = k % n; // important optimization

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                if (i % 2 == 0) {
                    // even row → left shift
                    int newCol = (j + shift) % n;
                    if (mat[i][j] != mat[i][newCol]) {
                        return false;
                    }
                } else {
                    // odd row → right shift
                    int newCol = (j - shift + n) % n;
                    if (mat[i][j] != mat[i][newCol]) {
                        return false;
                    }
                }

            }
        }

        return true;
    }
}
```

---

# 🧩 Example Walkthrough

### Input:

```
mat = [[1,2,1,2],
       [5,5,5,5],
       [6,3,6,3]]
k = 2
```

* `n = 4`
* `shift = 2 % 4 = 2`

### Row 0 (even → left shift)

```
[1,2,1,2] → [1,2,1,2]  ✅ same
```

### Row 1 (odd → right shift)

```
[5,5,5,5] → same  ✅
```

### Row 2 (even → left shift)

```
[6,3,6,3] → [6,3,6,3]  ✅
```

👉 All match → **true**

---

# 🚀 Intuition Summary

* Cyclic shift repeats every `n`
* Reduce `k → k % n`
* Don’t simulate → compare positions
* Even row → `(j + shift) % n`
* Odd row → `(j - shift + n) % n`

---

# 🔥 Interview Tip

If you simulate shifts → **TLE risk + messy code**

If you use index math → **clean O(mn)** solution ✔️

---
