# 3350. Adjacent Increasing Subarrays Detection II

### 🧠 Problem Understanding

You are given an integer array `nums`.
You need to find the **maximum possible value of `k`** such that there exist **two adjacent strictly increasing subarrays** of length `k` each.

In other words, there should exist an index `a` such that:

```
nums[a .. a+k-1] and nums[a+k .. a+2k-1]
```

are both strictly increasing,
and the second subarray starts immediately after the first.

---

### ⚙️ **How to Think About It**

Let’s look at the array as a collection of **increasing segments** — stretches where each element is strictly greater than the previous one.

Example:

```
nums = [2,5,7,8,9,2,3,4,3,1]
Increasing runs:
[2,5,7,8,9] → length = 5
[2,3,4]     → length = 3
[3]         → length = 1
[1]         → length = 1
```

If we have two consecutive increasing runs of lengths `prev` and `curr`,
the maximum possible `k` from this pair is:

```
min(prev, curr)
```

because both subarrays must have at least `k` elements to form two adjacent increasing subarrays of length `k`.

Additionally, within a **single long increasing run**, we can split it into two halves —
so even if there’s only one run, it can contain two adjacent increasing subarrays by dividing it in the middle.

For a run of length `L`, we can take:

```
k = L / 2   (integer division)
```

---

### 🧩 **Step-by-Step Logic**

We use one pass through the array with two main ideas:

1. **Find each strictly increasing run** — determine its length `curr`.
2. **Combine it with the previous run (`prev`)** — update the result using:

   ```
   res = max(res, max(curr / 2, min(prev, curr)))
   ```

   * `curr / 2`: maximum `k` possible **within** the current increasing run.
   * `min(prev, curr)`: maximum `k` possible **across** two adjacent runs.

Then move to the next run and repeat.

---

### 💡 **Why This Works**

This logic captures **both possibilities**:

1. Two adjacent increasing runs — e.g., `[7,8,9]` and `[2,3,4]`.
2. A single long increasing run — e.g., `[1,2,3,4,5,6]` which can be split into `[1,2,3]` and `[4,5,6]`.

No binary search or extra arrays are needed — just compare segment lengths.

---

### 🧮 **Example Walkthrough**

#### Example 1

```
nums = [2,5,7,8,9,2,3,4,3,1]
```

| Increasing Run | Length | Computation                | res |
| -------------- | ------ | -------------------------- | --- |
| [2,5,7,8,9]    | 5      | res = max(0, 5/2) = 2      | 2   |
| [2,3,4]        | 3      | res = max(2, min(5,3)) = 3 | 3   |
| [3]            | 1      | res = max(3, min(3,1)) = 3 | 3   |

✅ **Answer = 3**

---

#### Example 2

```
nums = [1,2,3,4,4,4,4,5,6,7]
```

| Increasing Run | Length | Computation                | res |
| -------------- | ------ | -------------------------- | --- |
| [1,2,3,4]      | 4      | res = max(0, 4/2) = 2      | 2   |
| [4]            | 1      | res = max(2, min(4,1)) = 2 | 2   |
| [4,5,6,7]      | 4      | res = max(2, min(1,4)) = 2 | 2   |

✅ **Answer = 2**

---

### ⏱️ **Time Complexity**

* Single traversal of `nums`: **O(n)**

### 💾 **Space Complexity**

* Only uses a few variables: **O(1)**

---

### ✅ **Final Answer**

| Input                 | Output |
| --------------------- | ------ |
| [2,5,7,8,9,2,3,4,3,1] | 3      |
| [1,2,3,4,4,4,4,5,6,7] | 2      |

---


