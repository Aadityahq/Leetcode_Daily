## LeetCode 3734 — Lexicographically Smallest Palindromic Permutation Greater Than Target

### 💡 Core Idea

The key observation is:

> A palindrome is completely determined by its **first half** and, when `n` is odd, its **middle character**.

For example:

```text
s = "aabbc"

frequency:
a -> 2
b -> 2
c -> 1

First half = "ab"
Middle     = "c"

Palindrome = "abcba"
```

So instead of generating permutations of all `n` characters, we only need to construct the correct **half**.

---

## 1. When can `s` form a palindrome?

A string can be rearranged into a palindrome if:

* For even `n`: every character has an even frequency.
* For odd `n`: at most one character has an odd frequency.

For example:

```text
"aabbc"

a = 2
b = 2
c = 1

Only c has odd frequency
→ palindrome is possible
```

If more than one character has an odd frequency, return `""`.

---

## 2. Reduce the problem to the first half

Suppose:

```text
s = "baba"
```

Frequencies:

```text
a = 2
b = 2
```

Half frequencies:

```text
a = 1
b = 1
```

Possible first halves:

```text
ab
ba
```

Corresponding palindromes:

```text
abba
baab
```

Therefore, we are really looking for:

> The lexicographically smallest valid first half whose resulting palindrome is greater than `target`.

---

# 3. How do we make the palindrome greater than `target`?

Suppose:

```text
target = "abba"
```

Its first half is:

```text
ab
```

The palindrome created by the exact half is:

```text
abba
```

But we need **strictly greater**, so `abba` isn't valid.

We need to increase the first half.

The next possible half is:

```text
ba
```

giving:

```text
baab
```

and:

```text
baab > abba
```

So the answer is:

```text
baab
```

---

# 4. The important greedy observation

Suppose the target's first half is:

```text
targetHalf = "abc"
```

We want the smallest half greater than it.

There are two possibilities:

### Option A — Keep everything equal

Try:

```text
abc
```

and construct its palindrome.

If that palindrome is already greater than `target`, it is the answer.

---

### Option B — Increase one position

If the exact half doesn't work, we need to increase some position.

For example:

```text
targetHalf = abc
```

Possible increases:

```text
abd
abe
...
```

or:

```text
acd
...
```

or:

```text
b...
```

To obtain the **smallest** result:

1. Keep the prefix equal to `target` for as long as possible.
2. Therefore, try changing a position as far to the **right** as possible.
3. At that position, choose the **smallest available character greater than target[i]**.
4. Fill everything after it in ascending order.

For example:

```text
target half = "abc"

Try increasing index 2:
ab + d
→ "abd"

If possible, this is smaller than:
ac...
```

That's why we iterate from right to left.

---

# 5. Why can we compare using only the first half?

Suppose two palindromes are:

```text
P1 = abc|cba
P2 = abd|dba
```

The first difference is:

```text
c < d
```

So:

```text
P1 < P2
```

The right half doesn't matter.

Therefore:

> Lexicographical ordering of palindromes is determined by their first halves, unless the first halves are exactly equal.

The only special case is when the entire first half is equal to the target's first half. Then we construct the palindrome and directly compare it with `target`.

---

# Java Solution

```java
class Solution {

    public String lexPalindromicPermutation(String s, String target) {

        int n = s.length();
        int half = n / 2;

        // Frequency of every character in s
        int[] freq = new int[26];

        for (char ch : s.toCharArray()) {
            freq[ch - 'a']++;
        }

        // Check whether a palindrome is possible
        int oddCount = 0;
        char middle = 0;

        for (int i = 0; i < 26; i++) {
            if (freq[i] % 2 == 1) {
                oddCount++;
                middle = (char) ('a' + i);
            }
        }

        if (oddCount > 1) {
            return "";
        }

        /*
         * halfFreq contains the number of each character
         * that must appear in the first half.
         */
        int[] halfFreq = new int[26];

        for (int i = 0; i < 26; i++) {
            halfFreq[i] = freq[i] / 2;
        }

        /*
         * ----------------------------------------------------
         * Step 1:
         * Try making the first half exactly equal to
         * target[0 ... half-1].
         * ----------------------------------------------------
         */
        int[] remaining = halfFreq.clone();
        boolean possible = true;

        for (int i = 0; i < half; i++) {
            int idx = target.charAt(i) - 'a';

            if (remaining[idx] == 0) {
                possible = false;
                break;
            }

            remaining[idx]--;
        }

        if (possible) {

            StringBuilder firstHalf = new StringBuilder();

            for (int i = 0; i < half; i++) {
                firstHalf.append(target.charAt(i));
            }

            String candidate = buildPalindrome(
                    firstHalf.toString(),
                    middle,
                    n
            );

            if (candidate.compareTo(target) > 0) {
                return candidate;
            }
        }

        /*
         * ----------------------------------------------------
         * Step 2:
         * The exact first half doesn't work.
         *
         * Find the rightmost position where we can increase
         * target[i].
         * ----------------------------------------------------
         */
        for (int i = half - 1; i >= 0; i--) {

            remaining = halfFreq.clone();

            /*
             * Keep target[0 ... i-1] exactly the same.
             */
            boolean prefixPossible = true;

            for (int j = 0; j < i; j++) {

                int idx = target.charAt(j) - 'a';

                if (remaining[idx] == 0) {
                    prefixPossible = false;
                    break;
                }

                remaining[idx]--;
            }

            if (!prefixPossible) {
                continue;
            }

            /*
             * Find the smallest character that is strictly
             * greater than target[i].
             */
            int current = target.charAt(i) - 'a';

            int bigger = -1;

            for (int c = current + 1; c < 26; c++) {
                if (remaining[c] > 0) {
                    bigger = c;
                    break;
                }
            }

            if (bigger == -1) {
                continue;
            }

            /*
             * Construct the first half:
             *
             * target[0 ... i-1]
             * + bigger character
             * + remaining characters in ascending order
             */
            StringBuilder firstHalf = new StringBuilder();

            for (int j = 0; j < i; j++) {
                firstHalf.append(target.charAt(j));
            }

            firstHalf.append((char) ('a' + bigger));
            remaining[bigger]--;

            // Fill the rest with the smallest possible characters
            for (int c = 0; c < 26; c++) {
                while (remaining[c] > 0) {
                    firstHalf.append((char) ('a' + c));
                    remaining[c]--;
                }
            }

            return buildPalindrome(
                    firstHalf.toString(),
                    middle,
                    n
            );
        }

        return "";
    }

    private String buildPalindrome(
            String firstHalf,
            char middle,
            int n) {

        StringBuilder result = new StringBuilder();

        // First half
        result.append(firstHalf);

        // Middle character for odd length
        if (n % 2 == 1) {
            result.append(middle);
        }

        // Reverse of first half
        for (int i = firstHalf.length() - 1; i >= 0; i--) {
            result.append(firstHalf.charAt(i));
        }

        return result.toString();
    }
}
```

---

# 🔍 Dry Run — Example 1

```text
s = "baba"
target = "abba"
```

### Frequencies

```text
a = 2
b = 2
```

So:

```text
halfFreq:
a = 1
b = 1
```

`half = 2`.

---

### Step 1: Try target's first half

```text
targetHalf = "ab"
```

This is possible.

Construct:

```text
firstHalf = "ab"
palindrome = "abba"
```

But:

```text
"abba" > "abba"
```

is false.

So we need a larger palindrome.

---

### Step 2: Try increasing from right to left

Start with:

```text
i = 1
target[i] = 'b'
```

Can we choose something greater than `'b'`?

```text
c, d, e, ...
```

But we only have:

```text
a, b
```

So impossible.

Now:

```text
i = 0
target[i] = 'a'
```

Prefix before `i` is empty.

Available characters:

```text
a, b
```

Smallest character greater than `'a'`:

```text
b
```

Construct:

```text
firstHalf = "ba"
```

Palindrome:

```text
baab
```

Therefore:

```text
answer = "baab"
```

---

# 🔍 Dry Run — Example 2

```text
s = "baba"
target = "bbaa"
```

Possible palindromes:

```text
abba
baab
```

Exact first half:

```text
targetHalf = "bb"
```

But we only have one `b` in the first half, so impossible.

Try increasing positions.

At `i = 1`:

```text
target[1] = b
```

No character greater than `b` is available.

At `i = 0`:

```text
target[0] = b
```

Again, no character greater than `b`.

Therefore:

```text
""
```

---

# 🔍 Dry Run — Example 4

```text
s = "aac"
target = "abb"
```

Frequency:

```text
a = 2
c = 1
```

Therefore:

```text
first half = "a"
middle = "c"
```

Only possible palindrome:

```text
aca
```

Compare:

```text
aca
abb
```

At index `0`:

```text
a == a
```

At index `1`:

```text
c > b
```

Therefore:

```text
aca > abb
```

Answer:

```text
aca
```

---

# Why the greedy approach is correct

There are three important reasons.

### 1. We try the exact target prefix first

If the first half exactly matches `target` and its palindrome is greater than `target`, then no other valid palindrome can be smaller.

Any palindrome with a larger character somewhere in the first half would already be lexicographically larger.

---

### 2. We increase the rightmost possible position

Suppose we have:

```text
targetHalf = abcde
```

Compare:

```text
abcz...
abcd...
```

The one changing later is smaller because it keeps more of the target prefix unchanged.

Therefore, we try:

```text
index 4
index 3
index 2
index 1
index 0
```

This guarantees the longest possible common prefix.

---

### 3. We choose the smallest possible character

At the position we decide to increase, suppose the target has:

```text
c
```

and available characters are:

```text
d, f, g
```

We must choose:

```text
d
```

not `f` or `g`.

Then all remaining characters are placed in ascending order.

That gives the smallest possible suffix.

Therefore, the resulting palindrome is the smallest valid palindrome for that position.

---

# Complexity

Let:

```text
n <= 300
```

and the alphabet size is only `26`.

We try at most `n / 2` positions.

For each position, we may scan the prefix of the target:

```text
O(n)
```

and the alphabet:

```text
O(26)
```

So the total complexity is:

### Time

```text
O(n² + 26n)
≈ O(n²)
```

With `n <= 300`, this is easily fast enough.

### Space

We use frequency arrays and strings:

```text
O(n)
```

---

## 🧠 The pattern to remember

This problem is essentially:

```text
Palindrome
    ↓
Only first half matters
    ↓
Check whether exact target prefix works
    ↓
If not:
    find rightmost position to increase
    ↓
choose smallest available larger character
    ↓
sort/fill remaining characters ascending
    ↓
mirror the first half
```

The most important insight is **not to generate palindromic permutations**. There can be an enormous number of permutations, but because we only need the **next lexicographically greater one**, we can construct it directly using the same idea as finding the **next permutation**, while respecting the character frequencies.
