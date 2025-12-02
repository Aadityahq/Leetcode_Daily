Below is a **clean, complete Markdown explanation** of the LeetCode problem **3623. Count Number of Trapezoids I**, including **how & why**, intuition, formulas, and a **Java solution**.

---

# #️⃣ **3623. Count Number of Trapezoids I — Full Explanation (Markdown Version)**

---

# ## 🧠 What is a Horizontal Trapezoid?

A **horizontal trapezoid** is a convex quadrilateral that has **at least one pair of horizontal sides**, meaning:

* The pair of sides must be **parallel to the x-axis**.
* A horizontal line has slope = 0, so any segment where both points have the same y-coordinate is horizontal.

### ✔ So to form a horizontal trapezoid, we need:

* **Two horizontal segments**
* Each formed by selecting **two points** on the **same y-coordinate**
* The segments must be at **two different y-levels**

---

# ## 🧩 Problem Reformulation

We are given many points.
To make horizontal segments:

### ▶ Group points by `y-coordinate`:

* Suppose at height `y`, we have `k` points.
* We can make **horizontal segments** using combinations of 2:

[
H[y] = \binom{k}{2} = \frac{k(k-1)}{2}
]

Each `H[y]` represents **the number of horizontal line segments at level y**.

---

# ## 🧠 Key Insight

A trapezoid is formed by selecting:

* **1 horizontal segment** from one y-level
* **1 horizontal segment** from another y-level

So:

[
\text{Answer} = \sum_{y_1 < y_2} H[y_1] \cdot H[y_2]
]

This is simply the number of ways to pick **two different y-levels**, one segment from each.

---

# ## ⚡ Efficient Computation

Let
[
S = \sum H[y]
]

Then the number of unordered pairs is:

[
\frac{S^2 - \sum H[y]^2}{2}
]

Why?

* ( S^2 = \sum H[y_1] \cdot H[y_2] ) including pairs where ( y_1 = y_2 )
* Subtract the invalid terms where ( y_1 = y_2 ):
  [
  \sum H[y]^2
  ]
* Divide by 2 because pairs (a,b) and (b,a) counted twice.

---

# ## 🧮 Example

### points:

```
[1,0], [2,0], [3,0], [2,2], [3,2]
```

Group by y:

| y | points | count | H[y]    |
| - | ------ | ----- | ------- |
| 0 | 3      | 3     | 3C2 = 3 |
| 2 | 2      | 2     | 1       |

Trapezoids =
[
3 \times 1 = 3
]

Matches answer ✔

---

# ## 🧠 Why This Always Works

* A horizontal side = any two points on the same y.
* A trapezoid must pick one segment from one y-level and one from another.
* No need to worry about ordering or shape → convexity automatically enforced because horizontal lines never intersect incorrectly.
* Each trapezoid is counted exactly once.

---

# ## ✅ Final Java Solution

```java
class Solution {
    static final long MOD = 1_000_000_007L;

    public int countTrapezoids(int[][] points) {
        // Count how many points exist for each y-coordinate
        Map<Integer, Long> map = new HashMap<>();
        for (int[] p : points) {
            map.put(p[1], map.getOrDefault(p[1], 0L) + 1);
        }

        long S = 0;         // sum of H[y]
        long squareSum = 0; // sum of H[y]^2

        // Compute H[y] for each horizontal level
        for (long count : map.values()) {
            if (count >= 2) {
                long H = (count * (count - 1) / 2) % MOD;
                S = (S + H) % MOD;
                squareSum = (squareSum + (H * H) % MOD) % MOD;
            }
        }

        // Apply formula: (S^2 - sum(H[y]^2)) / 2
        long result = (S * S % MOD - squareSum + MOD) % MOD;

        // Modular inverse of 2 under MOD = (MOD + 1) / 2
        long inv2 = (MOD + 1) / 2;
        result = (result * inv2) % MOD;

        return (int) result;
    }
}
```

---

# ## 🏁 Time & Space Complexity

| Operation            | Complexity  |
| -------------------- | ----------- |
| Group points by y    | O(n)        |
| Compute combinations | O(unique y) |
| Final math           | O(unique y) |
| **Total**            | **O(n)**    |

Space:
O(unique y)

---


