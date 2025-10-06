# 🏊‍♂️ 778. Swim in Rising Water

**Level:** Hard  
**Tags:** Graph, Dijkstra’s Algorithm, Priority Queue, Binary Search, BFS  

---

## 🧩 Problem Overview

You’re given an `n x n` integer matrix `grid` where each value `grid[i][j]` represents the **elevation** at point `(i, j)`.

It starts raining, and water rises over time.  
At time `t`, the water level is `t`, meaning any cell with elevation ≤ `t` is submerged or reachable.

You start at `(0, 0)` and want to reach `(n-1, n-1)`.

You can swim to **4-directionally adjacent** cells only if the elevation of both squares ≤ `t`.

**Goal:**  
Return the *minimum time `t`* such that you can swim from `(0, 0)` to `(n-1, n-1)`.

---

## 🧠 Intuition — “How It Works”

Imagine the island as a grid where each cell is a block of land of different heights.  
As the rain continues, water fills up gradually — at time `t`, you can swim over any block whose height ≤ `t`.

So the question becomes:

> “When is there *first* a path from top-left to bottom-right that stays underwater?”

---

## 💭 Why Dijkstra’s Algorithm?

This isn’t a classic shortest-path problem (like sum of weights).  
Instead, it’s about minimizing the **maximum elevation** you need to cross.

So, think of each cell as a node in a graph:
- Moving to a higher elevation = “costly” step.
- You want a path from start to end with the smallest possible *maximum elevation*.

That’s exactly what **Dijkstra’s algorithm** does — it always expands the next *cheapest* option.

Here, the “cost” = elevation.

---

## ⚙️ Approach (Using Priority Queue)

1. Use a **min-heap** (`PriorityQueue`) that always expands the cell with the lowest elevation.
2. Keep track of a `time` variable = the highest elevation we’ve had to swim over so far.
3. Start from `(0, 0)` and explore neighbors.
4. Each time we move, update `time = max(time, currentCellElevation)`.
5. When we reach `(n-1, n-1)`, that `time` is the **minimum time needed**.

---

## 🧩 Example Walkthrough

### Example

```text
grid = [
    [0, 2],
    [1, 3]
]
```

| Time | Reachable cells         | Explanation                    |
|------|------------------------|--------------------------------|
| t=0  | (0,0)                  | Only top-left reachable.       |
| t=1  | (0,0), (1,0)           | We can move down now.          |
| t=2  | (0,0), (1,0), (0,1)    | Right side unlocked.           |
| t=3  | All cells reachable    | We can finally reach bottom-right. ✅ |

**Answer → `3`**

---

## 💻 Code (Java - LeetCode Version)

```java
class Solution {
        public int swimInWater(int[][] grid) {
                int n = grid.length;
                boolean[][] visited = new boolean[n][n];
                int[][] dirs = {{1,0}, {-1,0}, {0,1}, {0,-1}};
                
                PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
                pq.offer(new int[]{grid[0][0], 0, 0});
                
                int time = 0;
                while (!pq.isEmpty()) {
                        int[] cur = pq.poll();
                        int elevation = cur[0];
                        int r = cur[1], c = cur[2];
                        
                        time = Math.max(time, elevation);
                        if (r == n - 1 && c == n - 1) return time;
                        
                        if (visited[r][c]) continue;
                        visited[r][c] = true;
                        
                        for (int[] d : dirs) {
                                int nr = r + d[0];
                                int nc = c + d[1];
                                if (nr >= 0 && nc >= 0 && nr < n && nc < n && !visited[nr][nc]) {
                                        pq.offer(new int[]{grid[nr][nc], nr, nc});
                                }
                        }
                }
                return -1;
        }
}
```

---

## ⏱️ Complexity Analysis

| Complexity | Explanation                                 |
|------------|---------------------------------------------|
| Time: O(n² log n²) ≈ O(n² log n) | Each cell pushed once into the heap. |
| Space: O(n²)                     | Visited matrix + heap storage.       |

---

## 💡 Alternative: Binary Search + DFS/BFS

Another valid approach:

- Binary search on time `t`.
- For each `t`, run BFS/DFS to check if `(n-1, n-1)` is reachable.
- Adjust bounds until you find the minimum `t`.

Still gives you O(n² log(maxElevation)) complexity.

---

## 🧭 Summary — Key Takeaways

- The problem is not about the shortest sum path, but about minimizing the maximum elevation.
- Dijkstra’s fits perfectly because it expands cells by smallest elevation first.
- Conceptually, the “water level” rises as you progress — and you’re tracking the moment both start and end get connected.

---

## ✅ Example Test Cases

| Input | Output | Explanation |
|-------|--------|-------------|
| `[[0,2],[1,3]]` | `3` | Must wait until 3 to reach end. |
| `[[0,1,2,3,4],[24,23,22,21,5],[12,13,14,15,16],[11,17,18,19,20],[10,9,8,7,6]]` | `16` | Path opens up at time 16. |
