## 🔢 868. Binary Gap — Explanation + Java Solution

### 🧠 Problem Understanding

You are given a positive integer `n`.

Your task:
👉 Convert `n` into its **binary representation**.
👉 Find the **maximum distance between two adjacent 1's**.

### 📌 Important Points

* Two 1’s are considered **adjacent** if there are only `0`s between them.
* Distance = difference between their bit positions.
* If there are fewer than two `1`s → return `0`.

---

### 📖 Example 1

Input: `n = 22`

Binary of 22:

```
22 → 10110
       ↑  ↑   distance = 2
         ↑ ↑  distance = 1
```

Maximum distance = **2**

---

### 📖 Example 2

Input: `n = 8`

Binary:

```
8 → 1000
```

Only one `1` → No pair → Output = **0**

---

## 💡 How to Think About the Solution

Instead of converting to a string, we can use **bit manipulation**.

### 🎯 Idea

1. Traverse bits from right to left.
2. Keep track of:

   * Current bit position
   * Position of previous `1`
3. Whenever we find a `1`:

   * If this is not the first `1`
   * Calculate distance with previous `1`
   * Update maximum distance
4. Shift number right (`n >> 1`) each step.

---

## 🔍 Why This Works

Binary representation is just bits.
By shifting right:

* We examine every bit.
* Time complexity is **O(log n)** because number of bits ≈ log₂(n).
* Space complexity is **O(1)**.

Efficient and clean ✅

---

## ✅ Java Solution

```java
class Solution {
    public int binaryGap(int n) {
        int maxDistance = 0;
        int previousPosition = -1;
        int currentPosition = 0;
        
        while (n > 0) {
            if ((n & 1) == 1) {  // Check if current bit is 1
                if (previousPosition != -1) {
                    maxDistance = Math.max(maxDistance, currentPosition - previousPosition);
                }
                previousPosition = currentPosition;
            }
            
            n = n >> 1;   // Right shift
            currentPosition++;
        }
        
        return maxDistance;
    }
}
```

---

## 🧩 Step-by-Step Dry Run (n = 22)

Binary = `10110`

| Position | Bit | Previous | Distance | Max |
| -------- | --- | -------- | -------- | --- |
| 0        | 0   | -        | -        | 0   |
| 1        | 1   | 1        | -        | 0   |
| 2        | 1   | 1        | 1        | 1   |
| 3        | 0   | -        | -        | 1   |
| 4        | 1   | 4-2=2    | 2        | 2   |

Final Answer = **2**

---

## 🚀 Complexity

* **Time:** O(log n)
* **Space:** O(1)

---

