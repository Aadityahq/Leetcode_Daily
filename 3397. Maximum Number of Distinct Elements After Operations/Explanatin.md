
## 🧩 Problem: Maximum Number of Distinct Elements After Operations

### 📝 Description

You are given:

* An integer array `nums`
* An integer `k`

You can **modify each element at most once**, by **adding any integer between `-k` and `k`** (inclusive).

Your goal is to find the **maximum number of distinct elements** that can exist in `nums` after performing these operations.

---

### 🧠 Example

#### Example 1:

```
Input: nums = [1, 2, 2, 3, 3, 4], k = 2
Output: 6
```

✅ We can make:

```
nums → [-1, 0, 1, 2, 3, 4]
```

All elements are now **distinct**, giving a total of **6 unique values**.

---

#### Example 2:

```
Input: nums = [4, 4, 4, 4], k = 1
Output: 3
```

✅ We can make:

```
nums → [3, 4, 5, 4]  (by adding -1, 0, +1, 0 respectively)
```

Unique values = {3, 4, 5} → **3 distinct elements**.

---

## ⚙️ How to Think About the Problem

### 🔍 Observation 1: Range of Reachable Values

Each element `x` in the array can be changed to **any value** in the range:

```
[x - k, x + k]
```

So each number gives us a **continuous range** of possible values.

Example:

```
x = 4, k = 2 → range = [2, 6]
```

If multiple elements have **overlapping ranges**, we can use those overlaps to make each one slightly different, ensuring more distinct numbers.

---

### 🔍 Observation 2: Sorting Helps

If we **sort the array**, we can handle numbers from **left to right**, greedily choosing the **smallest possible new number** for each element that hasn’t been used yet.

Why sorting works:

* It ensures we assign distinct values in increasing order.
* Prevents conflicts (two numbers ending up the same).

---

### 💡 Idea (Greedy Approach)

We can think of assigning **the smallest possible new number** to each element:

1. Sort `nums`.
2. Use a `Set` or variable to track the **last assigned distinct number**.
3. For each `num` in sorted order:

   * Its minimum possible value = `num - k`
   * Its maximum possible value = `num + k`
   * Assign the smallest possible number **greater than the last used one** within `[num - k, num + k]`.

If we can assign a new distinct value, increase the count.

---

### ✅ Why This Works

This approach ensures that:

* We always use **unique** values.
* We **maximize** distinct elements since we always pick the smallest unused number possible (greedy choice).
* Sorting guarantees that earlier (smaller) elements don't block the range of larger ones.

---

## 🧩 Example Walkthrough

```
nums = [1, 2, 2, 3, 3, 4], k = 2
Sorted = [1, 2, 2, 3, 3, 4]
```

| Step | num | Range   | Smallest available distinct | Assigned | Distinct so far     |
| ---- | --- | ------- | --------------------------- | -------- | ------------------- |
| 1    | 1   | [-1, 3] | -1                          | -1       | {-1}                |
| 2    | 2   | [0, 4]  | 0                           | 0        | {-1, 0}             |
| 3    | 2   | [0, 4]  | 1                           | 1        | {-1, 0, 1}          |
| 4    | 3   | [1, 5]  | 2                           | 2        | {-1, 0, 1, 2}       |
| 5    | 3   | [1, 5]  | 3                           | 3        | {-1, 0, 1, 2, 3}    |
| 6    | 4   | [2, 6]  | 4                           | 4        | {-1, 0, 1, 2, 3, 4} |

✅ Total distinct = 6

---

## 💻 Java Solution

```java
import java.util.*;

class Solution {
    public int maxDistinctElements(int[] nums, int k) {
        Arrays.sort(nums);
        int count = 0;
        long last = Long.MIN_VALUE;  // last used distinct number

        for (int num : nums) {
            long minVal = (long) num - k;
            long maxVal = (long) num + k;

            // Assign smallest possible number > last
            long assign = Math.max(minVal, last + 1);

            // Only count if within range
            if (assign <= maxVal) {
                count++;
                last = assign;
            }
        }

        return count;
    }
}
```

---

## 🧩 Complexity Analysis

| Aspect               | Complexity                       |
| -------------------- | -------------------------------- |
| **Time Complexity**  | O(n log n) — due to sorting      |
| **Space Complexity** | O(1) — only a few variables used |

---

### 🏁 Summary

* Sort the array.
* Use a greedy approach to assign the smallest distinct number possible to each element.
* This ensures the **maximum number of distinct elements**.

✅ **Efficient and elegant!**

---
