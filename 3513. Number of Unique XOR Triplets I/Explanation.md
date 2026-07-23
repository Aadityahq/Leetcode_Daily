### Intuition

Since `nums` is a **permutation of `[1, n]`**, the order of the array does **not** matter.

The triplet

```
nums[i] XOR nums[j] XOR nums[k]
```

depends only on **which values** are chosen, not on where they appear.

So the problem becomes:

> From the numbers `{1,2,...,n}`, pick any three numbers (repetition allowed because `i = j = k` is allowed), and count how many different XOR values are possible.

---

# Observation 1

If we choose the same number three times,

```
x ^ x ^ x = x
```

because

```
x ^ x = 0
0 ^ x = x
```

So every number from `1` to `n` is always obtainable.

Example:

```
5 ^ 5 ^ 5 = 5
```

---

# Observation 2

When `n = 1`

Only possible XOR:

```
1 ^ 1 ^ 1 = 1
```

Answer:

```
1
```

---

When `n = 2`

Possible values are

```
1
2
```

Answer:

```
2
```

---

# Observation 3 (Important)

For **every** `n ≥ 3`, something interesting happens.

Let

```
k = number of bits needed to represent n
```

For example

```
n = 5

5 = 101₂

needs 3 bits
```

Then every XOR result must be between

```
0
and
2^k − 1
```

because XOR never creates a higher bit than the inputs already have.

Example

```
n = 5

largest number = 101₂

Any XOR also fits in 3 bits.

Possible values:
0..7
```

Surprisingly,

> **Every value from `0` to `2^k−1` can actually be formed when `n ≥ 3`.**

So the number of unique XOR values is simply

```
2^k
```

---

# Examples

## Example 1

```
nums = [1,2]
```

Possible XOR values

```
1
2
```

Answer

```
2
```

---

## Example 2

```
nums = [3,1,2]
```

Possible XOR values become

```
0
1
2
3
```

Answer

```
4
```

Notice

```
k = 2

2² = 4
```

---

## Example 3

```
nums = [1,2,3,4,5]
```

Largest number

```
5 = 101₂

k = 3
```

Possible XOR values

```
0..7
```

Total

```
8
```

---

# Formula

If

```
n < 3
```

answer is

```
n
```

Otherwise

```
answer = 2^(bit length of n)
```

---

# How to compute bit length

In Java,

```
Integer.SIZE - Integer.numberOfLeadingZeros(n)
```

Example

```
n = 5

101

bit length = 3
```

Then

```
1 << bitLength
```

gives

```
8
```

---

# Algorithm

1. Let `n = nums.length`.
2. If `n <= 2`, return `n`.
3. Find the bit length of `n`.
4. Return `1 << bitLength`.

---

# Java Solution

```java
class Solution {
    public int uniqueXorTriplets(int[] nums) {
        int n = nums.length;

        if (n <= 2) {
            return n;
        }

        int bitLength = Integer.SIZE - Integer.numberOfLeadingZeros(n);

        return 1 << bitLength;
    }
}
```

---

# Why does this work?

* The array is a permutation of `1...n`, so only the set of values matters.
* Repeating the same value three times guarantees every number `1...n` is obtainable.
* For `n ≥ 3`, XOR combinations become rich enough to generate **every** value representable using the same number of bits as `n`.
* There are exactly

```
2^k
```

such values, where `k` is the bit length of `n`.

Hence,

* `n = 1` → `1`
* `n = 2` → `2`
* `n ≥ 3` → `2^(bit length of n)`

---

### Complexity

* **Time:** `O(1)`
* **Space:** `O(1)`

This is the optimal solution because the answer depends only on `n`, not on the arrangement of the permutation.
