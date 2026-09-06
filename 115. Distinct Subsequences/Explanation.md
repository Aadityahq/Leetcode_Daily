## LeetCode 115 — Distinct Subsequences

[LeetCode 115: Distinct Subsequences](https://leetcode.com/problems/distinct-subsequences/?utm_source=chatgpt.com)

This is a classic **Dynamic Programming (DP)** problem. The main difficulty is understanding **what exactly we are counting**.

---

# 1. Problem Understanding

You are given two strings:

* `s` → the original string
* `t` → the target string

You need to find **how many different subsequences of `s` can form `t`**.

### What is a subsequence?

A subsequence is formed by deleting some characters from a string **without changing the order** of the remaining characters.

For example:

```text
s = "abc"
```

Some subsequences are:

```text
""
"a"
"b"
"c"
"ab"
"ac"
"bc"
"abc"
```

Notice that characters don't have to be contiguous.

---

# 2. Example

```text
s = "rabbbit"
t = "rabbit"
```

There are three `b`s in `s`:

```text
r a b b b i t
    ↑ ↑ ↑
```

But `rabbit` needs only **two `b`s**.

We can choose:

```text
b₁ b₂
b₁ b₃
b₂ b₃
```

Therefore:

```text
Answer = 3
```

The important point is that we are counting **different ways of choosing characters**, not just the resulting string.

---

# 3. The Key Question

At every character of `s`, we have a choice:

> Should I use this character to construct `t`, or should I skip it?

Suppose:

```text
s = "bab"
t = "ba"
```

At the first `b`, we have two possibilities:

### Choice 1 — Use `b`

```text
b → b
```

Now we need to construct:

```text
"a"
```

from the remaining part of `s`.

### Choice 2 — Skip `b`

We don't use the current `b`.

We still need:

```text
"ba"
```

from the remaining characters.

This naturally creates a DP recurrence.

---

# 4. DP State

Let's define:

```java
dp[i][j]
```

as:

> Number of ways to form the first `j` characters of `t` using the first `i` characters of `s`.

For example:

```text
s = "babgbag"
t = "bag"
```

Then:

```text
dp[4][2]
```

means:

> Number of ways to form `"ba"` using the first 4 characters of `"babg"`.

---

# 5. Why Do We Need Two Cases?

Consider:

```text
s[i - 1] == t[j - 1]
```

If the current characters match, we have **two choices**.

### Choice 1 — Use the character

If we use:

```text
s[i - 1]
```

to match:

```text
t[j - 1]
```

then we need to form the remaining target:

```text
t[0 ... j-2]
```

from:

```text
s[0 ... i-2]
```

So:

```text
dp[i-1][j-1]
```

represents this case.

---

### Choice 2 — Don't use the character

We can ignore `s[i-1]`.

Then we still need to form the complete target of length `j` from the previous `i-1` characters of `s`.

So:

```text
dp[i-1][j]
```

represents this case.

Therefore:

```text
dp[i][j] = dp[i-1][j-1] + dp[i-1][j]
```

when characters match.

---

# 6. What If Characters Don't Match?

Suppose:

```text
s[i-1] != t[j-1]
```

Then we **cannot use** `s[i-1]` to match `t[j-1]`.

So our only option is to skip it.

Therefore:

```text
dp[i][j] = dp[i-1][j]
```

---

# 7. The Recurrence

Putting both cases together:

```text
if (s[i-1] == t[j-1]):

    dp[i][j] = dp[i-1][j-1] + dp[i-1][j]

else:

    dp[i][j] = dp[i-1][j]
```

This is the heart of the solution.

---

# 8. Base Cases

There are two extremely important base cases.

## Case 1: Empty `t`

How many ways can we form an empty string?

Always **1**.

We simply choose nothing.

For example:

```text
s = "abc"
t = ""
```

There is exactly one way:

```text
choose nothing
```

Therefore:

```text
dp[i][0] = 1
```

for every `i`.

---

## Case 2: Non-empty `t` from empty `s`

Suppose:

```text
s = ""
t = "abc"
```

There is no way to construct `"abc"`.

Therefore:

```text
dp[0][j] = 0
```

for every `j > 0`.

---

# 9. DP Table Example

Let's take a smaller example:

```text
s = "bab"
t = "ba"
```

The table represents:

```text
        ""   b   a
""       1   0   0
b        1   1   0
a        1   1   1
b        1   2   1
```

The final answer is:

```text
dp[3][2] = 2
```

The two ways are:

```text
b₁ a
b₂ a
```

---

# 10. Java Solution — 2D DP

```java
class Solution {
    public int numDistinct(String s, String t) {

        int n = s.length();
        int m = t.length();

        int[][] dp = new int[n + 1][m + 1];

        // Empty target can always be formed in 1 way
        for (int i = 0; i <= n; i++) {
            dp[i][0] = 1;
        }

        // Fill the DP table
        for (int i = 1; i <= n; i++) {

            for (int j = 1; j <= m; j++) {

                if (s.charAt(i - 1) == t.charAt(j - 1)) {

                    // Use current character OR skip it
                    dp[i][j] = dp[i - 1][j - 1] + dp[i - 1][j];

                } else {

                    // Current character cannot be used
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }

        return dp[n][m];
    }
}
```

---

# 11. Why `i - 1` and `j - 1`?

This is a very common source of confusion.

Our DP table has:

```text
dp[0][0]
```

representing empty strings.

So:

```text
dp[i][j]
```

represents the first `i` characters of `s` and first `j` characters of `t`.

But Java strings use **0-based indexing**.

Therefore:

```java
s.charAt(i - 1)
```

is the `i`th character of `s`.

And:

```java
t.charAt(j - 1)
```

is the `j`th character of `t`.

For example:

```text
s = "abc"

i = 2
```

The first two characters are:

```text
"ab"
```

and the last character among those is:

```text
s.charAt(1)
```

which is:

```text
'b'
```

Hence:

```java
s.charAt(i - 1)
```

---

# 12. Why Addition?

This is the most important part to understand.

Suppose:

```text
s = "aaa"
t = "aa"
```

We need to choose two `a`s from three.

There are:

```text
3
```

ways:

```text
a₁ a₂
a₁ a₃
a₂ a₃
```

When the current characters match, we split the possibilities into **two disjoint groups**:

### Group 1 — Use current character

```text
dp[i-1][j-1]
```

### Group 2 — Skip current character

```text
dp[i-1][j]
```

Since these are separate possibilities:

```text
total = use + skip
```

Therefore:

```text
dp[i][j] = dp[i-1][j-1] + dp[i-1][j]
```

---

# 13. Why Don't We Add When Characters Don't Match?

Suppose:

```text
s[i-1] = 'x'
t[j-1] = 'a'
```

We cannot use `x` to match `a`.

So:

```text
USE → impossible
```

Only:

```text
SKIP
```

is possible.

Therefore:

```text
dp[i][j] = dp[i-1][j]
```

---

# 14. Space Optimization

Notice something about the recurrence:

```text
dp[i][j]
```

only depends on:

```text
dp[i-1][j-1]
dp[i-1][j]
```

We don't need the entire 2D table.

We can reduce it to a **1D array**.

```java
class Solution {
    public int numDistinct(String s, String t) {

        int n = s.length();
        int m = t.length();

        int[] dp = new int[m + 1];

        // Empty target
        dp[0] = 1;

        for (int i = 1; i <= n; i++) {

            // Traverse backwards
            for (int j = m; j >= 1; j--) {

                if (s.charAt(i - 1) == t.charAt(j - 1)) {
                    dp[j] = dp[j] + dp[j - 1];
                }
            }
        }

        return dp[m];
    }
}
```

This is the version I would recommend using in an interview if you're comfortable explaining the optimization.

---

# 15. Why Do We Traverse `j` Backwards?

This is **very important** in the 1D solution.

Suppose:

```java
dp[j] = dp[j] + dp[j - 1];
```

We need:

```text
dp[j-1]
```

from the **previous row**.

If we iterate forward:

```java
for (int j = 1; j <= m; j++)
```

then `dp[j-1]` might already have been updated for the current character.

That would incorrectly use the same character of `s` multiple times.

Therefore we go:

```text
m → m-1 → ... → 1
```

This ensures `dp[j-1]` is still the value from the previous iteration.

### Think of it like this:

Before processing a character:

```text
dp = previous row
```

While processing it:

```text
dp[j] = old dp[j] + old dp[j-1]
```

Going right-to-left preserves the `old dp[j-1]`.

---

# 16. Complexity

Let:

```text
n = s.length()
m = t.length()
```

### 2D DP

Time:

```text
O(n × m)
```

Space:

```text
O(n × m)
```

### 1D DP

Time:

```text
O(n × m)
```

Space:

```text
O(m)
```

Given:

```text
1 <= s.length, t.length <= 1000
```

the optimized solution is very reasonable.

---

# 17. Dry Run — `"rabbbit"` → `"rabbit"`

Let's focus on the important part.

```text
s = r a b b b i t
t = r a b b i t
```

When processing the three `b`s in `s`, we need to select **two** of them.

The DP essentially counts:

```text
choose b₁ and b₂
choose b₁ and b₃
choose b₂ and b₃
```

Therefore:

```text
3
```

The recurrence automatically calculates this without explicitly generating the subsequences.

---

# 18. Why Brute Force Doesn't Work

One approach would be:

> Generate every subsequence of `s` and check whether it equals `t`.

But a string of length `n` has:

```text
2^n
```

possible subsequences.

For:

```text
n = 1000
```

that is astronomically large.

So we cannot enumerate them.

Instead, DP **counts all valid choices without generating them**.

That's the major idea of the problem.

---

# 19. The Pattern You Should Remember

This problem belongs to an important DP pattern:

> **Count the number of ways to form one string from another while preserving order.**

Whenever you see:

```text
subsequence
count number of ways
form target from source
```

you should immediately think:

```text
USE current character
        OR
SKIP current character
```

If:

```text
s[i-1] == t[j-1]
```

then:

```text
dp[i][j] =
    dp[i-1][j-1]   // use
    +
    dp[i-1][j]     // skip
```

If they don't match:

```text
dp[i][j] = dp[i-1][j]
```

---

## Final Java Code I Recommend

```java
class Solution {
    public int numDistinct(String s, String t) {

        int m = t.length();

        int[] dp = new int[m + 1];

        // There is exactly one way to form an empty target:
        // choose nothing.
        dp[0] = 1;

        for (int i = 1; i <= s.length(); i++) {

            // Go backwards so dp[j - 1] still represents
            // the previous state.
            for (int j = m; j >= 1; j--) {

                if (s.charAt(i - 1) == t.charAt(j - 1)) {
                    dp[j] = dp[j] + dp[j - 1];
                }
            }
        }

        return dp[m];
    }
}
```

### One-line intuition

**For every character in `s`, if it matches the current character of `t`, count both possibilities: use it or skip it; otherwise, only skip it.**
