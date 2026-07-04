# 2492. Minimum Score of a Path Between Two Cities

## Intuition

The **score of a path** is **not the sum** of distances.

Instead,

> **Score = Minimum edge (road) distance present in that path.**

We need to find **the smallest possible score** among all paths from city **1** to city **n**.

---

## Important Observation

The problem allows us to:

* Visit cities multiple times.
* Use the same road multiple times.

This changes everything.

Suppose city **1** and city **n** belong to the same connected component.

Since revisiting is allowed, we can travel to **any road inside that connected component**, use that road, and then continue to city **n**.

Therefore,

> **The answer is simply the minimum edge distance in the connected component containing city 1.**

We don't need shortest paths like Dijkstra.

We only need to:

1. Find every city connected to city 1.
2. Among every road inside that connected component, return the minimum distance.

---

# Approach (DFS/BFS)

### Step 1

Build an adjacency list.

```
1 ----9----2
|           |
7           5
|           |
4-----------

```

Store every road for both directions.

---

### Step 2

Run DFS (or BFS) starting from city 1.

Mark every reachable city.

---

### Step 3

While visiting edges,

keep updating

```
answer = min(answer, edgeDistance)
```

At the end,

`answer` is the minimum score.

---

# Example 1

```
n = 4

1 --9-- 2
|       | \
7       6  5
|       |   \
4-------3
```

DFS visits

```
1
↓
2
↓
3
↓
4
```

Roads seen:

```
9
7
6
5
```

Minimum

```
min = 5
```

Answer

```
5
```

---

# Example 2

```
1 --2-- 2

|
4

3 --7-- 4
```

Normally,

```
1 -> 3 -> 4

score = 4
```

But revisiting is allowed.

Take path

```
1 → 2 → 1 → 3 → 4
```

Roads

```
2
2
4
7
```

Minimum

```
2
```

Hence answer is

```
2
```

---

# Algorithm

1. Build graph.
2. DFS from city 1.
3. Visit every connected city.
4. For every road visited

```
answer = min(answer, distance)
```

5. Return answer.

---

# Why does this work?

Suppose the connected component of city 1 contains this edge:

```
A -----3------ B
```

Even if this edge is not on a direct path to city n,

since revisiting is allowed,

we can

```
1
↓

...
↓

A

↓

B

↓

...

↓

n
```

Thus every edge in the connected component can be included in some valid path.

Therefore the smallest edge in that component is always achievable.

---

# Time Complexity

Building graph:

```
O(E)
```

DFS:

```
O(V + E)
```

Overall

```
O(V + E)
```

where

* V = number of cities
* E = number of roads

---

# Space Complexity

Adjacency list

```
O(V + E)
```

Visited array

```
O(V)
```

Total

```
O(V + E)
```

---

# Java Solution (DFS)

```java
class Solution {

    int answer = Integer.MAX_VALUE;

    public int minScore(int n, int[][] roads) {

        List<int[]>[] graph = new ArrayList[n + 1];

        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int[] road : roads) {
            int u = road[0];
            int v = road[1];
            int d = road[2];

            graph[u].add(new int[]{v, d});
            graph[v].add(new int[]{u, d});
        }

        boolean[] visited = new boolean[n + 1];

        dfs(1, graph, visited);

        return answer;
    }

    private void dfs(int city, List<int[]>[] graph, boolean[] visited) {

        visited[city] = true;

        for (int[] next : graph[city]) {

            int neighbor = next[0];
            int distance = next[1];

            answer = Math.min(answer, distance);

            if (!visited[neighbor]) {
                dfs(neighbor, graph, visited);
            }
        }
    }
}
```

---

# Dry Run

Input

```text
n = 4

roads =

[1,2,9]
[2,3,6]
[2,4,5]
[1,4,7]
```

### Build Graph

```
1 → (2,9), (4,7)

2 → (1,9), (3,6), (4,5)

3 → (2,6)

4 → (2,5), (1,7)
```

---

### DFS starts from 1

Visit

```
1
```

Edges seen

```
9
7
```

Minimum

```
7
```

---

Go to

```
2
```

Edges

```
9
6
5
```

Minimum

```
5
```

---

Visit

```
3
```

Edge

```
6
```

Minimum remains

```
5
```

---

Visit

```
4
```

Edges

```
5
7
```

Minimum remains

```
5
```

DFS finishes.

Return

```
5
```

---

## Key Takeaway

The trick in this problem is recognizing that **because roads and cities may be revisited**, you are **not constrained to a simple path**. That means **any edge in the connected component containing city 1 can be included in a valid path from 1 to n**, so the answer is simply **the minimum edge weight in that connected component**, which can be found efficiently using a single DFS or BFS.
