# LeetCode 2333: Minimum Sum of Squared Difference

Difficulty: Medium Language: Java Approach: Greedy + Binary Search / Leveling Goal: Minimize the sum of squared differences between two arrays.

## 1. Understand the problem

You are given two arrays, `nums1` and `nums2`, of the same length.

You can change their elements:

- `nums1` at most `k1` times.
- `nums2` at most `k2` times.
- Each operation increases or decreases one element by `1`.

Your task is to make the sum of squared differences as small as possible.

For each index, calculate:

\\[ d_i = |nums1[i] - nums2[i]| \\]

Then minimize:

\\[ \sum\_{i=0}^{n-1} d_i^2 \\]

Important observation: Changing either array by one unit can reduce a difference by one unit. Therefore, we can combine both operation limits:

\\[ k = k_1 + k_2 \\]

Now we only need to reduce the differences using at most `k` operations.

## 2. Example walkthrough

Consider Example 2:

```
nums1 = [1, 4, 10, 12]
nums2 = [5, 8, 6, 9]

k1 = 1
k2 = 1
```

First, calculate the absolute differences.

Initial differences

## [4, 4, 4, 3]

Initial squared sum

\\[ 4^2+4^2+4^2+3^2=57 \\]

We have \\(k=1+1=2\\) operations.

Reduce the largest differences first.

Before

## [4, 4, 4, 3]

After 2 ops

## [3, 3, 4, 3]

\\[ 3^2+3^2+4^2+3^2=43 \\]

Minimum sum = 43

Why reduce the largest differences first? Because squaring makes large differences much more expensive. Reducing a difference from \\(4\\) to \\(3\\) saves \\(16-9=7\\), whereas reducing a difference from \\(3\\) to \\(2\\) saves only \\(9-4=5\\).

So, each operation should reduce a currently largest difference whenever possible.

## 3. Java solution — Greedy + Binary Search

This approach works efficiently even when `k1` and `k2` are as large as \\(10^9\\).

```

class Solution {
    public long minSumSquareDiff(int[] nums1, int[] nums2, int k1, int k2) {
        int n = nums1.length;
        int[] diff = new int[n];

        int maxDiff = 0;
        long totalDiff = 0;

        // Step 1: Calculate absolute differences
        for (int i = 0; i < n; i++) {
            diff[i] = Math.abs(nums1[i] - nums2[i]);
            maxDiff = Math.max(maxDiff, diff[i]);
            totalDiff += diff[i];
        }

        // Step 2: Combine both operation limits
        long k = (long) k1 + k2;

        // If all differences can become zero
        if (k >= totalDiff) {
            return 0L;
        }

        // Step 3: Binary search for the optimal level
        int low = 0;
        int high = maxDiff;

        while (low < high) {
            int mid = low + (high - low) / 2;
            long operations = 0;

            for (int d : diff) {
                if (d > mid) {
                    operations += d - mid;
                }
            }

            if (operations <= k) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }

        int level = low;
        long used = 0;
        long answer = 0;

        // Step 4: Reduce all differences to at most 'level'
        for (int d : diff) {
            if (d > level) {
                used += d - level;
                d = level;
            }

            answer += (long) d * d;
        }

        // Step 5: Use remaining operations to reduce
        // some differences from level to level - 1
        long remaining = k - used;
        answer -= remaining * (2L * level - 1);

        return answer;
    }
}

```

## 4. How and why does the solution work?

Let's understand each step in simple terms.

### Step 1: Calculate the differences

For every index, calculate:

```
diff[i] = Math.abs(nums1[i] - nums2[i]);
```

For example:

```
nums1 = [1, 4, 10, 12]
nums2 = [5, 8, 6, 9]

diff = [4, 4, 4, 3]
```

We no longer need to modify the original arrays. Only the differences matter.

### Step 2: Combine the operations

```
long k = (long) k1 + k2;
```

Suppose `k1 = 1` and `k2 = 1`.

We have two total operations. An operation on either array can reduce the absolute difference at a selected index by one, so we can treat the operations as one shared budget.

We use `long` because the sum can reach \\(2 \times 10^9\\).

### Step 3: Check whether all differences can become zero

```
if (k >= totalDiff) {
    return 0L;
}
```

If the total number of units across all differences is no greater than the available operations, we can make every pair equal.

For example:

```
diff = [2, 1, 3]
totalDiff = 6
k = 10
```

We need only six operations to make all differences zero. The answer is `0`.

### Step 4: Find the optimal level using binary search

This is the most important part.

Imagine that we want every difference to be at most `mid`.

The operations required are:

\\[ \text{operations}=\sum_i \max(0,d_i-mid) \\]

For example, if:

```
diff = [4, 4, 4, 3]
mid = 3
```

Then:

```
4 -> 3 requires 1 operation
4 -> 3 requires 1 operation
4 -> 3 requires 1 operation
3 -> 3 requires 0 operations

Total = 3 operations
```

If we have only two operations, level `3` is too low because we need three operations to reach it.

If we have enough operations to reach a level, we try a smaller level. Otherwise, we try a larger level.

That is why binary search works: as the target level increases, the number of required operations never increases.

### Step 5: Calculate the squared sum

After binary search, `level` is the smallest level we can reach within the operation budget.

We reduce every difference greater than `level` down to `level`, and calculate the squared sum.

```
if (d > level) {
    used += d - level;
    d = level;
}

answer += (long) d * d;
```

Now some operations might remain unused.

For example, if we have two remaining operations and the current differences are:

```
[3, 3, 3, 2]
```

We can reduce two of the `3`s to `2`:

```
[2, 2, 3, 2]
```

Each reduction from `level` to `level - 1` saves:

\\[ level^2-(level-1)^2=2level-1 \\]

Therefore, we can calculate the final answer without looping over every remaining operation:

```
answer -= remaining * (2L * level - 1);
```

This is particularly important because the operation count can be billions.

## 5. Dry run

Use the second example with `k = 2`.

| Difference | Initial value |
| ---------- | ------------- |
| `diff[0]`  | 4             |
| `diff[1]`  | 4             |
| `diff[2]`  | 4             |
| `diff[3]`  | 3             |

Binary search finds `level = 3`.

Reducing all differences to at most `3` requires three operations, which is too many. However, the optimal level under a budget of two is actually `4` if interpreting the threshold test alone. To avoid confusion, let's see the exact search behavior:

- At `mid = 2`, required operations = \\(2+2+2+1=7\\).
- At `mid = 3`, required operations = \\(1+1+1+0=3\\).
- At `mid = 4`, required operations = \\(0\\).

The smallest feasible level is therefore 4. The remaining two operations reduce two differences from `4` to `3`.

The final differences are:

```
[3, 3, 4, 3]
```

The final sum is:

\\[ 3^2+3^2+4^2+3^2=\boxed{43} \\]

## 6. Complexity analysis

Let \\(n\\) be the length of the arrays and \\(M\\) be the maximum absolute difference.

- Time complexity: \\(O(n\log M)\\), because binary search takes \\(O(\log M)\\) iterations and each iteration scans the array.
- Space complexity: \\(O(n)\\), because we store the difference array.

Given the constraints, \\(M \leq 10^5\\), so binary search needs at most about 17 iterations.

## 7. Key points to remember

- Convert the two arrays into absolute differences.
- Combine `k1` and `k2` into a single operation budget.
- Reduce larger differences first because they have a greater impact on the squared sum.
- Use binary search to find the smallest feasible maximum difference.
- Handle unused operations mathematically rather than processing them one by one.
- Use `long` for the answer because the squared sum can exceed the `int` range.

Interview takeaway: This problem is a good example of combining a greedy observation with binary search on the answer.