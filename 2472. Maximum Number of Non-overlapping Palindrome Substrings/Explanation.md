## 2472. Maximum Number of Non-overlapping Palindrome Substrings

The key here is to realize that this is **not** asking for the longest palindromes. We want the **maximum number of non-overlapping palindromic substrings**, each having length at least `k`.

### Idea

We can use dynamic programming:

Let `dp[i]` = maximum number of valid non-overlapping palindromes we can select from the first `i` characters (`s[0 ... i-1]`).

For every position, we have two choices:

1. **Don't end a palindrome at this position**

   * `dp[i] = dp[i - 1]`

2. **Choose a palindrome ending at `i - 1`**

   * Suppose `s[j ... i-1]` is a palindrome and its length is at least `k`.
   * Then everything before `j` is independent.
   * So:
     `dp[i] = max(dp[i], dp[j] + 1)`

The important part is efficiently determining whether `s[j...i-1]` is a palindrome.

Since `n <= 2000`, we can use a 2D DP table:

`palindrome[i][j] = true` if `s[i...j]` is a palindrome.

A substring is a palindrome when:

```text
s[i] == s[j]
AND
the inside substring s[i+1...j-1] is a palindrome
```

So:

```text
palindrome[i][j] = s.charAt(i) == s.charAt(j)
                  && (length <= 2 || palindrome[i+1][j-1])
```

---

### Java Solution

```java
class Solution {

    public int maxPalindromes(String s, int k) {

        int n = s.length();

        // palindrome[i][j] = true if s[i...j] is a palindrome
        boolean[][] palindrome = new boolean[n][n];

        // Build palindrome table
        for (int i = n - 1; i >= 0; i--) {

            for (int j = i; j < n; j++) {

                if (s.charAt(i) == s.charAt(j)
                        && (j - i <= 2 || palindrome[i + 1][j - 1])) {

                    palindrome[i][j] = true;
                }
            }
        }

        // dp[i] = maximum number of palindromes
        // using the first i characters
        int[] dp = new int[n + 1];

        for (int i = 1; i <= n; i++) {

            // Don't use a palindrome ending at i - 1
            dp[i] = dp[i - 1];

            // Try every palindrome ending at i - 1
            for (int j = 0; j < i; j++) {

                int length = i - j;

                if (length >= k && palindrome[j][i - 1]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
        }

        return dp[n];
    }
}
```

---

## Let's understand the palindrome DP

Suppose:

```text
s = "ababa"
```

We want to know whether:

```text
s[0...4] = "ababa"
```

is a palindrome.

First and last characters:

```text
a == a
```

So we only need to check:

```text
s[1...3] = "bab"
```

If `"bab"` is a palindrome, then `"ababa"` is also a palindrome.

Therefore:

```text
palindrome[i][j]
=
s[i] == s[j]
&&
palindrome[i+1][j-1]
```

For very small substrings, we don't need the inside check:

```text
"aa"
"aba"
```

Both can directly be recognized as palindromes when their outer characters match.

That's why we use:

```java
j - i <= 2
```

---

## Now the important part: `dp`

Consider:

```text
s = "abaccdbbd"
k = 3
```

Valid palindromes include:

```text
"aba"       length 3
"acca"      length 4
"bbd"?      no
"dbbd"      length 4
```

We want to select as many **non-overlapping** ones as possible.

We define:

```text
dp[i] = answer for s[0 ... i-1]
```

So:

```text
dp[0] = answer using ""
dp[1] = answer using "a"
dp[2] = answer using "ab"
...
dp[9] = answer using "abaccdbbd"
```

Suppose we find:

```text
s[j ... i-1]
```

is a valid palindrome.

If we select it, then we cannot select anything overlapping it.

Everything before it ends at `j - 1`, so the best answer before this palindrome is:

```text
dp[j]
```

Then we add this palindrome:

```text
dp[j] + 1
```

Hence:

```java
dp[i] = Math.max(dp[i], dp[j] + 1);
```

---

## Why `dp[i - 1]`?

We also need the possibility of **not selecting anything ending at `i - 1`**.

For example, maybe the best answer using the first `i - 1` characters is already optimal.

So:

```java
dp[i] = dp[i - 1];
```

Then we try every palindrome ending at `i - 1`.

---

## Why does this guarantee non-overlapping substrings?

This is the most important part.

Suppose we choose:

```text
s[j ... i-1]
```

Then we only take the solution represented by:

```text
dp[j]
```

`dp[j]` only uses characters:

```text
0 ... j-1
```

while our new palindrome uses:

```text
j ... i-1
```

Therefore they **cannot overlap**.

For example:

```text
        palindrome
       ↓↓↓↓↓
a b a c c d b b d
↑↑↑
 dp[j]
```

The two regions are completely separate.

---

# Walkthrough of Example 1

```text
s = "abaccdbbd"
k = 3
```

One optimal selection is:

```text
"aba"
```

and

```text
"dbbd"
```

Visualizing their positions:

```text
a b a c c d b b d
─────
 aba

          ───────
           dbbd
```

They don't overlap.

So:

```text
answer = 2
```

The DP eventually gets:

```text
dp[3] = 1
```

because `"aba"` is valid.

Later, when `"dbbd"` is found:

```text
dp[9] = max(dp[9], dp[5] + 1)
```

Since the first five characters can already give us `"aba"`:

```text
dp[5] = 1
```

we get:

```text
dp[9] = 1 + 1
      = 2
```

---

# Example 2

```text
s = "adbcda"
k = 2
```

There are no palindromic substrings with length ≥ 2.

The individual characters are palindromes, but:

```text
length = 1 < k
```

So none can be selected.

Therefore:

```text
dp[i] = dp[i - 1]
```

throughout the string, and:

```text
dp[n] = 0
```

---

# Complexity

There are two DP parts.

### 1. Palindrome table

We examine every pair `(i, j)`:

```text
O(n²)
```

### 2. Selection DP

For every `i`, we try every possible starting position `j`:

```text
O(n²)
```

Therefore total:

```text
Time:  O(n²)
Space: O(n²)
```

With:

```text
n <= 2000
```

this is completely reasonable.

---

## The main intuition to remember

Think of the problem as:

> **"Find palindromes, then treat each valid palindrome as an interval and select the maximum number of non-overlapping intervals."**

The palindrome DP answers:

```text
"Is s[j...i] a palindrome?"
```

The second DP answers:

```text
"What's the maximum number of non-overlapping palindromes I can get up to here?"
```

And the transition:

```java
dp[i] = Math.max(dp[i], dp[j] + 1);
```

works because if `[j, i-1]` is our chosen palindrome, `dp[j]` contains only characters **before** it.
