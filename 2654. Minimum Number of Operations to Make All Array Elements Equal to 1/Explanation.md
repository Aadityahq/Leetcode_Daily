**LeetCode 2654. Minimum Number of Operations to Make All Array Elements Equal to 1** 👇

---

## 🧩 Problem Understanding

We are given an array of **positive integers** `nums`.
We can perform this operation any number of times:

> Choose an index `i` (0 ≤ i < n-1) and replace either `nums[i]` **or** `nums[i+1]` with `gcd(nums[i], nums[i+1])`.

We must find the **minimum number of operations** required to make **all elements equal to 1**.

If it's **impossible**, return `-1`.

---

## 🧠 Key Insight — “When can we make all 1s?”

1. The **only way** to get a `1` is through the **GCD** of numbers.
   → If the **GCD of all elements** in the array is **greater than 1**,
   it means **we can never get a 1** → return `-1`.

   ```text
   gcd(nums[0], nums[1], ..., nums[n-1]) > 1  =>  impossible
   ```

2. If there is already at least **one `1`** in the array:

   * Every other number can be turned into 1 using **adjacent GCD operations**.
   * So the number of operations = `(number of non-1 elements)`.

3. If **no 1s exist**, we need to **create** a 1 using GCD operations.

   * Find the **smallest subarray whose GCD is 1**.
   * Let the length of that subarray = `L`.
   * To create that `1`, it takes `(L - 1)` operations.
   * Once we have one `1`, we still need `(n - 1)` more operations to spread it to all elements.

   So total operations = `(L - 1) + (n - 1)`.

---

## 🧮 Example Walkthrough

### Example 1:

`nums = [2, 6, 3, 4]`

* gcd(2,6,3,4) = 1 → possible
* No existing 1 → must create one.

Find smallest subarray with gcd = 1:

* gcd(2,6)=2
* gcd(6,3)=3
* gcd(3,4)=1 → subarray length L = 2 (indices [2,3])

Total operations:

```
(L - 1) + (n - 1) = (2 - 1) + (4 - 1) = 4
```

✅ Output = 4

---

### Example 2:

`nums = [2, 10, 6, 14]`

* gcd(2,10,6,14) = 2 ≠ 1 → impossible
  ✅ Output = -1

---

## 💡 Time Complexity

* To compute GCD of all subarrays: **O(n²)** (since n ≤ 50, this is fine).
* Each GCD computation is **O(log(max(nums[i])))**.
* So total: **O(n² logM)**, where M = max(nums[i]).

---

## ✅ Java Solution

```java
class Solution {
    public int minOperations(int[] nums) {
        int n = nums.length;
        int overallGcd = nums[0];

        // Step 1: Compute overall GCD of array
        for (int num : nums) {
            overallGcd = gcd(overallGcd, num);
        }

        // If overall gcd > 1, impossible to get 1
        if (overallGcd != 1) return -1;

        // Step 2: Check if there's already a 1
        int countOnes = 0;
        for (int num : nums) {
            if (num == 1) countOnes++;
        }
        if (countOnes > 0) {
            // Each non-1 element requires one operation
            return n - countOnes;
        }

        // Step 3: Find smallest subarray with gcd = 1
        int minLen = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            int currentGcd = nums[i];
            for (int j = i + 1; j < n; j++) {
                currentGcd = gcd(currentGcd, nums[j]);
                if (currentGcd == 1) {
                    minLen = Math.min(minLen, j - i + 1);
                    break; // no need to extend further
                }
            }
        }

        // Step 4: Total operations = (L - 1) + (n - 1)
        return (minLen - 1) + (n - 1);
    }

    // Helper function to compute GCD
    private int gcd(int a, int b) {
        if (b == 0) return a;
        return gcd(b, a % b);
    }
}
```

---

## 🧾 Explanation Summary

| Step | Description                       | Why                                       |
| ---- | --------------------------------- | ----------------------------------------- |
| 1    | Compute overall GCD               | If > 1 → impossible to make 1             |
| 2    | Check if 1 exists                 | Saves operations; only need to fix non-1s |
| 3    | Find smallest subarray with gcd=1 | That’s the minimal effort to create a 1   |
| 4    | Compute total operations          | Create 1 + Spread 1 to rest of array      |

---

