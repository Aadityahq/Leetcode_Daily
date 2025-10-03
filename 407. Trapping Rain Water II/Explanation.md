# 407. Trapping Rain Water II

## 📌 Problem Statement
Given an `m x n` integer matrix `heightMap` representing the height of each cell in a 2D elevation map, return the **total volume of water** that can be trapped after raining.

---

## 📝 Examples

### Example 1
**Input:**
```plaintext
heightMap = [
    [1,4,3,1,3,2],
    [3,2,1,3,2,4],
    [2,3,3,2,3,1]
]
```
**Output:**  
```
4
```
**Explanation:**  
After raining, water is trapped in two areas:
- 1 unit in one cell
- 3 units in another cell  
Total = **4**

---

### Example 2
**Input:**
```plaintext
heightMap = [
    [3,3,3,3,3],
    [3,2,2,2,3],
    [3,2,1,2,3],
    [3,2,2,2,3],
    [3,3,3,3,3]
]
```
**Output:**  
```
10
```

---

## 🚧 Constraints
- `m == heightMap.length`
- `n == heightMap[i].length`
- `1 <= m, n <= 200`
- `0 <= heightMap[i][j] <= 20,000`

---

## 🔎 Intuition

This problem extends the classic **Trapping Rain Water (1D)** to 2D.

- In **1D**, water at a position depends on the highest boundaries to its left and right.
- In **2D**, water at a cell depends on the lowest boundary height surrounding it in all four directions.

The challenge is efficiently determining these boundaries for every cell.

---

## 🛠️ Approach (Min-Heap / Priority Queue)

1. **Start from the boundary:**  
     - Water can't be trapped on the edges, so add all boundary cells to a min-heap (priority queue) by height.
     - Mark them as visited.

2. **Expand inward:**  
     - Always pop the lowest cell from the heap.
     - For each of its 4 neighbors (up, down, left, right):
         - If the neighbor is lower, water can be trapped:
             ```
             trapped = currentBoundaryHeight - neighborHeight
             ```
         - Update the neighbor’s effective height to:
             ```
             max(neighborHeight, currentBoundaryHeight)
             ```
         - Push the neighbor into the heap and mark as visited.

3. **Repeat until all cells are processed:**  
     - This simulates water filling from the lowest boundaries inward.

---

## 📦 Why this Works

- The priority queue ensures we always process the lowest boundary first.
- This prevents overcounting and accurately simulates water filling the terrain.
- The approach is similar to a multi-source BFS from the edges.

---

## ⏱️ Complexity
- **Time:** `O(m * n * log(mn))` (each cell is pushed/popped once)
- **Space:** `O(m * n)` (for visited array and heap)

---

## ✅ Java Solution

```java
import java.util.*;

class Solution {
        public int trapRainWater(int[][] heightMap) {
                int m = heightMap.length, n = heightMap[0].length;
                if (m <= 2 || n <= 2) return 0;

                boolean[][] visited = new boolean[m][n];
                PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[2]));

                // Add all boundary cells to heap
                for (int i = 0; i < m; i++) {
                        pq.offer(new int[]{i, 0, heightMap[i][0]});
                        pq.offer(new int[]{i, n - 1, heightMap[i][n - 1]});
                        visited[i][0] = visited[i][n - 1] = true;
                }
                for (int j = 0; j < n; j++) {
                        pq.offer(new int[]{0, j, heightMap[0][j]});
                        pq.offer(new int[]{m - 1, j, heightMap[m - 1][j]});
                        visited[0][j] = visited[m - 1][j] = true;
                }

                int[][] dirs = {{1,0}, {-1,0}, {0,1}, {0,-1}};
                int trappedWater = 0;

                while (!pq.isEmpty()) {
                        int[] cell = pq.poll();
                        int x = cell[0], y = cell[1], height = cell[2];

                        for (int[] dir : dirs) {
                                int nx = x + dir[0], ny = y + dir[1];
                                if (nx >= 0 && nx < m && ny >= 0 && ny < n && !visited[nx][ny]) {
                                        visited[nx][ny] = true;
                                        trappedWater += Math.max(0, height - heightMap[nx][ny]);
                                        pq.offer(new int[]{nx, ny, Math.max(heightMap[nx][ny], height)});
                                }
                        }
                }
                return trappedWater;
        }
}
```

---

**Summary:**  
- **What:** Find trapped water in a 2D elevation map.  
- **How:** Use a min-heap and BFS from the boundaries inward.  
- **Why:** Water level at a cell depends on the lowest surrounding boundary, simulating natural water filling.
