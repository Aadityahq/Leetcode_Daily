# 📚 Problem Explanation: Maximum Matrix Sum

## 🔍 Problem Understanding

You are given an **n × n integer matrix**.

### Allowed Operation

* Choose **any two adjacent cells** (sharing a side).
* Multiply **both values by -1**.
* You can do this operation **any number of times**.

### Goal

👉 **Maximize the sum of all elements** in the matrix.

---

## 🧠 Key Observations (WHY the solution works)

### 1️⃣ Flipping in pairs is powerful

* Each operation flips **two values at once**.
* So the **parity (even/odd)** of negative numbers matters.

### 2️⃣ Absolute values are what really matter

* Ideally, we want **all numbers positive**.
* The **maximum possible sum** is:

  ```
  sum of absolute values of all elements
  ```

### 3️⃣ When is that NOT possible?

* If the **number of negative elements is odd**, we **cannot make all values positive**.
* One element must remain negative.
* To **minimize loss**, we keep the **smallest absolute value** negative.

---

## 🧮 Final Logic (HOW we solve)

1. Traverse the matrix.
2. Keep:

   * `totalSum` → sum of absolute values
   * `negCount` → count of negative numbers
   * `minAbs` → smallest absolute value in matrix
3. If `negCount` is **even**:

   * Answer = `totalSum`
4. If `negCount` is **odd**:

   * Answer = `totalSum - 2 * minAbs`

Why `2 * minAbs`?

* Because instead of adding `+minAbs`, we are forced to add `-minAbs`
* Difference = `minAbs - (-minAbs) = 2 * minAbs`

---

## ✅ Example Walkthrough

### Example 2

```
matrix = [[1,2,3],
          [-1,-2,-3],
          [1,2,3]]
```

* Absolute sum = 18
* Negative count = 3 (odd)
* Minimum absolute value = 1

Answer:

```
18 - 2*1 = 16
```

✔ Correct!

---

## 🧪 Time & Space Complexity

* **Time:** `O(n²)`
* **Space:** `O(1)` (no extra data structures)

Efficient even for `n = 250`.

---

## 💻 Java Solution

```java
class Solution {
    public long maxMatrixSum(int[][] matrix) {
        long totalSum = 0;
        int negativeCount = 0;
        int minAbs = Integer.MAX_VALUE;

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                int val = matrix[i][j];
                
                if (val < 0) {
                    negativeCount++;
                }
                
                int absVal = Math.abs(val);
                totalSum += absVal;
                minAbs = Math.min(minAbs, absVal);
            }
        }

        // If odd number of negatives, one smallest absolute value stays negative
        if (negativeCount % 2 == 1) {
            totalSum -= 2L * minAbs;
        }

        return totalSum;
    }
}
```

---
