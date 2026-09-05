n## LeetCode 3904 — Smallest Stable Index II

The key is to avoid calculating the maximum and minimum from scratch for every index.

### 1. Understanding the problem

For every index `i`:

```text
instability score =
    max(nums[0..i]) - min(nums[i..n-1])
```

We need the **smallest** index where:

```text
max(nums[0..i]) - min(nums[i..n-1]) <= k
```

For example:

```text
nums = [5, 0, 1, 4]
k = 3
```

We get:

```text
i = 0 → max([5])       - min([5,0,1,4]) = 5 - 0 = 5
i = 1 → max([5,0])     - min([0,1,4])   = 5 - 0 = 5
i = 2 → max([5,0,1])   - min([1,4])     = 5 - 1 = 4
i = 3 → max([5,0,1,4]) - min([4])       = 5 - 4 = 1
```

Since `1 <= 3`, the answer is `3`.

---

# 2. Brute-force approach

One straightforward approach would be:

For every `i`:

1. Find `max(nums[0..i])`.
2. Find `min(nums[i..n-1])`.
3. Check the difference.

But if we scan the array for both values for every index, the complexity becomes:

```text
O(n²)
```

With `n = 100000`, this is too slow.

So we need to reuse our previous calculations.

---

# 3. The important observation

We can calculate the **suffix minimum** first.

Define:

```text
suffixMin[i] = minimum value from nums[i] to nums[n-1]
```

For:

```text
nums = [5, 0, 1, 4]
```

the suffix minimum array is:

```text
index:       0  1  2  3
nums:        5  0  1  4
suffixMin:   0  0  1  4
```

Why?

```text
suffixMin[3] = 4

suffixMin[2] = min(nums[2], suffixMin[3])
             = min(1, 4)
             = 1

suffixMin[1] = min(0, 1)
             = 0

suffixMin[0] = min(5, 0)
             = 0
```

Therefore:

```text
suffixMin[i] = min(nums[i], suffixMin[i + 1])
```

---

# 4. What about the prefix maximum?

We don't even need another array.

While traversing from left to right, maintain:

```java
prefixMax
```

At index `i`:

```text
prefixMax = max(nums[0..i])
```

And we already know:

```text
suffixMin[i] = min(nums[i..n-1])
```

Therefore the instability score is simply:

```java
prefixMax - suffixMin[i]
```

If:

```java
prefixMax - suffixMin[i] <= k
```

then `i` is stable.

Because we're checking indices from left to right, **the first stable index is automatically the smallest stable index**.

---

# 5. Java Solution

```java
class Solution {
    public int smallestStableIndex(int[] nums, int k) {
        int n = nums.length;

        // suffixMin[i] = minimum element from i to n - 1
        int[] suffixMin = new int[n];

        suffixMin[n - 1] = nums[n - 1];

        for (int i = n - 2; i >= 0; i--) {
            suffixMin[i] = Math.min(nums[i], suffixMin[i + 1]);
        }

        int prefixMax = nums[0];

        for (int i = 0; i < n; i++) {
            prefixMax = Math.max(prefixMax, nums[i]);

            int instability = prefixMax - suffixMin[i];

            if (instability <= k) {
                return i;
            }
        }

        return -1;
    }
}
```

---

# 6. Dry Run

Consider:

```text
nums = [5, 0, 1, 4]
k = 3
```

### Step 1 — Build suffix minimum

Starting from the right:

```text
nums:
[5, 0, 1, 4]

suffixMin:
[0, 0, 1, 4]
```

### Step 2 — Traverse from left

Initially:

```text
prefixMax = 5
```

#### Index 0

```text
prefixMax = max(5, 5) = 5
suffixMin[0] = 0

score = 5 - 0 = 5

5 <= 3 → false
```

Continue.

#### Index 1

```text
prefixMax = max(5, 0) = 5
suffixMin[1] = 0

score = 5 - 0 = 5

5 <= 3 → false
```

Continue.

#### Index 2

```text
prefixMax = max(5, 1) = 5
suffixMin[2] = 1

score = 5 - 1 = 4

4 <= 3 → false
```

Continue.

#### Index 3

```text
prefixMax = max(5, 4) = 5
suffixMin[3] = 4

score = 5 - 4 = 1

1 <= 3 → true
```

Return:

```text
3
```

---

# 7. Why this works

At every index `i`, we need exactly two things:

### Left side

```text
max(nums[0..i])
```

We maintain this using:

```java
prefixMax = Math.max(prefixMax, nums[i]);
```

So after processing index `i`:

```text
prefixMax = max(nums[0..i])
```

### Right side

```text
min(nums[i..n-1])
```

This is precomputed as:

```java
suffixMin[i]
```

Therefore:

```java
prefixMax - suffixMin[i]
```

is exactly the instability score required by the problem.

And because we check:

```text
0 → 1 → 2 → ... → n-1
```

the first index satisfying the condition is guaranteed to be the **smallest stable index**.

---

# 8. Complexity

### Time

Building `suffixMin`:

```text
O(n)
```

Checking every index:

```text
O(n)
```

Total:

```text
O(n)
```

### Space

We store:

```text
suffixMin[]
```

so:

```text
O(n)
```

---

## 9. Can we reduce the space?

Yes.

There's an even nicer observation.

We need the suffix minimum while going **left to right**, so normally we store it in an array.

But the problem asks for the **first** stable index. We can instead search from **right to left** while maintaining the suffix minimum, but then we would need to know the prefix maximum for each index.

We could precompute prefix maxima instead:

```text
prefixMax[i] = max(nums[0..i])
```

Then traverse right to left while maintaining `suffixMin`.

That gives the same `O(n)` space.

So for this problem, the suffix-minimum + running-prefix-maximum solution is already very clean and easy to understand.

---

### The pattern to remember

This problem is a classic **prefix + suffix preprocessing** problem.

Whenever you see something like:

```text
something involving nums[0..i]
+
something involving nums[i..n-1]
```

think:

> **Can I precompute one side and maintain the other side while traversing?**

Here:

```text
max(nums[0..i])  → running prefix maximum
min(nums[i..n-1]) → suffix minimum array
```

giving:

```text
score = prefixMax - suffixMin[i]
```

That reduces the naive `O(n²)` approach to **O(n)**.
