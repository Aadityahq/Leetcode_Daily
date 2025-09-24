# 2197. Replace Non-Coprime Numbers in Array

## 📝 Problem
You are given an array of integers `nums`. Perform the following steps:

1. Find any two adjacent numbers in `nums` that are **non-coprime** (i.e., their GCD > 1).  
2. Replace them with their **LCM (Least Common Multiple)**.  
3. Repeat this process until no adjacent non-coprime numbers remain.  

Return the final modified array.  
It can be shown that the result is **unique**, regardless of the order of merging.

---

## 🔍 Examples

### Example 1
**Input:**  
nums = [6,4,3,2,7,6,2]

makefile
Copy code
**Output:**  
[12,7,6]

markdown
Copy code
**Explanation:**  
- Merge (6,4) → 12 → `[12,3,2,7,6,2]`  
- Merge (12,3) → 12 → `[12,2,7,6,2]`  
- Merge (12,2) → 12 → `[12,7,6,2]`  
- Merge (6,2) → 6 → `[12,7,6]`  

Final array = `[12,7,6]`.

---

### Example 2
**Input:**  
nums = [2,2,1,1,3,3,3]

makefile
Copy code
**Output:**  
[2,1,1,3]

arduino
Copy code
**Explanation:**  
- Merge (3,3) → 3 → `[2,2,1,1,3,3]`  
- Merge (3,3) → 3 → `[2,2,1,1,3]`  
- Merge (2,2) → 2 → `[2,1,1,3]`  

Final array = `[2,1,1,3]`.

---

## 💡 Approach
- Use a **stack** to merge adjacent non-coprime numbers.  
- Iterate through the array:  
  - Push each number into the stack.  
  - While the top of the stack and the new number are **non-coprime**, pop and replace them with their LCM.  
  - Push the merged result back to the stack.  
- At the end, the stack contains the final array.

---

## 📊 Complexity
- **Time Complexity:** `O(n log A)`  
  - `n` = number of elements  
  - `log A` = cost of GCD computation  
- **Space Complexity:** `O(n)` (stack for merged results)  

---

## 🖥️ Java Solution
```java
import java.util.*;

class Solution {
    public List<Integer> replaceNonCoprimes(int[] nums) {
        Stack<Long> stack = new Stack<>();
        
        for (int num : nums) {
            long cur = num;
            
            while (!stack.isEmpty()) {
                long top = stack.peek();
                long g = gcd(top, cur);
                
                if (g == 1) break; // coprime → stop merging
                stack.pop();
                cur = lcm(top, cur); // merge into LCM
            }
            
            stack.push(cur);
        }
        
        // Convert stack to result list
        List<Integer> result = new ArrayList<>();
        for (long val : stack) {
            result.add((int) val);
        }
        return result;
    }
    
    private long gcd(long a, long b) {
        return b == 0 ? a : gcd(b, a % b);
    }
    
    private long lcm(long a, long b) {
        return a / gcd(a, b) * b;
    }
}