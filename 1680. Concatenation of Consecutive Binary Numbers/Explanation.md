## 🔢 1680. Concatenation of Consecutive Binary Numbers

### 🧠 Problem Understanding

You are given an integer `n`.

You must:

1. Convert every number from `1` to `n` into binary.
2. Concatenate all those binary strings.
3. Convert the final binary string into a decimal number.
4. Return it modulo **10⁹ + 7**.

---

### ❌ Naive Approach (Why It Won’t Work)

You might think:

* Convert each number to binary
* Append to a String
* Convert the full string to decimal

But ❗

* The binary string becomes extremely large (for n = 10⁵).
* It will overflow normal integer types.
* Converting such a long string is inefficient.

So we need a **mathematical + bit manipulation approach**.

---

## 💡 Key Idea (Important Insight)

Instead of building the binary string:

We simulate the concatenation using **bit shifting**.

When you append a binary number to another:

```
Example:
current = 110 (6 in decimal)
next = 101 (5 in decimal)

To append:
Shift current left by length of next (3 bits)
110 << 3 = 110000
Add next:
110000 + 101 = 110101
```

That’s exactly how concatenation works in binary!

---

## 🔥 Optimized Approach

For every number `i` from `1 → n`:

1. Find how many bits are needed to represent `i`
2. Left shift the result by that many bits
3. Add `i`
4. Take modulo

---

### 🧮 How to find number of bits?

When a number is a power of 2, its bit length increases.

Example:

* 1 → 1 bit
* 2 → 10 → 2 bits
* 4 → 100 → 3 bits
* 8 → 1000 → 4 bits

So we increase bit length whenever:

```
(i & (i - 1)) == 0
```

(because that means `i` is power of 2)

---

## ✅ Java Solution

```java
class Solution {
    public int concatenatedBinary(int n) {
        long result = 0;
        int mod = 1000000007;
        int bitLength = 0;
        
        for (int i = 1; i <= n; i++) {
            
            // If i is power of 2, increase bit length
            if ((i & (i - 1)) == 0) {
                bitLength++;
            }
            
            // Shift result left by bitLength and add i
            result = ((result << bitLength) + i) % mod;
        }
        
        return (int) result;
    }
}
```

---

## 🧠 Why This Works

### 1️⃣ Left Shift = Append in Binary

`result << bitLength`
→ makes space for new bits

### 2️⃣ Add i

→ fills those bits with current number

### 3️⃣ Modulo at every step

→ prevents overflow

---

## ⏱ Time and Space Complexity

* **Time:** O(n)
* **Space:** O(1)

Efficient enough for `n ≤ 10^5`.

---

## 🧪 Example Walkthrough (n = 3)

| i | Binary | result (binary) | result (decimal) |
| - | ------ | --------------- | ---------------- |
| 1 | 1      | 1               | 1                |
| 2 | 10     | 110             | 6                |
| 3 | 11     | 11011           | 27               |

Final answer = **27**

---

## 🎯 Interview Perspective (Very Important)

They are testing:

* Bit manipulation knowledge
* Power of 2 trick
* Modulo handling
* Overflow control
* Mathematical thinking

---
