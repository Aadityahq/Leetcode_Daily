## 🧩 Problem Understanding

We are given two non-negative integers, `num1` and `num2`.

We can perform the following operation repeatedly:

* If `num1 >= num2`, then `num1 = num1 - num2`
* Otherwise, `num2 = num2 - num1`

We must **count how many operations** it takes until **either number becomes 0**.

---

## 💡 Intuition

This is similar to the **Euclidean algorithm** used for finding the greatest common divisor (GCD).
Each operation reduces one of the numbers, and we stop when one becomes zero.

To optimize, instead of doing subtraction one by one, we can **subtract in bulk**:

* If `num1 >= num2`, we can perform `num1 / num2` operations in one step by subtracting `num2` multiple times.

---

## ✅ Java Solution

```java
class Solution {
    public int countOperations(int num1, int num2) {
        int operations = 0;
        
        while (num1 != 0 && num2 != 0) {
            if (num1 >= num2) {
                operations += num1 / num2; // count how many times num2 fits in num1
                num1 %= num2;              // update num1
            } else {
                operations += num2 / num1; // count how many times num1 fits in num2
                num2 %= num1;              // update num2
            }
        }
        
        return operations;
    }
}
```

---

## 🧠 Step-by-Step Explanation

### Example: `num1 = 2, num2 = 3`

| Step | num1 | num2 | Action                               | Operations Added | Total |
| ---- | ---- | ---- | ------------------------------------ | ---------------- | ----- |
| 1    | 2    | 3    | num1 < num2 → num2 = num2 - num1 = 1 | 1                | 1     |
| 2    | 2    | 1    | num1 > num2 → num1 = num1 - num2 = 1 | 1                | 2     |
| 3    | 1    | 1    | num1 == num2 → num1 = 0              | 1                | 3     |

✅ Result: **3 operations**

---

### Example: `num1 = 10, num2 = 10`

| Step | num1 | num2 | Action                  | Operations Added | Total |
| ---- | ---- | ---- | ----------------------- | ---------------- | ----- |
| 1    | 10   | 10   | num1 == num2 → num1 = 0 | 1                | 1     |

✅ Result: **1 operation**

---

## ⚙️ Time Complexity

* Each step reduces one number significantly (`num1 %= num2`), so the time complexity is **O(log(min(num1, num2)))**
* Space complexity: **O(1)**

---

## ✨ Why This Works

* This method efficiently counts how many times one number can be subtracted from the other.
* It leverages **integer division** to avoid looping multiple times unnecessarily.
* It’s both **simple and optimal**, using the same principle as the Euclidean GCD algorithm.

---


