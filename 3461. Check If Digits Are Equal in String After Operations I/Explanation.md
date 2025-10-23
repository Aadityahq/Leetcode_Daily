## 🧩 3461. Check If Digits Are Equal in String After Operations I

### 🧠 Problem Understanding

You are given a **string `s` consisting of digits** (0–9).
You need to repeatedly perform the following operation **until only two digits remain**:

> For each pair of **consecutive digits** `(s[i], s[i+1])`,
> replace them with `(s[i] + s[i+1]) % 10`.

After performing these operations, you'll be left with **exactly two digits**.
The task is to check if **both digits are equal**.

---

### 📘 Example 1

**Input:**

```
s = "3902"
```

**Process:**

```
Initial: 3902

Step 1:
(3+9)%10 = 2
(9+0)%10 = 9
(0+2)%10 = 2
→ s = "292"

Step 2:
(2+9)%10 = 1
(9+2)%10 = 1
→ s = "11"

Final: "11"
Both digits are equal → ✅ true
```

---

### 📗 Example 2

**Input:**

```
s = "34789"
```

**Process:**

```
Initial: 34789

Step 1: (3+4)%10=7, (4+7)%10=1, (7+8)%10=5, (8+9)%10=7 → "7157"
Step 2: (7+1)%10=8, (1+5)%10=6, (5+7)%10=2 → "862"
Step 3: (8+6)%10=4, (6+2)%10=8 → "48"

Final: "48"
Digits not equal → ❌ false
```

---

### 💡 Intuition

At each step:

* We **combine** every adjacent pair of digits.
* The length of the string decreases by 1 each time.
* The operation continues until only **two digits** remain.

Because the input size is at most 100, a **direct simulation** approach is simple and efficient (O(n²) time complexity is fine).

---

### ⚙️ Approach

1. Convert the input string into a list of digits.
2. While the list length > 2:

   * Create a new list.
   * For each consecutive pair `(a, b)` in the current list:

     * Append `(a + b) % 10` to the new list.
   * Replace the old list with the new one.
3. After the loop ends, compare the final two digits.
4. Return `true` if they are equal, otherwise `false`.

---

### 🧩 Why It Works

* Each operation reduces the string length by 1, ensuring we’ll eventually reach 2 digits.
* The modulo 10 operation keeps results within 0–9.
* Since no randomness is involved, the process is deterministic — it always leads to the same result for a given input.

---

### 🧮 Complexity Analysis

| Type  | Complexity                                              |
| ----- | ------------------------------------------------------- |
| Time  | O(n²) — each round processes (n-1), (n-2), ..., 2 pairs |
| Space | O(n) — for storing intermediate results                 |

Given `n ≤ 100`, this is perfectly efficient.

---

### 💻 Java Solution

```java
class Solution {
    public boolean hasSameDigits(String s) {
        // Convert string to list of digits
        List<Integer> digits = new ArrayList<>();
        for (char c : s.toCharArray()) {
            digits.add(c - '0');
        }

        // Keep reducing until only 2 digits remain
        while (digits.size() > 2) {
            List<Integer> next = new ArrayList<>();
            for (int i = 0; i < digits.size() - 1; i++) {
                int val = (digits.get(i) + digits.get(i + 1)) % 10;
                next.add(val);
            }
            digits = next; // Move to next stage
        }

        // Compare the final two digits
        return digits.get(0).equals(digits.get(1));
    }
}
```

---

### 🧾 Summary

| Step | Operation                    | Result               |
| ---- | ---------------------------- | -------------------- |
| 1    | Combine adjacent digits      | Shorter string       |
| 2    | Apply modulo 10              | Keep digits valid    |
| 3    | Repeat until 2 digits remain |                      |
| 4    | Compare last two digits      | Return true if equal |

✅ **Simple simulation works efficiently for n ≤ 100.**

