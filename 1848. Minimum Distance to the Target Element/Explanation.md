# 🧩 Problem Understanding

You are given:

* An array `nums`
* A value `target`
* A starting index `start`

👉 You need to:

* Find an index `i` such that `nums[i] == target`
* Minimize the distance:

|i - start|

---

# 🧠 Intuition (Why this works)

* There can be **multiple occurrences** of `target`
* From all those positions, we want the one **closest to `start`**
* So we:

  * Check every index
  * Compute distance
  * Take the minimum

Since constraints are small (`n ≤ 1000`), a simple loop is perfect ✅

---

# ⚙️ Approach (Step-by-Step)

1. Initialize:

   ```java
   int minDist = Integer.MAX_VALUE;
   ```

2. Traverse array:

   * If `nums[i] == target`
   * Compute `Math.abs(i - start)`
   * Update minimum

3. Return `minDist`

---

# 💻 Java Solution

```java
class Solution {
    public int getMinDistance(int[] nums, int target, int start) {
        int minDist = Integer.MAX_VALUE;

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == target) {
                int dist = Math.abs(i - start);
                minDist = Math.min(minDist, dist);
            }
        }

        return minDist;
    }
}
```

---

# 🧪 Example Walkthrough

### Input:

```
nums = [1,2,3,4,5]
target = 5
start = 3
```

### Process:

* i = 0 → skip
* i = 1 → skip
* i = 2 → skip
* i = 3 → skip
* i = 4 → nums[4] = 5 ✅

Distance:

```
|4 - 3| = 1
```

👉 Answer = **1**

---

# 🚀 Optimized Thinking (Optional)

Instead of checking all indices, you could:

* Expand from `start` → left & right
* Stop as soon as you find target

BUT:

* Worst case still O(n)
* Code becomes more complex

👉 So **linear scan is best here**

---

# ⏱️ Complexity

* **Time:** O(n)
* **Space:** O(1)

---

# 🎯 Key Takeaway

This is a classic:

> “Find minimum distance among valid positions”

Pattern:

* Scan → filter → compute → minimize

---
