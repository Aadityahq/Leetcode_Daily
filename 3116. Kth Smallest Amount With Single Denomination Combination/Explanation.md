# 3116. Kth Smallest Amount With Single Denomination Combination

The key phrase in this problem is:

> **You cannot combine different denominations.**

So, if we have:

`coins = [2, 5]`

We can make:

* Using only `2`: `2, 4, 6, 8, 10, 12, ...`
* Using only `5`: `5, 10, 15, 20, ...`

The valid amounts are the **union of multiples**:

`2, 4, 5, 6, 8, 10, 12, 14, 15, ...`

We need to find the `k`th smallest **distinct** amount.

---

## Example

### Input

```text
coins = [5, 2], k = 7
```

All valid amounts in sorted order:

```text
2, 4, 5, 6, 8, 10, 12, 14, 15, ...
```

The 7th smallest amount is:

```text
12
```

---

# Why is this problem difficult?

A simple approach would be to generate multiples:

```text
2, 4, 6, 8, 10, ...
5, 10, 15, 20, ...
```

and merge them.

But:

```text
k <= 2 * 10^9
```

We obviously cannot generate 2 billion numbers.

So instead of finding the answer directly, we ask:

> **For a given number `x`, how many valid amounts are less than or equal to `x`?**

If we can calculate this efficiently, we can use **Binary Search**.

---

# Main Idea: Binary Search on the Answer

Suppose:

```text
coins = [2, 5]
```

For `x = 10`:

Multiples of `2` ≤ `10`:

```text
2, 4, 6, 8, 10
```

Count = `10 / 2 = 5`

Multiples of `5` ≤ `10`:

```text
5, 10
```

Count = `10 / 5 = 2`

If we simply add:

```text
5 + 2 = 7
```

this is wrong because `10` is counted twice.

We need to count the **union** of multiples.

This is where **Inclusion-Exclusion Principle** comes in.

---

# Inclusion-Exclusion Principle

For two coins `a` and `b`:

```text
multiples of a + multiples of b
- multiples of both a and b
```

Numbers that are multiples of both `a` and `b` are multiples of:

```text
LCM(a, b)
```

So:

```text
count(x)
= x / a
+ x / b
- x / LCM(a, b)
```

### Example: `coins = [2, 5]`, `x = 10`

```text
10 / 2 = 5
10 / 5 = 2
10 / LCM(2, 5) = 10 / 10 = 1
```

Therefore:

```text
5 + 2 - 1 = 6
```

The valid numbers are:

```text
2, 4, 5, 6, 8, 10
```

Exactly 6 numbers. ✅

---

# What about 3 coins?

Suppose:

```text
coins = [2, 3, 5]
```

Then:

```text
Count(A ∪ B ∪ C)

= Count(A)
+ Count(B)
+ Count(C)

- Count(A ∩ B)
- Count(A ∩ C)
- Count(B ∩ C)

+ Count(A ∩ B ∩ C)
```

In general:

* Subset with an **odd number of coins** → add
* Subset with an **even number of coins** → subtract

Since:

```text
coins.length <= 15
```

the total number of subsets is:

```text
2^15 = 32768
```

This is manageable.

---

# Step 1: Count valid amounts ≤ `x`

For every non-empty subset of coins:

1. Calculate the LCM of all coins in that subset.
2. Count how many multiples of that LCM are ≤ `x`.
3. If the subset has an odd number of coins, add the count.
4. If it has an even number of coins, subtract the count.

For example:

```text
subset = [2, 3]
LCM = 6
```

Multiples of both 2 and 3 are:

```text
6, 12, 18, ...
```

Number of these ≤ `x`:

```text
x / 6
```

---

# Step 2: Binary Search

The answer has a very useful property.

If there are at least `k` valid amounts ≤ `x`, then the answer is:

```text
<= x
```

Otherwise:

```text
> x
```

So we binary search for the **smallest `x` such that count(x) >= k**.

---

# Dry Run

### Input

```text
coins = [5, 2]
k = 7
```

Suppose Binary Search checks:

```text
x = 10
```

Count valid amounts ≤ 10:

```text
multiples of 2 = 5
multiples of 5 = 2
multiples of both = multiples of 10 = 1
```

Therefore:

```text
5 + 2 - 1 = 6
```

Since:

```text
6 < 7
```

our answer is greater than `10`.

Now check:

```text
x = 12
```

```text
12 / 2 = 6
12 / 5 = 2
12 / 10 = 1
```

So:

```text
6 + 2 - 1 = 7
```

We have at least 7 valid amounts.

Therefore:

```text
answer <= 12
```

Finally, binary search finds:

```text
12
```

---

# Java Solution

```java
class Solution {

    public long findKthSmallest(int[] coins, int k) {
        int n = coins.length;

        // We can remove redundant coins.
        // For example, if 3 is present, 6 and 9 are unnecessary
        // because every multiple of 6 and 9 is already a multiple of 3.
        Arrays.sort(coins);

        List<Integer> filtered = new ArrayList<>();

        for (int coin : coins) {
            boolean redundant = false;

            for (int previous : filtered) {
                if (coin % previous == 0) {
                    redundant = true;
                    break;
                }
            }

            if (!redundant) {
                filtered.add(coin);
            }
        }

        int[] arr = new int[filtered.size()];
        for (int i = 0; i < filtered.size(); i++) {
            arr[i] = filtered.get(i);
        }

        long low = 1;
        long high = (long) arr[0] * k;

        while (low < high) {
            long mid = low + (high - low) / 2;

            if (count(mid, arr) >= k) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }

        return low;
    }

    private long count(long x, int[] coins) {
        int n = coins.length;
        long total = 0;

        int totalMasks = 1 << n;

        for (int mask = 1; mask < totalMasks; mask++) {
            long lcm = 1;
            int bits = 0;
            boolean valid = true;

            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) != 0) {
                    bits++;

                    long gcd = gcd(lcm, coins[i]);

                    // Calculate LCM safely:
                    // lcm(a, b) = a / gcd(a, b) * b
                    lcm = lcm / gcd * coins[i];

                    // If LCM is greater than x,
                    // x / lcm will be 0.
                    // No need to continue calculating.
                    if (lcm > x) {
                        valid = false;
                        break;
                    }
                }
            }

            if (!valid) {
                continue;
            }

            long multiples = x / lcm;

            if (bits % 2 == 1) {
                total += multiples;
            } else {
                total -= multiples;
            }
        }

        return total;
    }

    private long gcd(long a, long b) {
        while (b != 0) {
            long temp = a % b;
            a = b;
            b = temp;
        }
        return a;
    }
}
```

---

# Why do we remove redundant coins?

Consider:

```text
coins = [3, 6, 9]
```

Multiples of `3`:

```text
3, 6, 9, 12, 15, 18, ...
```

Multiples of `6`:

```text
6, 12, 18, ...
```

Multiples of `9`:

```text
9, 18, 27, ...
```

Every multiple of `6` is already a multiple of `3`.

Every multiple of `9` is also already a multiple of `3`.

Therefore:

```text
[3, 6, 9]
```

can effectively become:

```text
[3]
```

This reduces unnecessary subsets.

The code does this:

```java
if (coin % previous == 0)
```

If the current coin is divisible by a smaller existing coin, it is redundant.

---

# Why is `high = smallestCoin * k`?

Let the smallest coin be `c`.

We can always make:

```text
c, 2c, 3c, ..., kc
```

These are `k` valid amounts.

Therefore, the `k`th smallest answer is guaranteed to be at most:

```text
k * c
```

So:

```java
long high = (long) arr[0] * k;
```

is a safe upper bound.

---

# Understanding the Inclusion-Exclusion Code

This loop generates every non-empty subset:

```java
for (int mask = 1; mask < (1 << n); mask++)
```

For example, with:

```text
coins = [2, 3, 5]
```

A mask can represent:

```text
001 → [2]
010 → [3]
011 → [2, 3]
100 → [5]
101 → [2, 5]
110 → [3, 5]
111 → [2, 3, 5]
```

For each subset, we calculate its LCM.

Then:

```java
long multiples = x / lcm;
```

tells us how many numbers up to `x` are divisible by **all coins in that subset**.

Finally:

```java
if (bits % 2 == 1) {
    total += multiples;
} else {
    total -= multiples;
}
```

This is the Inclusion-Exclusion Principle:

```text
Odd-sized subset  → Add
Even-sized subset → Subtract
```

---

# Complexity

Let:

```text
n = coins.length
```

We examine every subset:

```text
2^n
```

For each subset, we may process up to `n` coins.

### Time Complexity

```text
O(log(answer) × n × 2^n)
```

Since:

```text
n <= 15
2^15 = 32768
```

this is feasible.

### Space Complexity

```text
O(n)
```

apart from the small filtered array/list.

---

# Simple Intuition to Remember

Think of the problem like this:

> We don't generate the amounts.
> We **guess an amount `x`** and count how many valid amounts exist before it.

```text
Can make at least k amounts ≤ x?
        |
       YES → answer may be smaller
        |
       NO  → answer must be larger
```

To count the distinct multiples correctly:

```text
Use Inclusion-Exclusion + LCM
```

So the complete approach is:

> **Binary Search on Answer + Inclusion-Exclusion Principle + LCM**

This is the standard efficient approach for this problem.
