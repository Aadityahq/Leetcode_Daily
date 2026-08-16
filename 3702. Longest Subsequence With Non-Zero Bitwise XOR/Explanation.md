## LeetCode 3702 — Longest Subsequence With Non-Zero Bitwise XOR

### 💡 Main Idea

We need the **longest subsequence whose XOR is not `0`**.

The important observation is:

> We can almost always take **all elements**.

Let:

```text
XOR = nums[0] ^ nums[1] ^ ... ^ nums[n-1]
```

There are only two cases.

### Case 1: Total XOR is non-zero

If the XOR of the entire array is not `0`, then the **entire array itself** is a valid subsequence.

So the answer is simply:

```text
n
```

For example:

```text
nums = [2, 3, 4]

2 ^ 3 ^ 4 = 5
```

Since `5 != 0`, we can take all 3 elements.

**Answer = 3**

---

### Case 2: Total XOR is zero

Now suppose:

```text
nums = [1, 2, 3]

1 ^ 2 ^ 3 = 0
```

Taking all `3` elements doesn't work.

Can we remove just **one element**?

Suppose we remove `x`.

The XOR of the remaining elements is:

```text
totalXOR ^ x
```

Since:

```text
totalXOR = 0
```

we get:

```text
0 ^ x = x
```

Therefore, if there is **any non-zero element**, removing that one element gives us a non-zero XOR.

So:

```text
answer = n - 1
```

Example:

```text
[1, 2, 3]

total XOR = 0
```

Remove `1`:

```text
[2, 3]

2 ^ 3 = 1
```

Non-zero, so answer = `2`.

---

### Special Case: All elements are zero

If:

```text
nums = [0, 0, 0]
```

Then every possible subsequence has XOR:

```text
0
```

So there is no valid subsequence.

Answer:

```text
0
```

---

## Java Solution

```java
class Solution {
    public int longestSubsequence(int[] nums) {
        int xor = 0;
        boolean hasNonZero = false;

        for (int num : nums) {
            xor ^= num;

            if (num != 0) {
                hasNonZero = true;
            }
        }

        // Entire array has non-zero XOR
        if (xor != 0) {
            return nums.length;
        }

        // Total XOR is zero, but we have a non-zero element.
        // Remove that element.
        if (hasNonZero) {
            return nums.length - 1;
        }

        // All elements are zero
        return 0;
    }
}
```

---

## 🔍 Why does removing one element work?

This is the most important part to understand.

Suppose:

```text
nums = [a, b, c, d]
```

and:

```text
a ^ b ^ c ^ d = 0
```

If we remove `b`, the remaining XOR is:

```text
a ^ c ^ d
```

We can write:

```text
(a ^ b ^ c ^ d) ^ b
```

Because:

```text
b ^ b = 0
```

we get:

```text
0 ^ b = b
```

Therefore:

```text
remaining XOR = b
```

If `b != 0`, the remaining XOR is non-zero.

That's why **one non-zero element is enough to construct a subsequence of length `n - 1`**.

---

## 🧠 Algorithm

We only need to calculate two things:

1. XOR of all elements.
2. Whether at least one element is non-zero.

Then:

```text
if total XOR != 0
    answer = n

else if there is a non-zero element
    answer = n - 1

else
    answer = 0
```

### Example 1

```text
nums = [1, 2, 3]

XOR = 1 ^ 2 ^ 3
    = 0
```

There is a non-zero element.

Therefore:

```text
answer = 3 - 1 = 2
```

---

### Example 2

```text
nums = [2, 3, 4]

XOR = 2 ^ 3 ^ 4
    = 5
```

Since:

```text
5 != 0
```

we can take the entire array.

```text
answer = 3
```

---

### Example 3

```text
nums = [0, 0, 0]
```

Total XOR:

```text
0 ^ 0 ^ 0 = 0
```

There is no non-zero element.

Therefore no valid subsequence exists.

```text
answer = 0
```

---

## ⏱️ Complexity

We traverse the array once.

**Time:**

```text
O(n)
```

**Space:**

```text
O(1)
```

This easily handles:

```text
n <= 100000
```

### ⭐ Key takeaway

The entire problem boils down to this:

> **If total XOR is non-zero → take everything.
> If total XOR is zero but there is a non-zero element → remove one element.
> If every element is zero → answer is 0.**

No dynamic programming or subsequence generation is required.
