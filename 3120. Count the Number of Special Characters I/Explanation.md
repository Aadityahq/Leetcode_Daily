## Intuition

A character is called **special** if it appears in **both lowercase and uppercase** form in the string.

For example:

* `'a'` and `'A'` both exist → special
* `'b'` exists but `'B'` does not → not special

We need to count how many such letters exist.

---

## Simple Idea

We can store all characters in a set.

Then for every lowercase letter from `'a'` to `'z'`:

* check if lowercase exists
* check if uppercase exists

If both are present, increase the count.

---

## Example

### Input

```text
word = "aaAbcBC"
```

Set contains:

```text
[a, A, b, c, B, C]
```

Now check:

* `a` and `A` → yes
* `b` and `B` → yes
* `c` and `C` → yes

Answer = `3`

---

# Java Solution

```java
import java.util.*;

class Solution {
    public int numberOfSpecialChars(String word) {
        Set<Character> set = new HashSet<>();

        // Store all characters
        for (char ch : word.toCharArray()) {
            set.add(ch);
        }

        int count = 0;

        // Check all letters a-z
        for (char ch = 'a'; ch <= 'z'; ch++) {

            // lowercase and uppercase both present
            if (set.contains(ch) && set.contains(Character.toUpperCase(ch))) {
                count++;
            }
        }

        return count;
    }
}
```

---

# Step-by-Step Dry Run

Input:

```text
word = "abBCab"
```

### Step 1: Store characters in set

Set:

```text
[a, b, B, C]
```

### Step 2: Check letters

* `a` and `A` → `A` missing ❌
* `b` and `B` → both present ✅
* `c` and `C` → `c` missing ❌

Count = `1`

Output:

```text
1
```

---

# Why This Works

For a letter to be special:

```text
lowercase exists AND uppercase exists
```

Using a `HashSet` makes checking very fast:

```text
contains() → O(1)
```

So we efficiently test all 26 letters.

---

# Time Complexity

We scan the string once:

```text
O(n)
```

Then check 26 letters:

```text
O(26) ≈ O(1)
```

Total:

```text
O(n)
```

---

# Space Complexity

We store characters in a set:

```text
O(52)
```

(at most 26 lowercase + 26 uppercase letters)

So effectively:

```text
O(1)
```
