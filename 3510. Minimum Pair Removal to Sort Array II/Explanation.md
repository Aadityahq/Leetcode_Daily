# 🧩 Problem Explanation

You are given an array `nums`.

You may repeatedly perform the following operation:

1. Select the **adjacent pair with the minimum sum**
2. If there are multiple such pairs, choose the **leftmost**
3. Replace the pair with their **sum**

Your task is to return the **minimum number of operations** needed to make the array **non-decreasing**
(i.e. `nums[i] ≥ nums[i-1]` for all valid `i`).

---

# ⚠️ Why this problem is tricky

* You **do not choose** which pair to merge — the rule forces it.
* Merging two elements affects their neighbors.
* After each merge, **new adjacent sums appear**, and old ones become invalid.
* The array size shrinks dynamically.

This makes the problem a **forced simulation**, not a greedy or DP problem.

---

# 💡 Core Idea of the Correct Solution

We **simulate the process efficiently**, exactly following the rules, until the array becomes non-decreasing.

To do this in `O(n log n)` time, we use:

### 1️⃣ Doubly linked list (via arrays)

To:

* Remove merged elements
* Reconnect neighbors in constant time

### 2️⃣ Min-heap (PriorityQueue)

To:

* Always extract the **minimum adjacent sum**
* Break ties by choosing the **leftmost pair**

### 3️⃣ Violation counter (`bad`)

To:

* Track how many indices violate the non-decreasing condition
* Stop as soon as the array becomes valid

---

# 🛠️ Step-by-Step Explanation of the Code

---

## 🔹 Data structures

```java
long[] a        // stores current values
int[] l, r      // left and right neighbors (linked list)
boolean[] alive // whether index still exists
```

This lets us “delete” elements without shifting the array.

---

## 🔹 Count initial violations

```java
int bad = 0;
for (int i = 1; i < n; i++)
    if (a[i] < a[i - 1]) bad++;
```

* `bad` counts how many places violate non-decreasing order
* If `bad == 0`, the array is already sorted

---

## 🔹 Min-heap of adjacent sums

```java
PriorityQueue<long[]> pq
```

Each entry:

```
{ sum of adjacent pair, left index }
```

The heap ensures:

* Minimum sum is chosen
* Leftmost pair is chosen if sums are equal

---

## 🔹 Main simulation loop

```java
while (bad > 0) {
```

We keep merging **only while violations exist**.

---

### ✅ Step 1: Get the minimum valid pair

```java
long[] cur = pq.poll();
int i = (int) cur[1];
int j = r[i];
```

We skip heap entries that are:

* Already merged
* Outdated (sum doesn’t match current values)

This is called **lazy deletion**.

---

### ✅ Step 2: Remove old violations

Before merging, the following comparisons may disappear:

* `(l[i], i)`
* `(i, j)`
* `(j, r[j])`

So we decrease `bad` accordingly.

---

### ✅ Step 3: Merge the pair

```java
a[i] += a[j];
alive[j] = false;
r[i] = rj;
if (rj != -1) l[rj] = i;
```

* Replace `(i, j)` with their sum at index `i`
* Remove `j` from the list

---

### ✅ Step 4: Add new violations

After merging:

* Check `(l[i], i)`
* Check `(i, r[i])`

If these violate order, increase `bad`.

---

### ✅ Step 5: Push new adjacent sums

```java
pq.add(new long[]{a[li] + a[i], li});
pq.add(new long[]{a[i] + a[rj], i});
```

These new pairs may become the next minimum.

---

### ✅ Step 6: Count the operation

```java
ops++;
```

---

## 🔹 Termination

The loop stops when:

```java
bad == 0
```

At that point, the array is guaranteed to be non-decreasing **according to the forced merge rules**.

---

# ⏱️ Complexity Analysis

| Metric | Value        |
| ------ | ------------ |
| Time   | `O(n log n)` |
| Space  | `O(n)`       |

Works efficiently for `n ≤ 10⁵`.

---

# 🧪 Example Walkthrough

### Input

```
nums = [5, 2, 3, 1]
```

### Operations

1. Merge `(3,1)` → `[5,2,4]`
2. Merge `(2,4)` → `[5,6]`

Array becomes non-decreasing
✔ Answer = **2**

---

# 🧠 Final One-Line Explanation (LeetCode-style)

> We simulate the forced merging process using a min-heap and a linked list, tracking order violations and stopping once the array becomes non-decreasing.

---
