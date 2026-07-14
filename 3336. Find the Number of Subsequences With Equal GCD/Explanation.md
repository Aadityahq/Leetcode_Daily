## LeetCode 3336. Find the Number of Subsequences With Equal GCD (Hard)

### Problem Summary

You are given an integer array `nums`.

You need to count the number of **ordered pairs** of subsequences `(seq1, seq2)` such that:

1. Both subsequences are **non-empty**.
2. They are **disjoint** (cannot use the same index).
3. `GCD(seq1) == GCD(seq2)`.

Return the answer modulo **10⁹ + 7**.

---

# Intuition

The constraints are:

* `n ≤ 200`
* `nums[i] ≤ 200`

Notice something important:

Although there are `2^200` subsequences, the **GCD can only be between 1 and 200**.

Instead of remembering the entire subsequence, we only need to remember:

* Current GCD of seq1
* Current GCD of seq2

Since GCD values are at most 200,

there are only

```
201 × 201 = 40401
```

possible states.

This is small enough for Dynamic Programming.

---

# DP State

Let

```
dp[g1][g2]
```

represent

> Number of ways after processing some elements where

* seq1 has GCD = g1
* seq2 has GCD = g2

Special meaning:

```
g = 0
```

means the subsequence is still empty.

Initially

```
dp[0][0] = 1
```

because nothing has been chosen yet.

---

# Processing Each Number

Suppose current number is

```
x
```

For every state `(g1, g2)` we have **3 choices**.

---

## Choice 1

Ignore x

```
(g1, g2)
```

remains unchanged.

---

## Choice 2

Put x into seq1

New gcd becomes

```
newG1 =
    x            if g1 == 0
    gcd(g1,x)    otherwise
```

State

```
(newG1, g2)
```

---

## Choice 3

Put x into seq2

Similarly

```
newG2 =
    x            if g2==0
    gcd(g2,x)
```

State

```
(g1,newG2)
```

---

Thus every element creates exactly three transitions.

---

# Final Answer

At the end,

we only count states where

```
g1 == g2
```

and

```
g1 > 0
```

because both subsequences must be non-empty.

Answer

```
Σ dp[g][g]
```

---

# Why does this work?

Every index has exactly three possibilities:

* unused
* seq1
* seq2

Thus every possible pair of disjoint subsequences is generated exactly once.

The DP keeps only their GCDs because future decisions depend only on the current GCD, not on the exact elements chosen.

This reduces an exponential search to roughly

```
200 × 201 × 201
≈ 8 million transitions
```

which is easily fast enough.

---

# Complexity Analysis

Let

```
MAX = 200
```

States

```
201 × 201
```

For each of 200 numbers:

```
O(n × MAX²)

= 200 × 201²

≈ 8 million
```

Time Complexity:

```
O(n × 200²)
```

Space Complexity:

```
O(200²)
```

---

# Java Solution

```java
class Solution {

    private static final int MOD = 1_000_000_007;
    private static final int MAX = 200;

    public int subsequencePairCount(int[] nums) {

        long[][] dp = new long[MAX + 1][MAX + 1];
        dp[0][0] = 1;

        for (int x : nums) {

            long[][] next = new long[MAX + 1][MAX + 1];

            for (int g1 = 0; g1 <= MAX; g1++) {
                for (int g2 = 0; g2 <= MAX; g2++) {

                    long ways = dp[g1][g2];

                    if (ways == 0)
                        continue;

                    // Option 1: Ignore current number
                    next[g1][g2] = (next[g1][g2] + ways) % MOD;

                    // Option 2: Add to seq1
                    int ng1 = (g1 == 0) ? x : gcd(g1, x);
                    next[ng1][g2] = (next[ng1][g2] + ways) % MOD;

                    // Option 3: Add to seq2
                    int ng2 = (g2 == 0) ? x : gcd(g2, x);
                    next[g1][ng2] = (next[g1][ng2] + ways) % MOD;
                }
            }

            dp = next;
        }

        long ans = 0;

        for (int g = 1; g <= MAX; g++) {
            ans = (ans + dp[g][g]) % MOD;
        }

        return (int) ans;
    }

    private int gcd(int a, int b) {
        while (b != 0) {
            int t = a % b;
            a = b;
            b = t;
        }
        return a;
    }
}
```

---

# Dry Run

For

```
nums = [2,4]
```

Initially

```
dp[0][0] = 1
```

Process `2`

Possible states:

```
(0,0)

(2,0)

(0,2)
```

Process `4`

Each state again branches into three choices.

Eventually we get states like

```
(2,2)
(4,4)
(2,4)
(4,2)
...
```

Only

```
(2,2)

(4,4)
```

contribute to the answer.

---

# Key Observation

The trick is realizing that **GCD has only 200 possible values**.

Instead of storing every subsequence (which is impossible), we only store:

* current GCD of seq1
* current GCD of seq2

This compresses the state space from exponential to only about **40,000 states**, making Dynamic Programming feasible.

---

