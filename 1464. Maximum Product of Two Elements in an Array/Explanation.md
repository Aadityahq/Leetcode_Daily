# 1464. Maximum Product of Two Elements in an Array

## Problem Explanation

You are given an integer array `nums`.

You need to choose **two different elements** and calculate:

[
(nums[i] - 1) \times (nums[j] - 1)
]

Your task is to return the **maximum possible value** of this expression.

### Example

```text
nums = [3,4,5,2]
```

Possible pairs:

* (3,4) → (2 × 3) = 6
* (3,5) → (2 × 4) = 8
* (4,5) → (3 × 4) = 12 ✅
* ...

Maximum = **12**

---

# Key Observation

The expression is

```text
(nums[i] - 1) * (nums[j] - 1)
```

Subtracting 1 from every number **does not change which numbers are the largest**.

For example,

```text
5 becomes 4
4 becomes 3
3 becomes 2
```

The largest numbers remain the largest.

So, to maximise the product:

* Find the largest number.
* Find the second largest number.
* Return

```text
(max1 - 1) * (max2 - 1)
```

---

# Why does this work?

Suppose the array is

```text
[3, 4, 5, 2]
```

Largest numbers are

```text
5 and 4
```

Their product after subtracting one is

```text
(5-1)*(4-1)
=4*3
=12
```

If we choose smaller numbers:

```text
(5-1)*(3-1)
=4*2
=8
```

which is smaller.

Since multiplication increases with larger positive numbers, choosing the **two largest elements always gives the maximum product**.

---

# Approach

We only need two variables:

* `max1` → largest element
* `max2` → second largest element

Traverse the array once.

For every number:

* If it is larger than `max1`

  * Move `max1` into `max2`
  * Update `max1`
* Else if it is larger than `max2`

  * Update `max2`

Finally return

```java
(max1 - 1) * (max2 - 1)
```

---

# Dry Run

### Input

```text
nums = [3,4,5,2]
```

Initial

```text
max1 = 0
max2 = 0
```

### num = 3

```text
3 > max1

max2 = 0
max1 = 3
```

### num = 4

```text
4 > max1

max2 = 3
max1 = 4
```

### num = 5

```text
5 > max1

max2 = 4
max1 = 5
```

### num = 2

```text
2 < max2

No change
```

Finally

```text
max1 = 5
max2 = 4
```

Answer

```text
(5-1)*(4-1)

=4*3

=12
```

---

# Java Solution

```java
class Solution {
    public int maxProduct(int[] nums) {
        int max1 = 0;
        int max2 = 0;

        for (int num : nums) {
            if (num > max1) {
                max2 = max1;
                max1 = num;
            } else if (num > max2) {
                max2 = num;
            }
        }

        return (max1 - 1) * (max2 - 1);
    }
}
```

---

# Time Complexity

We traverse the array only once.

```text
Time Complexity: O(n)
```

where `n` is the length of the array.

---

# Space Complexity

We only use two variables.

```text
Space Complexity: O(1)
```

---

# Why this algorithm is optimal

A brute-force solution would check every pair:

```text
for every i
    for every j
```

Time Complexity:

```text
O(n²)
```

But we don't need to compare every pair because the maximum product always comes from the **two largest numbers**. By finding those two numbers in a single pass, we reduce the complexity to **O(n)** while using constant extra space.

---

# Interview Takeaway

Whenever a problem asks for the **maximum product** (or sum) of two positive numbers after applying the **same operation** (like subtracting 1 from each), first ask yourself:

> **"Do I only need the two largest values?"**

In this problem, the answer is **yes**, because subtracting 1 preserves the ordering of the numbers, so the two largest elements still produce the maximum product.
