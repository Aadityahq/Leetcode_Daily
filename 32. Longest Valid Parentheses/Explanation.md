# 32. Longest Valid Parentheses

## Problem

Given a string containing only `'('` and `')'`, return the length of the longest valid (well-formed) parentheses substring.

A valid parentheses substring is one where:

- Every opening parenthesis `'('` has a matching closing parenthesis `')'`.
- The parentheses are properly nested.

### Example 1

```text
Input:  s = "(()"
Output: 2
```

The longest valid substring is:

```text
"()"
```

### Example 2

```text
Input:  s = ")()())"
Output: 4
```

The longest valid substring is:

```text
"()()"
```

### Example 3

```text
Input:  s = ""
Output: 0
```

---

## Approach

We use **Dynamic Programming**.

Create an array:

```text
dp[i]
```

where `dp[i]` represents the length of the longest valid parentheses substring that **ends at index `i`**.

We only need to calculate `dp[i]` when:

```text
s[i] == ')'
```

because a valid parentheses substring must end with `')'`.

There are two main cases.

---

## Case 1: `"()"`

If the current character is `')'` and the previous character is `'('`:

```text
s[i - 1] == '('
```

Then we have:

```text
()
```

So:

```text
dp[i] = 2
```

But there may be a valid substring before this pair.

For example:

```text
()()
```

When processing the second `')'`:

```text
dp[i] = 2 + dp[i - 2]
```

Therefore:

```java
dp[i] = 2;

if (i - 2 >= 0) {
    dp[i] += dp[i - 2];
}
```

---

## Case 2: `"))"`

Now suppose:

```text
s[i] == ')'
s[i - 1] == ')'
```

If `dp[i - 1] > 0`, then there is a valid substring ending at `i - 1`.

For example:

```text
(()) 
```

Consider the last `')'`.

The previous valid substring is:

```text
()
```

We need to find the `'('` that can match the current `')'`.

The index of that possible `'('` is:

```text
i - dp[i - 1] - 1
```

So we calculate:

```java
int openIndex = i - dp[i - 1] - 1;
```

If:

```text
openIndex >= 0
```

and:

```text
s[openIndex] == '('
```

then we can match this `'('` with the current `')'`.

Therefore:

```text
dp[i] = dp[i - 1] + 2
```

There may also be another valid substring immediately before this matching `'('`.

For example:

```text
() (())
```

The complete valid substring contains both parts.

So we add:

```text
dp[openIndex - 1]
```

if it exists.

```java
dp[i] = dp[i - 1] + 2;

if (openIndex - 1 >= 0) {
    dp[i] += dp[openIndex - 1];
}
```

---

## Dry Run

Consider:

```text
s = ")()())"
```

We create:

```text
dp = [0, 0, 0, 0, 0, 0]
```

### Index 1

Characters:

```text
()
```

So:

```text
dp[1] = 2
```

### Index 2

Character is `'('`.

Nothing to calculate.

```text
dp[2] = 0
```

### Index 3

Characters:

```text
()
```

So:

```text
dp[3] = 2 + dp[1]
      = 2 + 2
      = 4
```

### Index 4

Character is `'('`.

```text
dp[4] = 0
```

### Index 5

Previous character is `')'`.

We have:

```text
dp[4] = 0
```

So no new valid substring can be formed.

Final array:

```text
[0, 2, 0, 4, 0, 0]
```

The maximum value is:

```text
4
```

Therefore:

```text
Answer = 4
```

---

## Java Code

```java
class Solution {
    public int longestValidParentheses(String s) {
        int n = s.length();
        int[] dp = new int[n];

        int maxLength = 0;

        for (int i = 1; i < n; i++) {
            if (s.charAt(i) == ')') {

                // Case 1: "()"
                if (s.charAt(i - 1) == '(') {
                    dp[i] = 2;

                    if (i - 2 >= 0) {
                        dp[i] += dp[i - 2];
                    }
                }

                // Case 2: "...))"
                else if (s.charAt(i - 1) == ')' && dp[i - 1] > 0) {
                    int openIndex = i - dp[i - 1] - 1;

                    if (openIndex >= 0 && s.charAt(openIndex) == '(') {
                        dp[i] = dp[i - 1] + 2;

                        if (openIndex - 1 >= 0) {
                            dp[i] += dp[openIndex - 1];
                        }
                    }
                }

                maxLength = Math.max(maxLength, dp[i]);
            }
        }

        return maxLength;
    }
}
```

---

## Complexity

### Time Complexity

```text
O(n)
```

We traverse the string only once.

### Space Complexity

```text
O(n)
```

We use the `dp` array of size `n`.

---

## Key Takeaway

The important idea is:

```text
dp[i] = longest valid parentheses substring ending at i
```

For every `')'`, there are two possibilities:

1. It forms `"()"`.
2. It closes a previous valid substring and forms a larger valid substring.

The important formula for the second case is:

```text
openIndex = i - dp[i - 1] - 1
```

If `s[openIndex] == '('`, we can extend the valid substring:

```text
dp[i] = dp[i - 1] + 2 + dp[openIndex - 1]
```

Finally, the answer is the maximum value in `dp`.
```