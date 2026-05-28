## Intuition

We need, for every query word:

* Find the **longest suffix** common with any word in `wordsContainer`
* If multiple words match:

  1. choose the **smallest length**
  2. if still tied, choose the **earlier index**

---

## Important Observation

Suffix problems become prefix problems if we reverse the strings.

Example:

| Original | Reversed |
| -------- | -------- |
| `"abcd"` | `"dcba"` |
| `"bcd"`  | `"dcb"`  |
| `"cd"`   | `"dc"`   |

Now:

* Common suffix `"cd"`
* becomes common prefix `"dc"`

So we can use a **Trie** on reversed strings.

---

# Why Trie?

Trie helps us efficiently store prefixes.

Since we reverse strings:

* path in trie = suffix in original word

---

# Main Idea

We insert every `wordsContainer[i]` into a trie **from back to front**.

While inserting:

* every node stores:

  * shortest word length passing through it
  * corresponding index

Why?

Because if a query reaches that node,
that node represents the **longest matched suffix**.

And we already know the best answer among all words reaching there.

---

# Trie Structure

```java
class TrieNode {
    TrieNode[] children = new TrieNode[26];

    int bestLen = Integer.MAX_VALUE;
    int bestIdx = Integer.MAX_VALUE;
}
```

Each node stores:

* `children`
* `bestLen`
* `bestIdx`

---

# Building the Trie

Suppose:

```java
wordsContainer = ["abcd","bcd","xbcd"]
```

Reverse insertion:

```text
abcd  -> dcba
bcd   -> dcb
xbcd  -> dcbx
```

Trie:

```text
root
 |
 d
 |
 c
 |
 b
 / \
a   x
```

---

# Why Update bestLen and bestIdx at Every Node?

Suppose node `"dcb"` represents suffix `"bcd"`.

Words reaching there:

* `"abcd"` length 4
* `"bcd"` length 3
* `"xbcd"` length 4

Best answer should be:

```text
index of "bcd"
```

because it is shortest.

So every node keeps the best candidate.

---

# This Condition

```java
if (len < curr.bestLen || 
   (len == curr.bestLen && i < curr.bestIdx))
```

means:

Update if:

* smaller length found
  OR
* same length but earlier index

Exactly according to problem statement.

---

# Step-by-Step Insertion

Take:

```java
word = "abcd"
```

Reverse traversal:

```text
d -> c -> b -> a
```

At every node:

```java
curr.bestLen = 4
curr.bestIdx = 0
```

---

Then insert:

```java
word = "bcd"
```

Reverse:

```text
d -> c -> b
```

Now `"bcd"` length = 3

Since 3 < 4:

Update nodes:

```text
d
dc
dcb
```

to:

```text
bestLen = 3
bestIdx = 1
```

---

# Query Processing

Now query:

```java
query = "cd"
```

Reverse traversal:

```text
d -> c
```

We stop at node `"dc"`.

That node already stores:

```text
bestIdx = 1
```

Answer:

```java
1
```

---

# Why Does This Work?

Because:

* deeper trie node = longer suffix match
* while traversing query:

  * we go as deep as possible
  * deepest reachable node = longest common suffix

And that node already stores the optimal word.

---

# Example Dry Run

## Input

```java
wordsContainer = ["abcd","bcd","xbcd"]
wordsQuery = ["cd"]
```

---

## Trie Contains

```text
dcba
dcb
dcbx
```

---

## Query `"cd"`

Reverse traversal:

```text
d -> c
```

Reached node `"dc"`.

That node has:

```text
bestLen = 3
bestIdx = 1
```

Return:

```text
1
```

---

# What About No Matching Suffix?

Example:

```java
query = "xyz"
```

Reverse:

```text
z -> y -> x
```

Root has no `'z'`.

So traversal stops immediately.

We return:

```java
root.bestIdx
```

Why?

Because empty suffix `""` matches all strings.

And root stores globally best string:

* shortest length
* earliest index

---

# Time Complexity

Let:

* `N` = total characters in `wordsContainer`
* `M` = total characters in `wordsQuery`

## Building Trie

```text
O(N)
```

## Queries

```text
O(M)
```

Total:

```text
O(N + M)
```

Very efficient for constraints.

---

# Full Understanding of Query Logic

```java
for (int j = len - 1; j >= 0; j--) {
    int charIdx = query.charAt(j) - 'a';

    if (curr.children[charIdx] == null) {
        break;
    }

    curr = curr.children[charIdx];
}
```

This means:

Keep matching suffix characters.

If path exists:

* suffix still matching

If path breaks:

* longest suffix already found

Then:

```java
ans[i] = curr.bestIdx;
```

The current node is exactly the node representing the longest matched suffix.

---

# Core Concept Summary

The whole solution works because:

## 1. Reverse Strings

Suffix → Prefix

---

## 2. Use Trie

Efficient longest-prefix matching.

---

## 3. Store Best Candidate at Every Node

So query instantly gets:

* shortest word
* earliest index

among all words sharing that suffix.

---

# Visualization

For suffix `"bcd"`:

```text
Original words:
abcd
bcd
xbcd

Reversed:
dcba
dcb
dcbx
```

Trie path:

```text
d -> c -> b
```

Node `"dcb"` means:

```text
suffix = "bcd"
```

That node stores:

```text
bestIdx = 1
```

because `"bcd"` is shortest.
