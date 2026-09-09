## LeetCode 3871 — Count Commas in Range II

### 💡 Key Idea

We need to count **how many commas appear when writing every number from `1` to `n`**.

A comma is added:

* For numbers with **4–6 digits** → `1` comma
* For numbers with **7–9 digits** → `2` commas
* For numbers with **10–12 digits** → `3` commas
* For numbers with **13–15 digits** → `4` commas

Since `n` can be as large as `10^15`, we **cannot iterate from `1` to `n`**.

Instead, we count numbers in groups based on their number of digits.

---

## 🧠 Observation

Consider numbers from `1000` to `999999`.

Every one of them has exactly **one comma**:

```text
1,000
1,001
...
999,999
```

There are:

```text
999999 - 1000 + 1 = 999000
```

numbers, so they contribute:

```text
999000 × 1
```

commas.

Similarly:

### 7–9 digit numbers

From:

```text
1,000,000 → 999,999,999
```

Every number has **2 commas**.

So contribution:

```text
count × 2
```

We continue this for every group.

---

# Java Solution

```java
class Solution {
    public long countCommas(long n) {

        long ans = 0;

        // Numbers with 4, 7, 10, 13 digits...
        for (long start = 1000; start <= n; ) {

            long end = start * 1000 - 1;

            long count = Math.min(n, end) - start + 1;

            // Number of commas = digits / 3 - 1
            long commas = start / 1000;

            // Better calculate number of commas for this range
            long commaCount = 1;

            long temp = start;
            while (temp >= 1000) {
                temp /= 1000;
                if (temp >= 1000) {
                    commaCount++;
                }
            }

            ans += count * commaCount;

            // Move to the next group
            if (start > n / 1000) {
                break;
            }

            start *= 1000;
        }

        return ans;
    }
}
```

However, we can make the solution **much simpler and cleaner**.

### Recommended Solution

```java
class Solution {
    public long countCommas(long n) {

        long ans = 0;

        for (long power = 1000; power <= n; power *= 1000) {

            long count = n - power + 1;

            ans += count;

            if (power > n / 1000) {
                break;
            }
        }

        return ans;
    }
}
```

But there's an important detail: this counts each comma position independently.

Let's understand why this works.

---

# 🔍 Why `n - power + 1`?

For `power = 1000`:

```text
1000, 1001, 1002, ..., n
```

Every number from `1000` onward has **at least one comma**.

Number of such numbers:

```text
n - 1000 + 1
```

For `power = 1,000,000`:

```text
1,000,000
1,000,001
...
n
```

Every number from `1,000,000` onward has a **second comma**.

Number of such numbers:

```text
n - 1,000,000 + 1
```

For `power = 1,000,000,000`:

```text
1,000,000,000
...
n
```

These numbers have a **third comma**.

So we can simply add:

```text
(n - 1000 + 1)
+ (n - 1,000,000 + 1)
+ (n - 1,000,000,000 + 1)
+ ...
```

for every power of `1000` that is ≤ `n`.

---

## Example: `n = 1002`

Initially:

```text
power = 1000
```

Numbers that have the first comma:

```text
1000, 1001, 1002
```

Count:

```text
1002 - 1000 + 1 = 3
```

So:

```text
ans = 3
```

Next:

```text
power = 1,000,000
```

But:

```text
1,000,000 > 1002
```

Stop.

Answer:

```text
3
```

---

## Example: `n = 1,000,002`

First comma:

```text
1000 → 1,000,002
```

Count:

```text
1,000,002 - 1,000 + 1
= 999,003
```

Second comma:

```text
1,000,000 → 1,000,002
```

Count:

```text
1,000,002 - 1,000,000 + 1
= 3
```

Total:

```text
999,003 + 3
= 999,006
```

---

# ⚠️ Why `long`?

The constraint is:

```text
n <= 10^15
```

This is much larger than the maximum value safely represented by Java's `int`:

```text
int max ≈ 2.1 × 10^9
```

So we use:

```java
long
```

which can safely handle values up to approximately:

```text
9.22 × 10^18
```

---

# Complexity

There are only a few powers of `1000`:

```text
1000
10^6
10^9
10^12
10^15
```

Therefore:

**Time:** `O(log₁₀₀₀ n)` → effectively **O(1)** for the constraints.

**Space:** `O(1)`.

---

## ⭐ Final Code

```java
class Solution {
    public long countCommas(long n) {

        long ans = 0;

        for (long power = 1000; power <= n; power *= 1000) {
            ans += n - power + 1;

            // Prevent overflow when multiplying by 1000
            if (power > n / 1000) {
                break;
            }
        }

        return ans;
    }
}
```

### The core idea to remember

> **Instead of counting commas number-by-number, count each comma position separately.**

`1000` introduces the **1st comma**, `10^6` introduces the **2nd**, `10^9` introduces the **3rd**, and so on.

That turns an impossible `O(n)` problem into a tiny `O(log n)` solution.
