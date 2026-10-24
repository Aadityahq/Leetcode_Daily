# 3644. Maximum K to Sort a Permutation

**Difficulty:** Medium  
**Topics:** Array, Bit Manipulation  
**Language:** Java

## 1. Problem Statement

You are given an integer array `nums` of length `n`. It is a permutation of numbers from `0` to `n - 1`.

You can swap two elements `nums[i]` and `nums[j]` only when:

```java
(nums[i] & nums[j]) == k
```

Here, `&` represents the bitwise AND operator.

Your task is to find the **maximum possible value of `k`** such that you can sort the array in non-decreasing order using any number of valid swaps.

If the array is already sorted, return `0`.

## 2. Understanding the Problem

Consider Example 1:

```text
Input:  nums = [0, 3, 2, 1]
Output: 1
```

The sorted array should be:

```text
[0, 1, 2, 3]
```

The elements `3` and `1` are in the wrong positions.

We can swap them because:

```text
3 & 1 = 1
```

After swapping:

```text
[0, 1, 2, 3]
```

Therefore, `k = 1` works.

Can we choose a larger value, such as `k = 2`? No. The misplaced elements `3` and `1` cannot be swapped when `k = 2`, because their bitwise AND is `1`.

Hence, the answer is `1`.

## 3. Important Observation

The main observation is that **an element that is already in its correct position does not need to move, but every misplaced element must be movable**.

An element is in its correct position when:

```java
nums[i] == i
```

For example:

```text
nums = [0, 3, 2, 1]
```

| Index `i` | `nums[i]` | Correct position? |
|---:|---:|---|
| 0 | 0 | Yes |
| 1 | 3 | No |
| 2 | 2 | Yes |
| 3 | 1 | No |

Only the elements `3` and `1` are misplaced.

Now, consider the bitwise AND of these misplaced elements:

```text
3 & 1 = 1
```

This gives us the maximum possible value of `k`.

**Why?** Every valid swap requires both elements to contain all the set bits of `k`. Therefore, every misplaced value must contain those bits. The maximum possible `k` is the bitwise AND of all misplaced values.

## 4. Java Solution

```java
class Solution {
    public int sortPermutation(int[] nums) {
        int k = -1;

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != i) {
                k &= nums[i];
            }
        }

        return k == -1 ? 0 : k;
    }
}
```

## 5. Code Explanation: How and Why?

### Step 1: Initialize `k`

```java
int k = -1;
```

We initialize `k` with `-1`, whose binary representation has all bits set to `1` in Java's signed integer representation.

This lets us use bitwise AND to accumulate the common set bits of misplaced elements.

For example:

```text
  1111
& 0011
------
  0011
```

The initial value does not remove any set bits from the first value being processed.

### Step 2: Traverse the array

```java
for (int i = 0; i < nums.length; i++) {
```

We check every element and compare it with its index.

Because the array is a permutation of `0` through `n - 1`, the correct sorted position of value `x` is index `x`.

### Step 3: Find misplaced elements

```java
if (nums[i] != i) {
```

If the element does not equal its index, it is misplaced and must participate in the rearrangement.

Correctly placed elements do not restrict the maximum value of `k`, so we skip them.

### Step 4: Calculate the bitwise AND

```java
k &= nums[i];
```

This is shorthand for:

```java
k = k & nums[i];
```

We keep only the bits that are set in every misplaced element.

Why is this important?

For a valid swap:

```java
(nums[i] & nums[j]) == k
```

Every set bit of `k` must also be present in both swapped values. Therefore, those bits must be common to every misplaced value.

Taking the AND of all misplaced values gives the largest bitmask satisfying this requirement.

### Step 5: Handle an already sorted array

```java
return k == -1 ? 0 : k;
```

If no element is misplaced, the loop never changes `k`, so it remains `-1`.

In that case, the array is already sorted, and the problem requires returning `0`.

Otherwise, return the calculated bitmask.

## 6. Dry Run

Consider:

```text
nums = [0, 3, 2, 1]
```

Initially:

```text
k = -1
```

| Index | `nums[i]` | Condition | Calculation | `k` |
|---:|---:|---|---|---:|
| 0 | 0 | `0 == 0` | Skip | -1 |
| 1 | 3 | `3 != 1` | `-1 & 3` | 3 |
| 2 | 2 | `2 == 2` | Skip | 3 |
| 3 | 1 | `1 != 3` | `3 & 1` | 1 |

Final result:

```text
Output: 1
```

The maximum valid value is `1`.

## 7. More Examples

### Example 1

```text
Input:  [0, 1, 3, 2]
```

Misplaced elements are `3` and `2`.

```text
3 & 2 = 2
```

Output:

```text
2
```

### Example 2

```text
Input:  [3, 2, 1, 0]
```

Every element is misplaced.

```text
3 & 2 = 2
2 & 1 = 0
0 & 0 = 0
```

Output:

```text
0
```

### Example 3

```text
Input:  [0, 1, 2, 3]
```

Every element is already in its correct position. No calculation is needed.

Output:

```text
0
```

## 8. Complexity Analysis

**Time Complexity: `O(n)`**

We traverse the array exactly once. Each comparison and bitwise AND operation takes constant time.

**Space Complexity: `O(1)`**

We use only a constant number of additional variables, regardless of the array size.

## 9. Key Takeaways

- The correct position of each value `x` is index `x`.
- Only misplaced elements need to move.
- The bits of `k` must be present in every misplaced value.
- The bitwise AND of all misplaced values gives the maximum valid `k`.
- If no element is misplaced, return `0`.

**Remember:** Whenever a problem asks for the common set bits of several integers, think about using bitwise AND (`&`).