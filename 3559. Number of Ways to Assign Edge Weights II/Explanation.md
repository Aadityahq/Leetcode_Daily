### Intuition

For any path of length **L** edges:

* Each edge can be assigned either **1** or **2**.
* Total assignments = **2ᴸ**.
* We need the **path cost to be odd**.

Notice:

* Weight **1** is odd.
* Weight **2** is even.

The sum of weights is odd **iff the number of edges assigned weight 1 is odd**.

For L edges:

* Exactly half of all assignments produce an odd sum.
* Exactly half produce an even sum.

Therefore:

* If **L = 0** (same node), answer = **0**.
* Otherwise answer = **2ᴸ⁻¹**.

So the problem reduces to finding the **number of edges in the path between u and v**.

---

### Key Observation

Path length between two nodes:

[
L = depth[u] + depth[v] - 2 \times depth[lca(u,v)]
]

where **lca(u,v)** is the Lowest Common Ancestor.

Thus:

* Find depths using DFS/BFS.
* Build Binary Lifting table for LCA.
* For each query:

  * Compute path length L.
  * If L = 0 → answer = 0.
  * Else answer = (2^{L-1}) mod (10^9+7).

---

## Example

Tree:

```
    1
   / \
  2   3
     / \
    4   5
```

Query: `(2,5)`

Path:

```
2 → 1 → 3 → 5
```

Length = 3 edges.

Total assignments:

[
2^3 = 8
]

Half give odd sum:

[
8/2 = 4
]

Answer:

[
2^{3-1}=4
]

---

## Java Solution

```java
class Solution {
    private static final int MOD = 1_000_000_007;
    private int LOG;
    private int[][] up;
    private int[] depth;
    private List<Integer>[] graph;
    private long[] pow2;

    public int[] assignEdgeWeights(int[][] edges, int[][] queries) {
        int n = edges.length + 1;

        graph = new ArrayList[n + 1];
        for (int i = 0; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int[] e : edges) {
            int u = e[0];
            int v = e[1];
            graph[u].add(v);
            graph[v].add(u);
        }

        LOG = 1;
        while ((1 << LOG) <= n) LOG++;

        up = new int[n + 1][LOG];
        depth = new int[n + 1];

        dfs(1, 0);

        for (int j = 1; j < LOG; j++) {
            for (int i = 1; i <= n; i++) {
                up[i][j] = up[up[i][j - 1]][j - 1];
            }
        }

        pow2 = new long[n];
        pow2[0] = 1;

        for (int i = 1; i < n; i++) {
            pow2[i] = (pow2[i - 1] * 2) % MOD;
        }

        int m = queries.length;
        int[] ans = new int[m];

        for (int i = 0; i < m; i++) {
            int u = queries[i][0];
            int v = queries[i][1];

            int lca = lca(u, v);
            int len = depth[u] + depth[v] - 2 * depth[lca];

            if (len == 0) {
                ans[i] = 0;
            } else {
                ans[i] = (int) pow2[len - 1];
            }
        }

        return ans;
    }

    private void dfs(int node, int parent) {
        up[node][0] = parent;

        for (int nei : graph[node]) {
            if (nei == parent) continue;

            depth[nei] = depth[node] + 1;
            dfs(nei, node);
        }
    }

    private int lca(int u, int v) {
        if (depth[u] < depth[v]) {
            int temp = u;
            u = v;
            v = temp;
        }

        int diff = depth[u] - depth[v];

        for (int j = LOG - 1; j >= 0; j--) {
            if ((diff & (1 << j)) != 0) {
                u = up[u][j];
            }
        }

        if (u == v) return u;

        for (int j = LOG - 1; j >= 0; j--) {
            if (up[u][j] != up[v][j]) {
                u = up[u][j];
                v = up[v][j];
            }
        }

        return up[u][0];
    }
}
```

---

## Complexity Analysis

### Preprocessing

* Building graph: **O(n)**
* DFS: **O(n)**
* Binary Lifting table: **O(n log n)**

### Per Query

* LCA computation: **O(log n)**
* Answer calculation: **O(1)**

### Total

[
O(n \log n + q \log n)
]

where:

* (n \le 10^5)
* (q \le 10^5)

This easily fits within the constraints.
