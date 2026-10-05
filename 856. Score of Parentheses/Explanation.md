## LeetCode 856 — Score of Parentheses

### Problem Explanation

You are given a **balanced parentheses string** `s`.

You need to calculate its score using these rules:

1. `()` → score is **1**
2. `AB` → score is **A + B**
3. `(A)` → score is **2 × A**

### Examples

```text
"()"     → 1

"(())"   → 2
          ↓
         (())
          ↓
        2 × 1 = 2

"()()"   → 2
          ↓
        1 + 1 = 2

"(()())" → 4
          ↓
       ( () () )
          ↓
       2 × (1 + 1)
          ↓
          4
```

---

# Approach — Stack

The easiest way to handle the nested parentheses is using a **stack**.

### Main idea

Whenever we see:

- `(` → start a new level.
- `)` → finish the current level and calculate its score.

We can keep the score of every currently open parenthesis level in the stack.

Initially:

```text
stack = [0]
```

The `0` represents the score outside all parentheses.

### When we see `(`

Push `0` onto the stack.

```text
stack = [0, 0]
```

This `0` represents the score inside the newly opened parentheses.

### When we see `)`

Pop the score of the current level.

There are two cases:

#### Case 1: `()`

The popped score is `0`.

According to the rule:

```text
() = 1
```

So:

```text
value = 1
```

#### Case 2: `(A)`

The popped score is greater than `0`.

According to the rule:

```text
(A) = 2 × A
```

So:

```text
value = 2 * innerScore
```

Then add this value to the previous level.

---

# Java Solution

```java
import java.util.*;

class Solution {
    public int scoreOfParentheses(String s) {
        Stack<Integer> stack = new Stack<>();

        // Score outside all parentheses
        stack.push(0);

        for (char ch : s.toCharArray()) {

            if (ch == '(') {
                // Start a new level
                stack.push(0);
            } else {
                // Finish the current level
                int innerScore = stack.pop();

                int value;

                if (innerScore == 0) {
                    // "()"
                    value = 1;
                } else {
                    // "(A)"
                    value = 2 * innerScore;
                }

                // Add score to the previous level
                stack.push(stack.pop() + value);
            }
        }

        return stack.peek();
    }
}
```

---

# Dry Run

Let's understand it with:

```text
s = "(()())"
```

Initially:

```text
stack = [0]
```

### 1. `(`

Open a new level:

```text
stack = [0, 0]
```

### 2. `(`

Open another level:

```text
stack = [0, 0, 0]
```

### 3. `)`

Current level has score `0`.

Therefore this is:

```text
()
```

Score:

```text
1
```

Add it to the previous level:

```text
stack = [0, 1]
```

### 4. `(`

Open another level:

```text
stack = [0, 1, 0]
```

### 5. `)`

Again:

```text
()
```

Score:

```text
1
```

Add it to the previous level:

```text
stack = [0, 2]
```

### 6. `)`

Now we have:

```text
( ()() )
```

The inner score is:

```text
2
```

So:

```text
(A) = 2 × A
```

Therefore:

```text
2 × 2 = 4
```

Add it to the outer level:

```text
stack = [4]
```

Final answer:

```text
4
```

---

# Why Does This Work?

The important idea is that **each pair of parentheses creates a new scoring level**.

For example:

```text
(()())
```

can be viewed as:

```text
(  ()()  )
   └──┬─┘
      2
```

The inner part has:

```text
() + ()
= 1 + 1
= 2
```

Then the outer parentheses double it:

```text
(2)
= 2 × 2
= 4
```

The stack allows us to keep these different levels separate.

Whenever we encounter `(`, we create a new level.

Whenever we encounter `)`, we finish that level and send its score back to the previous level.

---

## Complexity

Let `n` be the length of the string.

### Time

```text
O(n)
```

We visit every character exactly once.

### Space

```text
O(n)
```

In the worst case, the string can be completely nested:

```text
((((((())))))
```

so the stack can contain `O(n)` elements.

---

## Key Pattern to Remember

This problem is a good example of **stack + nested state**.

Remember this pattern:

```text
'(' → push a new state

')' → calculate current state
      → combine it with previous state
```

And specifically for this problem:

```text
innerScore == 0
        ↓
       "()"
        ↓
        1

innerScore > 0
        ↓
      "(A)"
        ↓
      2 * A
```

This is the main trick that makes the solution simple.