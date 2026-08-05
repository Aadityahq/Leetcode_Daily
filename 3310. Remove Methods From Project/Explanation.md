



## Intuition

We are given a **directed graph** where:

- Each node = a method.
- An edge `a → b` means **method `a` invokes method `b`**.

There is a bug in method `k`.

Any method reachable from `k` is also suspicious because the bug can propagate through method calls.

However, **we can remove the suspicious methods only if no non-suspicious method calls any suspicious method.**

If even one outside method invokes a suspicious method, removing them would break the project, so **nothing can be removed**.

---

# Step 1: Find all suspicious methods

Since every method reachable from `k` is suspicious, this is simply a graph traversal problem.

Use **DFS/BFS** starting from `k`.

Example

```
0 → 1 → 2
     ↓
     3

k = 1
```

Starting from `1`

```
1
├──2
└──3
```

Suspicious methods

```
{1,2,3}
```

---

# Step 2: Check whether removal is possible

Now look at every invocation.

Suppose

```
0 → 1
```

If

```
0 = not suspicious
1 = suspicious
```

then

```
outside group
      |
      V
0 --->1
```

Removing `1` is impossible because method `0` still needs it.

So if there exists any edge

```
non-suspicious ----> suspicious
```

return all methods.

---

# Step 3: Otherwise remove them

If no outside method invokes any suspicious method, simply return every non-suspicious node.

---

# Example 1

```
n = 4

1→2
0→1
3→2

k=1
```

Graph

```
0 --->1 --->2
      ^
      |
3 -----|
```

DFS from 1

```
Suspicious

1
2
```

Now inspect edges.

```
0→1
```

0 is outside.

1 is suspicious.

Invalid.

Cannot remove.

Answer

```
[0,1,2,3]
```

---

# Example 2

```
0→1
0→2
1→2
3→4

k=0
```

Suspicious

```
0
1
2
```

Outside methods

```
3
4
```

No edge

```
3→0
3→1
3→2
4→...
```

Therefore remove them.

Remaining

```
3
4
```

---

# Example 3

```
0→1
1→2
2→0

k=2
```

DFS reaches

```
2
0
1
```

All methods are suspicious.

No outside node exists.

Remove all.

Answer

```
[]
```

---

# Algorithm

### Build graph

```
adj[a].add(b)
```

---

### DFS/BFS from k

```
visited[k]=true

visit every reachable node
```

Visited means suspicious.

---

### Check every edge

For every

```
u→v
```

if

```
u not suspicious
AND
v suspicious
```

return

```
0...n-1
```

---

### Otherwise

Return every node not marked suspicious.

---

# Correctness Proof

Let **S** be the set of methods reachable from `k`.

### Lemma 1

Every suspicious method belongs to `S`.

**Proof**

By definition, a suspicious method is either `k` or is invoked directly/indirectly from `k`.

DFS/BFS exactly visits all reachable vertices.

Therefore all suspicious methods are marked.

---

### Lemma 2

If an edge exists from outside `S` to inside `S`, then removing `S` is impossible.

**Proof**

Removing `S` deletes the destination of that invocation.

A remaining method would invoke a deleted method.

This violates the requirement.

Hence removal is impossible.

---

### Lemma 3

If no such edge exists, removing `S` is valid.

**Proof**

Every invocation into `S` originates inside `S`.

All such methods are removed together.

Remaining methods never invoke removed methods.

Therefore removal is valid.

---

Since the algorithm checks exactly this condition, it always returns the correct answer.

---

# Complexity Analysis

Let

- `V = n`
- `E = invocations.length`

DFS

```
O(V + E)
```

Checking edges

```
O(E)
```

Building answer

```
O(V)
```

Total

```
Time : O(V + E)

Space : O(V + E)
```

---

# Java Solution

```java
class Solution {
    public List<Integer> remainingMethods(int n, int k, int[][] invocations) {

        // Build adjacency list
        List<Integer>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int[] edge : invocations) {
            graph[edge[0]].add(edge[1]);
        }

        // Find all suspicious methods
        boolean[] suspicious = new boolean[n];
        dfs(k, graph, suspicious);

        // Check if any outside method invokes a suspicious method
        for (int[] edge : invocations) {
            int from = edge[0];
            int to = edge[1];

            if (!suspicious[from] && suspicious[to]) {
                List<Integer> ans = new ArrayList<>();
                for (int i = 0; i < n; i++) {
                    ans.add(i);
                }
                return ans;
            }
        }

        // Return remaining methods
        List<Integer> ans = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (!suspicious[i]) {
                ans.add(i);
            }
        }

        return ans;
    }

    private void dfs(int node, List<Integer>[] graph, boolean[] suspicious) {
        if (suspicious[node]) return;

        suspicious[node] = true;

        for (int next : graph[node]) {
            dfs(next, graph, suspicious);
        }
    }
}
```

## Why DFS Works

DFS starts from the buggy method `k` and recursively visits every method that `k` can invoke (directly or indirectly). Since the problem defines **all reachable methods from `k` as suspicious**, DFS naturally identifies exactly this set.

After identifying the suspicious set:

- If there is **any edge from a non-suspicious method to a suspicious method**, removing the suspicious methods would leave a remaining method calling a deleted one, so we **return all methods** (remove nothing).
- Otherwise, the suspicious methods form a self-contained group with no incoming calls from outside, so they can safely be removed, and we return all non-suspicious methods.

This leads to an optimal **O(n + m)** solution, where `m` is the number of invocations.