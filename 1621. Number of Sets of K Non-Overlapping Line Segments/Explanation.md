This problem looks like a **DP problem**, but there is actually a very nice **combinatorics solution**.

The key is to understand what a valid set of `k` non-overlapping segments looks like.

---

# 1621. Number of Sets of K Non-Overlapping Line Segments

### Example

For:

```text
n = 4, k = 2
```

Points are:

```text
0 --- 1 --- 2 --- 3
```

A valid pair could be:

```text
(0,1), (1,3)
```

They are allowed to **share endpoint `1`**.

The important condition is:

```text
a1 < b1 <= a2 < b2 <= a3 ...
```

For `k` segments, we can write:

```text
a1 < b1 <= a2 < b2 <= ... <= ak < bk
```

---

# 1. Think about the gaps

Suppose we have `k` segments:

```text
(a1, b1)
(a2, b2)
...
(ak, bk)
```

Each segment must cover at least two points, so:

```text
bi - ai >= 1
```

There are several kinds of gaps.

For example:

```text
0  1  2  3  4  5  6

    [------]
             [----]
```

We can describe the arrangement using:

### Segment lengths

```text
L1 = b1 - a1
L2 = b2 - a2
...
Lk = bk - ak
```

Each:

```text
Li >= 1
```

### Gaps

There can be:

```text
G0 = points before first segment
G1 = gap between segment 1 and segment 2
...
Gk = points after last segment
```

Because segments are allowed to share endpoints, these gaps can be `0`.

Therefore:

```text
Gi >= 0
```

---

# 2. The most important observation

The total distance from point `0` to point `n-1` is:

```text
n - 1
```

That distance consists of:

```text
L1 + L2 + ... + Lk
+ G0 + G1 + ... + Gk
```

Therefore:

```text
L1 + L2 + ... + Lk + G0 + ... + Gk = n - 1
```

But every segment must have at least length `1`.

So let's remove that mandatory `1` from every segment.

Define:

```text
Xi = Li - 1
```

Now:

```text
Xi >= 0
```

And the equation becomes:

```text
X1 + X2 + ... + Xk
+ G0 + G1 + ... + Gk
= n - 1 - k
```

How many variables do we have?

There are:

```text
k variables for X
+
k+1 variables for G
=
2k+1 variables
```

So we need the number of non-negative integer solutions to:

```text
x1 + x2 + ... + x(2k+1) = n - 1 - k
```

This is a classic **Stars and Bars** problem.

The answer is:

$$
\binom{(n-1-k)+(2k+1)-1}{(2k+1)-1}
$$

Simplifying:

$$
\boxed{\binom{n+k-1}{2k}}
$$

That's the entire mathematical solution.

---

# 3. Let's verify with examples

### Example 1

```text
n = 4
k = 2
```

Formula:

```text
C(n + k - 1, 2k)
= C(4 + 2 - 1, 4)
= C(5, 4)
= 5
```

Answer:

```text
5
```

---

### Example 2

```text
n = 3
k = 1
```

```text
C(3 + 1 - 1, 2)
= C(3, 2)
= 3
```

The three segments are:

```text
(0,1)
(0,2)
(1,2)
```

Correct.

---

# 4. Why does Stars and Bars work here?

Suppose we have:

```text
x1 + x2 + x3 = 4
```

One solution:

```text
x1 = 2
x2 = 1
x3 = 1
```

We can represent it as:

```text
** | * | *
```

We have:

* `4` stars
* `2` bars

Total:

```text
4 + 2 = 6
```

positions.

Choose the positions of the bars:

```text
C(6, 2)
```

In our problem:

```text
stars = n - 1 - k
variables = 2k + 1
bars = 2k
```

Therefore:

```text
C((n-1-k) + 2k, 2k)
```

which becomes:

```text
C(n+k-1, 2k)
```

---

# 5. Java Solution

Since:

```text
n <= 1000
k <= n-1
```

we have:

```text
n + k - 1 <= 1998
```

So we can calculate combinations using Pascal's Triangle.

A very simple implementation is:

```java
class Solution {

    static final long MOD = 1_000_000_007;

    public int numberOfSets(int n, int k) {

        int max = n + k - 1;

        long[][] dp = new long[max + 1][2 * k + 1];

        // C(i, 0) = 1
        for (int i = 0; i <= max; i++) {
            dp[i][0] = 1;
        }

        // Build Pascal's Triangle
        for (int i = 1; i <= max; i++) {
            for (int j = 1; j <= Math.min(i, 2 * k); j++) {
                dp[i][j] = (dp[i - 1][j - 1] + dp[i - 1][j]) % MOD;
            }
        }

        return (int) dp[max][2 * k];
    }
}
```

---

# 6. Understanding the code

We need:

```text
C(n + k - 1, 2k)
```

So:

```java
int max = n + k - 1;
```

For example:

```text
n = 4
k = 2

max = 5
```

We need:

```text
C(5, 4)
```

---

### Pascal's identity

We use:

$$
C(n,r) = C(n-1,r-1) + C(n-1,r)
$$

That's exactly:

```java
dp[i][j] =
    dp[i - 1][j - 1]
    + dp[i - 1][j];
```

For example:

```text
C(5,4)
```

becomes:

```text
C(4,3) + C(4,4)
```

---

### Base case

We know:

```text
C(n,0) = 1
```

because there is exactly one way to choose nothing.

So:

```java
for (int i = 0; i <= max; i++) {
    dp[i][0] = 1;
}
```

---

# 7. Space Optimized Java Solution

We don't actually need the entire 2D Pascal triangle.

We only need one row.

```java
class Solution {

    static final long MOD = 1_000_000_007;

    public int numberOfSets(int n, int k) {

        int N = n + k - 1;
        int R = 2 * k;

        long[] dp = new long[R + 1];

        dp[0] = 1;

        for (int i = 1; i <= N; i++) {

            for (int j = Math.min(i, R); j >= 1; j--) {
                dp[j] = (dp[j] + dp[j - 1]) % MOD;
            }
        }

        return (int) dp[R];
    }
}
```

This is the version I would recommend for LeetCode.

---

# 8. Why do we loop backwards?

This is important.

We are calculating:

```text
C(i,j) = C(i-1,j) + C(i-1,j-1)
```

When using a 1D array, before updating:

```text
dp[j]
```

we need:

```text
dp[j]       → C(i-1, j)
dp[j-1]     → C(i-1, j-1)
```

So we update `j` from **right to left**:

```java
for (int j = Math.min(i, R); j >= 1; j--)
```

If we went left to right, `dp[j-1]` would already have been modified for the current row, which would give the wrong result.

---

# 9. Complexity

Let:

```text
N = n + k - 1
R = 2k
```

The loops take:

```text
O(N × R)
```

Since:

```text
N <= 1998
R <= 1998
```

this is easily fast enough.

### Time

```text
O((n+k) × k)
```

### Space

```text
O(k)
```

---

# 10. The main trick to remember

This problem is **not really about drawing segments** after the transformation.

Think:

```text
k segments
      ↓
segment lengths + gaps
      ↓
make every segment length at least 1
      ↓
subtract 1 from every segment
      ↓
non-negative integer equation
      ↓
Stars and Bars
      ↓
C(n+k-1, 2k)
```

The key equation is:

$$
\boxed{\text{answer} = \binom{n+k-1}{2k}}
$$

And the reason this works is that **every valid arrangement of segments corresponds to exactly one distribution of the segment lengths and gaps**, so there is no double-counting.

If you're learning DSA, this is a good problem to remember as a **"DP-looking problem that can be reduced to combinatorics."**
