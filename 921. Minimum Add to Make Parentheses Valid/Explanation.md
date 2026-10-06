## LeetCode 921 — Minimum Add to Make Parentheses Valid

### Approach: Greedy

The key idea is to keep track of:

- `open` → number of unmatched `'('`
- `add` → number of parentheses we need to insert

When we see:

- `'('` → increase `open`
- `')'`:
  - If there is an unmatched `'('`, match it → decrease `open`
  - Otherwise, this `')'` has no matching `'('`, so we must insert an `'('` → increase `add`

At the end, any remaining unmatched `'('` needs a `')'`.

So the answer is:

```text
add + open
```

### Java Solution

```java
class Solution {
    public int minAddToMakeValid(String s) {
        int open = 0;
        int add = 0;

        for (char ch : s.toCharArray()) {
            if (ch == '(') {
                open++;
            } else {
                if (open > 0) {
                    open--;
                } else {
                    add++;
                }
            }
        }

        return add + open;
    }
}
```

---

## Explanation

### Why do we need `open`?

Suppose:

```text
s = "(()"
```

While scanning:

```text
( → open = 1
( → open = 2
) → open = 1
```

There is still one unmatched `'('`.

We need to insert one `')'`:

```text
(())
```

Therefore, the answer is `1`.

---

### Why do we need `add`?

Consider:

```text
s = "())"
```

Process it from left to right:

```text
( → open = 1

) → open = 0

) → no '(' available
```

The last `')'` cannot be matched.

So we need to insert an `'('` before it:

```text
()()
```

Therefore:

```text
add = 1
open = 0
answer = add + open = 1
```

---

## Step-by-step Example

For:

```text
s = "()))"
```

| Character | `open` | `add` | Explanation |
|---|---:|---:|---|
| `(` | 1 | 0 | Found an opening parenthesis |
| `)` | 0 | 0 | Matches the previous `(` |
| `)` | 0 | 1 | No `(` available, insert `(` |
| `)` | 0 | 2 | Again, no `(` available |

Final:

```text
open = 0
add = 2
```

Answer:

```text
2
```

We can make it valid as:

```text
()()()
```

by inserting two `'('`.

---

## Another Example

```text
s = "((("
```

Process:

```text
( → open = 1
( → open = 2
( → open = 3
```

There are no closing parentheses.

So we need three `')'`:

```text
((()))
```

Therefore:

```text
add = 0
open = 3

answer = 0 + 3 = 3
```

---

## Why does this work?

A valid parentheses string must satisfy two conditions:

1. At every point, we cannot have more `')'` than `'('`.
2. At the end, the number of `'('` and `')'` must be equal.

Our algorithm handles both cases:

### Case 1: Too many `')'`

If we encounter:

```text
)
```

when `open == 0`, there is no opening parenthesis available.

The only way to fix it is to insert:

```text
(
```

So we increment `add`.

### Case 2: Too many `'('`

If the string ends with unmatched opening parentheses:

```text
(((
```

we need one `')'` for each unmatched `'('`.

That's why we add `open` to the answer.

---

## Complexity

Let `n` be the length of the string.

**Time Complexity:**

```text
O(n)
```

We scan the string only once.

**Space Complexity:**

```text
O(1)
```

We use only two variables.

---

### Simple way to remember the approach

Think of `open` as **available opening brackets**.

```text
'(' → increase open

')' → 
    if open > 0:
        use one '('
    else:
        insert one '('
```

After the loop:

```text
answer = unmatched ')' + unmatched '('
       = add + open
```

This is a **greedy solution** because every `')'` is matched with an available `'('` immediately, and when no match is possible, we make the necessary insertion immediately.