## 1081. Smallest Subsequence of Distinct Characters

### Problem Understanding

You are given a string `s`.

You need to create a **subsequence** (not necessarily contiguous) such that:

1. Every distinct character appears **exactly once**.
2. The subsequence is **lexicographically smallest** among all possible valid subsequences.

### What is a subsequence?

A subsequence is formed by deleting zero or more characters without changing the order of the remaining characters.

Example:

```
String = "abcde"

Possible subsequences:
abc
ace
bde
ae
```

But:

```
aec ❌ (changes order)
```

---

## Example 1

```
Input:
s = "bcabc"

Distinct letters:
a, b, c

Possible valid subsequences:
bca
bac
abc

Smallest lexicographically:
abc
```

Output

```
abc
```

---

## Example 2

```
Input:
cbacdcbc

Distinct letters:
a, b, c, d

Output:
acdb
```

Why not `abcd`?

Because `abcd` cannot be formed while preserving the original order.

---

# Key Observation

We only need **one copy** of every character.

Whenever we encounter a smaller character, we'd like to place it earlier.

But...

We can only remove a character already chosen if it appears again later.

Example

```
cbacdcbc
```

Current answer:

```
c
```

Now we see

```
b
```

Since

```
b < c
```

we would prefer

```
b
```

instead of

```
c
```

Can we remove `c`?

Yes, because another `c` exists later.

So

```
c
↓

remove

↓

b
```

Later we'll add `c` again.

This greedy idea produces the smallest answer.

---

# Data Structures Needed

We use:

### 1. Last occurrence array

```
last[ch]
```

Stores the last index where every character appears.

Example

```
cbacdcbc

Character  Last Index

a          2
b          6
c          7
d          4
```

This tells us whether a character will appear again.

---

### 2. Visited array

```
visited[ch]
```

If a character is already in our answer,

don't add it again.

---

### 3. Stack

The stack stores the current answer.

It allows removing larger characters whenever beneficial.

---

# Algorithm

### Step 1

Store last occurrence.

```
for every character
    last[ch] = index
```

---

### Step 2

Traverse the string.

For every character:

### Case 1

Already used?

```
visited[ch] == true

Skip it.
```

---

### Case 2

Not used.

Now compare with the stack top.

While

```
stack not empty

AND

top > current

AND

top appears later
```

remove top.

```
stack.pop()
visited[top] = false
```

Finally push current.

---

# Dry Run

```
s = cbacdcbc
```

Last occurrence

```
c → 7
b → 6
a → 2
d → 4
```

---

Start

```
Stack = []
```

---

Read

```
c
```

Push

```
[c]
```

---

Read

```
b
```

Top

```
c
```

Since

```
c > b

and

c appears later
```

Remove c

```
[]

Push b

[b]
```

---

Read

```
a
```

Top

```
b
```

```
b > a

b appears later
```

Remove

```
[]

Push a

[a]
```

---

Read

```
c
```

```
a < c

Push

[a c]
```

---

Read

```
d
```

```
Push

[a c d]
```

---

Read

```
c
```

Already used

Skip

---

Read

```
b
```

Top

```
d
```

Can we remove d?

No.

Because

```
last[d] = current index
```

No future d.

So keep d.

Push b.

```
[a c d b]
```

---

Read

```
c
```

Already used.

Finished.

Answer

```
acdb
```

---

# Why Does This Work?

Suppose stack top is

```
d
```

Current character is

```
b
```

Since

```
b < d
```

we would like

```
...b...
```

instead of

```
...d...
```

But we can remove `d` **only if another `d` exists later**.

Otherwise we'd lose `d` forever.

That's why the condition

```
last[top] > currentIndex
```

is necessary.

---

# Why Use a Stack?

The stack always stores the **best answer built so far**.

Whenever we find a better (smaller) character,

we remove unnecessary larger characters.

This is a classic **Monotonic Stack + Greedy** problem.

---

# Java Solution

```java
class Solution {
    public String smallestSubsequence(String s) {
        int[] last = new int[26];

        // Store last occurrence of each character
        for (int i = 0; i < s.length(); i++) {
            last[s.charAt(i) - 'a'] = i;
        }

        boolean[] visited = new boolean[26];
        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);

            // Skip if already included
            if (visited[ch - 'a']) {
                continue;
            }

            // Remove larger characters if they appear later
            while (!stack.isEmpty()
                    && stack.peek() > ch
                    && last[stack.peek() - 'a'] > i) {

                visited[stack.pop() - 'a'] = false;
            }

            stack.push(ch);
            visited[ch - 'a'] = true;
        }

        // Build answer
        StringBuilder ans = new StringBuilder();

        for (char ch : stack) {
            ans.append(ch);
        }

        return ans.toString();
    }
}
```

---

# Time Complexity

Finding last occurrence:

```
O(n)
```

Traversing the string:

```
O(n)
```

Each character is pushed once and popped at most once.

Total:

```
O(n)
```

---

# Space Complexity

* `last` array → `O(26)`
* `visited` array → `O(26)`
* Stack → at most 26 distinct lowercase letters

Overall:

```
O(26) = O(1)
```

(Constant extra space, since the alphabet size is fixed.)

---

# Intuition to Remember

Whenever you see a problem that asks for:

* **smallest lexicographical string**
* **remove duplicates**
* **keep relative order (subsequence)**
* **each character exactly once**

Think of this pattern:

> **Greedy + Monotonic Stack**

**Rules:**

1. Skip characters already included.
2. While the current character is smaller than the stack top **and** the stack top appears again later, pop it.
3. Push the current character.
4. The stack at the end is the lexicographically smallest valid subsequence.
