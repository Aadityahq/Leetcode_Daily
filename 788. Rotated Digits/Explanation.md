## Understanding the Problem

We need to count how many numbers from `1` to `n` are **good numbers** after rotation by 180°.

### Rotation Rules

| Digit | Rotates To |
| ----- | ---------- |
| 0     | 0          |
| 1     | 1          |
| 8     | 8          |
| 2     | 5          |
| 5     | 2          |
| 6     | 9          |
| 9     | 6          |
| 3,4,7 | Invalid    |

---

A number is **good** if:

1. Every digit is valid after rotation.
2. The rotated number becomes **different** from the original number.

---

### Example: `n = 10`

Numbers from `1 → 10`

* `1` → `1` (same) ❌
* `2` → `5` (different) ✅
* `5` → `2` ✅
* `6` → `9` ✅
* `8` → `8` ❌
* `9` → `6` ✅
* `10` → `10` ❌

Answer = `4`

---

# Key Observation

Digits can be divided into 3 groups:

### 1. Same after rotation

These do not change the number.

`0, 1, 8`

---

### 2. Valid and changes

These make the number different.

`2, 5, 6, 9`

A number becomes **good** if it contains **at least one** of these digits.

---

### 3. Invalid digits

These break the number completely.

`3, 4, 7`

---

# Approach

We check every number from `1` to `n`.

For each number:

* If it contains `3,4,7` → invalid
* If it contains at least one of `2,5,6,9` → good
* Otherwise → not good

---

# Dry Run

Take `num = 269`

* `2` → changes ✅
* `6` → changes ✅
* `9` → changes ✅

No invalid digits and at least one changing digit → GOOD

---

Take `num = 108`

* `1` → same
* `0` → same
* `8` → same

No changing digit → NOT good

---

Take `num = 234`

* `3` invalid ❌

So immediately reject.

---

# Java Solution

```java
class Solution {
    
    public int rotatedDigits(int n) {
        int count = 0;

        for (int i = 1; i <= n; i++) {
            if (isGood(i)) {
                count++;
            }
        }

        return count;
    }

    private boolean isGood(int num) {
        boolean changed = false;

        while (num > 0) {
            int digit = num % 10;

            // Invalid digits
            if (digit == 3 || digit == 4 || digit == 7) {
                return false;
            }

            // Digits that change after rotation
            if (digit == 2 || digit == 5 || digit == 6 || digit == 9) {
                changed = true;
            }

            num /= 10;
        }

        return changed;
    }
}
```

---

# Why This Works

For every number:

* We verify all digits are rotatable.
* We ensure at least one digit changes the number.

If both conditions are true → the number is good.

---

# Time Complexity

For each number from `1 → n`, we process its digits.

If a number has `d` digits:

* Time = `O(n × d)`

Since `n <= 10^4`, this is very efficient.

---

# Space Complexity

```text
O(1)
```

No extra space used except variables.
