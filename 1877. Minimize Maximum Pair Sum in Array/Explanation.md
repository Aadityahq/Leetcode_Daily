# 1877. Minimize Maximum Pair Sum in Array
## 🔍 Problem Understanding (In Simple Words)

You are given an **even-length array** `nums`.

Your task:

* Pair up all numbers into `n/2` pairs
* Each number must be used **exactly once**
* Among all pairs, look at the **maximum pair sum**
* **Minimize that maximum pair sum**

👉 You’re not minimizing the total sum, you’re minimizing the **worst (largest) pair sum**.

---

## 💡 Key Insight (Very Important)

To minimize the **maximum pair sum**, we should:

> **Pair the smallest number with the largest number**

Why?

* If you pair large numbers together, their sum becomes very big ❌
* If you balance large numbers with small ones, the sums stay controlled ✅

This is a **greedy strategy**.

---

## 🧠 Optimal Strategy (How & Why)

### Step-by-step logic:

1. **Sort the array**
2. Use **two pointers**:

   * `left` → smallest element
   * `right` → largest element
3. Pair `nums[left] + nums[right]`
4. Track the **maximum** of all such pair sums
5. Move pointers inward

This guarantees:

* No pair becomes unnecessarily large
* The largest sum is as small as possible

---

## ✨ Example Walkthrough

### Example: `nums = [3,5,2,3]`

1. Sort → `[2,3,3,5]`
2. Pairs:

   * `(2,5) = 7`
   * `(3,3) = 6`
3. Maximum pair sum = `7` ✅ (minimum possible)

---

## ✅ Java Solution

```java
import java.util.Arrays;

class Solution {
    public int minPairSum(int[] nums) {
        // Step 1: Sort the array
        Arrays.sort(nums);
        
        int left = 0;
        int right = nums.length - 1;
        int maxPairSum = 0;
        
        // Step 2: Pair smallest with largest
        while (left < right) {
            int pairSum = nums[left] + nums[right];
            maxPairSum = Math.max(maxPairSum, pairSum);
            left++;
            right--;
        }
        
        return maxPairSum;
    }
}
```

---

## ⏱️ Time & Space Complexity

* **Time Complexity:** `O(n log n)`
  (due to sorting)
* **Space Complexity:** `O(1)`
  (ignoring sort’s internal space)

---

## 🧪 Why This Always Works

This is a classic **greedy + sorting** problem.

By always pairing:

* smallest ↔ largest
* second smallest ↔ second largest
* …

You **balance every pair**, ensuring no single pair dominates the maximum sum.

---

## 🎯 Final Takeaway

* Sort the array
* Use two pointers
* Pair extremes
* Track the maximum

This approach is:
✅ Optimal
✅ Efficient
✅ Easy to implement


