# LeetCode 3736: Minimum Moves to Equal Array Elements III



Easy

Java



## 1. Understanding the problem

You are given an integer array `nums`.

In one operation, you can increase any one element by 1.

Your task is to make all elements of the array equal using the minimum number of operations.

Return the minimum total number of operations required.

### Example 1

```
Input: nums = [2, 1, 3]
Output: 3
```

Explanation:

We need to make every element equal to `3`.

- `2 → 3` requires 1 move.
- `1 → 3` requires 2 moves.
- `3 → 3` requires 0 moves.

Total moves = 1 + 2 + 0 = 3.

### Example 2

```
Input: nums = [4, 4, 5]
Output: 2
```

We need to make every element equal to `5`.

- `4 → 5` requires 1 move.
- `4 → 5` requires 1 move.
- `5 → 5` requires 0 moves.

Total moves = 2.

## 2. Java Solution (Optimal)

```

class Solution {
    public int minMoves(int[] nums) {

        int max = 0;
        int moves = 0;

        // Find the maximum element
        for (int num : nums) {
            max = Math.max(max, num);
        }

        // Calculate the moves required
        for (int num : nums) {
            moves += max - num;
        }

        return moves;
    }
}

```

## 3. Explanation: How and Why?

Let's understand the logic step by step.

Step 1: Find the maximum element.

Why?

Because we are only allowed to increase elements. We cannot decrease them.

Therefore, the smallest possible final value that can make all elements equal is the maximum element already present in the array.

For example:

```
nums = [2, 1, 3]

max = 3
```

We cannot make all elements equal to `2` because `3` cannot be decreased.

Step 2: Calculate the moves required.

For every element:

```
moves += max - num;
```

Why?

Because the difference between the maximum element and the current element tells us exactly how many times we need to increase that element.

For `[2, 1, 3]`:

| Element | Maximum | Moves     |
| ------- | ------- | --------- |
| 2       | 3       | 3 - 2 = 1 |
| 1       | 3       | 3 - 1 = 2 |
| 3       | 3       | 3 - 3 = 0 |

Total:

\\[ 1+2+0=\boxed{3} \\]

Step 3: Return the total moves.

After calculating the difference for every element, return `moves`.

## 4. Dry Run

Input:

```
nums = [2, 1, 3]
```

Step 1: Find maximum

Maximum element

# 3

Step 2: Calculate moves

| Iteration | num | max - num | moves |
| --------- | --- | --------- | ----- |
| 1         | 2   | 1         | 1     |
| 2         | 1   | 2         | 3     |
| 3         | 3   | 0         | 3     |

Final Answer

# 3

## 5. Complexity Analysis

Let `n` be the length of the array.

| Complexity | Value | Reason                             |
| ---------- | ----- | ---------------------------------- |
| Time       | O(n)  | Two loops traverse the array.      |
| Space      | O(1)  | Only two extra variables are used. |

## 6. Important Concept to Remember

The key observation is:

- We can only increase elements.
- We cannot decrease elements.
- Therefore, all elements must become equal to the maximum element.
- The minimum moves are the sum of differences between the maximum and every element.

Formula:

\\[ \boxed{\text{Moves}=\sum\_{i=0}^{n-1}(\text{max}-nums[i])} \\]

## 7. Interview Explanation

If the interviewer asks you to explain your approach, you can say:

"I first find the maximum element in the array because we can only increase elements and cannot decrease them. So, the maximum element becomes our target value. Then I traverse the array again and calculate the difference between the maximum element and each element. I add all these differences to get the minimum number of moves. This solution takes O(n) time and O(1) extra space."