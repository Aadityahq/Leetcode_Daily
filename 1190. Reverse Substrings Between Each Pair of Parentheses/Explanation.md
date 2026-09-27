## LeetCode 1190 — Reverse Substrings Between Each Pair of Parentheses

### 🧠 Problem Understanding

You are given a string containing:

* lowercase English letters
* `(` opening brackets
* `)` closing brackets

For **every pair of matching parentheses**, reverse the substring inside them.

The important part is:

> **Inner parentheses are processed first.**

After all reversals, return the string **without parentheses**.

### Example

```text
s = "(u(love)i)"
```

First reverse the innermost:

```text
(u(evol)i)
```

Then reverse everything inside the outer parentheses:

```text
iloveu
```

So the answer is:

```text
"iloveu"
```

---

# Approach: Stack

The easiest way to think about this problem is:

> Whenever we see `(`, start a new level.
> Whenever we see `)`, finish that level by reversing it.

We can use a `Stack<StringBuilder>`.

### What will the stack contain?

Each `StringBuilder` represents the text at one level of parentheses.

For example:

```text
(u(love)i)
```

When we encounter:

```text
(
```

we push a new empty `StringBuilder`.

When we encounter:

```text
love
```

we add it to the current `StringBuilder`.

When we encounter:

```text
)
```

we reverse the current `StringBuilder` and attach it to the previous level.

---

# Java Solution

```java
import java.util.*;

class Solution {
    public String reverseParentheses(String s) {

        Stack<StringBuilder> stack = new Stack<>();

        // Start with the outermost level
        stack.push(new StringBuilder());

        for (char ch : s.toCharArray()) {

            // Start a new level
            if (ch == '(') {
                stack.push(new StringBuilder());
            }

            // Finish current level
            else if (ch == ')') {

                StringBuilder current = stack.pop();

                current.reverse();

                stack.peek().append(current);
            }

            // Normal character
            else {
                stack.peek().append(ch);
            }
        }

        return stack.peek().toString();
    }
}
```

---

# 🔍 Let's Understand the Code

### 1. Create the stack

```java
Stack<StringBuilder> stack = new Stack<>();
```

We use a stack because parentheses are naturally **nested**.

Then:

```java
stack.push(new StringBuilder());
```

This represents the final/result level.

---

## 2. Traverse the string

```java
for (char ch : s.toCharArray()) {
```

We process every character one by one.

There are only three cases.

---

## Case 1: Opening Parenthesis `(`

```java
if (ch == '(') {
    stack.push(new StringBuilder());
}
```

When we see `(`, we create a new level.

For:

```text
(u(love)i)
```

Initially:

```text
stack
└── ""
```

After first `(`:

```text
stack
├── ""
└── ""
```

The second `StringBuilder` is where we'll store:

```text
u(love)i
```

---

## Case 2: Normal Character

```java
else {
    stack.peek().append(ch);
}
```

`peek()` gives us the current level.

For example:

```text
(u(love)i)
```

After reading `u`:

```text
stack
├── ""
└── "u"
```

Then we encounter another `(`.

```java
stack.push(new StringBuilder());
```

Now:

```text
stack
├── ""
├── "u"
└── ""
```

Then we read:

```text
love
```

So:

```text
stack
├── ""
├── "u"
└── "love"
```

---

# Case 3: Closing Parenthesis `)`

This is the most important part.

```java
else if (ch == ')') {

    StringBuilder current = stack.pop();

    current.reverse();

    stack.peek().append(current);
}
```

Suppose the stack is:

```text
├── ""
├── "u"
└── "love"
```

We encounter:

```text
)
```

First:

```java
StringBuilder current = stack.pop();
```

So:

```text
current = "love"
```

and stack becomes:

```text
├── ""
└── "u"
```

Then:

```java
current.reverse();
```

Now:

```text
current = "evol"
```

Finally:

```java
stack.peek().append(current);
```

So:

```text
stack
├── ""
└── "uevol"
```

Then we read:

```text
i
```

Result:

```text
├── ""
└── "uevoli"
```

Finally we encounter the outer `)`.

We pop:

```text
"uevoli"
```

Reverse it:

```text
"iloveu"
```

Append it to the outer level:

```text
├── "iloveu"
```

Return:

```text
"iloveu"
```

---

# 🔥 Complete Dry Run

Let's use:

```text
(ed(et(oc))el)
```

### Start

```text
stack = [""]
```

### Read `(`

```text
stack = ["", ""]
```

### Read `ed`

```text
stack = ["", "ed"]
```

### Read `(`

```text
stack = ["", "ed", ""]
```

### Read `et`

```text
stack = ["", "ed", "et"]
```

### Read `(`

```text
stack = ["", "ed", "et", ""]
```

### Read `oc`

```text
stack = ["", "ed", "et", "oc"]
```

Now `)`.

Pop:

```text
"oc"
```

Reverse:

```text
"co"
```

Append to previous level:

```text
stack = ["", "ed", "etco"]
```

Next `)`.

Pop:

```text
"etco"
```

Reverse:

```text
"octe"
```

Append to previous:

```text
stack = ["", "edocte"]
```

Next we read:

```text
el
```

So:

```text
stack = ["", "edoctel"]
```

Final `)`:

Pop:

```text
"edoctel"
```

Reverse:

```text
"letcod e"
```

Let's carefully write the characters:

```text
edoctel
```

Reverse:

```text
letcode
```

So:

```text
Output = "letcode"
```

Wait — the expected answer is:

```text
leetcode
```

The important intermediate is actually:

```text
(ed(et(oc))el)
```

After `oc` → `co`:

```text
(ed(etco)el)
```

Then `etco` → `octe`:

```text
(ed(oct e)el)
```

More precisely:

```text
(ed(octe)el)
```

Combine:

```text
edocteel
```

Then reverse:

```text
leetcode
```

So the final answer is:

```text
"leetcode"
```

This example shows why processing the **innermost parentheses first** matters.

---

# Why Does the Stack Work?

Think of parentheses as creating **levels**.

For:

```text
(a(b(c)d)e)
```

we have:

```text
Level 1
    a
    Level 2
        b
        Level 3
            c
        d
    e
```

The innermost level must finish first.

That's exactly what a stack does:

```text
Last In → First Out
```

The last opened parenthesis is the first one that gets closed.

Therefore:

```text
Stack
```

is a natural data structure for nested parentheses.

---

# Complexity

Let `n` be the length of the string.

### Time

```text
O(n²)
```

In the worst case, repeated reversals can cause quadratic work.

For the given constraint:

```text
n <= 2000
```

this is completely acceptable.

### Space

```text
O(n)
```

The stack and stored characters together can contain up to `n` characters.

---

# 💡 Key Interview Idea

The most important thing to remember is:

> **`(` → push a new string**
>
> **normal character → append to current string**
>
> **`)` → pop, reverse, append to previous string**

You don't need to manually search for matching parentheses.

The **stack automatically handles the matching and nesting**.

### Pattern to remember

```java
if (ch == '(') {
    stack.push(new StringBuilder());
}
else if (ch == ')') {
    StringBuilder current = stack.pop();
    current.reverse();
    stack.peek().append(current);
}
else {
    stack.peek().append(ch);
}
```

This is a useful **Stack + Nested Structure** pattern that appears in many DSA problems.
