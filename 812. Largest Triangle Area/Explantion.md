# 812. Largest Triangle Area

## Problem Statement
You are given an array of points on the X-Y plane where `points[i] = [xi, yi]`. Return the area of the **largest triangle** that can be formed by any three different points. Answers within `10^-5` of the actual answer will be accepted.

### Example 1
**Input:**  
points = [[0,0],[0,1],[1,0],[0,2],[2,0]]


**Output:**  
2.00000


Explanation: The red triangle formed by points `(0,2), (2,0), (0,0)` has the largest area.

### Example 2
**Input:**  
points = [[1,0],[0,0],[0,1]]


**Output:**  
0.50000



## Constraints
- `3 <= points.length <= 50`  
- `-50 <= xi, yi <= 50`  
- All given points are unique  

## Approach & Explanation
We need to calculate the maximum area formed by any three points. The area of a triangle given three coordinates can be calculated using the **Shoelace Formula** (determinant method):

\[
\text{Area} = \frac{1}{2} \times |x_1(y_2-y_3) + x_2(y_3-y_1) + x_3(y_1-y_2)|
\]

### Steps:
1. Iterate through all combinations of 3 points (brute force is feasible since `n <= 50`).  
2. Compute the triangle area using the formula.  
3. Keep track of the **maximum area** found.  
4. Return the maximum area as a floating-point number.  

## Complexity Analysis
- **Time Complexity:** `O(n^3)` → At most 19,600 combinations for `n = 50`, which is efficient enough.  
- **Space Complexity:** `O(1)` → Only a few variables are used.  
