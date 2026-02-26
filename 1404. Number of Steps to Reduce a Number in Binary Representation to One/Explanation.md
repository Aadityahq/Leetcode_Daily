## 🔹 LeetCode 1404 – Number of Steps to Reduce a Number in Binary Representation to One

### 📌 Problem Understanding

You are given a **binary string** `s` representing a positive integer.

You must reduce it to `"1"` using:

1. **If number is even** → divide by 2
   👉 In binary: remove the last bit (`0`)
2. **If number is odd** → add 1
   👉 In binary: handle carry (like normal binary addition)

Return the total number of steps required.

---

### 🧠 Key Observations

* If last bit is `0` → number is **even** → just divide by 2.
* If last bit is `1` → number is **odd** → add 1 (which may cause carry propagation).

⚠️ Important:

* The string length can be up to **500**, so we **cannot convert to integer** (overflow).
* We must operate directly on the string.

---

## 🔎 Example Walkthrough

### Example: `"1101"` (13 in decimal)

```
1101 → odd → +1 → 1110
1110 → even → /2 → 111
111  → odd → +1 → 1000
1000 → even → /2 → 100
100  → even → /2 → 10
10   → even → /2 → 1
```

Total steps = **6**

---

# 💡 Optimal Approach (Iterative, O(n))

Instead of modifying the string repeatedly (which is costly),
we traverse from **right to left** and simulate operations.

### 🔥 Smart Idea

We keep a `carry` variable.

When scanning from right to left:

* If `(bit + carry) == 0` → even → 1 step
* If `(bit + carry) == 1` → odd → 2 steps
  (1 for +1, 1 for divide)
  and carry becomes 1

Special case:
We stop before first character.
If carry is still 1 at the end → add one more step.

---

## ✅ Java Solution (Optimal Iterative)

```java
class Solution {
    public int numSteps(String s) {
        int steps = 0;
        int carry = 0;
        
        // Traverse from right to left (ignore first bit)
        for (int i = s.length() - 1; i > 0; i--) {
            int bit = s.charAt(i) - '0';
            
            if (bit + carry == 1) {
                // Odd case
                steps += 2; 
                carry = 1;
            } else {
                // Even case
                steps += 1;
            }
        }
        
        // If carry remains after processing all bits
        return steps + carry;
    }
}
```

---

## 🧠 Why This Works

### Case 1: Even Number

If last bit is `0`, division by 2 → remove bit
→ 1 step

### Case 2: Odd Number

If last bit is `1`, we:

1. Add 1 (creates carry)
2. Then divide by 2

→ 2 steps

Carry handles cases like:

```
111 + 1 = 1000
```

Without carry logic, we'd need multiple string modifications.

---

## ⏱ Time & Space Complexity

| Complexity | Value |
| ---------- | ----- |
| Time       | O(n)  |
| Space      | O(1)  |

Very efficient even for length 500.

---

