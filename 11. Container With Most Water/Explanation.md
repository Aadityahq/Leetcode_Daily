# 🪣 Container With Most Water (LeetCode 11)

## 📌 Problem Statement
You are given an integer array `height` of length `n`.  
There are `n` vertical lines drawn such that the two endpoints of the `i-th` line are `(i, 0)` and `(i, height[i])`.

We need to find two lines that, together with the x-axis, form a container that holds the **most water**.

👉 You may **not** slant the container.

---

## ✨ Example
**Input:**  
`height = [1,8,6,2,5,4,8,3,7]`

**Output:**  
`49`

**Explanation:**  
The max area is formed between `height[1] = 8` and `height[8] = 7`.  
Area = `min(8, 7) * (8 - 1) = 7 * 7 = 49`.

---

## 🧠 How to Think About It (The "Why")
- The area between two lines depends on:
  - **Width**: the distance between the two lines.
  - **Height**: the shorter of the two chosen lines (since water spills over the shorter one).
- Formula:  
  `Area = min(height[i], height[j]) * (j - i)`

- **Naive approach (Brute Force):**  
  - Check all pairs `(i, j)` → O(n²).  
  - Too slow for `n` up to `10^5`.

- **Optimized approach (Two Pointers):**  
  - Start with the widest container (`left = 0`, `right = n-1`).  
  - Calculate area, update max if needed.  
  - Move the pointer with the smaller line inward:
    - Because the limiting factor is the smaller height.  
    - Moving the taller line doesn’t help (width decreases but height doesn’t improve).  
  - Repeat until `left < right`.

This greedy shrinking ensures we **don’t miss the best possible area**.

---

## 📝 Algorithm
1. Initialize two pointers: `left = 0`, `right = n-1`.
2. While `left < right`:
   - Compute area = `min(height[left], height[right]) * (right - left)`.
   - Update maximum area if greater.
   - Move the pointer at the smaller height inward.
3. Return the maximum area.

---

## 💻 Code (Java)

```java
class Solution {
    public int maxArea(int[] height) {
        int left = 0, right = height.length - 1;
        int maxArea = 0;

        while (left < right) {
            int h = Math.min(height[left], height[right]);
            int w = right - left;
            int area = h * w;
            maxArea = Math.max(maxArea, area);

            // Move the smaller line inward
            if (height[left] < height[right]) {
                left++;
            } else {
                right--;
            }
        }
        return maxArea;
    }
}
