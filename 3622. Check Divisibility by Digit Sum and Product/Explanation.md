# LeetCode 3622: Check Divisibility by Digit Sum and Product

**Difficulty:** Easy

## Problem Explanation

You are given a positive integer `n`.

You need to find two things from its digits:

1. **Digit Sum** → Sum of all digits
2. **Digit Product** → Product of all digits

Then calculate:

```text
digitSum + digitProduct
```

Finally, check:

```text
n % (digitSum + digitProduct) == 0
```

* If the remainder is `0`, return `true`.
* Otherwise, return `false`.

---

## Example 1

```text
n = 99
```

### Step 1: Find the digit sum

Digits are `9` and `9`.

```text
9 + 9 = 18
```

### Step 2: Find the digit product

```text
9 × 9 = 81
```

### Step 3: Add them

```text
18 + 81 = 99
```

### Step 4: Check divisibility

```text
99 % 99 = 0
```

So, the answer is:

```text
true
```

---

## Example 2

```text
n = 23
```

### Digit Sum

```text
2 + 3 = 5
```

### Digit Product

```text
2 × 3 = 6
```

### Add them

```text
5 + 6 = 11
```

### Check divisibility

```text
23 % 11 = 1
```

The remainder is not `0`, so the answer is:

```text
false
```

---

# Approach

We can extract every digit of a number using `% 10`.

For example:

```text
n = 234
```

```text
234 % 10 = 4
```

Remove the last digit using `/ 10`:

```text
234 / 10 = 23
23 / 10 = 2
2 / 10 = 0
```

So we process each digit one by one.

We maintain:

```text
sum = sum of digits
product = product of digits
```

Then check:

```java
n % (sum + product) == 0
```

---

# Java Solution

```java
class Solution {
    public boolean checkDivisibility(int n) {
        int original = n;
        int sum = 0;
        int product = 1;

        while (n > 0) {
            int digit = n % 10;

            sum += digit;
            product *= digit;

            n /= 10;
        }

        return original % (sum + product) == 0;
    }
}
```

---

# How the Code Works

Suppose:

```text
n = 99
```

Initially:

```text
original = 99
sum = 0
product = 1
```

### First iteration

```text
digit = 99 % 10 = 9

sum = 0 + 9 = 9
product = 1 × 9 = 9

n = 99 / 10 = 9
```

### Second iteration

```text
digit = 9 % 10 = 9

sum = 9 + 9 = 18
product = 9 × 9 = 81

n = 9 / 10 = 0
```

The loop ends.

Now:

```text
sum + product = 18 + 81 = 99
```

Check:

```text
original % (sum + product)
99 % 99 = 0
```

Therefore:

```java
return true;
```

---

# Why Do We Store `original`?

Inside the loop, we continuously modify `n`:

```java
n /= 10;
```

For example:

```text
99 → 9 → 0
```

But at the end, we need the **original number** to check divisibility.

Therefore, before changing `n`, we store it:

```java
int original = n;
```

Then we use:

```java
original % (sum + product)
```

---

# Complexity Analysis

Let `d` be the number of digits in `n`.

### Time Complexity

```text
O(d)
```

We visit every digit exactly once.

Since `n ≤ 10⁶`, there are at most 7 digits.

### Space Complexity

```text
O(1)
```

We only use a few variables.

---

## Key Idea

```text
Extract each digit
        ↓
Calculate digit sum
        ↓
Calculate digit product
        ↓
Add both values
        ↓
Check if n is divisible by the result
```

The important line is:

```java
return original % (sum + product) == 0;
```

If the remainder is `0`, the number is divisible, so we return `true`.
