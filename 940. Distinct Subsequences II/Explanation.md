## LeetCode 940 — Distinct Subsequences II

### Problem Understanding

We are given a string `s` and need to count the number of **distinct non-empty subsequences**.

The important word is **distinct**.

For example:

```text
s = "aba"
```

All possible subsequences, including duplicates, are:

```text
""
"a"
"b"
"a"
"ab"
"aa"
"ba"
"aba"
```

Here `"a"` occurs twice, but we count it only once.

So the distinct non-empty subsequences are:

```text
"a", "b", "ab", "aa", "ba", "aba"
```

Answer:

```text
6
```

---

# 1. The Key Idea

Let's think about the problem using **dynamic programming**.

Suppose:

```text
s = "abc"
```

After processing `"a"`:

```text
""
"a"
```

There are `2` subsequences including the empty subsequence.

Now add `'b'`.

Every existing subsequence can either:

* not use `'b'`
* use `'b'`

So:

```text
""
"a"
```

becomes:

```text
""
"a"
"b"
"ab"
```

There are `4`.

Now add `'c'`:

```text
""
"a"
"b"
"ab"
```

Each can produce a new subsequence by adding `'c'`:

```text
"c"
"ac"
"bc"
"abc"
```

Total:

```text
8
```

Removing the empty subsequence:

```text
8 - 1 = 7
```

This works perfectly when all characters are different.

But there is a problem with repeated characters.

---

# 2. What Goes Wrong With Duplicate Characters?

Consider:

```text
s = "aba"
```

After processing `"ab"` we have:

```text
""
"a"
"b"
"ab"
```

Now we process another `'a'`.

Naively, we append `'a'` to every existing subsequence:

```text
"a"
"aa"
"ba"
"aba"
```

But `"a"` already existed.

So simply doubling the number of subsequences would count `"a"` twice.

We need a way to remove the duplicates.

---

# 3. DP Definition

Let:

```text
dp[i] = number of distinct subsequences INCLUDING the empty subsequence
         after processing the first i characters
```

Initially:

```text
dp[0] = 1
```

Why?

There is exactly one subsequence of an empty string:

```text
""
```

---

## When we add a new character

Suppose the current character is `c`.

Every existing subsequence can create a new subsequence by appending `c`.

Therefore, we initially get:

```text
dp[i] = 2 * dp[i - 1]
```

But if `c` has appeared before, some of these newly created subsequences already existed.

So we need to subtract the duplicates.

---

# 4. How Many Duplicates?

Suppose the current character `c` previously appeared at position `j`.

At that earlier occurrence, we created all subsequences by appending `c`.

Therefore, exactly:

```text
dp[j - 1]
```

subsequences will be duplicated.

So:

```text
dp[i] = 2 * dp[i - 1] - dp[j - 1]
```

where `j` is the previous occurrence of the current character.

This is the central formula.

---

# 5. Example: `"aba"`

Let's calculate it step by step.

### Initially

```text
dp[0] = 1
```

Only:

```text
""
```

---

### Process `'a'`

`'a'` has never appeared.

```text
dp[1] = 2 * dp[0]
      = 2
```

Subsequences:

```text
""
"a"
```

---

### Process `'b'`

`'b'` has never appeared.

```text
dp[2] = 2 * dp[1]
      = 4
```

Subsequences:

```text
""
"a"
"b"
"ab"
```

---

### Process second `'a'`

`'a'` appeared before.

Its previous occurrence was at position `1`.

The subsequences before that first `'a'` were:

```text
""
```

So:

```text
dp[0] = 1
```

These produce duplicate subsequences when we add the second `'a'`.

Therefore:

```text
dp[3] = 2 * dp[2] - dp[0]
      = 2 * 4 - 1
      = 7
```

The subsequences are:

```text
""
"a"
"b"
"ab"
"aa"
"ba"
"aba"
```

Remove the empty subsequence:

```text
7 - 1 = 6
```

Answer = **6**.

---

# 6. Why `last[c]` Is Enough

We don't need to remember every previous occurrence.

We only need the **most recent occurrence** of each character.

Because when we process the current `c`, the duplicates are exactly the subsequences that were generated when the previous `c` was processed.

Since `s` contains only lowercase English letters, we can use:

```java
int[] last = new int[26];
```

We can store:

```text
last[c] = dp value before the previous occurrence of c
```

A convenient implementation uses `last[c]` to directly store the DP value that needs to be subtracted.

---

# 7. Java Solution

```java
class Solution {
    public int distinctSubseqII(String s) {
        final long MOD = 1_000_000_007;

        long dp = 1;

        long[] last = new long[26];

        for (char ch : s.toCharArray()) {
            int index = ch - 'a';

            long newDp = (2 * dp - last[index] + MOD) % MOD;

            last[index] = dp;
            dp = newDp;
        }

        return (int) ((dp - 1 + MOD) % MOD);
    }
}
```

---

# 8. Understanding the Code

### Step 1: MOD

```java
final long MOD = 1_000_000_007;
```

The number of subsequences can become extremely large.

The problem asks for the answer modulo:

```text
10^9 + 7
```

We use `long` because intermediate multiplication can exceed the range of `int`.

---

### Step 2: Initial DP

```java
long dp = 1;
```

Initially, we have only:

```text
""
```

So:

```text
dp = 1
```

Remember: **our DP includes the empty subsequence**.

---

### Step 3: Store previous information

```java
long[] last = new long[26];
```

For each character:

```text
'a' -> last[0]
'b' -> last[1]
...
'z' -> last[25]
```

`last[index]` tells us how many subsequences should be removed because they were duplicated by the previous occurrence of that character.

---

### Step 4: Process every character

```java
for (char ch : s.toCharArray()) {
```

For each character:

```java
int index = ch - 'a';
```

For example:

```text
ch = 'c'

'c' - 'a' = 2
```

So we access:

```java
last[2]
```

---

### Step 5: Calculate the new number

```java
long newDp = (2 * dp - last[index] + MOD) % MOD;
```

The formula is:

```text
newDp = 2 × dp - duplicates
```

where:

```text
duplicates = last[index]
```

Why multiply by `2`?

Every existing subsequence has two choices:

```text
Don't take current character
Take current character
```

So:

```text
2 × dp
```

But repeated characters create duplicate subsequences.

Therefore:

```text
2 × dp - duplicates
```

---

### Step 6: Save the current state

```java
last[index] = dp;
dp = newDp;
```

This is important.

Before processing the current character, `dp` represents the number of subsequences from the previous prefix.

So we save that value for this character:

```java
last[index] = dp;
```

Then move to the newly calculated value:

```java
dp = newDp;
```

---

### Step 7: Remove the empty subsequence

Our DP includes:

```text
""
```

But the problem asks for **non-empty** subsequences.

Therefore:

```java
return (int) ((dp - 1 + MOD) % MOD);
```

We subtract `1`.

The `+ MOD` prevents a negative value before taking modulo.

---

# 9. Detailed Dry Run

Let's use:

```text
s = "aaa"
```

Expected answer:

```text
3
```

The distinct subsequences are:

```text
"a"
"aa"
"aaa"
```

### Initial

```text
dp = 1
last = [0, 0, ..., 0]
```

---

### First `'a'`

```text
newDp = 2 * 1 - 0
      = 2
```

Save:

```text
last['a'] = 1
dp = 2
```

Subsequences:

```text
""
"a"
```

---

### Second `'a'`

```text
newDp = 2 * 2 - 1
      = 3
```

Save:

```text
last['a'] = 2
dp = 3
```

Subsequences:

```text
""
"a"
"aa"
```

---

### Third `'a'`

```text
newDp = 2 * 3 - 2
      = 4
```

So:

```text
dp = 4
```

Subsequences:

```text
""
"a"
"aa"
"aaa"
```

Finally:

```text
dp - 1 = 4 - 1 = 3
```

Correct.

---

# 10. Another Example: `"abc"`

Since there are no repeated characters, nothing needs to be subtracted.

```text
dp = 1
```

### `'a'`

```text
dp = 2
```

### `'b'`

```text
dp = 4
```

### `'c'`

```text
dp = 8
```

Remove empty:

```text
8 - 1 = 7
```

The 7 subsequences are:

```text
a
b
c
ab
ac
bc
abc
```

---

# 11. Why This Algorithm Works

The important observation is:

> When adding a character, every existing distinct subsequence can either exclude or include that character.

Therefore, without duplicates:

```text
new = 2 × old
```

However, if the character appeared previously, some newly generated subsequences are not actually new.

For example, in:

```text
"aba"
```

the second `'a'` can generate:

```text
a
aa
ba
aba
```

But:

```text
"a"
```

already existed.

The duplicate subsequences correspond exactly to the subsequences that existed **before the previous occurrence of `'a'`**.

Therefore:

```text
newDp = 2 × dp - previousDp
```

This ensures every distinct subsequence is counted exactly once.

---

# 12. Complexity

Let:

```text
n = s.length()
```

We process every character exactly once.

### Time

```text
O(n)
```

### Space

We only store information for 26 lowercase characters:

```text
O(26) = O(1)
```

So:

```text
Time:  O(n)
Space: O(1)
```

This is much better than generating all subsequences, which would take exponential time.

---

## Final Takeaway

The pattern to remember for this problem is:

```text
Start with dp = 1

For every character c:

    newDp = 2 * dp - last[c]

    last[c] = dp
    dp = newDp

Answer = dp - 1
```

The **most important concept** is why we subtract `last[c]`:

> `2 × dp` counts all subsequences obtained by taking/not taking the current character, but if the character has appeared before, `last[c]` represents the subsequences that would be generated again, so we subtract them to keep only distinct subsequences.

**Pattern:** `DP + Last Occurrence / Duplicate Removal` — this is the key technique to recognize in similar subsequence-counting problems.
