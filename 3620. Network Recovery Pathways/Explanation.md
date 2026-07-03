# 3620. Network Recovery Pathways (Hard)

## Intuition

The problem asks us to find a path from node `0` to node `n-1` such that:

* Every **intermediate node** is online.
* The **total cost** of the path is at most `k`.
* The **score** of a path is the **minimum edge cost** on that path.

Our goal is to **maximize this score**.

---

## Understanding the Problem

Suppose we have the following graph:

```text
        5
   0 --------> 1
   |           |
 3 |           |10
   |           |
   v           v
   2 --------> 3
        4
```

Budget

```text
k = 10
```

There are two paths.

### Path 1

```text
0 → 1 → 3
```

Total Cost

```text
5 + 10 = 15
```

Since

```text
15 > 10
```

this path is **invalid**.

---

### Path 2

```text
0 → 2 → 3
```

Total Cost

```text
3 + 4 = 7
```

This is within the budget.

Its score is

```text
min(3,4)=3
```

Since it is the only valid path,

Answer = **3**

---

# Important Observation

The score of a path depends on the **smallest edge** present in that path.

Suppose we want the answer to be at least **6**.

That means

> Every edge on the chosen path must have cost **≥ 6**.

Any edge smaller than 6 immediately makes the path's score less than 6.

So while checking a value `6`, we simply ignore every edge having cost less than `6`.

Now the question becomes

> Is there still a valid path whose total cost is at most `k`?

This transforms the problem into a **YES / NO** question.

---

# Why Binary Search?

Assume

```text
Score = 6
```

is possible.

Then scores

```text
5
4
3
2
1
```

will also be possible because allowing smaller minimum edges only gives us **more available edges**.

This creates a monotonic property.

```text
Possible scores

0 1 2 3 4 5 6 7 8

Y Y Y Y Y Y Y N N
```

Since the answers change only once from **Yes → No**, Binary Search can be applied.

---

# Overall Algorithm

1. Build the graph.
2. Compute a topological ordering because the graph is a DAG.
3. Binary search on the minimum edge value.
4. For every candidate value:

   * Ignore all edges smaller than it.
   * Ignore offline intermediate nodes.
   * Find the minimum total cost using DP on the DAG.
5. If the destination is reachable within budget `k`, the candidate is feasible.

---

# Step 1: Build the Graph

We store every edge in an adjacency list.

```java
List<List<int[]>> adj = new ArrayList<>();

for (int i = 0; i < n; i++) {
    adj.add(new ArrayList<>());
}

for (int[] e : edges) {
    adj.get(e[0]).add(new int[]{e[1], e[2]});
}
```

Each edge stores

```text
destination
cost
```

---

# Step 2: Topological Sort

Since the graph is a **Directed Acyclic Graph**, we can process nodes in topological order.

The code computes indegree first.

```java
int[] indegree = new int[n];
```

Then all nodes having indegree 0 are inserted into the queue.

```java
Queue<Integer> q = new LinkedList<>();
```

Using Kahn's Algorithm,

```java
while (!q.isEmpty()) {
    int node = q.poll();
    topoSort.add(node);

    ...
}
```

After this,

```text
topoSort
```

contains nodes in an order where every parent appears before its children.

This property allows shortest path DP.

---

# Step 3: Binary Search

We binary search on the minimum edge value.

```java
while (low <= high) {

    int mid = low + (high - low) / 2;

    if (find(mid))
        low = mid + 1;
    else
        high = mid - 1;
}
```

If

```text
mid
```

works,

we try larger values.

Otherwise,

we try smaller ones.

---

# Step 4: Checking a Candidate

The function

```java
find(limit,...)
```

checks whether a path exists whose

* minimum edge ≥ limit
* total cost ≤ k

---

## Distance Array

```java
long[] dist = new long[n];
Arrays.fill(dist, Long.MAX_VALUE);

dist[0] = 0;
```

`dist[i]`

stores

> Minimum total cost required to reach node `i`.

Initially,

only source has distance 0.

---

## Processing in Topological Order

```java
for (int node : topoSort)
```

Since parents always come first,

their shortest distance is already known.

---

## Ignore Unreachable Nodes

```java
if (dist[node] == Long.MAX_VALUE)
    continue;
```

No need to process them.

---

## Ignore Small Edges

```java
if (cst < limit)
    continue;
```

Those edges would reduce the minimum edge below the required score.

---

## Ignore Offline Intermediate Nodes

```java
if (nxtNode != n - 1 && !online[nxtNode])
    continue;
```

The destination is always allowed.

Every other offline node is skipped.

---

## Relax the Edge

```java
dist[nxtNode] =
Math.min(dist[nxtNode],
dist[node] + cst);
```

Exactly like shortest path DP on a DAG.

---

## Final Check

```java
return dist[n-1] <= k;
```

If destination can be reached within budget,

then this score is feasible.

---

# Dry Run

Suppose

```text
Edges

0→1 (7)
1→4 (5)
0→2 (6)
2→4 (6)

k=12
```

Binary Search

Suppose

```text
mid = 6
```

Allowed edges

```text
0→1 (7)

0→2 (6)

2→4 (6)
```

Edge

```text
1→4 (5)
```

is removed.

Distances

Initially

```text
0 = 0

others = INF
```

Process node 0

```text
dist[1]=7

dist[2]=6
```

Process node 2

```text
dist[4]=12
```

Destination

```text
12<=12
```

Possible.

Now Binary Search tries a larger value.

---

# Why Topological Sort Instead of Dijkstra?

The graph is guaranteed to be a DAG.

Shortest path in a DAG can be computed in

```text
O(V+E)
```

using DP.

Dijkstra would require

```text
O(E log V)
```

Topological DP is simpler and faster.

---

# Correctness

The algorithm is correct because:

* Binary search checks every possible minimum edge value.
* During each check, every edge with cost smaller than the candidate is ignored, ensuring every remaining path has a minimum edge at least equal to the candidate.
* Offline intermediate nodes are skipped, guaranteeing only valid paths are considered.
* Since the graph is a DAG, processing nodes in topological order correctly computes the minimum cost to every node.
* If the destination can be reached with total cost ≤ `k`, then the candidate score is feasible.
* Binary search returns the largest feasible candidate, which is exactly the maximum possible path score.

---

# Complexity Analysis

Let

* `n` = number of nodes
* `m` = number of edges

### Graph Construction

```text
O(n + m)
```

### Topological Sort

```text
O(n + m)
```

### Each Binary Search Check

We traverse every node and every edge once.

```text
O(n + m)
```

### Binary Search

The search range is from the minimum edge cost to the maximum edge cost.

```text
log₂(10^9) ≈ 30
```

### Total Time Complexity

```text
O((n + m) × log(10^9))
```

Approximately

```text
30 × (n + m)
```

which easily fits the constraints.

### Space Complexity

Adjacency list, indegree array, topological order, and distance array require:

```text
O(n + m)
```

---

## Key Techniques Used

* **Graph (Adjacency List)**
* **Topological Sort (Kahn's Algorithm)**
* **Dynamic Programming on DAG (Shortest Path)**
* **Binary Search on the Answer**

This combination efficiently finds the **maximum possible minimum edge cost** while ensuring the path stays within the given budget and avoids offline intermediate nodes.
