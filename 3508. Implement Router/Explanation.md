# 2197. Replace Non-Coprime Numbers in Array

## 📌 Problem
You are given an array of integers `nums`. Perform the following steps:

1. Find two adjacent numbers in `nums` that are **non-coprime** (i.e., their GCD > 1).  
2. Replace them with their **LCM (Least Common Multiple)**.  
3. Repeat this process until no adjacent non-coprime numbers remain.  

Return the final modified array.  

It can be proven that the result is **unique**, no matter the order of merging.

---

## 📖 Examples

### Example 1
**Input:**  
`nums = [6,4,3,2,7,6,2]`  
**Output:**  
`[12,7,6]`  

**Explanation:**  
- (6, 4) → LCM(6, 4) = 12 → `[12,3,2,7,6,2]`  
- (12, 3) → LCM(12, 3) = 12 → `[12,2,7,6,2]`  
- (12, 2) → LCM(12, 2) = 12 → `[12,7,6,2]`  
- (6, 2) → LCM(6, 2) = 6 → `[12,7,6]`  

Final array = `[12,7,6]`.

---

### Example 2
**Input:**  
`nums = [2,2,1,1,3,3,3]`  
**Output:**  
`[2,1,1,3]`  

**Explanation:**  
- (3, 3) → LCM(3, 3) = 3 → `[2,2,1,1,3,3]`  
- (3, 3) → LCM(3, 3) = 3 → `[2,2,1,1,3]`  
- (2, 2) → LCM(2, 2) = 2 → `[2,1,1,3]`  

Final array = `[2,1,1,3]`.

---

## 💡 Approach
1. Use a **stack** to build the final array from left to right.  
2. For each number `cur` in `nums`:  
   - While the stack is not empty:  
     - Let `top = stack.peek()`.  
     - Compute `g = gcd(top, cur)`.  
     - If `g == 1`, they are coprime → stop merging.  
     - Otherwise, replace both with their `LCM`, pop from stack, and continue checking with the new merged value.  
   - Push the final merged value into the stack.  
3. At the end, the stack contains the final answer.  

---

## ⏱️ Complexity
- **Time:** `O(n log A)`  
  - `n` = number of elements  
  - `A` = maximum value in `nums` (log A from gcd calculation)  
- **Space:** `O(n)` for the stack  

---

## 🧩 Code Snippet (Merging Logic)
```java
while (!stack.isEmpty()) {
    long top = stack.peek();
    long g = gcd(top, cur);
    if (g == 1) break;          // coprime → stop merging
    stack.pop();
    cur = lcm(top, cur);        // merge into lcm
}
stack.push(cur);
