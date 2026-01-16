## 🔍 Problem Intuition (in simple words)

* You have a big rectangular field from **(1,1)** to **(m,n)**.
* There are **horizontal fences** and **vertical fences** inside the field.
* You are allowed to **remove any internal fences**, but **boundary fences cannot be removed**.
* After removing fences, the field gets divided into smaller rectangles.
* Your task is to form the **largest possible square** and return its **area**.
* If forming a square is **impossible**, return **-1**.

---

## 🧠 Key Observation

A **square** needs:

* **height = width**

So we need to:

1. Find all possible **vertical gaps** (possible widths)
2. Find all possible **horizontal gaps** (possible heights)
3. Find the **largest common gap** that exists in both

---

## 🧩 How do we find gaps?

### Step 1: Add boundary fences

* Horizontal fences: add `1` and `m`
* Vertical fences: add `1` and `n`

### Step 2: Sort the fences

This allows us to easily compute distances.

### Step 3: Compute all possible gaps

* For every pair `(i, j)` where `i < j`, compute:

  * `gap = fence[j] - fence[i]`
* Store **all horizontal gaps**
* Store **all vertical gaps**

⚠️ Number of fences ≤ 600 →
Total gaps ≈ 600² = 360,000 → acceptable

---

## 🎯 Final Logic

* Iterate from **largest to smallest** horizontal gap
* If the same gap exists in vertical gaps → we found the largest square
* Area = `gap × gap`
* Return `area % (10⁹ + 7)`
* If no common gap exists → return `-1`

---

## ✅ Java Solution

```java
import java.util.*;

class Solution {
    public int maximizeSquareArea(int m, int n, int[] hFences, int[] vFences) {
        final int MOD = 1_000_000_007;

        // Add boundary fences
        int[] h = new int[hFences.length + 2];
        int[] v = new int[vFences.length + 2];

        h[0] = 1;
        h[h.length - 1] = m;
        for (int i = 0; i < hFences.length; i++) {
            h[i + 1] = hFences[i];
        }

        v[0] = 1;
        v[v.length - 1] = n;
        for (int i = 0; i < vFences.length; i++) {
            v[i + 1] = vFences[i];
        }

        // Sort fences
        Arrays.sort(h);
        Arrays.sort(v);

        // Store all possible vertical gaps
        Set<Integer> verticalGaps = new HashSet<>();
        for (int i = 0; i < v.length; i++) {
            for (int j = i + 1; j < v.length; j++) {
                verticalGaps.add(v[j] - v[i]);
            }
        }

        long maxSide = 0;

        // Check horizontal gaps from largest to smallest
        for (int i = 0; i < h.length; i++) {
            for (int j = i + 1; j < h.length; j++) {
                int gap = h[j] - h[i];
                if (verticalGaps.contains(gap)) {
                    maxSide = Math.max(maxSide, gap);
                }
            }
        }

        if (maxSide == 0) return -1;

        long area = (maxSide * maxSide) % MOD;
        return (int) area;
    }
}
```

---

## ⏱️ Time & Space Complexity

* **Time:** `O(H² + V²)` where `H, V ≤ 600`
* **Space:** `O(V²)` for storing vertical gaps

Efficient enough for the constraints 👍

---

## 🧪 Example

**Input**

```
m = 4, n = 3
hFences = [2,3]
vFences = [2]
```

**Output**

```
4
```

✔ Square side = 2 → Area = 4

---

