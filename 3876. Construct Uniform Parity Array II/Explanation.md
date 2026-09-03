## LeetCode 3876 — Construct Uniform Parity Array II

### 💡 Key Idea

We need to make **all elements of `nums2` have the same parity**:

* either all **odd**
* or all **even**

For every `nums1[i]`, we can choose:

1. `nums1[i]`
2. `nums1[i] - nums1[j]`, where `nums1[j] < nums1[i]`

The important thing is to understand **what parity a subtraction produces**.

### Parity rules

| `a`  | `b`  | `a - b` |
| ---- | ---- | ------- |
| odd  | odd  | even    |
| odd  | even | odd     |
| even | odd  | odd     |
| even | even | even    |

---

## Case 1: Can we make everything even?

Suppose there is at least one odd number.

Consider the **smallest odd number**.

* It cannot subtract another odd number because there is no smaller odd number.
* If it subtracts an even number, the result is **odd**.
* If it doesn't subtract anything, it remains **odd**.

Therefore, if `nums1` contains even **and** odd numbers, making everything even is impossible.

So:

> **All-even answer is possible only when every number is already even.**

For example:

```text
[4, 6, 8]
```

They're already all even → `true`.

---

## Case 2: Can we make everything odd?

This is the interesting case.

* Every **odd** number can simply remain unchanged.
* An **even** number needs to subtract an **odd smaller number**:

```text
even - odd = odd
```

Therefore, for every even number `x`, we need **some smaller odd number**.

The easiest way to guarantee this is to find the **smallest odd number**.

If:

```text
smallestOdd < everyEvenNumber
```

then every even number can subtract `smallestOdd` and become odd.

### Example

```text
nums1 = [1, 4, 7]
```

Smallest odd = `1`.

* `1` → keep `1`
* `4 - 1 = 3`
* `7` → keep `7`

So:

```text
[1, 3, 7]
```

All odd → `true`.

---

# Algorithm

1. Find the smallest odd number.
2. If there is **no odd number**, all numbers are even → return `true`.
3. Otherwise, check every even number:

   * if `even <= smallestOdd`, it cannot subtract a smaller odd number → `false`
4. Otherwise, return `true`.

Because the numbers are distinct, `even == smallestOdd` cannot actually happen due to different parity, but using `<=` makes the condition clear.

---

## Java Solution

```java
class Solution {
    public boolean uniformArray(int[] nums1) {
        int smallestOdd = Integer.MAX_VALUE;

        // Find the smallest odd number
        for (int num : nums1) {
            if (num % 2 != 0) {
                smallestOdd = Math.min(smallestOdd, num);
            }
        }

        // No odd number -> all numbers are already even
        if (smallestOdd == Integer.MAX_VALUE) {
            return true;
        }

        // Every even number must have a smaller odd number
        for (int num : nums1) {
            if (num % 2 == 0 && num < smallestOdd) {
                return false;
            }
        }

        return true;
    }
}
```

### 🔍 Why does `num < smallestOdd` cause `false`?

Suppose:

```text
nums1 = [2, 3]
```

Smallest odd = `3`.

For `2`:

```text
2 - 3
```

is negative, so it's not allowed.

There is no smaller odd number than `2`.

And `2` itself is even, so we cannot make it odd.

Therefore:

```text
false
```

---

### Another example

```text
nums1 = [3, 8, 5, 10]
```

Smallest odd = `3`.

For the even numbers:

```text
8 - 3 = 5
10 - 3 = 7
```

Odd numbers `3` and `5` can remain unchanged.

So we can construct:

```text
[3, 5, 5, 7]
```

All odd → `true`.

---

## Complexity

We scan the array twice:

* **Time:** `O(n)`
* **Space:** `O(1)`

With `n <= 10^5`, this is easily efficient enough.

### 🧠 The one-line intuition

> **If there are only even numbers, we're done; otherwise, find the smallest odd number, and every even number must be larger than it so that it can subtract that odd number and become odd.**
