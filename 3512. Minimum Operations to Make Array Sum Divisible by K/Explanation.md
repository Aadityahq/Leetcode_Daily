# ✅ **Problem Explanation**

You are given:

* An array of integers → `nums`
* A number → `k`
* You can perform this operation any number of times:

👉 **Choose any index i and do:**

```
nums[i] = nums[i] - 1
```

This means:

* Each operation reduces the total **sum of the array by 1**.

---

## 🎯 **Goal**

Make the **sum of the array divisible by k**
using the **minimum number of operations**.

---

# 🔍 Step-by-Step Logic

Let:

```
sum = total sum of the array
rem = sum % k
```

If `rem == 0`
→ The sum is already divisible by k.
→ **Answer = 0** operations.

---

## ❗ If `rem != 0`

The sum is not divisible by k.

To make sum divisible by k, we want:

```
(sum - operations) % k == 0
```

This means:

* We must reduce the total sum by **exactly `rem`**.

Because:

```
(sum - rem) % k == 0
```

So the **minimum** number of operations needed is:

```
operations = rem
```

---

# 🧠 WHY is this always enough?

Because:

### ✔️ 1. Every operation reduces the *total sum* by 1

So to reduce total sum by `rem`, we simply do `rem` operations.

---

### ✔️ 2. We can choose **any element**

There is no restriction that an element cannot go below 0
(as examples show subtracting until values become 0).

Example 3:

```
[3,2], k=6
sum=5
rem=5
```

They subtract 3 from 3 → becomes 0
and subtract 2 from 2 → becomes 0

So the array allows reducing any element any number of times.

---

### ✔️ 3. We don't need to think about which element to reduce

Because every element reduces the sum by the same amount: **1 per operation**.

---

# ✔️ Final Formula

```
Answer = sum(nums) % k
```

---

# 📌 Examples Explained

## Example 1

```
nums = [3, 9, 7], k = 5
sum = 19
rem = 19 % 5 = 4
```

So we need **4 operations** → reduce sum by 4.

---

## Example 2

```
nums = [4, 1, 3], k = 4
sum = 8
rem = 0
```

Already divisible → **0 operations**.

---

## Example 3

```
nums = [3, 2], k=6
sum = 5
rem = 5
```

We need to reduce sum by **5** → 5 operations.

---

# ✅ Final Java Code (Correct method name)

```java
class Solution {
    public int minOperations(int[] nums, int k) {
        int sum = 0;
        for (int num : nums) sum += num;
        
        return sum % k;
    }
}
```

---
