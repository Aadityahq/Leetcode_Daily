## 🔹 1536. Minimum Swaps to Arrange a Binary Grid

### 🧠 Problem Understanding

You are given an **n × n binary grid**.
In one operation, you can **swap two adjacent rows**.

A grid is considered **valid** if:

> All cells **above the main diagonal** contain only `0`.

### 📌 What does “above the main diagonal” mean?

For position `(i, j)`:

* Main diagonal → where `i == j`
* Above diagonal → where `j > i`

So for every row `i`, all positions:

```
(i, i+1), (i, i+2), ..., (i, n-1)
```

must be **0**.

---

## 🔎 Key Observation

For row `i`:

It must have at least:

```
(n - i - 1)
```

zeros at the **end of the row** (trailing zeros).

Why trailing zeros?

Because the right side of the row (above diagonal positions) must be zero.

---

## 💡 Core Idea of the Solution

### Step 1️⃣: Count trailing zeros for each row

Example:

```
[0,0,1] → 0 trailing zeros
[1,1,0] → 1 trailing zero
[1,0,0] → 2 trailing zeros
```

We store this in an array:

```
zeros[i] = number of trailing zeros in row i
```

---

### Step 2️⃣: Greedy Row Placement

For each row position `i`:

We need a row that has at least:

```
needed = n - i - 1
```

trailing zeros.

If current row doesn’t satisfy it:

👉 Look below to find the first row that satisfies it
👉 Bring it upward using adjacent swaps
👉 Count swaps

If no such row exists → return `-1`

---

## 📊 Example 1

```
grid = [[0,0,1],
        [1,1,0],
        [1,0,0]]
```

### Step 1: Count trailing zeros

```
Row 0 → 0
Row 1 → 1
Row 2 → 2

zeros = [0,1,2]
```

---

### Step 2: Process each row

#### i = 0

Need `n - i - 1 = 2` zeros

Find row with ≥2 zeros → row 2

Bring row 2 to index 0:

```
[0,1,2]
→ swap
[0,2,1]
→ swap
[2,0,1]
```

Swaps = 2

---

#### i = 1

Need `1` zero

Row 1 has 0 → not enough
Row 2 has 1 → good

Swap once:

Swaps = 3

---

#### i = 2

Need `0` → already satisfied

Final Answer = **3**

---

## ❌ When Do We Return -1?

If at some position `i`:

No row below has enough trailing zeros.

That means it's impossible to satisfy diagonal condition.

Example:

```
[[0,1,1,0],
 [0,1,1,0],
 [0,1,1,0],
 [0,1,1,0]]
```

All rows have only 1 trailing zero.

But first row needs 3 zeros → impossible.

---

## 🧩 Why This Works (Greedy Proof Intuition)

* We always place the nearest valid row to minimize swaps.
* Moving a row upward affects only local order.
* Since only adjacent swaps are allowed, this is optimal.
* Similar to **bubble sort style minimum adjacent swaps**.

---

## ⏱ Time Complexity

* Counting zeros → **O(n²)**
* Swapping rows → worst case **O(n²)**

Total:

```
O(n²)
```

Constraints allow up to `n = 200`, so this works fine.

---

## 🧠 Code Logic Breakdown

```java
int[] zeros = new int[n];
```

Stores trailing zeros count.

---

```java
for (int j = n - 1; j >= 0 && grid[i][j] == 0; j--)
    count++;
```

Counts trailing zeros from right side.

---

```java
int needed = n - i - 1;
```

Minimum zeros required for row `i`.

---

```java
while (j < n && zeros[j] < needed) j++;
```

Find first row that satisfies requirement.

---

```java
while (j > i)
```

Bring it upward using adjacent swaps.

---

## 🎯 Final Understanding

We are:

* Converting the matrix condition
* Into a trailing zero requirement
* Then greedily placing correct rows
* Using minimum adjacent swaps

---
