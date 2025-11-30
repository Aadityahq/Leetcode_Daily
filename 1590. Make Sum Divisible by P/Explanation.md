# 📘 **1590. Make Sum Divisible by P — Explanation (Markdown)**

## 🔵 **Problem Explanation**

You are given:

* An array of **positive integers** `nums`
* A number `p`

Your task:
👉 **Remove the smallest contiguous subarray** such that the sum of the *remaining* elements becomes divisible by `p`.

❗ **Important constraints:**

* You cannot remove the whole array.
* If no such subarray exists, return `-1`.
* You are allowed to remove an empty subarray (i.e., return `0`).

---

## 🔵 **Key Idea Behind the Solution**

Let:

* `S` = total sum of array
* We want: `(S - removed_subarray_sum) % p == 0`

This means:

### 👉 **removed_subarray_sum % p == S % p**

Let:

* `target = S % p`

If `target == 0`, then sum is already divisible → answer is **0**.

Otherwise, we need to find the **shortest subarray** whose **prefix sum difference** equals `target` modulo `p`.

---

## 🔵 **How to Find That Subarray?**

Use **prefix sums with modulo** and a **hashmap**:

### Prefix sum:

`prefix[i] = (nums[0] + ... + nums[i]) % p`

We want a subarray `(l, r)` such that:

```
(prefix[r] - prefix[l]) % p == target
```

Rearranging:

```
prefix[l] == (prefix[r] - target + p) % p
```

We store each prefix sum in a map:

```
map.put(prefix_value, index)
```

Then for each index `r`, compute the needed value and check in map.

We track the **minimum subarray length**.

---

## 🔵 **Why Modulo Works Here?**

Because we only care about divisibility by `p`, so removing subarray with the same remainder `target` ensures remaining sum becomes divisible.

---

## 🟢 **Time Complexity**

* `O(n)` using hashmap
* Works for **100,000** elements

---

# ✅ **Java Solution (Clean & Optimal)**

```java
import java.util.*;

class Solution {
    public int minSubarray(int[] nums, int p) {
        long total = 0;
        for (int x : nums) total += x;

        int target = (int)(total % p);
        if (target == 0) return 0; // Already divisible

        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, -1); // prefix before start

        long prefix = 0;
        int result = nums.length;

        for (int i = 0; i < nums.length; i++) {
            prefix = (prefix + nums[i]) % p;
            int need = (int)((prefix - target + p) % p);

            if (map.containsKey(need)) {
                result = Math.min(result, i - map.get(need));
            }

            map.put((int)prefix, i);
        }

        return result == nums.length ? -1 : result;
    }
}
```

---

# 🧠 **Example Walkthrough**

### Example:

`nums = [3,1,4,2], p = 6`

* Total sum = 10 → `10 % 6 = 4` → need subarray with sum `%6 == 4`
* Subarray `[4]` has sum `4`
* Remove it → remaining sum = 6 → divisible

So answer = **1**

---
