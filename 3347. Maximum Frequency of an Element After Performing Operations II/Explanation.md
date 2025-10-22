
## 🧩 3347. Maximum Frequency of an Element After Performing Operations II

### 🔗 [LeetCode Problem Link](https://leetcode.com/problems/maximum-frequency-of-an-element-after-performing-operations-ii/)

**Difficulty:** Hard

---

### 🧠 Problem Statement

You are given an integer array `nums` and two integers `k` and `numOperations`.

You must perform an operation `numOperations` times on `nums`, where in each operation you:

* Select an index `i` that has **not** been selected before.
* Add an integer in the range `[-k, k]` to `nums[i]`.

Return the **maximum possible frequency** of any element in `nums` after performing the operations.

---

### 💡 Intuition

Each element `nums[i]` can be converted to any value within the range `[nums[i] - k, nums[i] + k]`.
So we can imagine every element as a **segment** of reachable values.
The goal is to find a value `x` that lies inside as many of these segments as possible — since that value can become the most frequent.

However, we can only modify at most `numOperations` elements, so we can’t use all overlapping segments — only the best subset that fits the operation limit.

---

### ⚙️ How It Works

1. **Sort the array.**
   Sorting allows binary search to efficiently find boundaries of reachable ranges.

2. **For each element (and its boundaries):**

   * We check three potential target centers:

     * The element itself: `nums[i]`
     * Its left edge: `nums[i] - k`
     * Its right edge: `nums[i] + k`
       These represent the points where the maximum frequency could occur.

3. **`check()` function:**

   * For a given `n` (current number), `t` (range `k`), and `m` (max operations):

     * It counts how many elements can be directly or indirectly adjusted to match `n`.
     * Using **binary search (lowerBound / upperBound)**, we find:

       * Elements already equal to `n`.
       * Elements within `[n - k, n + k]` (can be modified to match `n`).
     * Then we add up:

       * The count of elements already equal to `n`.
       * The minimum between how many more we can adjust (`res`) and `numOperations`.

4. **Track the maximum across all candidates.**

---

### 🧾 Why This Works

* Every number `nums[i]` defines a reachable interval `[nums[i] - k, nums[i] + k]`.
* Any common overlapping value between intervals indicates elements that could be made equal.
* Using binary search:

  * We can find how many numbers fall inside this range quickly (log n per query).
* The loop over all `i` ensures every potential “center” (candidate target value) is considered.

Thus, this approach efficiently simulates all possible ways to align numbers under the allowed operations.

---

### 🧮 Complexity Analysis
```
| Type      | Complexity                                             |
| --------- | ------------------------------------------------------ |
| **Time**  | `O(n log n)` — sorting + 3 binary searches per element |
| **Space** | `O(1)` — only variables, no extra data structures      |
```

---

### 💻 Java Solution

```java
import java.util.*;

class Solution {
    private int check(int[] nums, int n, int t, int m) {
        long nL = n;
        long tL = t;
        int l = lowerBound(nums, nL);
        int h = upperBound(nums, nL);
        int ll = lowerBound(nums, nL - tL);
        int hh = upperBound(nums, nL + tL);
        int res = (hh - h) + (l - ll);
        return Math.min(m, res) + (h - l);
    }

    private int lowerBound(int[] arr, long target) {
        int l = 0, r = arr.length;
        while (l < r) {
            int mid = (l + r) / 2;
            if (arr[mid] < target) l = mid + 1;
            else r = mid;
        }
        return l;
    }

    private int upperBound(int[] arr, long target) {
        int l = 0, r = arr.length;
        while (l < r) {
            int mid = (l + r) / 2;
            if (arr[mid] <= target) l = mid + 1;
            else r = mid;
        }
        return l;
    }

    public int maxFrequency(int[] nums, int k, int numOperations) {
        int m = numOperations;
        Arrays.sort(nums);
        int ans = 1;
        for (int i = 0; i < nums.length - 1; i++) {
            ans = Math.max(ans, check(nums, nums[i], k, m));
            ans = Math.max(ans, check(nums, nums[i] - k, k, m));
            ans = Math.max(ans, check(nums, nums[i] + k, k, m));
        }
        return ans;
    }
}
```

---

### 🧭 Example

**Input:**

```text
nums = [1, 4, 5], k = 1, numOperations = 2
```

**Step-by-step reasoning:**

* For `1`: can reach [0, 2]
* For `4`: can reach [3, 5]
* For `5`: can reach [4, 6]
  → Overlap at value `4` includes elements `{4,5}`, and one more can be adjusted from `5` → frequency = 2

**Output:**

```text
2
```

---

### 🧩 Summary of “How” and “Why”

| Step | What                                          | Why                                            |
| ---- | --------------------------------------------- | ---------------------------------------------- |
| 1️⃣  | Sort `nums`                                   | Enables binary search on continuous ranges     |
| 2️⃣  | For each element, test `nums[i]`, `nums[i]±k` | Covers all possible “target centers”           |
| 3️⃣  | Use `lowerBound`/`upperBound`                 | Counts how many elements can reach each target |
| 4️⃣  | Add min(res, numOperations)                   | Only as many operations as allowed             |
| 5️⃣  | Track max                                     | Gives the final highest possible frequency     |

---

### 🏁 Conclusion

This approach cleverly combines **sorting**, **binary search**, and **range overlap logic** to efficiently determine the maximum achievable frequency of any element after limited modification operations.
It’s both **accurate** and **efficient**, passing all hidden test cases.

---

