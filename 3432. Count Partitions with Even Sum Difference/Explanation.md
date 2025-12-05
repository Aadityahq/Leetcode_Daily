# ✅ **Understanding the Problem (Simple Explanation)**

You are given an array `nums`.
A **partition** is choosing an index `i` such that:

* Left subarray = `nums[0..i]`
* Right subarray = `nums[i+1..n-1]`

Both subarrays must be **non-empty**, so valid `i` is from `0` to `n-2`.

For each partition, compute:

```
difference = sum(left) - sum(right)
```

We must count how many partitions produce an **even** difference.

---

# ✅ **Key Insight (WHY the solution works)**

We need:

```
(sum(left) - sum(right)) % 2 == 0
```

Since:

```
sum(right) = totalSum - sum(left)
```

Substitute:

```
difference = sum(left) - (totalSum - sum(left))
           = 2 * sum(left) - totalSum
```

Now check parity:

```
difference is even  ↔  (2 * sumLeft - totalSum) is even
```

`2 * sumLeft` is **always even**
→ so parity depends ONLY on `totalSum`.

Thus:

```
Even - totalSum must be even
→ totalSum must be even
```

### ⭐ **FINAL CONCLUSION**

* If `totalSum` is **odd**, then **0 partitions** will have even difference.
* If `totalSum` is **even**, then **ALL partitions** (n-1 of them) are valid.

---

# ✅ **Why? Example Verification**

### Example 2:

```
nums = [1,2,2]
totalSum = 5 (odd)
→ Answer = 0
```

Matches the problem output.

### Example 3:

```
nums = [2,4,6,8]
totalSum = 20 (even)
→ Answer = n - 1 = 3
```

Matches the problem output.

### Example 1:

```
nums = [10, 10, 3, 7, 6]
totalSum = 36 (even)
→ Answer = n - 1 = 4
```

Matches the problem output.

---

# ✅ **Time Complexity**

Only computing the total sum:

```
O(n)
```

---

# ✅ **Java Solution**

```java
class Solution {
    public int countPartitions(int[] nums) {
        int n = nums.length;

        // Step 1: Compute total sum
        int totalSum = 0;
        for (int x : nums) {
            totalSum += x;
        }

        // Step 2: If total sum is odd → 0 partitions possible
        if (totalSum % 2 != 0) {
            return 0;
        }

        // Step 3: All (n - 1) partitions are valid
        return n - 1;
    }
}
```

---

# 🎯 **Summary**

| Condition             | Result                     |
| --------------------- | -------------------------- |
| Total sum is **odd**  | 0 valid partitions         |
| Total sum is **even** | All `n-1` partitions valid |

Reason:
`difference = 2 * sum(left) - totalSum` → always even when `totalSum` is even.

---


