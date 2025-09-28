# 976. Largest Perimeter Triangle

## Problem Statement

You are given an integer array `nums`.  
Find three numbers from the array that can form a triangle of non-zero area and return the largest possible perimeter of such a triangle.

If it is impossible to form any triangle, return 0.

---

## Triangle Rule

A triangle with sides `a`, `b`, `c` is valid if:

- `a + b > c`
- `a + c > b`
- `b + c > a`

If these conditions are not met, the triangle collapses into a line.

---

## Approach

1. **Sort the array in ascending order.**
2. **Start from the largest side and check groups of three consecutive sides.**
    - If they satisfy the triangle condition, return their sum (perimeter).
3. **If no valid triangle exists, return 0.**

---

## HOW (Step-by-step)

1. **Sort the array**  
    Example: `[2, 1, 2] → [1, 2, 2]`

2. **Iterate from the largest side**  
    Check triples: `(nums[i-2], nums[i-1], nums[i])`

3. **Check the triangle condition**  
    For sorted numbers (`a ≤ b ≤ c`), only need to check:  
    `nums[i-2] + nums[i-1] > nums[i]`

    - If true → valid triangle found.

4. **Return the perimeter**  
    - First valid triplet = maximum perimeter.
    - If no triplet works, return 0.

---

## WHY (Reasoning)

- **Why sort?**  
  Sorting ensures we always try the largest sides first, maximizing the perimeter.

- **Why only one inequality?**  
  With sorted order (`a ≤ b ≤ c`), the conditions `a + c > b` and `b + c > a` are always true.  
  The only possible failure is `a + b > c`.

- **Why return immediately?**  
  Because we start from the largest numbers, the first valid triangle guarantees the maximum perimeter.

---

## Example Walkthrough

**Input:**  
`nums = [2, 1, 2]`

**Steps:**

- Sort → `[1, 2, 2]`
- Check last three: `(1, 2, 2)`
- Condition: `1 + 2 > 2` ✅ valid
- Perimeter = `1 + 2 + 2 = 5`

**Output:**  
`5`

---

## Complexity

- Sorting: `O(n log n)`
- Checking triplets: `O(n)`
- **Overall:** `O(n log n)`76. Largest Perimeter Triangle


