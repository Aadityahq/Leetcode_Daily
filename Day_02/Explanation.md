# 2197. Replace Non-Coprime Numbers in Array

## Problem
You are given an array of integers `nums`. Perform the following steps:

1. Find any two adjacent numbers in `nums` that are **non-coprime** (i.e., their GCD > 1).  
2. Replace them with their **LCM (Least Common Multiple)**.  
3. Repeat this process until no adjacent non-coprime numbers remain.  

Return the final modified array.  
It can be shown that the result is **unique**, regardless of the order of merging.

---

## Examples

### Example 1
Input: nums = [6,4,3,2,7,6,2]  
Output: [12,7,6]  

Explanation:  
- (6, 4) → LCM(6, 4) = 12 → [12,3,2,7,6,2]  
- (12, 3) → LCM(12, 3) = 12 → [12,2,7,6,2]  
- (12, 2) → LCM(12, 2) = 12 → [12,7,6,2]  
- (6, 2) → LCM(6, 2) = 6 → [12,7,6]  
No more adjacent non-coprimes, so final array = [12,7,6].

---

### Example 2
Input: nums = [2,2,1,1,3,3,3]  
Output: [2,1,1,3]  

Explanation:  
- (3, 3) → LCM(3, 3) = 3 → [2,2,1,1,3,3]  
- (3, 3) → LCM(3, 3) = 3 → [2,2,1,1,3]  
- (2, 2) → LCM(2, 2) = 2 → [2,1,1,3]  
No more adjacent non-coprimes, so final array = [2,1,1,3].

---

## Approach
1. Use a **stack** to process numbers from left to right.  
2. Push each number into the stack.  
3. While the stack is not empty:
   - Check the top element of the stack and the current number.  
   - If they are **non-coprime** (gcd > 1), pop the top and replace both with their **LCM**.  
   - Repeat until they become coprime.  
4. Push the merged number back into the stack.  
5. At the end, the stack contains the final array.  

---

## Complexity
- **Time:** O(n log A), where `n` is the length of `nums` and `A` is the maximum value in `nums` (log A for gcd calculation).  
- **Space:** O(n) for the stack.  

---

## Snippet
The key merging logic looks like this:

```java
while (!stack.isEmpty()) {
    long top = stack.peek();
    long g = gcd(top, cur);
    if (g == 1) break;  // coprime → stop merging
    stack.pop();
    cur = lcm(top, cur); // merge into lcm
}
stack.push(cur);
