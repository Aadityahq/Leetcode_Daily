## LeetCode 3498 — Reverse Degree of a String

### 🧠 Problem in simple words

We are given a lowercase string `s`.

For every character:

1. Find its **reverse alphabet position**:

   * `a = 26`
   * `b = 25`
   * `c = 24`
   * ...
   * `z = 1`
2. Multiply it by the character's **position in the string**, starting from `1`.
3. Add all the products.

---

### Example: `"abc"`

| Character | Reverse Alphabet Value | String Position | Product |
| --------- | ---------------------: | --------------: | ------: |
| `a`       |                     26 |               1 |      26 |
| `b`       |                     25 |               2 |      50 |
| `c`       |                     24 |               3 |      72 |

**Answer = 26 + 50 + 72 = 148**

---

# 💡 How do we find the reverse alphabet value?

Normally, Java gives us the ASCII value of a character.

For example:

```text
'a' = 97
'b' = 98
'c' = 99
...
'z' = 122
```

We can calculate the normal alphabet position using:

```java
ch - 'a' + 1
```

For example:

```text
'a' → 97 - 97 + 1 = 1
'b' → 98 - 97 + 1 = 2
'c' → 99 - 97 + 1 = 3
```

But we need the **reverse** position.

So:

```text
reverse position = 26 - normal position + 1
```

Simplifying:

```text
reverse position = 26 - (ch - 'a')
```

For example:

```text
'a' → 26 - 0 = 26
'b' → 26 - 1 = 25
'c' → 26 - 2 = 24
'z' → 26 - 25 = 1
```

---

# ✅ Java Solution

```java
class Solution {
    public int reverseDegree(String s) {

        int sum = 0;

        for (int i = 0; i < s.length(); i++) {

            char ch = s.charAt(i);

            // Reverse alphabet position
            int reverseValue = 26 - (ch - 'a');

            // Position in string is i + 1
            int position = i + 1;

            sum += reverseValue * position;
        }

        return sum;
    }
}
```

---

# 🔍 Dry Run

Let's take:

```text
s = "zaza"
```

Initially:

```text
sum = 0
```

### Iteration 1

```text
i = 0
ch = 'z'
```

Reverse alphabet value:

```text
26 - ('z' - 'a')
= 26 - 25
= 1
```

Position:

```text
i + 1 = 1
```

Product:

```text
1 × 1 = 1
```

So:

```text
sum = 1
```

---

### Iteration 2

```text
i = 1
ch = 'a'
```

Reverse value:

```text
26 - ('a' - 'a')
= 26
```

Position:

```text
2
```

Product:

```text
26 × 2 = 52
```

Now:

```text
sum = 1 + 52 = 53
```

---

### Iteration 3

```text
i = 2
ch = 'z'
```

Reverse value:

```text
1
```

Position:

```text
3
```

Product:

```text
1 × 3 = 3
```

```text
sum = 53 + 3 = 56
```

---

### Iteration 4

```text
i = 3
ch = 'a'
```

Reverse value:

```text
26
```

Position:

```text
4
```

Product:

```text
26 × 4 = 104
```

Final:

```text
sum = 56 + 104
    = 160
```

Therefore:

```text
Answer = 160
```

---

# ⭐ Why this approach?

We don't need any complex data structure or algorithm.

The problem only asks us to:

* visit every character
* calculate its reverse alphabet value
* multiply by its position
* add to the answer

So a simple **single loop** is enough.

The important observation is:

```java
26 - (ch - 'a')
```

This directly converts a lowercase character into its reverse alphabet position.

---

## ⏱️ Complexity

If the string has `n` characters:

### Time

```text
O(n)
```

We visit every character exactly once.

### Space

```text
O(1)
```

We only use a few integer/character variables.

---

### 📝 Pattern to remember

Whenever a problem asks for **alphabet position**, remember:

```java
ch - 'a' + 1
```

For **reverse alphabet position**:

```java
26 - (ch - 'a')
```

This is a useful small trick for character/string problems.
