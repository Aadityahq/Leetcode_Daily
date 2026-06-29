# 1967. Number of Strings That Appear as Substrings in Word (Easy)

## Problem Explanation

You are given:

* An array of strings `patterns`
* A string `word`

Your task is to count **how many strings in `patterns` appear as a substring of `word`**.

### What is a substring?

A substring is a **continuous part** of a string.

Example:

```
word = "abcdef"

Substrings:
"a"
"ab"
"abc"
"bcd"
"def"
"abcdef"

Not substrings:
"ac"
"ad"
"aef"
```

---

### Example 1

```text
patterns = ["a","abc","bc","d"]
word = "abc"
```

Check each pattern:

| Pattern | Exists in "abc"? |
| ------- | ---------------- |
| "a"     | ✅ Yes            |
| "abc"   | ✅ Yes            |
| "bc"    | ✅ Yes            |
| "d"     | ❌ No             |

Answer = **3**

---

### Example 2

```text
patterns = ["a","b","c"]
word = "aaaaabbbbb"
```

```
"a" → Yes
"b" → Yes
"c" → No
```

Answer = **2**

---

### Example 3

```text
patterns = ["a","a","a"]
word = "ab"
```

Each `"a"` is counted separately.

```
1st "a" → Yes
2nd "a" → Yes
3rd "a" → Yes
```

Answer = **3**

---

# Intuition

The problem is actually very straightforward.

For every string in `patterns`:

* Check whether it exists inside `word`.
* If yes, increase the answer.

Java already provides a built-in method for this:

```java
word.contains(pattern)
```

It returns:

* `true` if `pattern` is a substring of `word`
* `false` otherwise

So we simply check every pattern one by one.

---

# Approach

1. Initialize `count = 0`.
2. Traverse every string in `patterns`.
3. If `word.contains(pattern)` is true:

   * Increment `count`.
4. Return `count`.

---

# Dry Run

Input

```text
patterns = ["a","abc","bc","d"]
word = "abc"
```

Initially

```
count = 0
```

### Iteration 1

```
pattern = "a"

word.contains("a")
→ true

count = 1
```

---

### Iteration 2

```
pattern = "abc"

word.contains("abc")
→ true

count = 2
```

---

### Iteration 3

```
pattern = "bc"

word.contains("bc")
→ true

count = 3
```

---

### Iteration 4

```
pattern = "d"

word.contains("d")
→ false

count = 3
```

Return

```
3
```

---

# Java Solution

```java
class Solution {
    public int numOfStrings(String[] patterns, String word) {
        int count = 0;

        for (String pattern : patterns) {
            if (word.contains(pattern)) {
                count++;
            }
        }

        return count;
    }
}
```

---

# Why does this work?

For every pattern:

```
Is it inside word?
```

If yes,

```
count++
```

Otherwise,

```
ignore it
```

Since every pattern is checked exactly once, every valid substring is counted correctly.

Even duplicate patterns are counted separately because the loop processes each element independently.

Example:

```
patterns = ["a","a","a"]

Loop:

1st "a" → count = 1
2nd "a" → count = 2
3rd "a" → count = 3
```

---

# Time Complexity

Let

* **n = patterns.length**
* **m = average length of a pattern**
* **L = length of word**

`contains()` internally searches for the pattern in `word`.

Worst-case:

```
O(L × m)
```

We do this for every pattern.

Overall:

```
O(n × L × m)
```

Given the constraints:

```
n ≤ 100
L ≤ 100
m ≤ 100
```

Maximum operations are very small, so this solution is efficient enough.

---

# Space Complexity

```
O(1)
```

Only a counter variable is used; no extra data structures are required.

---

# Key Takeaways

* A substring is a **continuous** sequence of characters.
* Use Java's built-in `word.contains(pattern)` to check if a pattern exists.
* Check every pattern independently.
* Duplicate patterns are counted separately.
* **Time Complexity:** `O(n × L × m)`
* **Space Complexity:** `O(1)`
