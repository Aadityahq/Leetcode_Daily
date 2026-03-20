# 🔍 Problem Understanding

You are given:

* A matrix `grid (m × n)`
* A window size `k`

👉 For **every k × k submatrix**, you must:

1. Take all elements inside it
2. Find **minimum absolute difference between any two distinct values**

---

## 💡 Key Observations

* A k×k submatrix contains **k² elements**
* If all elements are same → answer = **0**
* Otherwise → find minimum difference between any two numbers

---

## 🧠 Important Trick

Instead of comparing **all pairs (O(n²))**, do this:

👉 Sort the elements
👉 Minimum difference will be between **adjacent elements**

---

# ✨ Example Walkthrough

### Input:

```
grid = [[1, -2, 3],
        [2,  3, 5]], k = 2
```

### Submatrix 1:

```
[1, -2]
[2,  3]
```

Flatten → `[1, -2, 2, 3]`
Sort → `[-2, 1, 2, 3]`

Differences:

```
1 - (-2) = 3
2 - 1 = 1   ✅ minimum
3 - 2 = 1
```

Answer = **1**

---

# 🚀 Solution Approach

## Step-by-step

### 1. Slide over all k×k submatrices

```
for i in 0 → m-k
  for j in 0 → n-k
```

---

### 2. Extract elements

```
for x in i → i+k
  for y in j → j+k
```

Store in list `v`

---

### 3. Sort the list

```
Collections.sort(v)
```

---

### 4. Find minimum difference

Compare adjacent elements:

```
for p = 1 → size-1
    diff = v[p] - v[p-1]
```

⚠️ Skip duplicates:

```
if (v[p] != v[p-1])
```

---

### 5. Edge Case

If no valid pair found → all values same → return **0**

---

# 🧾 Code Explanation (Your Code)

```java
List<Integer> v = new ArrayList<>();
```

👉 Stores elements of current submatrix

---

```java
for (int x = i; x < i + k; x++)
    for (int y = j; y < j + k; y++)
        v.add(grid[x][y]);
```

👉 Collect all k² elements

---

```java
Collections.sort(v);
```

👉 Sort to make closest elements adjacent

---

```java
int mn = Integer.MAX_VALUE;
int prev = v.get(0);
```

👉 Initialize minimum difference

---

```java
for (int p = 1; p < v.size(); p++) {
    if (v.get(p) != prev)
        mn = Math.min(mn, v.get(p) - prev);
    prev = v.get(p);
}
```

👉 Compare only neighbors
👉 Skip duplicates

---

```java
ans[i][j] = (mn == Integer.MAX_VALUE) ? 0 : mn;
```

👉 If no difference found → all equal → return 0

---

# ⏱️ Time Complexity

Let:

* Submatrices = `(m-k+1)*(n-k+1)`
* Each submatrix = `k²`

### Total:

```
O((m*n) * k² log(k²))
```

✔ Works because constraints are small (≤ 30)

---

# 🧠 Why Sorting Works?

Instead of:

```
Check all pairs → O(n²)
```

We do:

```
Sort → O(n log n)
Check neighbors → O(n)
```

👉 Huge optimization

---

# ⚡ Interview Insight

If interviewer asks optimization:

👉 This is already optimal for constraints
👉 Advanced optimization possible using:

* Balanced BST / TreeSet (sliding window)
* Frequency map (if small values)

But not needed here

---

# ✅ Final Summary

* Slide k×k window
* Collect elements
* Sort
* Compare adjacent
* Track minimum difference
* Handle duplicate case

---

