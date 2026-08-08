## Intuition of the Problem

We need to select exactly `word2.length()` indices from `word1` such that:

1. Indices are in increasing order.
2. The characters picked from `word1` form a string that differs from `word2` in **at most one position**.
3. Among all valid index sequences, return the **lexicographically smallest sequence of indices**.

### What does lexicographically smallest indices mean?

Compare index arrays from left to right.

Example:

```
[0,1,4]
[0,2,3]
```

At the first differing position:

```
1 < 2
```

So:

```
[0,1,4] is smaller
```

Notice that we are comparing **indices**, not the resulting string.

---

## Key Observation

Since we can change **at most one character**, while matching `word2`:

* Either every character matches.
* Or exactly one selected character is allowed to mismatch.

To get the lexicographically smallest index sequence, we always want to pick the **earliest possible index**.

However, if a character doesn't match, we can use our **one allowed modification**.

The challenge is knowing:

> If I use a mismatch at position `i`, can I still complete the rest of `word2` using characters after `i`?

---

# Reverse DP / Suffix Information

The array `indices[i]` stores:

```
How many characters of word2's suffix
can be matched using word1 starting from i.
```

### Building it

Start from the end.

```java
j = m - 1
```

where

```
m = word2.length()
```

Traverse `word1` from right to left.

Whenever:

```java
word1[i] == word2[j]
```

we match one more suffix character.

---

### Example

```
word1 = vbcca
word2 = abc
```

Need suffix matching.

Start:

```
j = 2 ('c')
```

Traverse backward.

```
i=4 a
no match

i=3 c
match c
j=1

i=2 c
no match with b

i=1 b
match b
j=0

i=0 v
no match with a
```

The array ultimately tells us:

```
from position i onwards,
how many remaining chars of word2 can still be matched.
```

This lets us answer:

> If I spend my one mismatch now, can the remaining characters still be matched exactly?

in O(1).

---

# Greedy Construction

Now build the answer from left to right.

Let:

```java
j = current position in word2
```

---

## Case 1: Characters Match

```java
word1[i] == word2[j]
```

Then taking this index is always best.

Why?

Because we want the smallest possible index sequence.

Earlier index = lexicographically smaller answer.

So:

```java
result.add(i);
j++;
```

---

## Case 2: Characters Do Not Match

Normally we cannot take it.

But we have one allowed modification.

Suppose we use it here.

Then after choosing this index:

```
remaining chars needed
=
length2 - j - 1
```

We must verify whether the remaining part of `word2`
can still be matched later.

That is exactly what:

```java
indices[i + 1]
```

tells us.

If

```java
indices[i + 1] >= length2 - j - 1
```

then completion is possible.

So we greedily take this mismatch immediately.

```java
result.add(i);
j++;
```

and mark that we've used the modification.

This guarantees the lexicographically smallest sequence because we use the earliest feasible index.

---

# Why Break After First Mismatch?

The problem allows:

```
at most one modification
```

After using it once:

```java
finalIndex = i + 1;
break;
```

Now the remaining characters must match exactly.

So we continue normally and finish matching the suffix.

---

# Example Walkthrough

## Example 1

```
word1 = vbcca
word2 = abc
```

Need 3 indices.

### i = 0

```
v != a
```

Can we use mismatch?

Remaining needed:

```
2 chars
```

Suffix array says yes.

Take index 0.

```
result = [0]
j = 1
```

Modification used.

---

Now match exactly.

### i = 1

```
b == b
```

Take.

```
result = [0,1]
j = 2
```

---

### i = 2

```
c == c
```

Take.

```
result = [0,1,2]
```

Done.

Answer:

```
[0,1,2]
```

---

# Example 2

```
word1 = bacdc
word2 = abc
```

### i = 0

```
b != a
```

Can we use mismatch?

If we do, remaining:

```
bc
```

cannot be completed lexicographically as well as waiting.

Greedy logic checks feasibility.

---

### i = 1

```
a == a
```

Take.

```
[1]
```

---

### i = 2

```
c != b
```

Can use modification.

Suffix says remaining `'c'` can still be matched.

Take.

```
[1,2]
```

---

### later

Match final `c`.

```
[1,2,4]
```

Answer:

```
[1,2,4]
```

---

# Why This Produces Lexicographically Smallest Sequence

At every step:

1. If current index can be part of a valid answer, we take it immediately.
2. We only skip an index when taking it would make completion impossible.
3. Therefore the first index is minimized.
4. Then the second index is minimized.
5. Then the third, and so on.

This is exactly the definition of lexicographical minimization.

---

# Complexity Analysis

Let

```
n = word1.length()
m = word2.length()
```

### Reverse pass

```
O(n)
```

### Forward pass

```
O(n)
```

### Total

```
O(n)
```

### Space

```
indices[] : O(n)
answer     : O(m)
```

Overall:

```
Time  : O(n)
Space : O(n)
```

which works efficiently for:

```
n ≤ 3 × 10^5
```

The clever idea is the **suffix-match array (`indices`)**. It lets us instantly know whether using the single allowed mismatch at the current position still allows the rest of `word2` to be matched, enabling an O(n) greedy solution.
