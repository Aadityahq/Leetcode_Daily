### **Problem Understanding**

We are given a grid where:

* Some cells have **guards** 👮‍♂️.
* Some cells have **walls** 🧱.
* Guards can "see" horizontally and vertically (up, down, left, right) until their vision is blocked by another guard or a wall.

We need to find:

> How many empty cells (no guard or wall) are **not seen by any guard**.

---

### **Example**

```
m = 4, n = 6
guards = [[0,0],[1,1],[2,3]]
walls  = [[0,1],[2,2],[1,4]]
```

We visualize:

```
G W . . . .
. G . . W .
. . W G . .
. . . . . .
```

Guards mark cells in straight lines until blocked.
We count cells that remain unseen → **7 unguarded cells**.

---

### **Approach (How)**

1. **Represent the grid**

   * Use a 2D array `grid[m][n]`.
   * Use integer values to represent:

     * `0` → Empty
     * `1` → Guard
     * `2` → Wall
     * `3` → Guarded cell

2. **Mark guards and walls**

   ```java
   for (int[] g : guards) grid[g[0]][g[1]] = 1;
   for (int[] w : walls) grid[w[0]][w[1]] = 2;
   ```

3. **Simulate guard vision**
   For each guard:

   * Move in **four directions** (up, down, left, right).
   * Stop when hitting a wall (`2`) or another guard (`1`).
   * Mark all reachable empty cells (`0`) as guarded (`3`).

   ```java
   while (nr >= 0 && nr < m && nc >= 0 && nc < n && grid[nr][nc] != 1 && grid[nr][nc] != 2)
       if (grid[nr][nc] == 0) grid[nr][nc] = 3;
   ```

4. **Count unguarded cells**
   After marking all visible cells, simply count the cells still `0`.

---

### **Why This Works**

* Each guard’s line of sight is **independent** — we just mark all reachable cells.
* The complexity is efficient since every cell is processed at most once in a guard’s direction.
* Walls and guards act as **boundaries** that block further visibility.

---

### **Complexity Analysis**

| Type      | Description                                                                             | Value                         |
| --------- | --------------------------------------------------------------------------------------- | ----------------------------- |
| **Time**  | For each guard, we move in 4 directions until a wall/guard stops us. Overall ≤ O(m × n) | ✅ Efficient for `m * n ≤ 10⁵` |
| **Space** | Storing grid                                                                            | O(m × n)                      |

---

### ✅ **Final Output Example**

**Input**

```text
m = 4, n = 6
guards = [[0,0],[1,1],[2,3]]
walls = [[0,1],[2,2],[1,4]]
```

**Output**

```
7
```

**Explanation**

> 7 cells are unguarded and unoccupied.

---

