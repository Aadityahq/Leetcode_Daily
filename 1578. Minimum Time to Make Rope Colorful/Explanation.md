## 🧩 Problem Understanding

We are given:

* A string `colors`, where each character represents a balloon’s color.
* An array `neededTime`, where `neededTime[i]` is the time required to remove the `i-th` balloon.

We need to make the rope **colorful**, meaning **no two adjacent balloons have the same color**.

To achieve that, Bob can remove balloons — and we must find the **minimum total time** required to do so.

---

## 🎯 Goal

Ensure **no two consecutive balloons** have the same color while minimizing the total removal time.

---

## 🤔 Why This Problem Happens

If there are consecutive balloons with the **same color**, one must be removed — otherwise, the condition "no two consecutive same colors" is violated.

For example:

```
colors = "aabaa"
```

Here, groups of same colors are:

* `"aa"` → we need to remove one of them
* `"aa"` (at the end) → we need to remove one again

So the problem boils down to:

> For each group of consecutive same-colored balloons, remove all except the one with the **maximum neededTime**.

---

## ⚙️ How to Solve (Step-by-Step Logic)

1. **Initialize a variable** `totalTime = 0` — this will store our answer.
2. **Iterate through the colors** from left to right.
3. Whenever we find two consecutive balloons with the same color:

   * We can’t keep both, so we remove the one that takes **less time**.
   * Add that smaller time to `totalTime`.
   * Keep the balloon with the **larger neededTime**, because keeping the more expensive balloon minimizes future removals.
4. Continue until all balloons are checked.

---

## 🧠 Example Walkthrough

### Example:

```
colors = "abaac"
neededTime = [1,2,3,4,5]
```

Step-by-step:

| i | colors[i] | Compare with prev | Action             | totalTime | Keep balloon time |
| - | --------- | ----------------- | ------------------ | --------- | ----------------- |
| 0 | a         | —                 | —                  | 0         | 1                 |
| 1 | b         | different         | —                  | 0         | 2                 |
| 2 | a         | different         | —                  | 0         | 3                 |
| 3 | a         | same              | remove smaller (3) | +3        | 4                 |
| 4 | c         | different         | —                  | 3         | 5                 |

✅ Final `totalTime = 3`

---

## 💻 Java Code Solution

```java
class Solution {
    public int minCost(String colors, int[] neededTime) {
        int totalTime = 0;
        int n = colors.length();
        
        for (int i = 1; i < n; i++) {
            if (colors.charAt(i) == colors.charAt(i - 1)) {
                // Remove the smaller one and keep the larger one
                totalTime += Math.min(neededTime[i], neededTime[i - 1]);
                
                // Update the current balloon’s time to the max one (as if we kept the larger)
                neededTime[i] = Math.max(neededTime[i], neededTime[i - 1]);
            }
        }
        return totalTime;
    }
}
```

---

## 🧩 Complexity Analysis

| Type     | Complexity | Explanation                      |
| -------- | ---------- | -------------------------------- |
| ⏱️ Time  | **O(n)**   | We traverse the array once       |
| 💾 Space | **O(1)**   | We use only constant extra space |

---

## 🏁 Summary

✅ We minimize time by always keeping the **most expensive balloon** in a same-color group.
✅ We add the **smaller removal times** to the total whenever a conflict occurs.
✅ Efficient single-pass solution with clear logic.

---
