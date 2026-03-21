# 🧠 Core Idea of the Problem

You are given a matrix and asked to:

> Flip a **k × k square submatrix vertically**

---

## 🔄 What “vertical flip” actually means

It means:

> Reverse the **order of rows**, NOT columns

So:

```
Top row  ↔ Bottom row  
2nd row ↔ 2nd last row
```

📌 Columns stay exactly the same.

---

# 🎯 Goal

Only modify the square defined by:

```
Top-left = (x, y)
Size = k
```

So affected area is:

```
Rows: x → x + k - 1
Cols: y → y + k - 1
```

---

# ⚙️ Step-by-Step Explanation of Code

## 🔁 Outer Loop (Row Swapping)

```java
for (int i = 0; i < k / 2; i++)
```

### 👉 Why `k / 2`?

Because:

* Each swap fixes **two rows at once**
* So we only need to go halfway

Example:

```
k = 4 → swap (0↔3), (1↔2)
k = 3 → swap (0↔2), middle stays
```

---

## 📍 Identify Rows to Swap

```java
int topRow = x + i;
int bottomRow = x + (k - 1 - i);
```

### 👉 Why this works:

* `x` is starting row
* `i` moves downward
* `(k - 1 - i)` moves upward from bottom

So you're pairing:

```
first ↔ last
second ↔ second last
```

---

## 🔁 Inner Loop (Column Traversal)

```java
for (int j = 0; j < k; j++)
```

### 👉 Why?

We must swap **entire rows inside the submatrix**, but only within the square.

So columns go:

```
y → y + k - 1
```

---

## 🔄 Swapping Elements

```java
int col = y + j;

int temp = grid[topRow][col];
grid[topRow][col] = grid[bottomRow][col];
grid[bottomRow][col] = temp;
```

### 👉 Why column-wise swap?

Because:

* Java arrays don't support direct row swapping easily
* So we swap each element in the row manually

---

# 🧩 Putting It All Together

For each pair of rows:

1. Identify top and bottom row
2. Traverse all columns in the square
3. Swap elements

---

# 🔍 Dry Run Example

### Input:

```
grid =
[1, 2, 3, 4]
[5, 6, 7, 8]
[9,10,11,12]
[13,14,15,16]

x = 1, y = 0, k = 3
```

### Submatrix:

```
[5   6   7]
[9  10  11]
[13 14 15]
```

---

### Iteration 1 (i = 0):

```
topRow = 1
bottomRow = 3
```

Swap:

```
[5 6 7] ↔ [13 14 15]
```

---

### Result:

```
[1, 2, 3, 4]
[13,14,15,8]
[9,10,11,12]
[5, 6, 7,16]
```

---

# 💡 Why This Approach is Optimal

### ✔️ In-place modification

* No extra matrix → saves memory

### ✔️ Minimal traversal

* Only visits submatrix → efficient

---

# ⏱️ Complexity

### Time:

```
O(k²)
```

Because:

* We visit each element in k × k area once

---

### Space:

```
O(1)
```

No extra memory used

---

# 🚨 Common Mistakes

❌ Swapping full rows of matrix
✔️ Only swap inside submatrix

❌ Using `n` instead of `k`
✔️ Always stay within square

❌ Confusing with rotation
✔️ This is just row reversal

---

# 🧠 Interview Insight

This problem teaches a **core pattern**:

> 🔥 “Operate only within a bounded window in a matrix”

This pattern is used in:

* Matrix rotation
* Image processing
* Sliding window on grids

---

# 🚀 Final Takeaway

Think of it as:

> “Reverse rows inside a square — nothing more”

---

