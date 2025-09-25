# Triangle Minimum Path Sum

This document explains the problem, approach, and Java solution for finding the minimum path sum in a triangle.

## Problem

Given a triangle array, return the **minimum path sum** from top to bottom.

For each step, you may move to an adjacent number in the row below. Formally, if you are on index `i` on the current row, you may move to either index `i` or `i + 1` on the next row.

### Example

**Input:**

```
triangle = [[2],[3,4],[6,5,7],[4,1,8,3]]
```

**Output:**

```
11
```

**Explanation:**

```
   2
  3 4
 6 5 7
4 1 8 3
```

The minimum path sum is 2 + 3 + 5 + 1 = 11.

---

## Approach: Bottom-Up Dynamic Programming

1. Start from the **last row** of the triangle.
2. Create a DP array that stores the minimum path sum for each element.
3. Move from the second-last row upwards:

   * For each element `triangle[i][j]`, update `dp[j]` as:

     ```
     dp[j] = triangle[i][j] + Math.min(dp[j], dp[j + 1]);
     ```
4. Continue until reaching the top row. The first element of `dp` contains the minimum path sum.

**Why Bottom-Up?**

* Starting from the bottom allows us to **reuse the same DP array** and save space.
* No need to calculate the same subproblems multiple times.

**Time Complexity:** O(n²) (visiting every element)

**Space Complexity:** O(n) (one row of DP array)

---

## Java Solution

```java
import java.util.List;
import java.util.ArrayList;

public class Solution {
    public int minimumTotal(List<List<Integer>> triangle) {
        int n = triangle.size();
        int[] dp = new int[n];
        for (int i = 0; i < n; i++) {
            dp[i] = triangle.get(n - 1).get(i);
        }
        
        for (int i = n - 2; i >= 0; i--) {
            for (int j = 0; j <= i; j++) {
                dp[j] = triangle.get(i).get(j) + Math.min(dp[j], dp[j + 1]);
            }
        }
        
        return dp[0];
    }
    
    public static void main(String[] args) {
        Solution sol = new Solution();
        List<List<Integer>> triangle = new ArrayList<>();
        triangle.add(List.of(2));
        triangle.add(List.of(3,4));
        triangle.add(List.of(6,5,7));
        triangle.add(List.of(4,1,8,3));
        
        System.out.println(sol.minimumTotal(triangle)); // Output: 11
    }
}
```

---

This solution works for any valid triangle and handles negative numbers as well.
