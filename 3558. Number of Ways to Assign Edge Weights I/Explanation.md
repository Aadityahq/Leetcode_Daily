## Intuition

We only care about the path from the root node `1` to **any node at maximum depth**.

Suppose the selected deepest node is `x`.

Let:

* `d` = number of edges in the path `1 → x`

Each edge can be assigned either:

* weight `1` (odd)
* weight `2` (even)

We need the **sum of weights on the path to be odd**.

---

## Key Observation

A sum is odd **iff** it contains an odd number of odd values.

Here:

* Weight `1` = odd
* Weight `2` = even

So the path cost is odd when the number of edges assigned weight `1` is odd.

---

### Example

Path length = 2

Possible assignments:

| Edge 1 | Edge 2 | Sum       |
| ------ | ------ | --------- |
| 1      | 1      | 2 (even)  |
| 1      | 2      | 3 (odd) ✅ |
| 2      | 1      | 3 (odd) ✅ |
| 2      | 2      | 4 (even)  |

Answer = 2.

---

## Counting Directly

For a path containing `d` edges:

Each edge has 2 choices.

Total assignments:

[
2^d
]

Among them:

* Half produce an odd sum.
* Half produce an even sum.

Why?

Take any assignment and flip the first edge:

* If it was 1 → make it 2
* If it was 2 → make it 1

This changes parity of the total sum.

Thus every odd assignment pairs with exactly one even assignment.

Therefore:

[
\text{odd assignments}=\frac{2^d}{2}=2^{d-1}
]

So we only need the maximum depth `d`.

---

## Algorithm

1. Build tree using adjacency list.
2. DFS/BFS from node `1`.
3. Find maximum depth (`d`).
4. Answer =

[
2^{d-1} \pmod{10^9+7}
]

---

## Why Maximum Depth Only?

The statement says:

> Select **any one node x at the maximum depth**.

All deepest nodes have the same depth.

Therefore every valid choice gives the same path length `d`.

Hence answer depends only on maximum depth.

---

## Complexity

* Building graph: **O(n)**
* DFS/BFS: **O(n)**
* Fast exponentiation: **O(log n)**

Overall:

[
O(n)
]

Space:

[
O(n)
]

---

## Java Solution

```java
class Solution {
    private static final long MOD = 1_000_000_007L;

    public int assignEdgeWeights(int[][] edges) {
        int n = edges.length + 1;

        List<Integer>[] graph = new ArrayList[n + 1];
        for (int i = 0; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int[] e : edges) {
            int u = e[0];
            int v = e[1];

            graph[u].add(v);
            graph[v].add(u);
        }

        int maxDepth = 0;

        Queue<int[]> q = new LinkedList<>();
        boolean[] visited = new boolean[n + 1];

        q.offer(new int[]{1, 0}); // node, depth
        visited[1] = true;

        while (!q.isEmpty()) {
            int[] cur = q.poll();

            int node = cur[0];
            int depth = cur[1];

            maxDepth = Math.max(maxDepth, depth);

            for (int nei : graph[node]) {
                if (!visited[nei]) {
                    visited[nei] = true;
                    q.offer(new int[]{nei, depth + 1});
                }
            }
        }

        return (int) modPow(2, maxDepth - 1);
    }

    private long modPow(long base, long exp) {
        long result = 1;

        while (exp > 0) {
            if ((exp & 1) == 1) {
                result = (result * base) % MOD;
            }

            base = (base * base) % MOD;
            exp >>= 1;
        }

        return result;
    }
}
```

### Mathematical Shortcut

If maximum depth is `d`:

[
\boxed{\text{Answer} = 2^{d-1} \pmod{10^9+7}}
]

So the entire problem reduces to finding the **maximum depth of the tree**.
