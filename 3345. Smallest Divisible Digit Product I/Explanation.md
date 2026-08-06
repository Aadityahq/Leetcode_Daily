



## Intuition

We are given:

- `n` → starting number.
- `t` → divisor.

We need to find the **smallest number ≥ n** whose **product of digits** is divisible by `t`.

Since:

- `1 ≤ n ≤ 100`
- `1 ≤ t ≤ 10`

the search space is very small. We can simply check each number starting from `n` until we find the answer.

---

## Understanding the Problem

For every number:

1. Find the product of all its digits.
2. Check whether

\[
\text{product} \bmod t = 0
\]

If yes, return that number.

---

### Example 1

```
n = 10
t = 2
```

Check 10

Digits:

```
1, 0
```

Product

```
1 × 0 = 0
```

Now,

```
0 % 2 = 0
```

So answer is

```
10
```

---

### Example 2

```
n = 15
t = 3
```

Check 15

```
1 × 5 = 5
```

```
5 % 3 = 2
```

Not divisible.

Next number:

```
16
```

Product

```
1 × 6 = 6
```

```
6 % 3 = 0
```

Answer:

```
16
```

---

# Approach

Start from `n`.

For each number:

- Extract every digit.
- Multiply the digits together.
- If product is divisible by `t`, return the number.
- Otherwise move to the next number.

Because constraints are very small (`n ≤ 100`), this brute-force approach is perfectly acceptable.

---

# Algorithm

```
current = n

while(true)

    calculate digit product of current

    if(product % t == 0)
         return current

    current++
```

---

# Dry Run

### Input

```
n = 18
t = 8
```

Check 18

Digits:

```
1,8
```

Product

```
1×8 = 8
```

```
8 % 8 = 0
```

Answer

```
18
```

---

Another example

```
n = 22
t = 6
```

Check 22

```
2×2 = 4
```

```
4 % 6 != 0
```

Next

```
23
```

```
2×3 = 6
```

```
6 % 6 == 0
```

Answer

```
23
```

---

# Java Solution

```java
class Solution {
    public int smallestNumber(int n, int t) {
        while (true) {
            int product = digitProduct(n);

            if (product % t == 0)
                return n;

            n++;
        }
    }

    private int digitProduct(int num) {
        int product = 1;

        while (num > 0) {
            product *= (num % 10);
            num /= 10;
        }

        return product;
    }
}
```

---

# Why does this work?

For every integer starting from `n`:

- We compute the product of its digits exactly once.
- We check whether that product is divisible by `t`.
- Since we examine numbers in increasing order, the **first valid number** we encounter is automatically the **smallest** number satisfying the condition.

This guarantees the correct answer.

---

# Complexity Analysis

Let:

- `k` = number of digits in a number (at most 3 since `n` is around 100).

If we check `m` numbers before finding the answer:

- **Time Complexity:** `O(m × k)` ≈ `O(m)`
- **Space Complexity:** `O(1)`

Since `n ≤ 100` and `t ≤ 10`, `m` is very small, making this solution efficient.

---

## Key Takeaways

- Use **brute force** because the constraints are tiny.
- Compute the **product of digits** using `% 10` and `/ 10`.
- Check `product % t == 0`.
- The first valid number encountered is the smallest answer because we search in increasing order.