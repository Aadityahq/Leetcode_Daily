# 🧠 Problem Understanding (Intuition)

You are given a grid. Think of it as a **graph**:

* Each cell = a **node**
* You can move in 4 directions (up, down, left, right)
* Only move if the **character is the same**

👉 So effectively, you're forming **connected components of same characters**

---

# 🔁 What is a Cycle Here?

A valid cycle must:

* Have **length ≥ 4**
* Move through **same characters**
* Not immediately go back to the previous cell

💡 Key idea:

> If during traversal, you visit a cell that is **already visited AND not your parent**, then a cycle exists.

---

# 🚀 Approach: DFS (Depth First Search)

We will:

1. Traverse every cell
2. If not visited → start DFS
3. During DFS:

   * Mark current cell visited
   * Explore neighbors with same value
   * Track **parent cell (px, py)**

---

# ⚠️ Important Logic

When exploring neighbors:

```
If neighbor is visited AND not parent → cycle found ✅
```

Why?

Because:

* If it's parent → just came from there (ignore)
* If it's visited but NOT parent → loop detected 🔁

---

# 🧩 Example Insight

For this:

```
a a a
a b a
a a a
```

DFS will eventually revisit a node from a different path → cycle found.

---

# 💻 Java Solution

```java
class Solution {

    int[][] dirs = {{0,1}, {1,0}, {0,-1}, {-1,0}};

    public boolean containsCycle(char[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        boolean[][] visited = new boolean[m][n];

        for(int i = 0; i < m; i++) {
            for(int j = 0; j < n; j++) {
                if(!visited[i][j]) {
                    if(dfs(grid, visited, i, j, -1, -1)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private boolean dfs(char[][] grid, boolean[][] visited, int x, int y, int px, int py) {
        visited[x][y] = true;

        for(int[] d : dirs) {
            int nx = x + d[0];
            int ny = y + d[1];

            // boundary check
            if(nx < 0 || ny < 0 || nx >= grid.length || ny >= grid[0].length)
                continue;

            // same character check
            if(grid[nx][ny] != grid[x][y])
                continue;

            // if not visited → go deeper
            if(!visited[nx][ny]) {
                if(dfs(grid, visited, nx, ny, x, y))
                    return true;
            }
            // visited and NOT parent → cycle
            else if(nx != px || ny != py) {
                return true;
            }
        }

        return false;
    }
}
```

---

# 🔍 Why This Works

### 1. DFS ensures full traversal

You explore all connected same-character cells.

### 2. Parent tracking avoids false cycles

Without `(px, py)`, you'd always detect a cycle when going back.

### 3. Visited array prevents reprocessing

Avoids infinite loops and redundant work.

---

# ⏱️ Complexity

* **Time:** `O(m × n)`
* **Space:** `O(m × n)` (visited + recursion stack)

---

# 🧠 Key Takeaways

* Convert grid → graph mentally
* Cycle detection = **visited + parent check**
* This is similar to:

  * Undirected graph cycle detection

---

# 💡 Interview Tip

If interviewer asks:
👉 *"Why do we track parent?"*

Answer:

> Because in an undirected graph, visiting the previous node is expected. A cycle only exists if we reach an already visited node that is NOT the parent.

---

