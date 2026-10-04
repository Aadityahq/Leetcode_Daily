## LeetCode 678 — Valid Parenthesis String

### Approach: Greedy — Track Minimum and Maximum Open Parentheses

The main difficulty is `'*'`.

A `'*'` can be:
- `'('`
- `')'`
- `''` (empty)

Instead of trying all possibilities, we keep a **range** of possible open-parenthesis counts.

- `minOpen` = minimum possible number of unmatched `'('`
- `maxOpen` = maximum possible number of unmatched `'('`

For every character:

- `'('` → both increase by `1`
- `')'` → both decrease by `1`
- `'*'`:
  - It can be `')'`, so `minOpen--`
  - It can be `'('`, so `maxOpen++`

`minOpen` can never be negative because if it becomes negative, we can treat the `'*'` as empty or `'('`.

At the end, if `minOpen == 0`, there is at least one valid interpretation of the string.

### Java Solution

```java
class Solution {
    public boolean checkValidString(String s) {
        int minOpen = 0;
        int maxOpen = 0;

        for (char ch : s.toCharArray()) {

            if (ch == '(') {
                minOpen++;
                maxOpen++;
            } 
            else if (ch == ')') {
                minOpen--;
                maxOpen--;
            } 
            else { // '*'
                minOpen--;  // Treat '*' as ')'
                maxOpen++;  // Treat '*' as '('
            }

            // Even the maximum possible opens are negative
            if (maxOpen < 0) {
                return false;
            }

            // Minimum cannot be negative
            minOpen = Math.max(minOpen, 0);
        }

        return minOpen == 0;
    }
}
```

---

## How Does It Work?

Consider:

```text
s = "(*))"
```

We maintain:

```text
minOpen
maxOpen
```

### Character 1: `'('`

Both possibilities have one open parenthesis.

```text
minOpen = 1
maxOpen = 1
```

### Character 2: `'*'`

`'*'` can be `'('`, `')'`, or empty.

So:

```text
minimum = 0   // '*' is ')'/empty
maximum = 2   // '*' is '('
```

```text
minOpen = 0
maxOpen = 2
```

### Character 3: `')'`

A closing parenthesis reduces the number of open parentheses.

```text
minOpen = -1
maxOpen = 1
```

Since `minOpen` cannot actually be negative, we set:

```text
minOpen = 0
```

So:

```text
minOpen = 0
maxOpen = 1
```

### Character 4: `')'`

```text
minOpen = -1
maxOpen = 0
```

Again:

```text
minOpen = 0
maxOpen = 0
```

At the end:

```text
minOpen == 0
```

Therefore:

```text
true
```

One valid interpretation is:

```text
(*) )
 ↓
(())
```

which is valid.

---

## Why Do We Need Both `minOpen` and `maxOpen`?

This is the key idea of the problem.

Suppose:

```text
s = "(*"
```

After processing:

```text
'(' → min = 1, max = 1
'*' → min = 0, max = 2
```

The `'*'` gives us multiple possibilities:

```text
"()"   → 0 open
"("    → 1 open
"(( "  → 2 open
```

So instead of storing every possibility, we only store the **range**:

```text
0 ... 2
```

This is why the greedy solution is efficient.

---

## Important Failure Case

Consider:

```text
s = "*)"
```

First:

```text
'*'
minOpen = 0
maxOpen = 1
```

Then:

```text
')'
minOpen = -1 → 0
maxOpen = 0
```

Valid:

```text
"*)"
 ↓
"()"
```

So the answer is `true`.

Now consider:

```text
s = ")*"
```

First character:

```text
')'
minOpen = -1
maxOpen = -1
```

Since:

```text
maxOpen < 0
```

we immediately return:

```text
false
```

Why? Because even if every `'*'` later becomes `'('`, the first `')'` has no preceding `'('`.

---

## Why `maxOpen < 0` Means False

`maxOpen` represents the **maximum possible number of unmatched opening parentheses**.

If even this maximum becomes negative:

```text
maxOpen < 0
```

then there is no way to match the current `')'`.

For example:

```text
s = ")"
```

There is no `'*'` or `'('` before it that could match it.

Therefore, we can immediately return `false`.

---

## Why `minOpen` Is Set to 0

Suppose:

```text
s = "(*"
```

After processing `'*'`:

```text
minOpen = 0
```

We never allow:

```text
minOpen < 0
```

because a negative number of open parentheses doesn't make sense.

It simply means that, under that interpretation, we have already matched all available opening parentheses.

So:

```java
minOpen = Math.max(minOpen, 0);
```

keeps the lower bound meaningful.

---

## Why Does `minOpen == 0` at the End Mean Valid?

At the end, we need **at least one interpretation** of `'*'` that produces exactly zero unmatched `'('`.

`minOpen` is the smallest number of unmatched opening parentheses we can achieve.

Therefore:

```java
minOpen == 0
```

means we can choose appropriate interpretations of `'*'` so that all parentheses are matched.

If:

```text
minOpen > 0
```

then even the best interpretation leaves some `'('` unmatched.

Therefore the string is invalid.

---

## Complexity

Let `n` be the length of the string.

### Time

```text
O(n)
```

We process every character exactly once.

### Space

```text
O(1)
```

We only use two integer variables.

---

## Final Takeaway

The important idea to remember for interviews is:

> **For `'*'`, don't decide immediately whether it is `'('`, `')'`, or empty. Keep the range of possible open parentheses.**

```text
minOpen → minimum possible open parentheses
maxOpen → maximum possible open parentheses
```

And the rules are:

```text
'(' → min++, max++
')' → min--, max--
'*' → min--, max++
```

Then:

```text
if maxOpen < 0 → false
minOpen = max(minOpen, 0)

at the end:
minOpen == 0 → true
```

This greedy approach is much better than trying every possible interpretation of `'*'`, which could lead to exponential time.