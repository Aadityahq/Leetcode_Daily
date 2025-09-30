# 2221. Find Triangular Sum of an Array

## 📌 Problem Statement
You are given a 0-indexed integer array `nums`, where each element is a digit between `0` and `9` (inclusive).  

The **triangular sum** of `nums` is the single value left after repeatedly performing the following operation until only one element remains:

1. If the array has only one element, stop.
2. Otherwise, construct a new array `newNums` of size `n - 1`.
3. For each index `i` (`0 <= i < n - 1`):  
newNums[i] = (nums[i] + nums[i+1]) % 10
4. Replace `nums` with `newNums` and repeat.

Return the final remaining value.

---

## ✨ Example

### Example 1
**Input:**  
nums = [1,2,3,4,5]

**Process:**  
- Step 1 → [3, 5, 7, 9]  
- Step 2 → [8, 2, 6]  
- Step 3 → [0, 8]  
- Step 4 → [8]  

**Output:**  
8

---

### Example 2
**Input:**  
nums = [5]

**Output:**  
5



---

## ✅ Constraints
- `1 <= nums.length <= 1000`
- `0 <= nums[i] <= 9`

---

## 🔍 How It Works
The process is like repeatedly collapsing the array by summing adjacent pairs modulo 10 until only one element is left.  

At every step:
- The size of the array decreases by **1**.  
- The operation `(nums[i] + nums[i+1]) % 10` ensures that values stay between 0 and 9 (digits).  

Thus, after `n - 1` steps, only **one digit remains** — the **triangular sum**.

---

## 🤔 Why This Approach
- The constraints are small (`n <= 1000`), so we can afford to **simulate** the process directly.  
- Each step reduces the array size, leading to a total of about `n + (n-1) + (n-2) + ... + 1 = O(n^2)` operations.  
- For `n = 1000`, this is about 500,000 operations, which is efficient enough.  

There’s also a **mathematical way** using **binomial coefficients**, where the result is:
TriangularSum = Σ ( C(n-1, i) * nums[i] ) % 10