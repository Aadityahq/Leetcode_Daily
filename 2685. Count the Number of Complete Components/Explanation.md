# 2685. Count the Number of Complete Components

## Intuition

The graph may have multiple **connected components**. We need to check each component separately.

A connected component is **complete** if **every pair of vertices has an edge between them**.

For a component with **k vertices**:

* Every vertex connects to every other vertex.
* Total number of edges must be:

[
\frac{k \times (k-1)}{2}
]

So our job is:

1. Find every connected component using **DFS/BFS**.
2. Count:

   * Number of vertices in the component.
   * Number of edges in the component.
3. Compare the actual number of edges with the expected number.

If they are equal, the component is complete.

---

# Understanding the Problem

Suppose we have

```
0 ---- 1
 \    /
   2

3 ---- 4

5
```

Component 1:

```
0,1,2
```

Vertices = 3

Edges = 3

Possible edges in a complete graph:

```
3 × 2 / 2 = 3
```

So it is complete.

---

Component 2:

```
3,4
```

Vertices = 2

Edges = 1

Expected:

```
2 × 1 /2 =1
```

Complete.

---

Component 3:

```
5
```

Single node.

Expected edges:

```
1 ×0 /2 =0
```

Complete.

Answer = **3**

---

## Example 2

```
3
|\
| \
4  5
```

Edges:

```
3-4
3-5
```

Missing

```
4-5
```

Vertices = 3

Actual edges = 2

Expected =

```
3 ×2 /2 =3
```

Not complete.

---

# Approach

### Step 1

Create an adjacency list.

Example

```
0 -> [1,2]
1 -> [0,2]
2 -> [0,1]
3 -> [4]
4 -> [3]
```

---

### Step 2

Maintain a visited array.

```
visited[i]
```

so each node is processed only once.

---

### Step 3

For every unvisited node

Run DFS.

During DFS collect

* number of nodes
* sum of degrees

---

### Why sum of degrees?

Every edge contributes to the degree of **two vertices**.

Example

```
0---1
```

Degrees

```
0 =1
1 =1

Sum =2
```

Actual edges

```
2 /2 =1
```

So

```
edges = degreeSum /2
```

---

### Step 4

Suppose DFS found

```
nodes = k
edges = e
```

Expected edges

```
k × (k-1)/2
```

If

```
e == expected
```

then increase answer.

---

# Dry Run

```
n = 6

edges =
0-1
0-2
1-2
3-4
```

DFS starting from 0

Visited

```
0
1
2
```

nodes = 3

Degree sum

```
0 ->2
1 ->2
2 ->2

Total =6
```

Actual edges

```
6/2=3
```

Expected

```
3×2/2=3
```

Complete.

Answer =1

---

Next component

```
3
4
```

nodes =2

Degree sum

```
1+1=2
```

Edges

```
1
```

Expected

```
2×1/2=1
```

Complete.

Answer =2

---

Next

```
5
```

nodes =1

Degree sum

```
0
```

Edges

```
0
```

Expected

```
0
```

Complete.

Answer =3

---

# Why does this work?

For a component with **k** nodes:

A complete graph must contain every possible pair.

Number of possible pairs:

```
C(k,2)

= k(k-1)/2
```

If the component contains exactly this many edges, then every pair is connected.

Otherwise, at least one pair is missing.

---

# Java Solution (DFS)

```java
class Solution {

    private int nodes;
    private int degreeSum;

    public int countCompleteComponents(int n, int[][] edges) {

        List<Integer>[] graph = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int[] edge : edges) {
            graph[edge[0]].add(edge[1]);
            graph[edge[1]].add(edge[0]);
        }

        boolean[] visited = new boolean[n];

        int answer = 0;

        for (int i = 0; i < n; i++) {

            if (!visited[i]) {

                nodes = 0;
                degreeSum = 0;

                dfs(i, graph, visited);

                int actualEdges = degreeSum / 2;
                int expectedEdges = nodes * (nodes - 1) / 2;

                if (actualEdges == expectedEdges) {
                    answer++;
                }
            }
        }

        return answer;
    }

    private void dfs(int node, List<Integer>[] graph, boolean[] visited) {

        visited[node] = true;

        nodes++;

        degreeSum += graph[node].size();

        for (int neighbor : graph[node]) {
            if (!visited[neighbor]) {
                dfs(neighbor, graph, visited);
            }
        }
    }
}
```

---

# Time Complexity

Building graph:

```
O(E)
```

DFS:

Each node visited once.

Each edge visited twice.

```
O(V + E)
```

Overall:

```
O(V + E)
```

where:

* **V = n** (number of vertices)
* **E = edges.length**

---

# Space Complexity

Adjacency list:

```
O(V + E)
```

Visited array:

```
O(V)
```

DFS recursion stack:

```
O(V)
```

Overall:

```
O(V + E)
```

---

# Key Takeaways

* A **connected component** is a group of vertices where every vertex is reachable from every other vertex.
* A **complete component** means **every pair of vertices is directly connected by an edge**.
* For a component with **k** vertices, the required number of edges is:
  [
  \frac{k(k-1)}{2}
  ]
* DFS helps us identify each connected component.
* During DFS, count:

  * `nodes` = number of vertices in the component.
  * `degreeSum` = sum of the degrees of all vertices.
* Since each edge is counted twice in the degree sum:

  ```
  actualEdges = degreeSum / 2
  ```
* If:

  ```
  actualEdges == k * (k - 1) / 2
  ```

  then the component is complete.
