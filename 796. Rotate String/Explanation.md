## Problem Understanding

You are given two strings:

* `s`
* `goal`

You need to check whether `s` can become `goal` after performing some number of **left rotations**.

A left rotation means:

* remove the first character
* append it at the end

Example:

```text
"abcde" -> "bcdea"
```

Another rotation:

```text
"bcdea" -> "cdeab"
```

So `"abcde"` can become `"cdeab"` after 2 shifts.

---

# Key Observation

If a string can be rotated to form another string, then:

1. Both strings must have the **same length**
2. `goal` must exist inside `s + s`

Why?

Suppose:

```text
s = "abcde"
```

All rotations are:

```text
abcde
bcdea
cdeab
deabc
eabcd
```

Now combine the string with itself:

```text
s + s = "abcdeabcde"
```

Notice all rotations appear inside it:

```text
abcdeabcde
 ^^^^^
  ^^^^^
   ^^^^^
    ^^^^^
     ^^^^^
```

So if `goal` is a rotation of `s`, then it must be a substring of `s + s`.

---

# Efficient Idea

Instead of generating all rotations manually:

* check lengths
* create `s + s`
* check if it contains `goal`

---

# Java Solution

```java
class Solution {
    public boolean rotateString(String s, String goal) {
        
        // Lengths must be same
        if (s.length() != goal.length()) {
            return false;
        }

        // Combine string with itself
        String doubled = s + s;

        // Check if goal exists inside
        return doubled.contains(goal);
    }
}
```

---

# Dry Run

## Example 1

```text
s = "abcde"
goal = "cdeab"
```

### Step 1: Length check

```text
5 == 5
```

continue

### Step 2: Double the string

```text
"abcdeabcde"
```

### Step 3: Check substring

```text
"cdeab" exists?
```

Yes.

Return:

```text
true
```

---

## Example 2

```text
s = "abcde"
goal = "abced"
```

### Double string

```text
"abcdeabcde"
```

Check:

```text
"abced"
```

Not present.

Return:

```text
false
```

---

# Why This Works

Every possible rotation of `s` appears inside `s + s`.

Example:

```text
s = "water"

s+s = "waterwater"
```

Rotations:

```text
water
aterw
terwa
erwat
rwater
```

All are substrings of `"waterwater"`.

So checking substring is enough.

---

# Time Complexity

## `contains()` operation

In Java, substring search is approximately:

```text
O(n)
```

Overall:

```text
Time: O(n)
Space: O(n)
```

where `n` is the string length.

---

# Alternative Brute Force Approach

You could rotate the string one by one and compare:

```text
abcde
bcdea
cdeab
...
```

But that is slower and unnecessary.

The `s + s` trick is the optimal and elegant solution.
