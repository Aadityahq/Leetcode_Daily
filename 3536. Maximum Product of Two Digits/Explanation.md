## Intuition

We need to find the **maximum product of any two digits** in the given number.

Since a digit can only be between **0 and 9**, the maximum product will always come from the **two largest digits**.

For example:

* `124` → digits = `[1, 2, 4]`

  * Products:

    * `1 × 2 = 2`
    * `1 × 4 = 4`
    * `2 × 4 = 8`
  * Maximum = **8**

Instead of checking every pair, we can simply find the **largest** and **second largest** digits.

---

# Approach

1. Extract every digit using:

   ```java
   digit = n % 10;
   n /= 10;
   ```
2. Maintain two variables:

   * `max1` → largest digit seen so far.
   * `max2` → second largest digit.
3. For every digit:

   * If digit is greater than `max1`

     * Move current `max1` to `max2`
     * Update `max1`
   * Else if digit is greater than `max2`

     * Update `max2`
4. Return `max1 * max2`.

---

# Why does this work?

The product is maximized when the two numbers being multiplied are as large as possible.

Since digits are all non-negative (`0–9`), replacing either digit with a larger one can only increase (or keep) the product.

Therefore, the answer is always:

> **Largest digit × Second largest digit**

---

# Dry Run

### Example: `n = 124`

Digits processed from right to left:

| Digit | max1 | max2 |
| ----- | ---- | ---- |
| 4     | 4    | -1   |
| 2     | 4    | 2    |
| 1     | 4    | 2    |

Answer:

```
4 × 2 = 8
```

---

### Example: `n = 22`

| Digit | max1 | max2 |
| ----- | ---- | ---- |
| 2     | 2    | -1   |
| 2     | 2    | 2    |

Answer:

```
2 × 2 = 4
```

Notice that duplicate digits are handled correctly.

---

# Java Solution

```java
class Solution {
    public int maxProduct(int n) {
        int max1 = -1;
        int max2 = -1;

        while (n > 0) {
            int digit = n % 10;

            if (digit > max1) {
                max2 = max1;
                max1 = digit;
            } else if (digit > max2) {
                max2 = digit;
            }

            n /= 10;
        }

        return max1 * max2;
    }
}
```

---

# Complexity Analysis

Let **d** be the number of digits in `n`.

* **Time Complexity:** `O(d)`

  * We scan each digit exactly once.

* **Space Complexity:** `O(1)`

  * Only two variables are used.

---

# Why the `if-else` logic?

Suppose the current digit is `7`.

### Case 1: It is the new largest digit.

```
Current:
max1 = 5
max2 = 3

digit = 7
```

Since `7 > 5`:

```
max2 = max1  → 5
max1 = 7
```

Now:

```
max1 = 7
max2 = 5
```

The old largest becomes the second largest.

---

### Case 2: It is not the largest but bigger than the second largest.

```
Current:
max1 = 8
max2 = 5

digit = 6
```

Since:

```
6 > 5
```

Update only:

```
max2 = 6
```

Result:

```
max1 = 8
max2 = 6
```

---

### Case 3: It is smaller than both.

```
Current:
max1 = 8
max2 = 6

digit = 2
```

Nothing changes.

---

# Why not sort the digits?

We could:

1. Extract all digits into an array.
2. Sort the array.
3. Multiply the last two elements.

But sorting costs:

* **Time:** `O(d log d)`
* **Extra Space:** `O(d)`

Finding the two largest digits in one pass is more efficient:

* **Time:** `O(d)`
* **Space:** `O(1)`

So the one-pass approach is optimal.

---

## Key Takeaway

The problem doesn't require checking every pair. Because all digits are non-negative, the **maximum product always comes from the two largest digits**. Therefore, a single traversal while tracking the largest and second-largest digits is enough, giving an optimal solution with **O(d)** time and **O(1)** space.
