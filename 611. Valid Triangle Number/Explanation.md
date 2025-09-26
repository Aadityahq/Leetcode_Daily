# Explanation - Valid Triangle Number (LeetCode 611)

## Problem Recap
We need to count the number of triplets `(a, b, c)` from an array that can form a valid triangle.  
A triangle is valid if:
a + b > c
a + c > b
b + c > a

---

## Key Observation
If the array is **sorted**:
- Let `c` be the largest side.
- Then we only need to check:
a + b > c


(because the other two conditions will automatically hold since `c` is the maximum).

---

## Approach (Two Pointers)
1. **Sort the array**.
2. **Fix `c`** as the largest side (iterate from end to start).
3. Use **two pointers**:
   - `left = 0`
   - `right = i - 1` (just before `c`)
4. While `left < right`:
   - If `nums[left] + nums[right] > nums[i]`  
     → All pairs between `left` and `right-1` with `nums[right]` are valid.  
     → Add `(right - left)` to the count.  
     → Move `right--`.
   - Else → Move `left++`.

---

## Example Walkthrough
Input:
nums = [2, 2, 3, 4]


Step 1: Sort → `[2, 2, 3, 4]`

### Iteration 1: Fix `c = 4`
- `left = 0`, `right = 2`
- Check: `2 + 3 > 4` → **Yes** ✅  
  → Count += (2 - 0) = 2 (pairs: (2,3), (2,3))  
  → Move `right = 1`

- `left = 0`, `right = 1`
- Check: `2 + 2 > 4` → **No** ❌  
  → Move `left = 1`

Loop ends.

### Iteration 2: Fix `c = 3`
- `left = 0`, `right = 1`
- Check: `2 + 2 > 3` → **Yes** ✅  
  → Count += (1 - 0) = 1

Loop ends.

### Total Count = 3

---

## Complexity
- Sorting: `O(n log n)`  
- Two-pointer search: `O(n²)`  
- Extra space: `O(1)`

---

## Why This Works
- Sorting ensures that the largest side is always at the end of the triplet.  
- This reduces the 3 inequality checks to just **one check**:  
  `nums[left] + nums[right] > nums[i]`.  
- Using two pointers makes it efficient (instead of checking all triplets).
Do you want me to also add a diagram with arrows (left, right, i) inside the README so it’s easier to visualize the two-pointer movement?


