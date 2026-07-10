## Intuition

The graph is **not given explicitly**.

An edge exists between two nodes if:

[
|nums[i] - nums[j]| \le maxDiff
]

If we build all edges directly, the graph could contain **O(n²)** edges, which is impossible for (n = 10^5).

So we need to exploit the property of the values.

---

# Key Observation

Suppose we sort the nodes by their values.

Example:

```
nums = [5,3,1,9,10]

Sorted by value

value : 1  3  5  9 10
node  : 2  1  0  3  4
```

### Observation 1

If two **adjacent values** differ by more than `maxDiff`, then no node on the left can ever connect to any node on the right.

Example

```
1   3   8
    gap=5 (> maxDiff)
```

Since everything is sorted,

```
left values <=3
right values >=8

difference >=5
```

No edge can cross this gap.

So these become **different connected components**.

---

### Observation 2

Inside one connected component, we always want to jump as **far as possible**.

Suppose

```
values

1 2 3 4 5 6 7
```

and

```
maxDiff = 2
```

From value `1`, we can directly reach

```
2
3
```

Obviously going to `3` is never worse than going to `2`.

This greedy idea is always optimal.

---

# Greedy Transition

For every sorted position `i`, compute

```
next[i]
```

= farthest index reachable directly from `i`.

Example

```
values

1 2 3 5 6
maxDiff=2

next

0 ->2
1 ->3
2 ->4
...
```

We compute this using a two-pointer in **O(n)**.

---

# Answering Queries

For each query

```
u -> v
```

1. Convert original nodes into their positions in sorted order.
2. If they belong to different connected components

```
answer = -1
```

3. Otherwise, greedily jump using `next`.

To make jumping fast, use **Binary Lifting**.

Exactly like Jump Game or LCA.

---

# Algorithm

### Step 1

Sort nodes by value.

```
(value,node)
```

---

### Step 2

Build

```
rank[node]
```

which tells where every node lies in sorted order.

---

### Step 3

Build connected component ids.

Whenever adjacent sorted values differ by more than `maxDiff`, start a new component.

---

### Step 4

Using two pointers compute

```
next[i]
```

=farthest directly reachable sorted index.

---

### Step 5

Binary lifting.

```
up[k][i]

= position after 2^k greedy jumps
```

---

### Step 6

For every query

```
a = rank[u]
b = rank[v]

swap so a <= b
```

If different components

```
-1
```

Otherwise binary lift until reaching `b`.

---

# Correctness Proof

### Lemma 1

If two adjacent sorted values differ by more than `maxDiff`, no path can cross this gap.

**Proof**

Every value on the left is ≤ left value.

Every value on the right is ≥ right value.

Therefore every cross difference is larger than `maxDiff`, so no edge exists across the gap. ∎

---

### Lemma 2

Among all neighbors reachable in one step, jumping to the farthest reachable value is always optimal.

**Proof**

The farthest reachable node can reach every node that any closer reachable node can eventually reach (or farther), because values are sorted.

Stopping earlier never creates a shorter path.

Hence choosing the farthest neighbor is optimal. ∎

---

### Lemma 3

Binary lifting computes exactly the minimum number of greedy jumps.

**Proof**

Binary lifting only compresses repeated greedy jumps.

It never changes the sequence of jumps.

Therefore it returns the same minimum number of jumps in logarithmic time. ∎

---

### Theorem

The algorithm returns the shortest path length for every query.

**Proof**

By Lemma 1 we correctly detect unreachable queries.

By Lemma 2 greedy jumps are optimal.

By Lemma 3 binary lifting computes the minimum number of those jumps efficiently.

Hence every returned answer equals the shortest path distance. ∎

---

# Complexity

Sorting:

```
O(n log n)
```

Two pointers:

```
O(n)
```

Binary lifting:

```
O(n log n)
```

Each query:

```
O(log n)
```

Total:

```
O((n+q) log n)
```

Space:

```
O(n log n)
```

---

# Java Solution

```java
import java.util.*;

class Solution {
    public int[] pathExistenceQueries(int n, int[] nums, int maxDiff, int[][] queries) {

        Integer[] order = new Integer[n];
        for (int i = 0; i < n; i++) order[i] = i;

        Arrays.sort(order, (a, b) -> {
            if (nums[a] != nums[b]) return nums[a] - nums[b];
            return a - b;
        });

        int[] value = new int[n];
        int[] rank = new int[n];

        for (int i = 0; i < n; i++) {
            value[i] = nums[order[i]];
            rank[order[i]] = i;
        }

        // Connected components
        int[] comp = new int[n];
        int id = 0;
        comp[0] = 0;

        for (int i = 1; i < n; i++) {
            if (value[i] - value[i - 1] > maxDiff) id++;
            comp[i] = id;
        }

        // next[i] = farthest reachable index in one edge
        int[] next = new int[n];
        int j = 0;

        for (int i = 0; i < n; i++) {
            while (j + 1 < n && value[j + 1] - value[i] <= maxDiff) {
                j++;
            }
            next[i] = j;
        }

        int LOG = 18; // since 2^17 > 1e5
        int[][] up = new int[LOG][n];

        for (int i = 0; i < n; i++) up[0][i] = next[i];

        for (int k = 1; k < LOG; k++) {
            for (int i = 0; i < n; i++) {
                up[k][i] = up[k - 1][up[k - 1][i]];
            }
        }

        int[] ans = new int[queries.length];

        for (int qi = 0; qi < queries.length; qi++) {

            int a = rank[queries[qi][0]];
            int b = rank[queries[qi][1]];

            if (a > b) {
                int t = a;
                a = b;
                b = t;
            }

            if (comp[a] != comp[b]) {
                ans[qi] = -1;
                continue;
            }

            if (a == b) {
                ans[qi] = 0;
                continue;
            }

            int cur = a;
            int jumps = 0;

            for (int k = LOG - 1; k >= 0; k--) {
                if (up[k][cur] < b) {
                    cur = up[k][cur];
                    jumps += 1 << k;
                }
            }

            ans[qi] = jumps + 1;
        }

        return ans;
    }
}
```

This approach is optimal for the given constraints (`n, queries ≤ 10^5`) because it avoids constructing the (O(n^2)) graph and answers each query in (O(\log n)).
