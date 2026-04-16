# 🧠 Problem Explanation

You are given:

* A **circular array** `nums`
* A list of **query indices**

For each query index `idx`:

1. Look at the value `nums[idx]`
2. Find another index `j` such that:

   * `nums[j] == nums[idx]`
   * `j ≠ idx`
3. Return the **minimum circular distance** between `idx` and `j`

---

## 🔄 Circular Distance Concept

In a normal array:

```text
distance = |i - j|
```

But here array is **circular**, so:

👉 You can go forward OR wrap around

So distance becomes:

d = \min(|i - j|,\ n - |i - j|)

---

## ❌ When to return -1

If that value appears only once → no valid `j` exists.

---

# 💡 Intuition Behind Your Solution

Brute force would be:

* For every query → scan whole array → O(n²) ❌

Instead, your idea is:

👉 “Group same values together and search smartly”

---

# ⚙️ Step-by-Step Solution Explanation

## 🔹 Step 1: Build a Map

```java
Map<Integer, List<Integer>> map = new HashMap<>();
```

### What it stores:

```text
value → list of indices where it appears
```

### Example:

```text
nums = [1,3,1,4,1,3,2]

map:
1 → [0,2,4]
3 → [1,5]
4 → [3]
2 → [6]
```

### ✅ Why?

So you don’t scan the whole array again and again.

---

## 🔹 Step 2: Process Each Query

```java
for (int idx : queries)
```

For each index:

* Get value → `nums[idx]`
* Get all positions → `map.get(value)`

---

## 🔹 Step 3: Edge Case

```java
if (list.size() == 1)
```

### ✅ Why?

Only one occurrence → no other equal element → answer = `-1`

---

## 🔹 Step 4: Binary Search

```java
int pos = Collections.binarySearch(list, idx);
```

### ✅ Why binary search?

* `list` is sorted (because indices were added in order)
* We quickly find where `idx` lies in that list

---

## 🔹 Step 5: Check Neighbors Only (Key Insight)

```java
int left = (pos - 1 + list.size()) % list.size();
int right = (pos + 1) % list.size();
```

### 💡 Why only left & right?

Because:

* The list is sorted
* Closest equal index must be:

  * just before OR
  * just after

👉 No need to check all elements

---

### 🔁 Circular Trick

```java
(pos - 1 + size) % size
```

* Handles wrap-around
* If `pos = 0`, left becomes last element

---

## 🔹 Step 6: Compute Distance

```java
int d1 = Math.abs(idx - leftIdx);
int distLeft = Math.min(d1, n - d1);
```

Same for right.

### ✅ Why?

Because array is circular → choose minimum path

---

## 🔹 Step 7: Store Answer

```java
ans.add(Math.min(distLeft, distRight));
```

---

# 🔍 Dry Run

### Input:

```text
nums = [1,3,1,4,1,3,2]
queries = [5]
```

### Step:

* idx = 5 → value = 3
* indices = [1,5]

Binary search:

```text
pos = 1
```

Neighbors:

```text
left = 1
right = 1 (circular)
```

Distance:

```text
|5 - 1| = 4
circular = 7 - 4 = 3
```

✅ Answer = 3

---

# ⏱️ Complexity Analysis

| Operation  | Time           |
| ---------- | -------------- |
| Build map  | O(n)           |
| Each query | O(log n)       |
| Total      | O(n + q log n) |

Space: `O(n)`

---

# 🧠 Why This Approach Works

### ✔ HashMap

Avoids repeated scanning

### ✔ Sorted Indices

Allows binary search

### ✔ Neighbor Check

Reduces work to constant time

---

# 🎯 Interview Summary (Best Answer)

If interviewer asks:

👉 “Explain your approach”

You say:

> “I grouped indices of equal values using a HashMap. For each query, I used binary search to locate the index in its group. Since indices are sorted, the closest equal element must be adjacent, so I only check left and right neighbors and compute circular distance.”

---

# 🚀 Key Pattern You Learned

👉 **"Index Mapping + Binary Search on Positions"**

Used in:

* Closest duplicate problems
* Range queries
* Distance-based problems

---
