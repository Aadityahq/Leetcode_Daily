# 1039. Minimum Score Triangulation of Polygon

## Problem Statement

You are given a convex polygon with `n` vertices. Each vertex has an integer value represented by an array `values`, where `values[i]` is the value of the ith vertex in clockwise order.

**Triangulation** is the process of dividing a polygon into triangles such that:
- Each triangle's vertices are vertices of the original polygon.
- Only triangles are allowed.
- The process results in `n - 2` triangles for an n-sided polygon.

The **score** of a triangle is the product of its vertex values. The total score of a triangulation is the sum of all triangle scores.  

**Goal:** Return the **minimum possible total score** among all triangulations of the polygon.

---

## Examples

**Example 1:**

Input: values = [1,2,3]
Output: 6
Explanation: Only one triangle is possible, with score 123 = 6.


**Example 2:**

Input: values = [3,7,4,5]
Output: 144
Explanation: Two possible triangulations:

(3,7,5) + (4,5,7) = 245

(3,4,5) + (3,4,7) = 144
Minimum score = 144



**Example 3:**

Input: values = [1,3,1,4,1,5]
Output: 13



---

## How to Solve (Approach)

This problem is a **Dynamic Programming (DP)** problem because:

1. Triangulating a polygon can be **broken down into smaller sub-polygons**.
2. The **minimum score** of the whole polygon can be computed using the **minimum scores of smaller intervals**.

We define:

dp[i][j] = minimum score of triangulating the polygon from vertex i to vertex j



- **Base Case:** If the polygon has less than 3 vertices (`j - i + 1 < 3`), no triangle can be formed, so `dp[i][j] = 0`.
- **Recurrence:** For vertices `i < k < j`:

dp[i][j] = min(dp[i][k] + dp[k][j] + values[i] * values[k] * values[j]) for all k



Explanation:

- `(i, k, j)` forms a triangle.
- `dp[i][k]` is the minimum score of the left sub-polygon.
- `dp[k][j]` is the minimum score of the right sub-polygon.
- `values[i] * values[k] * values[j]` is the score of the current triangle.

We try **all possible k** between i and j to find the **minimum total score**.

---

## Why This Works

- The polygon triangulation problem has **optimal substructure**, meaning the solution of a large polygon depends on the solutions of smaller polygons.
- Every triangle we pick splits the polygon into **independent sub-polygons**.  
- By computing the minimum score for each sub-polygon and combining them with the triangle we just picked, we ensure the overall minimum score.  
- The DP table avoids recomputation of the same sub-polygons, making it efficient.

---

## Complexity Analysis

- **Time Complexity:** `O(n^3)`  
  - There are `O(n^2)` sub-polygons (i,j).  
  - For each sub-polygon, we try `O(n)` vertices `k`.  
- **Space Complexity:** `O(n^2)`  
  - For storing the DP table.

Constraints allow this complexity since `n <= 50`.

---

## Java Implementation

```java
class Solution {
    public int minScoreTriangulation(int[] values) {
        int n = values.length;
        int[][] dp = new int[n][n];

        // gap = length of interval - 1
        for (int gap = 2; gap < n; gap++) { // interval length >= 3
            for (int i = 0; i + gap < n; i++) {
                int j = i + gap;
                dp[i][j] = Integer.MAX_VALUE;
                for (int k = i + 1; k < j; k++) {
                    dp[i][j] = Math.min(dp[i][j], dp[i][k] + dp[k][j] + values[i] * values[k] * values[j]);
                }
            }
        }

        return dp[0][n-1];
    }
}
Key Takeaways
Use interval DP for polygon or string partition problems.

Identify optimal substructure: a triangle splits the polygon into independent sub-polygons.

Consider all possible splits to guarantee the minimum score.

Dynamic programming helps avoid recalculating sub-problems, improving efficiency.