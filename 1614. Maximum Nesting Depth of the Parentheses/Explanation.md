## LeetCode 1614 — Maximum Nesting Depth of the Parentheses

### 🔗 Problem

[**1614. Maximum Nesting Depth of the Parentheses**](https://leetcode.com/problems/maximum-nesting-depth-of-the-parentheses/)

### 💡 Problem Explanation

We are given a **valid parentheses string** `s`.

We need to find the **maximum number of parentheses that are open at the same time**.

For example:

```text
(1+(2*3)+((8)/4))+1
```

Let's look at the nesting around `8`:

```text
(1+(2*3)+((8)/4))+1
            ↑
```

For `8`:

```text
(
    (
        8
    )
)
```

There are **3 levels** of nested parentheses.

So the answer is:

```text
3
```

---

# 🧠 Key Idea

We only need to keep track of two things:

1. `depth` → current number of open parentheses
2. `maxDepth` → maximum depth we have seen so far

### When we see `(`

We are entering another level of nesting.

```java
depth++;
```

Then update the maximum:

```java
maxDepth = Math.max(maxDepth, depth);
```

### When we see `)`

We are leaving one level of nesting.

```java
depth--;
```

That's it.

---

# ✅ Java Solution

```java
class Solution {
    public int maxDepth(String s) {
        int depth = 0;
        int maxDepth = 0;

        for (char ch : s.toCharArray()) {

            if (ch == '(') {
                depth++;
                maxDepth = Math.max(maxDepth, depth);
            } 
            else if (ch == ')') {
                depth--;
            }
        }

        return maxDepth;
    }
}
```

---

# 🔍 Dry Run

Consider:

```text
s = "(1+(2*3)+((8)/4))+1"
```

We process the string character by character.

| Character | `depth` | `maxDepth` |
| --------- | ------: | ---------: |
| `(`       |       1 |          1 |
| `1`       |       1 |          1 |
| `+`       |       1 |          1 |
| `(`       |       2 |          2 |
| `2`       |       2 |          2 |
| `*`       |       2 |          2 |
| `3`       |       2 |          2 |
| `)`       |       1 |          2 |
| `+`       |       1 |          2 |
| `(`       |       2 |          2 |
| `(`       |       3 |          3 |
| `8`       |       3 |          3 |
| `)`       |       2 |          3 |
| `)`       |       1 |          3 |
| `)`       |       0 |          3 |

Finally:

```text
maxDepth = 3
```

So we return:

```text
3
```

---

# 🤔 Why Does This Work?

Think of `depth` like going inside and outside rooms.

### `(` → Enter a room

```text
depth = depth + 1
```

### `)` → Leave a room

```text
depth = depth - 1
```

The deepest point we ever reach is the answer.

For:

```text
((()))
```

The depth changes like this:

```text
(     → 1
((    → 2
(((   → 3
))    → 2
)     → 1
)     → 0
```

Therefore:

```text
Maximum depth = 3
```

---

# ⏱️ Complexity

### Time: `O(n)`

We visit every character exactly once.

### Space: `O(1)`

We only use two integer variables:

```java
depth
maxDepth
```

---

# 🧩 Important Pattern to Remember

This problem teaches a very useful pattern:

> **When a problem asks for maximum nesting / balance of parentheses, maintain a running `depth` and track the maximum.**

The general pattern is:

```java
int depth = 0;
int maxDepth = 0;

for (char ch : s.toCharArray()) {

    if (ch == '(') {
        depth++;
        maxDepth = Math.max(maxDepth, depth);
    } 
    
    else if (ch == ')') {
        depth--;
    }
}
```

### One-line memory trick:

**`(` → increase depth**
**`)` → decrease depth**
**Every increase → check maximum**

This is a simple **string traversal + counting** problem.
