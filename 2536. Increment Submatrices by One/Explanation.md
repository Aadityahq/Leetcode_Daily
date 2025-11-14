# 🧠 **Problem Understanding**

You are given:

* An integer `n`, meaning an **n × n matrix**, filled initially with **all zeroes**.
* A list of queries, where each query is:

```
[row1, col1, row2, col2]
```

This means:

➡️ **Increase every element inside the rectangle**
starting at `(row1, col1)` (top-left)
and ending at `(row2, col2)` (bottom-right)
by exactly **+1**.

Your task:

👉 After performing **all** queries, return the **final matrix**.

---

# 📘 **Example Breakdown**

Example:

```
n = 3
queries = [[1,1,2,2], [0,0,1,1]]
```

Start:

```
0 0 0
0 0 0
0 0 0
```

After first query (increment rectangle (1,1) → (2,2)):

```
0 0 0
0 1 1
0 1 1
```

After second query (rectangle (0,0) → (1,1)):

```
1 1 0
1 2 1
0 1 1
```

This matches the output.

---

# ❌ Why the Brute Force Approach Fails

Brute force means for every query, we loop through the rectangle and increment each cell.

But worst-case constraints:

* n = 500 → matrix size = 250,000 cells
* queries = 10,000

So worst case:

```
250,000 × 10,000 = 2.5 Billion operations ❌
```

This **cannot run in time**.

We need something faster.

---

# 🔥 **Optimal Idea: 2D Difference Array**

Instead of updating every cell in the rectangle…
we update **only 4 cells** in a helper matrix `diff[][]`.

Why?
Because we will later use prefix sums to spread those increments correctly.

---

# 📘 How a Difference Matrix Works (Intuition)

Imagine the matrix as having "spreadable ink":

* If you drop +1 at (r1, c1), it spreads right and downward.
* If you place −1 at boundaries, the spread **stops** where we want.

So each query says:

```
Start spreading +1 from (r1, c1)
Stop spreading after col c2
Stop spreading after row r2
Fix the corner overlap
```

This is done using these 4 updates:

```
diff[r1][c1] += 1
diff[r1][c2+1] -= 1
diff[r2+1][c1] -= 1
diff[r2+1][c2+1] += 1
```

---

# 🔄 **Building the Final Matrix**

After processing all queries in the difference matrix, we convert it to the real matrix using **2D prefix sums**:

```
mat[i][j] = diff[i][j]
            + mat[i-1][j]
            + mat[i][j-1]
            - mat[i-1][j-1]
```

This automatically:

✔ propagates the +1 across rows
✔ propagates down columns
✔ subtracts the double-counted corner

So every rectangle gets incremented perfectly.

---

# 🧮 Time Complexity

* Processing queries: **O(Q)**
* Building final matrix with prefix sums: **O(n²)**

Total:

```
O(n² + Q)  ✔ Efficient ✔ Fast 
```

---

# 🏁 **Final Summary (Perfect for Interview Answers)**

1. We need to increment many submatrices.
2. Brute force is too slow (2.5 billion operations).
3. Use a **2D difference array**:

   * Only update 4 boundary points per query.
4. After all queries, convert diff[][] → actual matrix using **2D prefix sums**.
5. This ensures every rectangle gets incremented correctly.
6. Total time is efficient: **O(n² + Q)**.

---


