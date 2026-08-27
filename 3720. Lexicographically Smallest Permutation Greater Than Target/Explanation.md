# 3720. Lexicographically Smallest Permutation Greater Than Target

## Problem in simple words

You have:

* `s`: a string whose characters you can rearrange in any order.
* `target`: another string of the same length.

You need to create a **permutation of `s`** that is:

1. **Strictly lexicographically greater** than `target`.
2. Among all such permutations, **the lexicographically smallest**.

If no permutation satisfies this, return `""`.

---

## Understanding lexicographical order

Compare characters from left to right.

For example:

```text
"bca" > "bba"
```

Why?

```text
b = b  → same
c > b  → first different position
```

So `"bca"` is greater.

The characters after the first greater position should be as small as possible.

---

# Key Idea

To make our answer the **smallest possible string greater than `target`**, we should:

1. Try to match `target` character-by-character for as long as possible.
2. At some position `i`, choose the **smallest available character greater than `target[i]`**.
3. After that, put all remaining characters in **sorted order**.

But there is an important situation:

> We may not be able to make the current position greater, so we need to go back to an earlier position and increase that position instead.

Therefore, we first greedily match the target as much as possible, then search **from right to left** for the right position to increase.

---

# Example 1

```text
s      = "abc"
target = "bba"
```

Available characters:

```text
a, b, c
```

### Position 0

Target needs:

```text
'b'
```

We have `'b'`, so match it:

```text
answer = "b"
remaining = {a, c}
```

### Position 1

Target needs:

```text
'b'
```

We don't have another `'b'`.

Now, can we choose the smallest character greater than `'b'`?

```text
remaining: a, c
                ↑
```

`'c' > 'b'`, so choose `'c'`.

Then put the remaining character in sorted order:

```text
answer = "bc" + "a"
       = "bca"
```

Answer:

```text
"bca"
```

---

# Why do we search from right to left?

Suppose we matched some prefix:

```text
target:  a b c d ...
answer:  a b c ...
```

To get the smallest answer greater than `target`, we want to keep the equal prefix **as long as possible**.

Changing a later character is always better than changing an earlier character.

For example:

```text
abz...
```

is lexicographically smaller than:

```text
ac...
```

because at the second position:

```text
b < c
```

Therefore, we try to make the answer greater at the **rightmost possible position**.

---

# Algorithm

### Step 1: Count all characters of `s`

Since we only have lowercase English letters, use:

```java
int[] freq = new int[26];
```

---

### Step 2: Match `target` from left to right

At every position:

* If `target[i]` is available, use it.
* Otherwise, stop because we can no longer remain equal to `target`.

Store how far we successfully matched.

---

### Step 3: Try to make the string greater

Starting from the rightmost matched position and moving left:

* Restore the character used at that position.
* Find the smallest available character strictly greater than `target[i]`.
* If found:

  * Use that character.
  * Append all remaining characters in sorted order.
  * Return the result.

If no position can be increased, return `""`.

---

# Java Solution

```java
class Solution {
    public String lexicographicallySmallest(String s, String target) {
        int n = s.length();

        // Count frequency of characters in s
        int[] freq = new int[26];

        for (char ch : s.toCharArray()) {
            freq[ch - 'a']++;
        }

        // prefix[i] stores the characters used to match target
        StringBuilder prefix = new StringBuilder();

        int matched = 0;

        // Try to match target character by character
        while (matched < n) {
            char ch = target.charAt(matched);

            if (freq[ch - 'a'] > 0) {
                prefix.append(ch);
                freq[ch - 'a']--;
                matched++;
            } else {
                break;
            }
        }

        /*
         * Try making the answer greater at position i.
         * We start from the rightmost possible position.
         */
        for (int i = matched; i >= 0; i--) {

            // If i is part of the matched prefix,
            // restore target[i] because we are going to replace it.
            if (i < matched) {
                char ch = prefix.charAt(prefix.length() - 1);
                prefix.deleteCharAt(prefix.length() - 1);
                freq[ch - 'a']++;
            }

            // Find the smallest available character > target[i]
            if (i < n) {
                for (int c = target.charAt(i) - 'a' + 1; c < 26; c++) {
                    if (freq[c] > 0) {

                        StringBuilder answer = new StringBuilder(prefix);

                        // Place the smallest character greater than target[i]
                        answer.append((char) ('a' + c));
                        freq[c]--;

                        // Append remaining characters in sorted order
                        for (int j = 0; j < 26; j++) {
                            while (freq[j] > 0) {
                                answer.append((char) ('a' + j));
                                freq[j]--;
                            }
                        }

                        return answer.toString();
                    }
                }
            }
        }

        return "";
    }
}
```

---

# Dry Run — Example 1

```text
s = "abc"
target = "bba"
```

Initial frequency:

```text
a = 1
b = 1
c = 1
```

## Matching the target

### `i = 0`

```text
target[0] = 'b'
```

`'b'` is available.

```text
prefix = "b"
remaining = a, c
matched = 1
```

### `i = 1`

```text
target[1] = 'b'
```

No `'b'` is available.

Stop matching.

---

## Try making the answer greater

Start at:

```text
i = 1
```

Available:

```text
a, c
```

Need the smallest character greater than:

```text
target[1] = 'b'
```

Checking:

```text
c > b ✅
```

Choose `'c'`:

```text
prefix + c = "bc"
```

Remaining:

```text
a
```

Append remaining characters in sorted order:

```text
"bca"
```

Return:

```text
"bca"
```

---

# Example 2

```text
s = "leet"
target = "code"
```

Initially, the smallest character in `s` is `'e'`.

At position `0`:

```text
target[0] = 'c'
```

The smallest available character greater than `'c'` is:

```text
'e'
```

After choosing `'e'`, the remaining characters should be as small as possible:

```text
eelt
```

Answer:

```text
"eelt"
```

---

# Example 3

```text
s = "baba"
target = "bbaa"
```

We can match:

```text
"bbaa"
```

exactly.

Now we need a permutation **strictly greater** than `"bbaa"`.

We try from right to left, but there is no available way to increase any position while keeping the earlier prefix equal.

Therefore:

```text
""
```

---

# Why is this solution correct?

The answer must differ from `target` at some first position `i`.

Before position `i`, the answer must be exactly equal to `target`.

At position `i`, we need:

```text
answer[i] > target[i]
```

To make the entire answer as small as possible:

1. We want `i` as far to the **right** as possible.
2. At position `i`, choose the **smallest available character greater than `target[i]`**.
3. After position `i`, arrange every remaining character in **ascending order**.

That is exactly what the algorithm does.

---

# Complexity

There are only 26 lowercase letters.

* Building frequency array: `O(n)`
* Matching prefix: `O(n)`
* Trying positions: `O(26 × n)` in the worst case
* Building the answer: `O(n)`

### Time Complexity

```text
O(26 × n) = O(n)
```

because `26` is constant.

### Space Complexity

```text
O(n + 26) = O(n)
```

The `StringBuilder` storing the answer uses `O(n)` space.

---

## Important intuition to remember

> **Keep the prefix equal to `target` for as long as possible. Then increase one character by the smallest possible amount, and sort everything remaining.**

This is the central idea behind solving this problem efficiently.
