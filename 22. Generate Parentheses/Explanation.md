
## LeetCode 22 — Generate Parentheses

### Problem

Given `n` pairs of parentheses, generate **all possible combinations of well-formed parentheses**.

For example, when `n = 3`, we have `3` opening `(` and `3` closing `)` brackets.

The valid combinations are:

```text
((()))
(()())
(())()
()(())
()()()
```

The important part is that **at every point**, the number of closing brackets cannot be greater than the number of opening brackets.

---

## Approach — Backtracking

We can solve this using **Backtracking**.

At every step, we have two choices:

1. Add an opening bracket `(` if we still have some left.
2. Add a closing bracket `)` only when it is safe to do so.

We maintain:

- `open` → number of opening brackets used
- `close` → number of closing brackets used
- `current` → parentheses string being built

### The important rule

We can add `(` when:

```text
open < n
```

We can add `)` when:

```text
close < open
```

Why `close < open`?

Because we cannot close a parenthesis that hasn't been opened.

For example:

```text
())(
```

is invalid because after `()` we have no unmatched opening bracket, so adding `)` would make the string invalid.

---

## Java Solution

```java
import java.util.*;

class Solution {

    public List<String> generateParenthesis(int n) {
        List<String> result = new ArrayList<>();

        backtrack(n, 0, 0, new StringBuilder(), result);

        return result;
    }

    private void backtrack(
            int n,
            int open,
            int close,
            StringBuilder current,
            List<String> result
    ) {

        // If we have used all n pairs
        if (current.length() == 2 * n) {
            result.add(current.toString());
            return;
        }

        // Add opening bracket
        if (open < n) {
            current.append('(');

            backtrack(n, open + 1, close, current, result);

            current.deleteCharAt(current.length() - 1);
        }

        // Add closing bracket
        if (close < open) {
            current.append(')');

            backtrack(n, open, close + 1, current, result);

            current.deleteCharAt(current.length() - 1);
        }
    }
}
```

---

# How the Solution Works

Let's take:

```text
n = 3
```

Initially:

```text
current = ""
open = 0
close = 0
```

We can only add `(` because:

```text
open < n
```

So:

```text
(
```

Now:

```text
open = 1
close = 0
```

We can again add `(`:

```text
((
```

Then:

```text
(((
```

Now all opening brackets are used:

```text
open = 3
close = 0
```

So we cannot add another `(`.

We can add `)` because:

```text
close < open
0 < 3
```

Giving:

```text
((()
```

We continue exploring both possible choices whenever they are valid.

Eventually we get:

```text
((()))
(()())
(())()
()(())
()()()
```

---

## Why Backtracking?

Suppose we are building:

```text
(())
```

After reaching this state, we need to go back and try another possibility.

This is exactly what backtracking does.

For example:

```text
current = "(("
```

We try:

```text
(((
```

After completely exploring that path, we remove the last character:

```text
((
```

Then try:

```text
(()
```

This part of the code performs that undo operation:

```java
current.append('(');

backtrack(...);

current.deleteCharAt(current.length() - 1);
```

The `deleteCharAt()` is extremely important.

It means:

> "I have finished exploring this choice. Remove it and try another choice."

---

# Recursion Tree

For `n = 2`, the recursion looks roughly like this:

```text
                    ""
                    |
                   "("
                 /     \
               "(("    "()"
                |        |
              "(()"    "()("
                |        |
             "(())"    "()()"
```

The leaf nodes are the valid answers:

```text
(()) 
()()
```

Notice that we **never generate invalid strings** such as:

```text
)(
())
```

because of this condition:

```java
if (close < open)
```

---

# Why `close < open`?

This is the most important concept in this problem.

Suppose:

```text
open = 2
close = 2
```

Current string could be:

```text
()()
```

Can we add `)`?

No.

Because there is no unmatched `(` left.

Therefore:

```java
close < open
```

is false.

But suppose:

```text
open = 2
close = 1
```

Current string could be:

```text
(()
```

There is still one unmatched `(`.

So we can safely add `)`:

```text
(())
```

That's why the condition works.

---

# Why `open < n`?

We have exactly `n` opening brackets available.

For:

```text
n = 3
```

we can use:

```text
(
((
(((
```

but never:

```text
((((
```

So we check:

```java
if (open < n)
```

This ensures that we never use more than `n` opening brackets.

The same applies to closing brackets indirectly because:

```java
close < open
```

ensures we never use a closing bracket without a corresponding opening bracket.

---

# Dry Run

For `n = 1`:

Initially:

```text
open = 0
close = 0
current = ""
```

Add `(`:

```text
current = "("
open = 1
close = 0
```

Cannot add another `(` because:

```text
open < n
1 < 1 → false
```

Can add `)` because:

```text
close < open
0 < 1 → true
```

So:

```text
current = "()"
```

Length is:

```text
2 * n = 2
```

Therefore:

```text
result = ["()"]
```

---

# Complexity

The number of valid combinations is the **Catalan number**:

```text
Cₙ = (1 / (n + 1)) × C(2n, n)
```

Therefore, we need to generate `Cₙ` strings, each having length `2n`.

### Time Complexity

```text
O(Cₙ × n)
```

because there are `Cₙ` valid answers and each answer contains `2n` characters.

### Space Complexity

```text
O(n)
```

for the recursion depth and `StringBuilder`, **excluding the output list**.

Including the output:

```text
O(Cₙ × n)
```

because we store all generated strings.

---

## Key Idea to Remember

For this problem, remember just these **two conditions**:

```java
// We can add '('
if (open < n)
```

and

```java
// We can add ')'
if (close < open)
```

Think of it as:

> **Opening brackets create possibilities, while closing brackets are allowed only when an opening bracket is available to close.**

That's the core idea behind the entire solution.