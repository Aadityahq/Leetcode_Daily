## 📘 Problem Explanation

You are given an integer array `nums`.

A **subarray** (continuous part of the array) is called **balanced** if:

> **Number of distinct even numbers = Number of distinct odd numbers**

Your task is to **find the maximum length** of such a balanced subarray.

---

## 🔎 Key Points to Notice

1. We care about **distinct values**, not total count

   * `[2,2,2]` → distinct evens = 1
   * `[3,3]` → distinct odds = 1

2. The subarray must be **continuous**.

3. We must check **all possible subarrays** to find the longest one.

---

## 💡 Intuition Behind the Solution

Since:

* `nums.length ≤ 1500`
* We are allowed an **O(n²)** solution

We can:

* Fix a starting index `i`
* Extend the subarray to the right using index `j`
* Track distinct evens and odds using **HashSet**

This allows us to efficiently count **unique** even and odd numbers in the current subarray.

---

## 🛠️ Approach (How the Solution Works)

### Step-by-step:

1. Initialize `maxLen = 0`
2. Loop over all starting indices `i`
3. For each `i`, create:

   * `HashSet evenSet` → stores distinct even numbers
   * `HashSet oddSet` → stores distinct odd numbers
4. Extend subarray from `i` to `j`

   * If `nums[j]` is even → add to `evenSet`
   * Else → add to `oddSet`
5. If:

   ```
   evenSet.size() == oddSet.size()
   ```

   then the subarray is **balanced**
6. Update:

   ```
   maxLen = max(maxLen, j - i + 1)
   ```

---

## 🧪 Example Walkthrough

### Input

```
nums = [3, 2, 2, 5, 4]
```

Subarray: `[3, 2, 2, 5, 4]`

* Distinct evens → `{2, 4}` → count = 2
* Distinct odds → `{3, 5}` → count = 2

✔ Balanced
✔ Length = 5 (maximum)

---

## ⏱️ Time & Space Complexity

### Time Complexity

* Two nested loops → **O(n²)**
* HashSet operations are **O(1)**

### Space Complexity

* HashSets store at most `n` elements → **O(n)**

---

## 🧠 Why This Solution Is Correct

* We check **every possible subarray**
* We accurately track **distinct values**
* We only update the answer when the balance condition is satisfied
* Constraints allow this approach to run efficiently

---

## ✨ Final Java Code (Reference)

```java
import java.util.*;

class Solution {
    public int longestBalanced(int[] nums) {
        int n = nums.length;
        int maxLen = 0;

        for (int i = 0; i < n; i++) {
            Set<Integer> evenSet = new HashSet<>();
            Set<Integer> oddSet = new HashSet<>();

            for (int j = i; j < n; j++) {
                if (nums[j] % 2 == 0) {
                    evenSet.add(nums[j]);
                } else {
                    oddSet.add(nums[j]);
                }

                if (evenSet.size() == oddSet.size()) {
                    maxLen = Math.max(maxLen, j - i + 1);
                }
            }
        }
        return maxLen;
    }
}
```

---

