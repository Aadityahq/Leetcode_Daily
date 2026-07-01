# 2812. Find the Safest Path in a Grid (Java)

## Problem Understanding

You are given an **n × n** grid.

* `1` = Thief
* `0` = Empty cell

You start from **(0,0)** and want to reach **(n−1,n−1)**.

You can move in **4 directions**.

### Safeness Factor

For every cell in a path, find its **distance to the nearest thief**.

The **minimum** of those distances is called the **safeness factor** of that path.

Your goal is to **maximize** this minimum value.

---

## Example

```
0 0 1
0 0 0
0 0 0
```

Distance of every cell from nearest thief:

```
2 1 0
3 2 1
4 3 2
```

Possible path

```
(0,0)
↓
↓
→
→
```

Distances along path

```
2 → 3 → 4 → 3 → 2
```

Minimum = **2**

Answer = **2**

---

# Observation

The problem has **two parts**.

## Part 1

For every cell,

calculate

> Distance to nearest thief.

This is a **multi-source BFS** problem.

Why?

Because all thieves are starting points simultaneously.

---

## Part 2

Now every cell has a value.

Example

```
2 1 0
3 2 1
4 3 2
```

Need a path from

```
Start → End
```

whose **minimum value is maximum**.

This becomes

> Maximum Minimum Path

This can be solved using

* Binary Search + BFS
* Priority Queue (Modified Dijkstra)
* Union Find

The most common interview solution is

**Multi-source BFS + Binary Search**

---

# Step 1 : Compute distance from nearest thief

Suppose

```
0 0 1
0 0 0
1 0 0
```

Initially

Queue contains every thief.

```
(0,2)
(2,0)
```

Distance

```
INF INF 0
INF INF INF
0   INF INF
```

Run BFS.

Every expansion increases distance by 1.

Final

```
2 1 0
1 2 1
0 1 2
```

Time

```
O(n²)
```

---

# Step 2 : Binary Search the answer

Suppose maximum distance is

```
5
```

Binary search

```
0...5
```

Check

Can we make a path where every cell has distance ≥ mid?

Example

mid = 3

Allowed

```
3 4 5
```

Blocked

```
0 1 2
```

Run BFS.

If destination reachable

```
Answer ≥ 3
```

Otherwise

```
Answer < 3
```

---

# Why Binary Search Works?

Suppose

Safeness = 4 is possible.

Then

```
3
2
1
0
```

are also possible.

If

Safeness = 4 is NOT possible

Then

```
5
6
7
```

also impossible.

This monotonic property allows binary search.

---

# Algorithm

### Step 1

Run Multi-source BFS.

Store

```
dist[i][j]
```

=

Nearest thief distance.

---

### Step 2

Binary Search

```
low = 0
high = maximum distance
```

---

### Step 3

For every mid

Run BFS.

Visit only cells where

```
dist >= mid
```

If destination reached

```
low = mid + 1
```

Else

```
high = mid - 1
```

Answer

```
high
```

---

# Dry Run

Grid

```
0 0 1
0 0 0
0 0 0
```

Distance

```
2 1 0
3 2 1
4 3 2
```

Binary Search

```
low=0
high=4
```

mid=2

Allowed

```
2 X X
3 2 X
4 3 2
```

Reachable?

Yes

Answer may be larger.

---

mid=3

Allowed

```
X X X
3 X X
4 3 X
```

Not reachable.

Answer

```
2
```

---

# Java Solution

```java
import java.util.*;

class Solution {

    private static final int[][] DIRS = {
        {1, 0}, {-1, 0}, {0, 1}, {0, -1}
    };

    public int maximumSafenessFactor(List<List<Integer>> grid) {
        int n = grid.size();

        int[][] dist = new int[n][n];
        for (int[] row : dist) {
            Arrays.fill(row, -1);
        }

        Queue<int[]> queue = new LinkedList<>();

        // Multi-source BFS from all thieves
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid.get(i).get(j) == 1) {
                    dist[i][j] = 0;
                    queue.offer(new int[]{i, j});
                }
            }
        }

        while (!queue.isEmpty()) {
            int[] curr = queue.poll();

            int r = curr[0];
            int c = curr[1];

            for (int[] d : DIRS) {
                int nr = r + d[0];
                int nc = c + d[1];

                if (nr >= 0 && nr < n && nc >= 0 && nc < n &&
                        dist[nr][nc] == -1) {

                    dist[nr][nc] = dist[r][c] + 1;
                    queue.offer(new int[]{nr, nc});
                }
            }
        }

        int low = 0;
        int high = 0;

        for (int[] row : dist) {
            for (int val : row) {
                high = Math.max(high, val);
            }
        }

        int ans = 0;

        while (low <= high) {
            int mid = low + (high - low) / 2;

            if (canReach(dist, mid)) {
                ans = mid;
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return ans;
    }

    private boolean canReach(int[][] dist, int limit) {

        int n = dist.length;

        if (dist[0][0] < limit)
            return false;

        boolean[][] visited = new boolean[n][n];
        Queue<int[]> queue = new LinkedList<>();

        queue.offer(new int[]{0, 0});
        visited[0][0] = true;

        while (!queue.isEmpty()) {

            int[] curr = queue.poll();

            int r = curr[0];
            int c = curr[1];

            if (r == n - 1 && c == n - 1)
                return true;

            for (int[] d : DIRS) {

                int nr = r + d[0];
                int nc = c + d[1];

                if (nr >= 0 && nr < n &&
                    nc >= 0 && nc < n &&
                    !visited[nr][nc] &&
                    dist[nr][nc] >= limit) {

                    visited[nr][nc] = true;
                    queue.offer(new int[]{nr, nc});
                }
            }
        }

        return false;
    }
}
```

---

# Complexity Analysis

### Multi-source BFS

```
O(n²)
```

Each cell is visited once.

---

### Binary Search

Maximum distance ≤ `2(n−1)`.

```
log(2n)
≈ log n
```

For each binary search step we perform one BFS.

```
O(n² log n)
```

---

### Total Time

```
O(n² log n)
```

For `n = 400`, this is efficient enough.

---

### Space Complexity

* Distance array: `O(n²)`
* Visited array: `O(n²)`
* Queue: `O(n²)`

**Total:** `O(n²)`

---

# Why This Solution Works

1. **Multi-source BFS** computes the shortest Manhattan distance from every cell to the nearest thief in one traversal, because all thieves are treated as simultaneous BFS sources.
2. The answer has a **monotonic property**: if a path exists with safeness factor `k`, then a path also exists for every value `< k`. This makes **binary search** applicable.
3. For each candidate safeness factor `mid`, a simple **BFS** checks whether the destination can be reached while only stepping on cells whose distance to the nearest thief is at least `mid`.
4. Combining these ideas yields an optimal time complexity of **O(n² log n)**, which comfortably handles the constraint `n ≤ 400`.
