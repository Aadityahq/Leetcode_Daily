
# 3349. Adjacent Increasing Subarrays Detection I

**Difficulty:** Easy  
**Topics:** Arrays, Sliding Window  

---

## 🧩 Problem Statement

Given an array `nums` of `n` integers and an integer `k`, determine whether there exist **two adjacent subarrays** of length `k` such that both subarrays are **strictly increasing**.

Specifically, check if there are two subarrays starting at indices `a` and `b` (`a < b`), where:

- Both subarrays `nums[a..a + k - 1]` and `nums[b..b + k - 1]` are strictly increasing.  
- The subarrays must be **adjacent**, meaning `b = a + k`.

Return `true` if it is possible to find two such subarrays, and `false` otherwise.

---

## 🧠 Example

### Example 1
```

Input: nums = [2,5,7,8,9,2,3,4,3,1], k = 3
Output: true

```

**Explanation:**
- Subarray starting at index 2 → `[7, 8, 9]` → strictly increasing ✅  
- Subarray starting at index 5 → `[2, 3, 4]` → strictly increasing ✅  
- They are adjacent since `5 = 2 + 3`  
Hence, result = `true`.

---

### Example 2
```

Input: nums = [1,2,3,4,4,4,4,5,6,7], k = 5
Output: false

````
No two adjacent strictly increasing subarrays of length 5 exist here.

---

## ⚙️ How It Works

We use a **sliding window** approach:
1. Slide a window of size `k` through the array and check if it is **strictly increasing**.
2. For each position `i`, check:
   - First subarray: `nums[i .. i + k - 1]`
   - Second subarray: `nums[i + k .. i + 2k - 1]`
3. If both are strictly increasing → return `true`.
4. If the loop ends without finding such a pair → return `false`.

---

## 🧩 Why This Works

- The **adjacency condition** `b = a + k` means the second subarray starts immediately after the first.
- By checking both subarrays at every valid starting index `i`, we ensure that we don’t miss any valid pair.
- Since `n ≤ 100`, a simple nested check is efficient enough.

---

## ⏱️ Complexity Analysis

| Complexity | Explanation |
|-------------|--------------|
| **Time:** O(n × k) | For each possible start index `i`, we check `k` elements in both subarrays. |
| **Space:** O(1) | We only use constant extra space. |

---

## 💻 Java Solution

```java
import java.util.*;

class Solution {
    public boolean hasIncreasingSubarrays(List<Integer> nums, int k) {
        int n = nums.size();

        for (int i = 0; i <= n - 2 * k; i++) {
            if (isIncreasing(nums, i, k) && isIncreasing(nums, i + k, k)) {
                return true;
            }
        }
        return false;
    }

    // Helper function to check if subarray nums[start .. start + k - 1] is strictly increasing
    private boolean isIncreasing(List<Integer> nums, int start, int k) {
        for (int i = start; i < start + k - 1; i++) {
            if (nums.get(i) >= nums.get(i + 1)) {
                return false;
            }
        }
        return true;
    }
}
````

---

## ✅ Key Takeaways

* Use **sliding windows** to analyze adjacent subarrays.
* The **adjacency rule (`b = a + k`)** ensures non-overlapping windows.
* Works efficiently for small arrays (`n ≤ 100`).
* Reinforces understanding of subarray traversal and comparison logic.

---

**Author’s Note:**
This problem helps build a clear understanding of window-based traversal and adjacency logic — both fundamental patterns in array problems.

```

