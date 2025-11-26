# ✅ **📌 PROBLEM EXPLANATION**

You are given an `m × n` grid of integers.
You start at the top-left cell `(0,0)` and want to reach the bottom-right cell `(m-1, n-1)`.

### ✔ Allowed Moves

* Move **right**
* Move **down**

Every path collects numbers along the way.
Example of a path in a 3×3 grid:

```
(0,0) → (0,1) → (1,1) → (2,1) → (2,2)
```

You add all these numbers together — that gives you the **sum of the path**.

### ✔ What You Need to Count

You must count:

> The number of possible paths where the **sum of all numbers on the path is divisible by k**.

So if a path’s sum = `S`, then it must satisfy:

```
S % k == 0
```

### ✔ Constraints

* Up to **50,000 cells** in total (so grid is large).
* Each number ≤ 100.
* k ≤ 50.
* There may be many paths → return modulo **1,000,000,007**.

### ❌ Why Brute Force Won’t Work

Total number of paths can be exponential (~2⁴⁹⁹⁹⁹ in worst case).
So we **must** use dynamic programming.

---

# ✅ **📌 SOLUTION EXPLANATION (How + Why)**

To solve the problem, we use **Dynamic Programming with Remainders**.

---

# 🔷 **1. Key Observation**

We don’t need the full sum of the path.
We only need the **remainder when divided by k**:

```
remainder = sum % k
```

Because two sums with the same remainder behave the same way when extended.

---

# 🔷 **2. DP State Definition**

Let:

```
dp[i][j][r] = number of ways to reach cell (i,j)
              with sum % k = r
```

But storing a full 3D array is impossible because:

* m×n can be 50,000
* k ≤ 50
  → dp would be: 50,000 × 50 = **2.5 million entries**
  → doable, but storing for every row is still heavy.

We optimize for memory by storing only:

* `prev` row (for row i-1)
* `curr` row (for row i)

Each row has:

```
n columns × k remainders
```

---

# 🔷 **3. Transition (Move Right or Down)**

At cell `(i, j)`, value = `grid[i][j]`.

Previous cell can be:

* From **top**: `(i-1, j)` → `prev[j]`
* From **left**: `(i, j-1)` → `curr[j-1]`

Suppose the previous remainder was `r`.
After adding the current value:

```
newR = (r + grid[i][j]) % k
```

So we update:

```
curr[j][newR] = (prev[j][r] + curr[j-1][r]) % MOD
```

This means:

* Every path from top contributes
* Every path from left contributes

---

# 🔷 **4. Initialization**

### First row:

You can only move **right**.

We accumulate the sum and assign the remainder.

### First column:

You can only move **down**.

---

# 🔷 **5. Final Answer**

At the last cell `(m-1, n-1)`:

```
We want remainder = 0
So answer = dp[lastCell][0]
```

In code:

```java
return prev[n-1][0];
```

---

# 🎉 **Why the DP Works**

✔ Divisibility only depends on remainder → track remainder
✔ At each cell you only need results from top + left
✔ Time = O(m × n × k) which is within constraints
✔ Space optimized to O(n × k)

This is exactly the technique required for large grids.

---
