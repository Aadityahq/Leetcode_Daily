## 🧠 Problem Understanding

You are given a number as an **array of digits**:

* Each element is a single digit (`0–9`)
* The array represents a number from **most significant digit → least significant digit**
* No leading zeros

Your task is to **add 1** to this number and return the result as a new digit array.

---

## 🔍 Key Observations

1. **Normal case**
   If the last digit is **not 9**, just add `1` to it and you’re done.
   Example:
   `[1, 2, 3] → [1, 2, 4]`

2. **Carry case (digit = 9)**

   * If a digit is `9`, adding `1` makes it `0` and produces a **carry**
   * The carry moves to the left

3. **All digits are 9**
   Example:
   `[9, 9, 9] → [1, 0, 0, 0]`

   * The number length increases by 1

---

## 🛠️ Approach (How & Why)

### Strategy:

* Start from the **last digit** (rightmost)
* Add `1`
* Handle carry if digit becomes `10`
* Stop early if no carry is generated
* If carry remains after the first digit → create a new array

### Why this works:

* Addition always starts from the least significant digit
* We only need **one pass from right to left**
* Time complexity is optimal

---

## ✅ Java Solution

```java
class Solution {
    public int[] plusOne(int[] digits) {

        // Start from the last digit
        for (int i = digits.length - 1; i >= 0; i--) {

            // If digit is less than 9, just add 1 and return
            if (digits[i] < 9) {
                digits[i]++;
                return digits;
            }

            // If digit is 9, it becomes 0 (carry continues)
            digits[i] = 0;
        }

        // If all digits were 9, we need a new array
        int[] result = new int[digits.length + 1];
        result[0] = 1; // Remaining digits are already 0 by default

        return result;
    }
}
```

---

## 📊 Example Walkthrough

### Input:

```
digits = [9, 9]
```

### Steps:

* Last digit → 9 → becomes 0 (carry)
* Next digit → 9 → becomes 0 (carry)
* Carry still exists → create new array

### Output:

```
[1, 0, 0]
```

---

## ⏱️ Complexity Analysis

* **Time Complexity:** `O(n)`
  (Worst case: all digits are 9)

* **Space Complexity:**

  * `O(1)` if no new array needed
  * `O(n)` only when all digits are `9`

