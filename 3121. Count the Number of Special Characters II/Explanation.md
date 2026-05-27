## Problem Understanding

A character is called **special** if:

1. It appears in both lowercase and uppercase.
2. **All lowercase occurrences** come **before** the **first uppercase occurrence**.

---

### Example

`word = "aaAbcBC"`

For `'a'`:

* lowercase `'a'` → indexes 0,1
* uppercase `'A'` → index 2
* all lowercase are before uppercase ✅

For `'b'`:

* `'b'` at 3
* `'B'` at 5
  ✅

For `'c'`:

* `'c'` at 4
* `'C'` at 6
  ✅

Answer = 3

---

# Main Idea

For every character:

* Find the **last lowercase position**
* Find the **first uppercase position**

A character is special if:

```text
lastLowercaseIndex < firstUppercaseIndex
```

and both lowercase + uppercase exist.

---

# Why This Works

Suppose:

```text
a a a A A
```

Last lowercase `'a'` = 2
First uppercase `'A'` = 3

Since:

```text
2 < 3
```

all lowercase letters are before uppercase letters.

---

But if:

```text
a A a
```

Last lowercase `'a'` = 2
First uppercase `'A'` = 1

```text
2 < 1 ❌
```

meaning a lowercase appears after uppercase, so not special.

---

# Efficient Approach (O(n))

We only have 26 letters.

We'll store:

* `lastSmall[26]`
* `firstCapital[26]`

---

# Dry Run

## Input

```text
"aaAbcBC"
```

### Lowercase positions

| Char | Last Lowercase |
| ---- | -------------- |
| a    | 1              |
| b    | 3              |
| c    | 4              |

---

### Uppercase positions

| Char | First Uppercase |
| ---- | --------------- |
| A    | 2               |
| B    | 5               |
| C    | 6               |

---

Now check:

### a

```text
1 < 2 ✅
```

### b

```text
3 < 5 ✅
```

### c

```text
4 < 6 ✅
```

Answer = 3

---

# Java Solution

```java
class Solution {
    public int numberOfSpecialChars(String word) {
        
        int[] lastLower = new int[26];
        int[] firstUpper = new int[26];

        // initialize
        for (int i = 0; i < 26; i++) {
            lastLower[i] = -1;
            firstUpper[i] = -1;
        }

        // traverse string
        for (int i = 0; i < word.length(); i++) {

            char ch = word.charAt(i);

            // lowercase
            if (Character.isLowerCase(ch)) {
                lastLower[ch - 'a'] = i;
            }

            // uppercase
            else {
                int idx = ch - 'A';

                // store first occurrence only
                if (firstUpper[idx] == -1) {
                    firstUpper[idx] = i;
                }
            }
        }

        int count = 0;

        // check all characters
        for (int i = 0; i < 26; i++) {

            // both exist
            if (lastLower[i] != -1 && firstUpper[i] != -1) {

                // all lowercase before uppercase
                if (lastLower[i] < firstUpper[i]) {
                    count++;
                }
            }
        }

        return count;
    }
}
```

---

# Time Complexity

```text
O(n)
```

We traverse the string once.

---

# Space Complexity

```text
O(1)
```

Only 26-sized arrays used.

---

# Intuition Summary

We only care about:

* the **last lowercase**
* the **first uppercase**

because:

```text
all lowercase before uppercase
```

means exactly:

```text
last lowercase index < first uppercase index
```

That single condition fully solves the problem efficiently.
