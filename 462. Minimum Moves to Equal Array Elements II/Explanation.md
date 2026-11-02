# LeetCode 462 – Minimum Moves to Equal Array Elements II



Medium

Array · Sorting · Math · Median



## 1. Understand the problem

You are given an integer array `nums`.

You need to make all elements equal using the minimum number of moves.

In one move, you can:

- Increase an element by `1`.
- Decrease an element by `1`.

Your task is to return the minimum total number of moves.

### Example 1

```
nums = [1, 2, 3]
```

Let's make every element equal to `2`.

| Element | Moves required |
| ------- | -------------- |
| 1 → 2   | 1              |
| 2 → 2   | 0              |
| 3 → 2   | 1              |
| Total   | 2              |

Output:

```
2
```

### Example 2

```
nums = [1, 10, 2, 9]
```

First, sort the array:

```
[1, 2, 9, 10]
```

Let's make every element equal to `2` (or `9`).

If we choose `2`:

| Element | Moves |
| ------- | ----- |
| 1 → 2   | 1     |
| 2 → 2   | 0     |
| 9 → 2   | 7     |
| 10 → 2  | 8     |
| Total   | 16    |

Output:

```
16
```

## 2. The most important concept: Median

The key to solving this problem is understanding the Median.

The median is the middle element of a sorted array.

For example:

```
[1, 2, 3, 4, 5]
```

Median = `3`

For an even-sized array:

```
[1, 2, 9, 10]
```

There are two middle elements: `2` and `9`.

Any number between `2` and `9` gives the same minimum number of moves.

We can simply choose either middle element.

Important: The median gives the minimum total distance when we calculate the absolute difference between every element and the target.

## 3. Java Solution

Recommended approach – Sorting + Median

```
import java.util.Arrays;

class Solution {
    public int minMoves2(int[] nums) {

        // Step 1: Sort the array
        Arrays.sort(nums);

        // Step 2: Find the median
        int median = nums[nums.length / 2];

        // Step 3: Calculate total moves
        long moves = 0;

        for (int num : nums) {
            moves += Math.abs((long) num - median);
        }

        // Step 4: Return the total moves
        return (int) moves;
    }
}
```

## 4. Dry Run – Understand the code step by step

Let's take:

```
nums = [1, 10, 2, 9]
```

Step 1: Sort the array

```
Arrays.sort(nums);
```

After sorting:

```
[1, 2, 9, 10]
```

Step 2: Find the median

```
int median = nums[nums.length / 2];
```

Here:

```
nums.length = 4

median = nums[4 / 2]
       = nums[2]
       = 9
```

Our target value is `9`.

Step 3: Calculate the moves

```
long moves = 0;

for (int num : nums) {
    moves += Math.abs((long) num - median);
}
```

Let's calculate each iteration:

| Iteration | num | Calculation   | Moves |
| --------- | --- | ------------- | ----- |
| 1         | 1   | `abs(1 - 9)`  | 8     |
| 2         | 2   | `abs(2 - 9)`  | 7     |
| 3         | 9   | `abs(9 - 9)`  | 0     |
| 4         | 10  | `abs(10 - 9)` | 1     |
|           |     | Total         | 16    |

Therefore:

```
return 16;
```

Output: 16

## 5. Why does the median give the minimum moves?

This is the most important interview concept.

Suppose we have:

```
[1, 2, 3]
```

Let's try different target values.

If target = 1:

```
|1 - 1| + |2 - 1| + |3 - 1|
= 0 + 1 + 2
= 3 moves
```

If target = 2:

```
|1 - 2| + |2 - 2| + |3 - 2|
= 1 + 0 + 1
= 2 moves
```

If target = 3:

```
|1 - 3| + |2 - 3| + |3 - 3|
= 2 + 1 + 0
= 3 moves
```

See the difference?

Comparison of target values

Target = 1

3 moves

Target = 2 (Median)

2 moves

Target = 3

3 moves

The median produces the minimum total moves.

### The mathematical reason

Imagine the sorted array:

```
[1, 2, 3, 4, 5]
```

- If you choose a target smaller than the median, more elements on the right need to move a larger distance.
- If you choose a target greater than the median, more elements on the left need to move a larger distance.
- At the median, the total distance is minimum because the elements on both sides are balanced.

This is why we use the median instead of the average.

Remember:

- Median minimizes the sum of absolute differences.
- Mean (average) is useful for minimizing the sum of squared differences.

## 6. Complexity Analysis

| Complexity | Explanation                                                                  |
| ---------- | ---------------------------------------------------------------------------- |
| Time       | `O(n log n)`                                                                 |
| Space      | `O(log n)` auxiliary space for Java's primitive array sorting implementation |

Breaking down the time:

1. Sorting: `O(n log n)`
2. Finding median: `O(1)`
3. Calculating moves: `O(n)`

Overall:

\\[ O(n\log n)+O(n)=O(n\log n) \\]

## 7. Interview Questions

Try answering these yourself:

Q1. Why do we sort the array?

Show answer

Q2. Why do we use median instead of average?

Show answer

Q3. Why do we use Math.abs()?

Show answer

Q4. Why do we use long for moves?

Show answer

Q5. Can we choose either middle element for an even-sized array?

Show answer

## 8. What should you remember from this problem?

| Problem situation                   | Technique    |
| ----------------------------------- | ------------ |
| Minimum sum of absolute differences | Median       |
| Find middle element                 | Sorting      |
| Calculate distance                  | `Math.abs()` |

My learning tip: Don't just memorize the code. Remember this one important connection:

> When a problem asks you to minimize the sum of absolute differences between elements and a common target, think about the Median.

This is a useful mathematical pattern that can appear in other DSA problems too.