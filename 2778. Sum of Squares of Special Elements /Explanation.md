# LeetCode 2778: Sum of Squares of Special Elements

Difficulty: Easy Topic: Array, Basic Math Language: Java

## 1. Understanding the problem

You are given an integer array `nums` of length `n`.

An element is called special if its 1-based index `i` divides the length of the array `n` completely.

For every special element, we need to:

1. Check whether its index divides `n`.
2. Square the element, meaning `nums[i] * nums[i]`.
3. Add the square to the total sum.
4. Return the final sum.

Important: The problem uses 1-based indexing to define special elements, but Java arrays use 0-based indexing. So, we need to use `nums[i - 1]` to access the element at index `i`.

## 2. Java solution

```

class Solution {
    public int sumOfSquares(int[] nums) {
        int n = nums.length;
        int sum = 0;

        for (int i = 1; i <= n; i++) {
            if (n % i == 0) {
                sum += nums[i - 1] * nums[i - 1];
            }
        }

        return sum;
    }
}

```

## 3. Explanation of the solution: How and why?

Let's understand each part of the code.

### Step 1: Find the array length

```
int n = nums.length;
```

`nums.length` gives the total number of elements in the array.

For example:

```
nums = [1, 2, 3, 4]
```

Here, `n = 4`.

We need `n` because an element is special when its index divides the array length.

### Step 2: Initialize the sum

```
int sum = 0;
```

We use `sum` to store the sum of squares of all special elements.

Initially, the sum is zero because we have not processed any elements.

### Step 3: Traverse the array

```
for (int i = 1; i <= n; i++)
```

We start from `i = 1` because the problem defines special elements using 1-based indexing.

We continue until `i = n`, checking every possible index from `1` to `n`.

### Step 4: Check whether the index is special

```
if (n % i == 0)
```

The `%` operator gives the remainder after division.

- If `n % i == 0`, the index divides `n` completely, so the element is special.
- Otherwise, the element is not special, and we skip it.

For `n = 4`:

| Index `i` | `4 % i` | Special? |
| --------- | ------- | -------- |
| 1         | 0       | Yes      |
| 2         | 0       | Yes      |
| 3         | 1       | No       |
| 4         | 0       | Yes      |

Therefore, the special indices are `1`, `2`, and `4`.

### Step 5: Calculate the square and add it

```
sum += nums[i - 1] * nums[i - 1];
```

Why do we use `i - 1`?

Because the problem uses 1-based indexing, whereas Java arrays start at index `0`.

For example, when `i = 1`, we access `nums[0]`.

The statement above is equivalent to:

```
sum = sum + nums[i - 1] * nums[i - 1];
```

We square the element and add it to the existing sum.

### Step 6: Return the answer

```
return sum;
```

After checking all indices, we return the total sum of squares.

## 4. Dry run with Example 1

Input:

```
nums = [1, 2, 3, 4]
```

Initially, `n = 4` and `sum = 0`.

i = 1

`4 % 1 == 0` → Special

Add 1 × 1 = 1

### Sum = 1

i = 2

`4 % 2 == 0` → Special

Add 2 × 2 = 4

### Sum = 5

i = 3

`4 % 3 != 0` → Not special

Skip this element.

### Sum = 5

i = 4

`4 % 4 == 0` → Special

Add 4 × 4 = 16

### Sum = 21

Final answer

# 21

The special elements are `1`, `2`, and `4`.

\\[ 1^2 + 2^2 + 4^2 = 1 + 4 + 16 = 21 \\]

Output: `21`

## 5. Dry run with Example 2

Input:

```
nums = [2, 7, 1, 19, 18, 3]
```

Here, `n = 6`.

The special indices are `1`, `2`, `3`, and `6`, because each divides `6` completely.

| Index `i` | Element | Square  | Running sum |
| --------- | ------- | ------- | ----------- |
| 1         | 2       | 4       | 4           |
| 2         | 7       | 49      | 53          |
| 3         | 1       | 1       | 54          |
| 4         | 19      | Skipped | 54          |
| 5         | 18      | Skipped | 54          |
| 6         | 3       | 9       | 63          |

Output: `63`

## 6. Complexity analysis

Time complexity: \\(O(n)\\)

We traverse the array once, checking each index from `1` to `n`. Each iteration performs a constant amount of work.

Space complexity: \\(O(1)\\)

We use only two extra variables, `n` and `sum`. No additional array or data structure is needed.

## 7. What should you remember for interviews?

- `%` checks the remainder after division.
- `n % i == 0` checks whether `i` divides `n` completely.
- Java arrays use 0-based indexing, so use `nums[i - 1]` for the problem's 1-based index.
- `sum += value * value` squares a value and adds it to the running total.

The main idea: Check every 1-based index, and whenever it divides the array length, add the square of its corresponding element.