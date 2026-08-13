### 2213. Longest Substring of One Repeating Character

### Understanding the Problem

You have a string `s`.

For every query:

* change `s[index]` to a new character,

* then find the length of the longest contiguous substring made of only one repeating character.

### Example

Initial string:

```
babacc
```

Query:

```
index = 1, char = 'b'
```

New string:

```
bbbacc
```

The longest repeating substring is:

```
bbb
```

Length = 3.

### Why a Naive Solution Fails

After each update, we could scan the whole string and compute the answer.

* One scan = O(n)

* Up to 10⁵ queries

Total:

```
O(n × k) = 10^10
```

This is far too slow.

### Key Observation

For any segment of the string, we need to know:

* left character

* right character

* longest repeating prefix

* longest repeating suffix

* maximum repeating substring inside the segment

These values can be merged from two child segments.

That is exactly what a Segment Tree is designed for.

### Segment Tree Information

For each node representing `[l, r]`:

| Field     | Meaning                                       |
| --------- | --------------------------------------------- |
| leftChar  | first character in the segment                |
| rightChar | last character in the segment                 |
| prefix    | longest repeating substring starting from l   |
| suffix    | longest repeating substring ending at r       |
| best      | longest repeating substring anywhere in [l,r] |
| len       | segment length                                |

### How Two Segments Are Merged

Suppose:

```
Left  = [aaaaab]
Right = [bbbccc]
```

The boundary characters are:

```
Left.rightChar = 'b'
Right.leftChar = 'b'
```

So a repeating substring can cross the middle.

The crossing length is:

```
left.suffix + right.prefix
```

Then:

```
best = max(left.best,
           right.best,
           crossing)
```

### Java Solution

Java

```
class Solution {

    class Node {
        int prefix, suffix, best, len;
        char leftChar, rightChar;
    }

    private Node[] tree;
    private char[] arr;

    public int[] longestRepeating(String s, String queryCharacters, int[] queryIndices) {
        int n = s.length();
        arr = s.toCharArray();

        tree = new Node[4 * n];
        build(1, 0, n - 1);

        int k = queryIndices.length;
        int[] ans = new int[k];

        for (int i = 0; i < k; i++) {
            update(1, 0, n - 1, queryIndices[i], queryCharacters.charAt(i));
            ans[i] = tree[1].best;
        }

        return ans;
    }

    private void build(int idx, int l, int r) {
        tree[idx] = new Node();

        if (l == r) {
            tree[idx].prefix = 1;
            tree[idx].suffix = 1;
            tree[idx].best = 1;
            tree[idx].len = 1;
            tree[idx].leftChar = arr[l];
            tree[idx].rightChar = arr[l];
            return;
        }

        int mid = (l + r) / 2;

        build(idx * 2, l, mid);
        build(idx * 2 + 1, mid + 1, r);

        tree[idx] = merge(tree[idx * 2], tree[idx * 2 + 1]);
    }

    private void update(int idx, int l, int r, int pos, char ch) {
        if (l == r) {
            arr[pos] = ch;

            tree[idx].prefix = 1;
            tree[idx].suffix = 1;
            tree[idx].best = 1;
            tree[idx].leftChar = ch;
            tree[idx].rightChar = ch;
            return;
        }

        int mid = (l + r) / 2;

        if (pos <= mid) {
            update(idx * 2, l, mid, pos, ch);
        } else {
            update(idx * 2 + 1, mid + 1, r, pos, ch);
        }

        tree[idx] = merge(tree[idx * 2], tree[idx * 2 + 1]);
    }

    private Node merge(Node a, Node b) {
        Node res = new Node();

        res.len = a.len + b.len;
        res.leftChar = a.leftChar;
        res.rightChar = b.rightChar;

        // Prefix
        res.prefix = a.prefix;
        if (a.prefix == a.len && a.rightChar == b.leftChar) {
            res.prefix = a.len + b.prefix;
        }

        // Suffix
        res.suffix = b.suffix;
        if (b.suffix == b.len && a.rightChar == b.leftChar) {
            res.suffix = b.len + a.suffix;
        }

        // Best inside
        res.best = Math.max(a.best, b.best);

        // Crossing middle
        if (a.rightChar == b.leftChar) {
            res.best = Math.max(res.best, a.suffix + b.prefix);
        }

        return res;
    }
}
```

### Step-by-Step Example

### Input

```
s = "babacc"
```

### Build Tree

Leaf nodes:

```
b a b a c c
```

Each has:

```
prefix = suffix = best = 1
```

### Query 1

```
update(1, 'b')
```

String becomes:

```
bbbacc
```

When merging:

```
bb + b  -> crossing = 3
```

Root node stores:

```
best = 3
```

Answer = 3.

### Why This Works

The segment tree always keeps enough information to answer:

“What is the longest repeating-character substring in this segment?”

Because every segment knows:

* its longest prefix run,

* its longest suffix run,

* its internal best run.

Any repeating substring must be:

* entirely in the left child,

* entirely in the right child, or

* cross the boundary between them.

The merge operation checks all three possibilities, so the result is always correct.

### Complexity Analysis

### Build

```
O(n)
```

### Each Update

A segment tree update touches only one path from root to leaf:

```
O(log n)
```

### Total

For `k` queries:

```
O(n + k log n)
```

With `n, k ≤ 100000`, this easily fits the limits.

### Intuition to Remember

Think of each segment as storing three important runs:

```
[ prefix .... best .... suffix ]
```

When two neighboring segments are joined:

```
left.suffix + right.prefix
```

may create a longer run across the middle.

That single idea is the heart of the entire solution.

This is the standard optimal solution using a Segment Tree with custom node information for LeetCode 2213 – Longest Substring of One Repeating Character.
