## 🔍 Problem Understanding (In Simple Words)

You are given an array `nums`.

You can repeat the following operation any number of times:

1. **Find the adjacent pair with the minimum sum**

   * If there are multiple such pairs, pick the **leftmost** one.
2. **Replace that pair with their sum**

   * This reduces the array length by 1.

Your goal is to make the array **non-decreasing** (each element ≥ previous element) using the **minimum number of operations**.

---

## ✅ What Does “Non-Decreasing” Mean?

An array is non-decreasing if:

```
nums[i] >= nums[i - 1]   for all i > 0
```

Example:

* ✅ `[1, 2, 2, 3]` → non-decreasing
* ❌ `[3, 2, 5]` → decreasing at `3 → 2`

---

## 🧠 Key Observations

1. **If the array is already non-decreasing**, answer is `0`.
2. Every operation:

   * Shrinks the array by 1
   * Potentially removes a “bad” (decreasing) point
3. The problem **forces** which pair you can remove:

   * You must always remove the **adjacent pair with minimum sum**
   * No freedom to choose arbitrarily

Because `n ≤ 50`, we can **simulate the process directly**.

---

## 🛠️ Strategy (How the Solution Works)

### Step 1: Check if array is non-decreasing

If yes → return `0`

### Step 2: While array is NOT non-decreasing

* Find the adjacent pair with the **minimum sum**
* Replace that pair with their sum
* Increment operation count

Repeat until the array becomes non-decreasing.

---

## 💡 Why This Works

* The operation is **deterministic** (no choices to optimize).
* The array is small, so brute-force simulation is safe.
* Each operation reduces array size → loop always terminates.
* We stop as soon as the array becomes valid → minimum operations.

---

## 🧪 Example Walkthrough

### Input

```
[5, 2, 3, 1]
```

### Steps

1. Adjacent sums → `(5,2)=7`, `(2,3)=5`, `(3,1)=4` → min is `(3,1)`

   ```
   → [5, 2, 4]
   ```
2. Adjacent sums → `(5,2)=7`, `(2,4)=6` → min is `(2,4)`

   ```
   → [5, 6]
   ```

Now array is non-decreasing.

✅ **Answer = 2**

---

## 🧾 Java Solution

```java
import java.util.*;

class Solution {
    public int minimumPairRemoval(int[] nums) {
        List<Integer> list = new ArrayList<>();
        for (int x : nums) {
            list.add(x);
        }

        int operations = 0;

        while (!isNonDecreasing(list)) {
            int minSum = Integer.MAX_VALUE;
            int index = 0;

            // Find leftmost adjacent pair with minimum sum
            for (int i = 0; i < list.size() - 1; i++) {
                int sum = list.get(i) + list.get(i + 1);
                if (sum < minSum) {
                    minSum = sum;
                    index = i;
                }
            }

            // Replace the pair with their sum
            list.set(index, minSum);
            list.remove(index + 1);

            operations++;
        }

        return operations;
    }

    // Helper method to check if array is non-decreasing
    private boolean isNonDecreasing(List<Integer> list) {
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i) < list.get(i - 1)) {
                return false;
            }
        }
        return true;
    }
}
```

---

## ⏱️ Time & Space Complexity

* **Time Complexity:**
  ( O(n^2) )
  (Each operation scans the array, and max operations ≤ n)

* **Space Complexity:**
  ( O(n) )
  (Using a list to simulate array changes)

---

## 🎯 Final Takeaway

* This is a **simulation + greedy** problem.
* You don’t choose what’s best — you **follow the rule exactly**.
* Small constraints make brute-force simulation the right choice.
