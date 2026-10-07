For **LeetCode 301 — Remove Invalid Parentheses**, the cleanest approach to understand is **BFS (Breadth-First Search)**.

The key idea is:

> Remove parentheses **level by level**. The first level where we find valid strings gives us the **minimum number of removals**.

### Java Solution

```java
import java.util.*;

class Solution {
    public List<String> removeInvalidParentheses(String s) {

        List<String> result = new ArrayList<>();

        // BFS queue
        Queue<String> queue = new LinkedList<>();
        queue.offer(s);

        // To avoid processing duplicate strings
        Set<String> visited = new HashSet<>();
        visited.add(s);

        // Once we find valid strings, don't generate next level
        boolean found = false;

        while (!queue.isEmpty()) {

            String current = queue.poll();

            // Check if current string is valid
            if (isValid(current)) {
                result.add(current);
                found = true;
            }

            // If valid strings are found, we have used
            // minimum number of removals.
            if (found) {
                continue;
            }

            // Try removing each parenthesis
            for (int i = 0; i < current.length(); i++) {

                // Only remove parentheses, not letters
                if (current.charAt(i) != '(' &&
                    current.charAt(i) != ')') {
                    continue;
                }

                String next =
                    current.substring(0, i) +
                    current.substring(i + 1);

                // Avoid duplicates
                if (visited.add(next)) {
                    queue.offer(next);
                }
            }
        }

        return result;
    }

    // Checks whether a string has valid parentheses
    private boolean isValid(String s) {

        int balance = 0;

        for (char ch : s.toCharArray()) {

            if (ch == '(') {
                balance++;
            } 
            else if (ch == ')') {
                balance--;

                // More closing parentheses than opening
                if (balance < 0) {
                    return false;
                }
            }
        }

        // All opening parentheses must be closed
        return balance == 0;
    }
}
```

## 1. Understanding the Problem

We need to remove the **minimum number of parentheses** so that the remaining string becomes valid.

For example:

```text
s = "()())()"
```

We can remove one `)`:

```text
"(())()"
```

or another `)`:

```text
"()()()"
```

Both are valid, and both require only **1 removal**.

So the answer is:

```text
["(())()", "()()()"]
```

Notice that we need **all unique valid strings**, not just one.

---

# 2. What makes parentheses valid?

We can use a `balance` variable.

For every:

```text
(
```

increase balance:

```text
balance++
```

For every:

```text
)
```

decrease balance:

```text
balance--
```

A valid parentheses string must satisfy two conditions:

### Condition 1: Balance can never become negative

Example:

```text
")("
```

Start:

```text
balance = 0
```

Read `)`:

```text
balance = -1
```

This is invalid immediately.

### Condition 2: Final balance must be 0

Example:

```text
"(()"
```

Balances:

```text
(  -> 1
(  -> 2
)  -> 1
```

Final balance is `1`, so one `(` is unmatched.

Therefore:

```java
return balance == 0;
```

---

# 3. Why BFS?

This is the most important part.

Suppose:

```text
s = "()())()"
```

We can think about removing parentheses in levels.

### Level 0 — remove 0 characters

```text
()())()
```

Not valid.

### Level 1 — remove 1 character

We generate:

```text
)())()
()())(
(())()
()()()
...
```

Among these, we find:

```text
(())()
()()()
```

Both are valid.

Since we found valid strings after **1 removal**, we know that the minimum number of removals is `1`.

We **must not continue to level 2**.

That's exactly what BFS gives us.

---

# 4. Why not DFS?

DFS can also be used for this problem, but we need to be careful because we want the **minimum number of removals**.

DFS might explore something like:

```text
remove 1
  remove 1
    remove 1
      ...
```

before discovering a solution that required only one removal.

With BFS:

```text
0 removals
       ↓
1 removal
       ↓
2 removals
       ↓
3 removals
```

So the first valid level is automatically the minimum-removal level.

---

# 5. How the BFS works

We start with:

```java
Queue<String> queue = new LinkedList<>();
queue.offer(s);
```

Initially:

```text
Queue:

"()())()"
```

Then:

```java
while (!queue.isEmpty()) {
    String current = queue.poll();
```

We take one string from the queue.

First:

```text
current = "()())()"
```

Check:

```java
isValid(current)
```

It's invalid.

So we generate all strings by removing **one parenthesis**.

For example:

```text
")())()"
"())()"
"()()"
...
```

These are added to the queue.

---

# 6. Why do we use `visited`?

This is extremely important.

Different removal operations can produce the same string.

For example:

```text
"(())"
```

If we remove one of the two identical `(` characters, we can get the same result.

Without `visited`, we may process the same string multiple times.

So:

```java
Set<String> visited = new HashSet<>();
visited.add(s);
```

Before adding a new string:

```java
if (visited.add(next)) {
    queue.offer(next);
}
```

`Set.add()` returns `true` only when the string was not already present.

Therefore duplicates are automatically avoided.

---

# 7. Why do we only remove parentheses?

Look at the problem:

```text
"(a)())()"
```

The letters are never responsible for invalid parentheses.

Only:

```text
(
)
```

can make the parentheses invalid.

Therefore:

```java
if (current.charAt(i) != '(' &&
    current.charAt(i) != ')') {
    continue;
}
```

We skip letters.

For:

```text
(a)())()
```

we only try removing:

```text
(
)
(
)
...
```

and never:

```text
a
```

---

# 8. The most important part — `found`

We use:

```java
boolean found = false;
```

When we find a valid string:

```java
if (isValid(current)) {
    result.add(current);
    found = true;
}
```

Now we know that the current BFS level has produced a valid answer.

But why do we continue processing other strings already in the queue?

Because there may be **multiple valid answers at the same level**.

For example:

```text
()())()
```

can produce:

```text
(())()
()()()
```

We need both.

Therefore we don't immediately `break`.

Instead:

```java
if (found) {
    continue;
}
```

This means:

> Don't generate children from this string, because that would mean removing one more character.

But other strings already present at the same BFS level can still be checked.

Eventually the queue becomes empty, and we return all valid strings found at the minimum-removal level.

---

# 9. Example walkthrough

Consider:

```text
s = ")("
```

### Level 0

```text
")("
```

Invalid.

Generate strings by removing one parenthesis:

```text
"("
")"
```

### Level 1

Both are invalid.

Generate their children:

```text
""
```

### Level 2

```text
""
```

is valid.

So:

```text
result = [""]
```

We stop generating further levels.

Answer:

```text
[""]
```

---

# 10. Why BFS guarantees minimum removals

Suppose the original string is:

```text
S
```

BFS explores:

```text
Level 0 → 0 removals
Level 1 → 1 removal
Level 2 → 2 removals
Level 3 → 3 removals
...
```

Therefore, if the first valid string appears at level `k`, there cannot be a valid answer requiring fewer than `k` removals.

So:

> **First valid BFS level = minimum number of removals.**

And because we collect **all valid strings from that level**, we get all required answers.

---

# 11. Time Complexity

There can be many possible strings because each parenthesis can potentially be removed.

With at most **20 parentheses**, the search space can be exponential.

In the worst case, we may generate roughly:

```text
O(2^n)
```

different states, where `n` is the number of parentheses.

For each state, `isValid()` takes:

```text
O(n)
```

and creating a new string also takes up to:

```text
O(n)
```

So the overall complexity is approximately:

```text
O(2^n × n)
```

with `O(2^n × n)` space in the worst case for the queue and visited set.

Because the problem limits the number of parentheses to **20**, this approach is practical.

---

## The core idea to remember

Don't try to memorize the code. Remember these **4 steps**:

```text
1. Put the original string into a BFS queue.
             ↓
2. Check whether the current string is valid.
             ↓
3. If invalid, remove one parenthesis at every possible position.
             ↓
4. The first level containing valid strings is the minimum-removal answer.
```

And there are two important helpers:

```text
visited → prevents duplicate strings

found → stops us from going to a deeper level after finding
        the minimum-removal answers
```

### Short interview explanation

> “I use BFS because every BFS level represents removing one additional parenthesis. I start with the original string and generate all strings by removing one parenthesis at a time. For every generated string, I check validity using a balance counter. The first level where valid strings are found represents the minimum number of removals, so I collect all valid strings from that level and don't generate any deeper states. A HashSet is used to avoid duplicate strings.”