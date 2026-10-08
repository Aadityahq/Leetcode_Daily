## LeetCode 1021 — Remove Outermost Parentheses

### 1. Problem in simple words

You are given a **valid parentheses string**.

The string can contain multiple **primitive parentheses groups**.

For example:

```text
(()())(()) 
```

can be divided into:

```text
(()()) + (())
```

We need to **remove the first `(` and last `)` from every primitive group**.

So:

```text
(()()) → ()()
(())   → ()
```

Final answer:

```text
()()()
```

---

## 2. Key idea

We can solve this using a **balance counter**.

### What does `balance` mean?

- When we see `(` → increase `balance`
- When we see `)` → decrease `balance`

The important observation is:

> The outermost `(` of a primitive starts when `balance == 0`.

And:

> The outermost `)` of a primitive is encountered when `balance == 1` before decreasing it.

So we simply **skip those two characters**.

---

## 3. Java Solution

```java
class Solution {
    public String removeOuterParentheses(String s) {
        
        StringBuilder result = new StringBuilder();
        int balance = 0;

        for (char ch : s.toCharArray()) {

            if (ch == '(') {
                // If balance is greater than 0,
                // this '(' is not the outermost '('
                if (balance > 0) {
                    result.append(ch);
                }

                balance++;
            } 
            else {
                balance--;

                // If balance is greater than 0,
                // this ')' is not the outermost ')'
                if (balance > 0) {
                    result.append(ch);
                }
            }
        }

        return result.toString();
    }
}
```

---

# 4. How does it work?

Let's take:

```text
s = "(()())(())"
```

We'll maintain:

```text
balance
```

### First primitive: `(()())`

| Character | Before | Action | After | Add to result? |
|---|---:|---|---:|---|
| `(` | 0 | outer `(` → skip | 1 | ❌ |
| `(` | 1 | inner `(` → add | 2 | ✅ |
| `)` | 2 | inner `)` → add | 1 | ✅ |
| `(` | 1 | inner `(` → add | 2 | ✅ |
| `)` | 2 | inner `)` → add | 1 | ✅ |
| `)` | 1 | outer `)` → skip | 0 | ❌ |

Result:

```text
()()
```

---

### Second primitive: `(())`

| Character | Before | Action | After | Add? |
|---|---:|---|---:|---|
| `(` | 0 | outer `(` → skip | 1 | ❌ |
| `(` | 1 | inner `(` → add | 2 | ✅ |
| `)` | 2 | inner `)` → add | 1 | ✅ |
| `)` | 1 | outer `)` → skip | 0 | ❌ |

Result:

```text
()
```

Therefore:

```text
()() + ()
```

= 

```text
()()()
```

---

# 5. Why `balance > 0`?

This is the most important part.

### For `(`

```java
if (balance > 0) {
    result.append(ch);
}
balance++;
```

Suppose we encounter:

```text
(
```

when:

```text
balance = 0
```

That means we are starting a **new primitive**.

Therefore, this is the **outermost `(`**, so we don't add it.

But if:

```text
balance = 1
```

and we see:

```text
(
```

we are already inside a primitive.

Therefore, this `(` is an **inner parenthesis**, so we add it.

---

### For `)`

We first decrease:

```java
balance--;
```

Then:

```java
if (balance > 0) {
    result.append(ch);
}
```

Suppose before `)`:

```text
balance = 1
```

After decreasing:

```text
balance = 0
```

That means this `)` closed the entire primitive.

Therefore, it is the **outermost `)`**, so we skip it.

If after decreasing:

```text
balance > 0
```

then we are still inside the primitive, so we add the `)`.

---

# 6. Example: `"()()"`

There are two primitive groups:

```text
() + ()
```

Let's process:

```text
(
balance = 0
```

Skip it.

```text
)
balance becomes 0
```

Skip it.

First primitive produces:

```text
""
```

Second primitive also produces:

```text
""
```

Therefore:

```text
"()()" → ""
```

---

# 7. Why `StringBuilder`?

We could technically use:

```java
String result = "";
```

but repeatedly doing:

```java
result += ch;
```

creates many new `String` objects.

Instead, we use:

```java
StringBuilder result = new StringBuilder();
```

and:

```java
result.append(ch);
```

This is more efficient for building a string character by character.

---

# 8. Complexity

Let `n = s.length()`.

### Time Complexity

```text
O(n)
```

We visit every character exactly once.

### Space Complexity

```text
O(n)
```

The `StringBuilder` stores the resulting string.

The extra balance variable uses:

```text
O(1)
```

space.

---

## 9. Interview explanation

If the interviewer asks **"Explain your approach"**, you can say:

> I use a balance counter to track the current nesting depth of parentheses. When I encounter `(` at balance 0, it is the outermost opening parenthesis of a primitive, so I skip it. Otherwise, I add it to the result. For `)`, I first decrease the balance. If the resulting balance is 0, it is the outermost closing parenthesis, so I skip it; otherwise, I add it. This processes the string in one pass with O(n) time complexity.

### Remember this pattern

The core logic is:

```text
'(':
    if balance > 0 → add
    balance++

')':
    balance--
    if balance > 0 → add
```

The main thing to understand is **not the code**, but **why `balance == 0` identifies the outermost parentheses**.