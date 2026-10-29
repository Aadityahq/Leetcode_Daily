# LeetCode 3841 — Palindromic Path Queries in a Tree

Let's understand the solution step by step in simple language: what we are doing, how it works, and why we need each technique.

## 1. Understand the problem first

We have a tree. Every node contains one lowercase character.

For example:

## a

Node 0

## a

Node 1

## c

Node 2

Here, `s = "aac"`.

There are two types of operations:

- `query u v`: Check whether the characters on the path from node `u` to node `v` can be rearranged into a palindrome.
- `update u c`: Change the character at node `u` to `c`.

For example, `query 0 2` gives the path characters `aac`.

We can rearrange `aac` into `aca`, which is a palindrome. Therefore, the answer is `true`.

### The most important observation

A string can be rearranged into a palindrome if at most one character has an odd frequency.

Why?

A palindrome reads the same forward and backward. Characters at mirrored positions must form pairs. Only the middle character, if the length is odd, can remain unpaired.

Examples:

| String  | Odd-frequency characters | Answer  |
| ------- | ------------------------ | ------- |
| `aabb`  | None                     | `true`  |
| `aab`   | `b`                      | `true`  |
| `abc`   | `a`, `b`, `c`            | `false` |
| `aabbc` | `c`                      | `true`  |

We now need a fast way to count odd frequencies on a tree path.

## 2. Why not simply traverse the path?

The constraints allow up to 50,000 nodes and 50,000 operations.

Imagine a tree shaped like a long chain:

`0 — 1 — 2 — 3 — 4 — ... — 49999`

A query from node `0` to node `49999` could require visiting almost every node.

If we traverse the path for every query, the total time could reach \\(O(nq)\\), or roughly 2.5 billion node visits.

That is too slow.

Instead, we combine four techniques:

1. Bitmask and XOR to track odd character frequencies.
2. Root path masks to represent characters from the root to any node.
3. Lowest Common Ancestor (LCA) to calculate a path between two nodes.
4. Euler Tour and Fenwick Tree to support updates efficiently.

Let's understand each one.

## 3. Technique 1: Bitmask and XOR

There are only 26 lowercase English letters. We can represent their odd/even frequencies using 26 bits in an integer.

We assign one bit to each character:

| Character | Bit       |
| --------- | --------- |
| `a`       | `1 << 0`  |
| `b`       | `1 << 1`  |
| `c`       | `1 << 2`  |
| `d`       | `1 << 3`  |
| ...       | ...       |
| `z`       | `1 << 25` |

In Java, the mask for a character is:

```
private int charMask(char c) {
    return 1 << (c - 'a');
}
```

For example:

```
charMask('a') // 1
charMask('b') // 2
charMask('c') // 4
```

### Why use XOR (`^`)?

XOR has a useful property:

- `0 ^ 1 = 1`
- `1 ^ 1 = 0`

So, when we XOR the same character's bit twice, it disappears.

Consider `aab`:

First `a`

001

Second `a`

001

`a ^ a`

000

Remaining `b` bit

010

The two `a` characters cancel out. Only `b` remains because it appears an odd number of times.

The final mask is `010`, which has one set bit.

In Java:

```
Integer.bitCount(mask) <= 1
```

This checks whether at most one character has an odd frequency.

Why is this efficient? We do not need to store or count all 26 frequencies separately. XOR tracks their parity in a single integer.

## 4. Technique 2: Root path masks

We root the tree at node `0`.

Define:

`baseMask[u]` = XOR of all character masks on the path from node `0` to node `u`, including both endpoints.

Consider this tree:

0

`a`

1

`b`

Mask: a ^ b

2

`c`

Mask: a ^ c

The root path masks are:

- `baseMask[0] = bit(a)`
- `baseMask[1] = bit(a) ^ bit(b)`
- `baseMask[2] = bit(a) ^ bit(c)`

We calculate these using:

```
baseMask[u] = baseMask[parent[u]] ^ charMask(chars[u]);
```

Why does this work?

The path from the root to a child contains everything on the path to its parent, plus the child's character.

Therefore, we can calculate the child's mask from the parent's mask in constant time.

### How do we calculate a path between two nodes?

Suppose we want `query 1 2`.

The actual path is:

`1 → 0 → 2`

Its characters are `b`, `a`, and `c`.

We can combine the two root path masks:

```
baseMask[1] ^ baseMask[2]
```

Expanding them:

```
(a ^ b) ^ (a ^ c)
```

The two `a` bits cancel out, leaving:

```
b ^ c
```

But we need `a ^ b ^ c`, because node `0` is part of the actual path.

So we must add the character of the Lowest Common Ancestor (LCA).

The general formula is:

\\[ \begin{aligned} \text{pathMask}(u,v)={}& \text{rootMask}[u]\\\ &\oplus\text{rootMask}[v]\\\ &\oplus\text{bit}(s[\text{LCA}(u,v)]) \end{aligned} \\]

## 5. Technique 3: Lowest Common Ancestor (LCA)

The Lowest Common Ancestor of two nodes is the deepest node that is an ancestor of both.

For example:

0

LCA

1

3

2

4

The LCA of nodes 3 and 4 is node 0.

Why do we need LCA?

When two nodes are in different branches, their path goes up to their common ancestor and then down to the other node.

If we know the LCA, the root masks can cancel the unnecessary shared portions.

### How does the code find the LCA?

We use binary lifting.

We store:

- `up[0][u]`: the parent of node `u`.
- `up[1][u]`: the ancestor two levels above.
- `up[2][u]`: the ancestor four levels above.
- `up[k][u]`: the ancestor \\(2^k\\) levels above.

We build the table with:

```
up[k][u] = up[k - 1][up[k - 1][u]];
```

This means: to jump \\(2^k\\) levels, make two jumps of \\(2^{k-1}\\) levels.

When finding the LCA, we:

1. Bring both nodes to the same depth.
2. Jump both nodes upward, starting with the largest jumps.
3. Stop when their parents are the same.

This takes \\(O(\log n)\\) time instead of climbing one edge at a time.

## 6. Technique 4: Euler Tour

So far, we can answer a path query quickly if root masks are correct.

But there is a problem: what happens when a node's character changes?

Suppose we change node `1` from `a` to `b`.

Every root-to-node path passing through node `1` changes. That includes node `1` and every descendant of node `1`.

Updating every descendant individually could take \\(O(n)\\) time per update.

Euler Tour solves this problem by arranging the nodes in DFS order.

The key property is that all nodes in a subtree occupy a contiguous interval in this order.

For example, if node `1` has descendants `3` and `4`, the Euler Tour might be:

| Euler index | Node |
| ----------- | ---- |
| 0           | 0    |
| 1           | 1    |
| 2           | 3    |
| 3           | 4    |
| 4           | 2    |

The subtree of node `1` occupies indices `1` through `3`.

We store:

- `tin[u]`: the first Euler index of node `u`.
- `tout[u]`: the last Euler index in its subtree.

The subtree interval is:

\\[ [\text{tin}[u],\text{tout}[u]] \\]

Why is this useful?

Instead of updating every descendant, we can update one continuous interval in a data structure.

The code calculates the interval using:

```
tout[u] = tin[u] + subtreeSize[u] - 1;
```

This works because the DFS ordering keeps each subtree contiguous.

## 7. Technique 5: Fenwick Tree with XOR updates

Euler Tour gives us a range, but we still need an efficient way to apply updates to that range.

We use a Fenwick Tree (also called a Binary Indexed Tree).

Normally, a Fenwick Tree is often used for sums. Here, we use XOR because our masks are combined using XOR.

### What changes when a character is updated?

Suppose node `u` changes from `a` to `b`.

Calculate:

```
int delta = charMask('a') ^ charMask('b');
```

Why XOR the old and new masks?

The old character must be removed from the parity mask, and the new character must be added. XORing with both achieves exactly that.

We apply `delta` to the entire subtree interval:

```
rangeXor(tin[u], tout[u], delta);
```

The range update is implemented using two Fenwick Tree updates:

```
add(left + 1, delta);
add(right + 2, delta);
```

The Fenwick Tree uses 1-based indices, which is why we add one to the Euler indices.

Later, to obtain the current mask for node `u`, we retrieve the accumulated updates at its entry time:

```
int maskU = baseMask[u] ^ pointQuery(tin[u]);
```

The original mask plus all changes affecting the node gives its current root path mask.

### Why use a range update instead of updating every descendant?

Because a single character update affects an entire subtree. The Fenwick Tree represents the effect of that update with just two boundary updates.

Both the range update and the point query take \\(O(\log n)\\) time.

## 8. Put everything together: Complete query walkthrough

Let's use Example 1.

```
n = 3
edges = [[0,1],[1,2]]
s = "aac"

queries = [
    "query 0 2",
    "update 1 b",
    "query 0 2"
]
```

### Operation 1: `query 0 2`

The path is:

```
0(a) → 1(a) → 2(c)
```

The LCA of nodes `0` and `2` is node `0`.

We calculate:

```
int maskU = baseMask[0] ^ pointQuery(tin[0]);
int maskV = baseMask[2] ^ pointQuery(tin[2]);

int pathMask = maskU
        ^ maskV
        ^ charMask(chars[0]);
```

Initially, there are no updates, so the Fenwick Tree contributes zero.

The resulting mask represents `aac`.

The two `a` bits cancel out, leaving only the `c` bit.

```
Integer.bitCount(pathMask) // 1
```

Since `1 <= 1`, the answer is `true`.

### Operation 2: `update 1 b`

Node `1` changes from `a` to `b`.

```
int delta = charMask('a') ^ charMask('b');
rangeXor(tin[1], tout[1], delta);
chars[1] = 'b';
```

Because node `1` is an ancestor of node `2`, both nodes' root path masks must change.

The range update handles this without individually visiting node `2`.

### Operation 3: `query 0 2`

The updated path is:

```
0(a) → 1(b) → 2(c)
```

The path mask now represents `abc`.

```
Integer.bitCount(pathMask) // 3
```

Since `3 > 1`, the answer is `false`.

Final output:

```
[true, false]
```

## 9. How the entire Java solution works

Here is the overall execution order.

Step 1: Build the tree

Create the adjacency list from `edges`.

Step 2: Run DFS

Calculate parents, depths, Euler Tour intervals and initial root masks.

Step 3: Build binary lifting

Prepare the table needed for fast LCA queries.

Step 4: Process each operation

For `update`, modify the subtree interval. For `query`, calculate the LCA and path mask, then check the number of set bits.

## 10. Complexity analysis

Let \\(n\\) be the number of nodes and \\(q\\) the number of operations.

| Part                       | Time                     |
| -------------------------- | ------------------------ |
| Build tree and perform DFS | \\(O(n)\\)               |
| Build binary lifting table | \\(O(n\log n)\\)         |
| Process one update         | \\(O(\log n)\\)          |
| Process one path query     | \\(O(\log n)\\)          |
| Total                      | \\(O(n\log n+q\log n)\\) |

Space complexity is \\(O(n\log n)\\), primarily because of the binary lifting table.

## 11. Quick revision for interviews

Remember these five points:

- Bitmask: One bit represents each character.
- XOR: Tracks whether each character appears an odd or even number of times.
- LCA: Helps combine two root paths into the required tree path.
- Euler Tour: Converts a subtree into a continuous array interval.
- Fenwick Tree: Supports subtree XOR updates and current root-mask queries efficiently.

The central idea is to avoid traversing the path for every query and avoid updating every descendant whenever a character changes.

Instead, we represent the path using XOR masks and represent the effect of updates using Euler Tour intervals and a Fenwick Tree. That is what makes the solution efficient enough for the given constraints.