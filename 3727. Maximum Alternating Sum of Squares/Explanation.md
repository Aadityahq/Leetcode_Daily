# LeetCode 3727: Maximum Alternating Sum of Squares

## Java Solution

```

import java.util.Arrays;

class Solution {
    public long maxAlternatingSum(int[] nums) {
        int n = nums.length;
        long[] sq = new long[n];

        for (int i = 0; i < n; i++) {
            sq[i] = 1L * nums[i] * nums[i];
        }

        Arrays.sort(sq);

        long ans = 0;
        for (int i = 0; i < n; i++) {
            if (i < n / 2) {
                ans -= sq[i];
            } else {
                ans += sq[i];
            }
        }

        return ans;
    }
}

```

## Problem Explanation

We are given an integer array `nums`. We can rearrange its elements in any order.

We need to maximize the alternating score:

\\[ arr[0]^2-arr[1]^2+arr[2]^2-arr[3]^2+\cdots \\]

- Even indices contribute positively.
- Odd indices contribute negatively.
- Since every element is squared, negative numbers also produce positive squares.

Our goal: Subtract the smallest squares and add the largest squares to maximize the score.

## How the solution works

### Step 1: Create an array of squares

```
int n = nums.length;
long[] sq = new long[n];

for (int i = 0; i < n; i++) {
    sq[i] = 1L * nums[i] * nums[i];
}
```

We create a new array `sq` to store the square of every element.

For example:

```
nums = [1, 2, 3]

sq = [1, 4, 9]
```

Why use `1L`?

It converts the multiplication to `long`, preventing integer overflow while calculating the square.

### Step 2: Sort the squares

```
Arrays.sort(sq);
```

This sorts the square values in ascending order.

```
sq = [1, 4, 9]
```

Why sort?

We want to subtract the smallest squares and add the largest squares.

### Step 3: Calculate the maximum score

```
long ans = 0;

for (int i = 0; i < n; i++) {
    if (i < n / 2) {
        ans -= sq[i];
    } else {
        ans += sq[i];
    }
}
```

Here, `n / 2` tells us how many elements belong to the smaller group.

For `n = 3`, integer division gives `n / 2 = 1`.

| Index | Square | Operation | `ans` |
| ----- | ------ | --------- | ----- |
| 0     | 1      | Subtract  | -1    |
| 1     | 4      | Add       | 3     |
| 2     | 9      | Add       | 12    |

The final answer is `12`.

### Step 4: Return the answer

```
return ans;
```

This returns the maximum alternating score.

## Dry run

Input:

```
nums = [1, -1, 2, -2, 3, -3]
```

After squaring and sorting:

```
sq = [1, 1, 4, 4, 9, 9]
```

Here, `n = 6` and `n / 2 = 3`.

The first three values are subtracted, and the remaining values are added:

\\[ ans=-(1+1+4)+(4+9+9) \\]

\\[ ans=-6+22=\boxed{16} \\]

Output:

```
16
```

## Complexity analysis

- Time Complexity: \\(O(n\log n)\\), because sorting takes \\(O(n\log n)\\).
- Space Complexity: \\(O(n)\\), because we store the squares in a separate array.

## Key takeaway

The main idea is Greedy + Sorting. Sort the squares, subtract the smallest `n / 2` values, and add the remaining values. This gives the maximum possible score.