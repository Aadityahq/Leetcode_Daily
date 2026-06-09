## Key Observation

We need to choose **exactly `k` subarrays**.

A subarray's value is:

[
\text{value} = \max(\text{subarray}) - \min(\text{subarray})
]

The total value is the sum of the values of the chosen subarrays.

### Very Important Detail

The problem states:

> The exact same subarray can be chosen more than once.

So if we find the **best possible subarray value**, we can simply choose that same subarray `k` times.

Therefore:

[
\text{Maximum Total Value}
==========================

k \times (\text{Maximum Subarray Value})
]

So the problem reduces to:

> Find the maximum value of `max(subarray) - min(subarray)` among all subarrays.

---

## Finding the Maximum Subarray Value

Let:

* `globalMax` = maximum element in the entire array
* `globalMin` = minimum element in the entire array

For any subarray:

[
\max(\text{subarray}) \le globalMax
]

and

[
\min(\text{subarray}) \ge globalMin
]

Therefore:

[
\max(\text{subarray}) - \min(\text{subarray})
\le
globalMax - globalMin
]

So no subarray can have a value larger than:

[
globalMax - globalMin
]

Can we achieve it?

Yes.

Take the subarray that spans from the position of `globalMin` to the position of `globalMax` (or vice versa). That subarray contains both values, so:

[
\max = globalMax
]

[
\min = globalMin
]

Thus its value is exactly:

[
globalMax - globalMin
]

Hence:

[
\text{Maximum Subarray Value}
=============================

globalMax - globalMin
]

and

[
\boxed{
\text{Answer}
=============

k \times (globalMax - globalMin)
}
]

---

## Example 1

### Input

```text
nums = [1,3,2]
k = 2
```

### Step 1

```text
globalMax = 3
globalMin = 1
```

### Step 2

```text
bestValue = 3 - 1 = 2
```

### Step 3

```text
answer = 2 × 2 = 4
```

Output:

```text
4
```

---

## Example 2

### Input

```text
nums = [4,2,5,1]
k = 3
```

### Step 1

```text
globalMax = 5
globalMin = 1
```

### Step 2

```text
bestValue = 5 - 1 = 4
```

### Step 3

```text
answer = 3 × 4 = 12
```

Output:

```text
12
```

---

## Algorithm

1. Find the minimum element in the array.

2. Find the maximum element in the array.

3. Compute:

   ```text
   bestValue = max - min
   ```

4. Return:

   ```text
   k * bestValue
   ```

---

## Java Solution

```java
class Solution {
    public long maxTotalValue(int[] nums, int k) {
        long min = Long.MAX_VALUE;
        long max = Long.MIN_VALUE;

        for (int num : nums) {
            min = Math.min(min, num);
            max = Math.max(max, num);
        }

        return (max - min) * k;
    }
}
```

---

## Why This Works

* Every subarray value is at most `globalMax - globalMin`.
* A subarray containing both the global maximum and global minimum achieves exactly that value.
* Since repeating the same subarray is allowed, choosing that optimal subarray `k` times gives the maximum possible total.

Therefore:

[
\boxed{\text{Maximum Total Value} = k \times (globalMax - globalMin)}
]

---

## Complexity Analysis

* **Time Complexity:** `O(n)`

  * One pass to find the minimum and maximum.

* **Space Complexity:** `O(1)`

  * Only a few variables are used.
