# LeetCode 3754. Concatenate Non-Zero Digits and Multiply by Sum I

## Problem Explanation

You are given an integer `n`.

Your task is to:

1. Extract all **non-zero digits** from `n` while keeping their **original order**.
2. Concatenate these digits to form a new number `x`.
3. Calculate the **sum of the digits** of `x`.
4. Return `x × sum`.

### Example

**Input:**

```text
n = 10203004
```

**Step 1: Extract non-zero digits**

```text
1, 2, 3, 4
```

**Step 2: Form x**

```text
x = 1234
```

**Step 3: Sum of digits**

```text
1 + 2 + 3 + 4 = 10
```

**Step 4: Multiply**

```text
1234 × 10 = 12340
```

**Output**

```text
12340
```

---

# Key Observation

We need to preserve the **original order** of digits.

If we process digits from **right to left** using `% 10`, the order gets reversed.

Instead, convert the number into a **string** and process each character from **left to right**.

While traversing:

* Ignore `'0'`
* Build the new number `x`
* Keep adding digits to the sum

---

# Algorithm

1. Convert `n` into a string.
2. Initialize:

   * `x = 0`
   * `sum = 0`
3. Traverse every character.
4. Convert character into digit.
5. If digit is not zero:

   * Append it to `x`

     ```
     x = x * 10 + digit
     ```
   * Add it to `sum`
6. Return

   ```
   x * sum
   ```

---

# Dry Run

### Input

```text
n = 10203004
```

String = `"10203004"`

| Character | Digit | Action | x    | sum |
| --------- | ----- | ------ | ---- | --- |
| '1'       | 1     | Append | 1    | 1   |
| '0'       | 0     | Ignore | 1    | 1   |
| '2'       | 2     | Append | 12   | 3   |
| '0'       | 0     | Ignore | 12   | 3   |
| '3'       | 3     | Append | 123  | 6   |
| '0'       | 0     | Ignore | 123  | 6   |
| '0'       | 0     | Ignore | 123  | 6   |
| '4'       | 4     | Append | 1234 | 10  |

Finally,

```text
1234 × 10 = 12340
```

---

# Java Solution

```java
class Solution {
    public int concatenateAndCalculate(int n) {
        String s = String.valueOf(n);

        int x = 0;
        int sum = 0;

        for (char ch : s.toCharArray()) {
            int digit = ch - '0';

            if (digit != 0) {
                x = x * 10 + digit;
                sum += digit;
            }
        }

        return x * sum;
    }
}
```

---

# Why This Works

Suppose the digits are:

```text
1 2 3 4
```

Initially,

```text
x = 0
```

Append each digit:

After 1:

```text
0 × 10 + 1 = 1
```

After 2:

```text
1 × 10 + 2 = 12
```

After 3:

```text
12 × 10 + 3 = 123
```

After 4:

```text
123 × 10 + 4 = 1234
```

This preserves the original order because every new digit is added to the right.

At the same time, we keep calculating:

```text
sum = 1 + 2 + 3 + 4 = 10
```

Finally,

```text
answer = 1234 × 10 = 12340
```

---

# Edge Cases

### Case 1

```text
n = 1000
```

Non-zero digits:

```text
1
```

```text
x = 1
sum = 1

Answer = 1
```

---

### Case 2

```text
n = 0
```

There are no non-zero digits.

```text
x = 0
sum = 0

Answer = 0
```

---

### Case 3

```text
n = 987654321
```

All digits are non-zero.

```text
x = 987654321
sum = 45

Answer = 987654321 × 45
```

---

# Complexity Analysis

* **Time Complexity:** **O(d)**, where `d` is the number of digits in `n` (at most 10 since `n ≤ 10⁹`).
* **Space Complexity:** **O(d)** due to the string representation. Since `d ≤ 10`, this is effectively constant space for the given constraints.

---

# Why This Approach?

* Processing the string from left to right naturally preserves digit order.
* We build `x` and compute the digit sum in a **single traversal**.
* The solution is simple, efficient, and handles all edge cases, including `n = 0`.
