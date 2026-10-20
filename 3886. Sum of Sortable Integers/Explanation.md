# LeetCode 3886 — Sum of Sortable Integers

Let's understand the exact Java solution you are using, step by step, including how and why it works.

## 1. Problem in simple words

You are given an array `nums`. You need to find every divisor `k` of the array length `n` for which the array can be sorted using cyclic rotations of consecutive subarrays of length `k`.

Finally, return the sum of all valid `k` values.

For example:

`nums = [3, 1, 2]`

- `k = 1`: Each block has one element, so we cannot change the order. Invalid.
- `k = 3`: We can rotate `[3, 1, 2]` into `[1, 2, 3]`. Valid.

Answer: `3`.

## 2. Java solution

```
import java.util.*;

class Solution {
    public int sortableIntegers(int[] nums) {
        int n = nums.length;
        int[] sorted = nums.clone();
        Arrays.sort(sorted);

        int ans = 0;

        for (int k = 1; k <= n; k++) {
            if (n % k != 0) {
                continue;
            }

            boolean valid = true;

            for (int start = 0; start < n; start += k) {
                int descents = 0;

                for (int j = 0; j < k; j++) {
                    int current = nums[start + j];
                    int next = nums[start + (j + 1) % k];

                    if (current > next) {
                        descents++;
                    }
                }

                if (descents > 1) {
                    valid = false;
                    break;
                }

                int min = nums[start];
                for (int j = 1; j < k; j++) {
                    min = Math.min(min, nums[start + j]);
                }

                int minSorted = sorted[start];
                if (min != minSorted) {
                    valid = false;
                    break;
                }

                int[] block = Arrays.copyOfRange(nums, start, start + k);
                Arrays.sort(block);

                for (int j = 0; j < k; j++) {
                    if (block[j] != sorted[start + j]) {
                        valid = false;
                        break;
                    }
                }

                if (!valid) {
                    break;
                }
            }

            if (valid) {
                ans += k;
            }
        }

        return ans;
    }
}
```

## 3. Explanation of the code

### Step 1: Find the length and create a sorted copy

```
int n = nums.length;
int[] sorted = nums.clone();
Arrays.sort(sorted);
```

Suppose:

`nums = [3, 1, 2]`

After these statements:

- `n = 3`
- `nums = [3, 1, 2]`
- `sorted = [1, 2, 3]`

Why do we create a copy?

We need the original array to check whether rotations can sort it. The sorted copy gives us the target order without changing the original array.

### Step 2: Initialize the answer

```
int ans = 0;
```

This variable stores the sum of all valid values of `k`.

### Step 3: Check every possible `k`

```
for (int k = 1; k <= n; k++) {
    if (n % k != 0) {
        continue;
    }
```

The length of every block must divide the total array length exactly.

For `n = 6`:

- `k = 1`: valid divisor
- `k = 2`: valid divisor
- `k = 3`: valid divisor
- `k = 4`: skipped
- `k = 5`: skipped
- `k = 6`: valid divisor

The `continue` statement skips values that cannot divide the array into equal-sized blocks.

### Step 4: Assume the current `k` is valid

```
boolean valid = true;
```

Initially, we assume the value of `k` works. If any block fails a required condition, we change `valid` to `false`.

### Step 5: Divide the array into blocks

```
for (int start = 0; start < n; start += k) {
```

The variable `start` indicates the first index of each block.

For `nums = [3, 1, 2, 6, 4, 5]` and `k = 3`, the blocks are:

- Block 1: `[3, 1, 2]`
- Block 2: `[6, 4, 5]`

The value of `start` becomes `0`, then `3`.

### Step 6: Count circular descents

```
int descents = 0;

for (int j = 0; j < k; j++) {
    int current = nums[start + j];
    int next = nums[start + (j + 1) % k];

    if (current > next) {
        descents++;
    }
}
```

A descent occurs when the current element is greater than the next element.

For example, `[3, 1, 2]`:

| Comparison | Result     |
| ---------- | ---------- |
| `3 > 1`    | Descent    |
| `1 > 2`    | No descent |
| `2 > 3`    | No descent |

There is one circular descent, so the block can be rotated into non-decreasing order.

Why use `(j + 1) % k`?

This expression makes the last element connect back to the first element.

For `k = 3`:

- `j = 0` → next index `1`
- `j = 1` → next index `2`
- `j = 2` → next index `0`

That final comparison is important because the array is treated as circular when checking rotations.

```
if (descents > 1) {
    valid = false;
    break;
}
```

If there is more than one circular descent, the block cannot be sorted by rotation. We mark the candidate invalid and stop checking its blocks.

### Step 7: Find the minimum element of the block

```
int min = nums[start];

for (int j = 1; j < k; j++) {
    min = Math.min(min, nums[start + j]);
}

int minSorted = sorted[start];

if (min != minSorted) {
    valid = false;
    break;
}
```

This code finds the smallest element in the original block and compares it with the first element of the corresponding sorted block.

For example:

- Original block: `[3, 1, 2]`
- Minimum of the block: `1`
- Corresponding sorted block starts with `1`

The comparison passes.

However, checking only the minimum is not sufficient to prove that the elements match. That is why the next step checks all the elements.

### Step 8: Compare the block's elements

```
int[] block = Arrays.copyOfRange(nums, start, start + k);
Arrays.sort(block);

for (int j = 0; j < k; j++) {
    if (block[j] != sorted[start + j]) {
        valid = false;
        break;
    }
}
```

First, `Arrays.copyOfRange()` extracts the current block.

Then, `Arrays.sort(block)` sorts that temporary block.

Finally, we compare it with the corresponding portion of the globally sorted array.

For example:

- Original block: `[3, 1, 2]`
- Sorted temporary block: `[1, 2, 3]`
- Target block: `[1, 2, 3]`

They match.

Important: The actual rotation condition is the circular descent check. The element comparison verifies that the block contains the elements needed for its target section in the globally sorted array.

### Step 9: Add valid `k` values

```
if (valid) {
    ans += k;
}
```

If every block passes the checks, we add `k` to the answer.

### Step 10: Return the answer

```
return ans;
```

Once every divisor has been checked, the function returns the sum of the valid values.

## 4. Dry run

Input:

`nums = [3, 1, 2]`

Sorted copy:

`sorted = [1, 2, 3]`

For `k = 1`

Invalid

Each block has one element. The original order `[3, 1, 2]` cannot be changed by rotating single-element blocks.

`ans = 0`

For `k = 3`

Valid

There is one block: `[3, 1, 2]`.

- Circular descents: `1`
- Minimum: `1`, matching the first element of the sorted array
- Sorted block: `[1, 2, 3]`, matching the target

`ans = 0 + 3 = 3`

Output: `3`

## 5. Complexity analysis

Let \\(d\\) be the number of divisors of \\(n\\).

- Sorting the original copy takes \\(O(n\log n)\\).
- Checking circular descents and block elements takes \\(O(n)\\) per divisor.
- Sorting individual blocks adds sorting work across the blocks.

The overall time complexity is \\(O(n\log n + dn\log n)\\) as a safe upper bound, and the auxiliary space complexity is \\(O(n)\\).

## One important correction

The code above is not a complete solution for the original problem. The checks are not sufficient to guarantee that rotating each block makes the entire array sorted: a block must be rotatable into its exact target segment, and the boundary between adjacent blocks must also be non-decreasing. For example, checking each block's minimum and its circular descents does not verify every boundary condition.

So while the explanation describes what each part of this code does, I would not recommend submitting this version as a guaranteed-correct solution.