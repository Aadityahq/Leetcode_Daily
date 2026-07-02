# 3286. Find a Safe Walk Through a Grid

## Intuition

We need to move from **(0,0)** to **(m-1,n-1)**.

* Every time we enter a cell with value **1**, we lose **1 health**.
* Cells with value **0** do not affect health.
* At every step, health must remain **greater than 0**.
* We need to check if there exists **any path** that reaches the destination with health **≥ 1**.

---

## Key Observation

Instead of thinking about **remaining health**, think about the **minimum health lost** to reach every cell.

For every cell,

> **cost = number of unsafe cells (1s) visited so far.**

If we can reach the destination with

```
cost < health
```

then remaining health is

```
health - cost >= 1
```

which satisfies the condition.

So the problem becomes:

> Find the path with the **minimum total cost**, where entering a `1` costs `1` and entering a `0` costs `0`.

This is exactly a **shortest path problem** on a graph with edge weights **0 or 1**.

---

# Why 0-1 BFS?

Normally,

* Weight = 0
* Weight = 1

When graph edges have only **0 or 1 weight**, **0-1 BFS** is faster than Dijkstra.

It uses a **Deque**.

### Rule

If next cell cost is

* **0** → add to **front**
* **1** → add to **back**

This always processes the smallest cost first.

Time Complexity:

```
O(m × n)
```

instead of

```
O(mn log(mn))
```

---

# Example

```
grid =
0 1 0
0 1 0
0 0 0

health = 1
```

Cost matrix initially

```
0 INF INF
INF INF INF
INF INF INF
```

Start

```
cost = grid[0][0] = 0
```

Visit neighbors.

Moving onto

```
0
```

doesn't increase cost.

Moving onto

```
1
```

increases cost by 1.

Eventually destination gets

```
cost = 0
```

Since

```
0 < health(1)
```

Answer is

```
true
```

---

# Algorithm

1. Create a `dist[][]` array.

   * `dist[i][j]` = minimum unsafe cells used to reach `(i,j)`.

2. Initialize everything with `INF`.

3. Start cost

```
grid[0][0]
```

because if starting cell is unsafe, health decreases immediately.

4. Use a deque.

5. Pop front.

6. Try all 4 directions.

7. New cost

```
currentCost + grid[newRow][newCol]
```

8. If smaller than previous cost:

* update distance
* if new cell is safe (0), push front
* else push back

9. After BFS,

```
dist[m-1][n-1] < health
```

return true.

Else false.

---

# Dry Run

```
grid

0 1 0
0 1 0
0 0 0

health = 1
```

Start

```
dist

0 INF INF
INF INF INF
INF INF INF
```

Deque

```
[(0,0)]
```

Process

```
(0,0)
```

Neighbors

```
(1,0) cost=0
(0,1) cost=1
```

Deque

```
Front -> (1,0)
Back  -> (0,1)
```

Process

```
(1,0)
```

Reach

```
(2,0)
```

still cost

```
0
```

Continue...

Eventually

```
destination cost = 0
```

Check

```
0 < 1

true
```

---

# Correctness

Let `dist[i][j]` denote the minimum number of unsafe cells visited to reach cell `(i, j)`.

* Each move adds either `0` (safe cell) or `1` (unsafe cell) to the total cost.
* 0-1 BFS guarantees that when a state is processed, it has the smallest possible cost because edges with weight `0` are explored before edges with weight `1`.
* Therefore, after the algorithm finishes, `dist[m-1][n-1]` is the minimum health loss required to reach the destination.
* If this minimum loss is less than the initial health, then at least one health point remains (`health - loss >= 1`), so the walk is possible.
* Otherwise, every possible path would reduce health to `0` or below before or upon reaching the destination, making the walk impossible.

Hence, the algorithm is correct.

---

# Complexity Analysis

Let

```
V = m × n
```

Each cell is processed at most once for each improved distance.

Time:

```
O(m × n)
```

Space:

```
O(m × n)
```

---

# Java Solution

```java
import java.util.*;

class Solution {
    public boolean findSafeWalk(List<List<Integer>> grid, int health) {
        int m = grid.size();
        int n = grid.get(0).size();

        int[][] dist = new int[m][n];
        for (int[] row : dist) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }

        Deque<int[]> deque = new ArrayDeque<>();

        dist[0][0] = grid.get(0).get(0);
        deque.offerFirst(new int[]{0, 0});

        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        while (!deque.isEmpty()) {
            int[] curr = deque.pollFirst();
            int r = curr[0];
            int c = curr[1];

            for (int k = 0; k < 4; k++) {
                int nr = r + dr[k];
                int nc = c + dc[k];

                if (nr < 0 || nr >= m || nc < 0 || nc >= n) {
                    continue;
                }

                int newCost = dist[r][c] + grid.get(nr).get(nc);

                if (newCost < dist[nr][nc]) {
                    dist[nr][nc] = newCost;

                    if (grid.get(nr).get(nc) == 0) {
                        deque.offerFirst(new int[]{nr, nc});
                    } else {
                        deque.offerLast(new int[]{nr, nc});
                    }
                }
            }
        }

        return dist[m - 1][n - 1] < health;
    }
}
```

## Why this works

* We transform the problem from **tracking remaining health** to **minimizing health loss**.
* Entering a safe cell (`0`) has cost `0`, and entering an unsafe cell (`1`) has cost `1`.
* Since edge weights are only `0` or `1`, **0-1 BFS** efficiently computes the minimum health loss to every cell in **O(m × n)** time.
* Finally, if the minimum health loss to the destination is **strictly less than the initial health**, at least one health point remains, so we return `true`; otherwise, we return `false`.
