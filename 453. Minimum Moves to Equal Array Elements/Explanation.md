# LeetCode 453 – Minimum Moves to Equal Array Elements



Java Solution

Easy Approach



The main concept behind this problem is Mathematical Observation. We can solve it using a single traversal of the array.

Let's understand the problem from scratch, including why the mathematical formula works.

## 1. Understanding the problem

You are given an integer array `nums`.

Your task is to make all elements of the array equal using the minimum number of moves.

Operation allowed:

In one move, you can increment exactly `n - 1` elements by `1`.

Here, `n` is the size of the array.

For example:

```
nums = [1, 2, 3]
```

Array size = 3.

Therefore, in every move, we can increment 2 elements by 1.

Let's perform the operations:

Initially

# 1

# 2

# 3

Move 1: Increment 2 elements (2 and 3).

# 2

# 3

# 3

Move 2: Increment 2 elements (2 and 3).

# 3

# 4

# 3

Move 3: Increment 2 elements (1 and 3).

# 4

# 4

# 4

All elements are equal in 3 moves

Output: `3`

## 2. Think about the operation carefully

Here is the most important observation.

Suppose we have:

```
nums = [1, 2, 3]
```

In one operation, we increment `n - 1` elements.

Since the array has 3 elements, we increment 2 elements.

Instead of thinking:

\- Increment two elements by 1.

We can think:

\- Decrement the remaining one element by 1.

Why?

Because incrementing all elements does not change the difference between any two elements.

For example:

```
[1, 2, 3]
```

Increment the last two elements:

```
[1, 3, 4]
```

Now subtract 1 from every element:

```
[0, 2, 3]
```

The relative differences are exactly the same as:

```
[1, 2, 3]
```

Therefore, incrementing `n - 1` elements is mathematically equivalent to decrementing one element by 1.

This changes the entire problem!

Instead of increasing elements to make them equal, we can simply decrease all elements until they reach the smallest element.

## 3. Mathematical observation

Consider:

```
nums = [1, 2, 3]
```

The minimum element is:

```
min = 1
```

How many operations are needed?

| Element | Minimum | Operations |
| ------- | ------- | ---------- |
| 1       | 1       | 0          |
| 2       | 1       | 1          |
| 3       | 1       | 2          |

Total operations:

\\[ 0+1+2=3 \\]

Therefore:

\\[ \text{Answer} = \sum\_{i=0}^{n-1}(nums[i]-min) \\]

We can simplify this formula:

\\[ \boxed{Answer = Sum - (n \times min)} \\]

Where:

- `Sum` = Sum of all array elements.
- `n` = Size of the array.
- `min` = Minimum element in the array.

## 4. Java Solution

```

class Solution {
    public int minMoves(int[] nums) {

        int min = nums[0];
        int sum = 0;

        // Find minimum element and calculate sum
        for (int num : nums) {
            min = Math.min(min, num);
            sum += num;
        }

        // Calculate minimum moves
        return sum - (nums.length * min);
    }
}

```

## 5. Dry Run – Step-by-step execution

Let's take:

```
nums = [1, 2, 3]
```

Step 1: Initialize variables

```
int min = nums[0];  // 1
int sum = 0;
```

Step 2: Execute the for-each loop

| Iteration | num | min | sum |
| --------- | --- | --- | --- |
| 1         | 1   | 1   | 1   |
| 2         | 2   | 1   | 3   |
| 3         | 3   | 1   | 6   |

After the loop:

```
min = 1
sum = 6
```

Step 3: Apply the formula

```
return sum - (nums.length * min);
```

Substitute the values:

\\[ =6-(3\times1) \\]

\\[ =6-3 \\]

\\[ \boxed{3} \\]

Output:

```
3
```

## 6. Another Example

Input:

```
nums = [5, 6, 8, 10]
```

Minimum element:

```
min = 5
```

Sum:

```
sum = 5 + 6 + 8 + 10 = 29
```

Array size:

```
n = 4
```

Apply the formula:

\\[ Answer = 29-(4\times5) \\]

\\[ =29-20 \\]

\\[ \boxed{9} \\]

We can also verify:

| Element | Operations to reach 5 |
| ------- | --------------------- |
| 5       | 0                     |
| 6       | 1                     |
| 8       | 3                     |
| 10      | 5                     |
| Total   | 9                     |

## 7. Complexity Analysis

| Complexity        | Explanation                                  |
| ----------------- | -------------------------------------------- |
| Time: \\(O(n)\\)  | We traverse the array once.                  |
| Space: \\(O(1)\\) | We only use two variables (`min` and `sum`). |

We don't need sorting or an additional array.

## 8. Important Interview Questions

Q1. Why do we find the minimum element instead of the maximum element?

Because incrementing `n - 1` elements is equivalent to decrementing one element. Therefore, we can think of reducing every element to the minimum element.

Q2. Why don't we use sorting?

Sorting takes \\(O(n\log n)\\) time. We only need the minimum element and sum, which we can calculate in one traversal using \\(O(n)\\) time.

Q3. Why is the formula `sum - (n * min)`?

Every element needs to decrease by its difference from the minimum element.

\\[ (nums[0]-min)+(nums[1]-min)+\cdots \\]

Taking `min` outside:

\\[ Sum-(n\times min) \\]

Q4. What happens if all elements are already equal?

Example:

```
nums = [4, 4, 4]
```

\\[ Answer=12-(3\times4)=0 \\]

No moves are required.

## 9. Important Java Note

The constraints allow negative numbers, and the answer is guaranteed to fit in a 32-bit integer.

However, the intermediate multiplication and sum can potentially exceed the `int` range for some inputs, even if the final answer fits.

A safer implementation uses `long`:

```

class Solution {
    public int minMoves(int[] nums) {

        long min = nums[0];
        long sum = 0;

        for (int num : nums) {
            min = Math.min(min, num);
            sum += num;
        }

        return (int) (sum - (nums.length * min));
    }
}

```

This is the version I recommend submitting.

## Final takeaway

Remember this one important observation:

> Incrementing `n - 1` elements by 1 is equivalent to decrementing the remaining element by 1, in terms of relative differences.

Therefore, the entire problem becomes:

1. Find the minimum element.
2. Calculate the sum of all elements.
3. Apply `sum - (n * min)`.



Time: O(n)

Space: O(1)



This is a good example of how understanding the operation mathematically can eliminate unnecessary simulation.