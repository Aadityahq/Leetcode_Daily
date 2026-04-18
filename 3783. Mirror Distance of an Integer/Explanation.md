## 🔍 Problem Understanding

You are given a number `n`.

You need to:

1. Reverse its digits → `reverse(n)`
2. Compute → `|n - reverse(n)|` (absolute difference)

This result is called the **mirror distance**.

---

### 📌 Example

**n = 25**

* reverse → 52
* difference → |25 - 52| = **27**

---

## 🧠 Key Observations

* Reversing a number is a **digit manipulation problem**
* Leading zeros disappear automatically:

  * `10 → 01 → 1`
* If number is same after reverse (like `7`, `121`) → answer = `0`

---

## ⚙️ Approach (Step-by-Step)

### Step 1: Reverse the number

We extract digits using `% 10` and build reversed number.

### Step 2: Compute absolute difference

Use:

```java
Math.abs(n - reversed)
```

---

## 💡 Why this works

* `% 10` gives last digit
* `/ 10` removes last digit
* We rebuild reversed number by:

  ```
  rev = rev * 10 + digit
  ```

This ensures digits are placed in reverse order.

---

## ✅ Java Solution

```java
class Solution {
    
    // Function to reverse the number
    private int reverse(int n) {
        int rev = 0;
        
        while (n > 0) {
            int digit = n % 10;   // get last digit
            rev = rev * 10 + digit; // build reversed number
            n = n / 10;          // remove last digit
        }
        
        return rev;
    }
    
    public int mirrorDistance(int n) {
        int reversed = reverse(n);
        return Math.abs(n - reversed);
    }
}
```

---

## 🧪 Dry Run

### Example: n = 10

| Step | n  | digit | rev |
| ---- | -- | ----- | --- |
| 1    | 10 | 0     | 0   |
| 2    | 1  | 1     | 1   |

reverse = 1
answer = |10 - 1| = **9**

---

## ⏱️ Complexity

* Time: **O(d)** (d = number of digits, max 10)
* Space: **O(1)**

---

## 🚀 Important Edge Cases

* Single digit → answer = 0
* Numbers ending with 0 → leading zeros ignored after reverse
* Large number → still safe (within int range)

---

## 🧩 Intuition Summary

* You're not doing anything complex—just:

  * **flip digits**
  * **compare difference**
* The real trick is knowing **how to reverse efficiently**

---

