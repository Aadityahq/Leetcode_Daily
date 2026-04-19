# 🧠 Problem Understanding

You are given two arrays:

* `nums1` (non-increasing → decreasing or same)
* `nums2` (non-increasing)

You need to find a pair `(i, j)` such that:

### ✅ Conditions:

1. `i <= j`
2. `nums1[i] <= nums2[j]`

### 🎯 Goal:

Maximize **distance = j - i**

---

# 🔍 Key Observations

* Both arrays are **sorted in non-increasing order**
* This means:

  * Moving right → values **decrease or stay same**
* We want:

  * **small value from nums1**
  * **large value from nums2**
  * and **maximize gap (j - i)**

---

# 💡 Optimal Approach — Two Pointers (Greedy)

Instead of checking all pairs (O(n²) ❌), we use **two pointers**:

### Idea:

* Start `i = 0`, `j = 0`
* Move `j` forward to maximize distance
* Only move `i` when condition breaks

---

# ⚙️ Algorithm

1. Initialize:

   * `i = 0`, `j = 0`, `maxDist = 0`

2. Loop while both pointers are valid:

   * If `nums1[i] <= nums2[j]`:

     * Valid pair → update answer
     * Move `j++` (try to increase distance)
   * Else:

     * Condition fails → move `i++`

---

# 🚀 Why This Works

* Since arrays are sorted:

  * If `nums1[i] > nums2[j]`, increasing `j` won’t help ❌
  * So we must increase `i` to find smaller value
* If valid:

  * Increase `j` to maximize `j - i`

👉 This guarantees **O(n + m)** time complexity

---

# 💻 Java Solution

```java
class Solution {
    public int maxDistance(int[] nums1, int[] nums2) {
        int i = 0, j = 0;
        int maxDist = 0;

        while (i < nums1.length && j < nums2.length) {
            if (nums1[i] <= nums2[j]) {
                maxDist = Math.max(maxDist, j - i);
                j++; // try to expand distance
            } else {
                i++; // need smaller nums1[i]
            }
        }

        return maxDist;
    }
}
```

---

# 🧪 Example Walkthrough

### Input:

```
nums1 = [55,30,5,4,2]
nums2 = [100,20,10,10,5]
```

### Step-by-step:

| i | j | nums1[i] | nums2[j] | Valid? | Distance |
| - | - | -------- | -------- | ------ | -------- |
| 0 | 0 | 55       | 100      | ✅      | 0        |
| 0 | 1 | 55       | 20       | ❌      | -        |
| 1 | 1 | 30       | 20       | ❌      | -        |
| 2 | 2 | 5        | 10       | ✅      | 0        |
| 2 | 3 | 5        | 10       | ✅      | 1        |
| 2 | 4 | 5        | 5        | ✅      | 2 ✅      |

👉 Answer = **2**

---

# ⏱️ Complexity

* **Time:** `O(n + m)`
* **Space:** `O(1)`

---

# 🧠 Intuition Recap (Important for Interviews)

* Use **two pointers because arrays are sorted**
* Always try to:

  * Expand `j` → maximize distance
  * Move `i` only when necessary
* Avoid brute force

---
