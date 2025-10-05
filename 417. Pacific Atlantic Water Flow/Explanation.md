# 🌊 LeetCode 417: Pacific Atlantic Water Flow

## 🧩 Problem Statement

There is an `m x n` grid `heights` representing an island where:
- Each cell value is the height above sea level.
- The **Pacific Ocean** touches the **top** and **left** edges.
- The **Atlantic Ocean** touches the **bottom** and **right** edges.

Water can flow from a cell to another **if the next cell’s height is less than or equal to the current cell’s height** (i.e., water flows downhill or flat).

Return a list of all coordinates `(r, c)` from which water can flow to **both** oceans.

---

## 🧠 Intuition — “Think in Reverse!”

Instead of starting from each cell and checking *if it can reach the ocean*,  
we reverse the logic:

> Start from the **oceans** and find out which cells **can reach** them.

- For the **Pacific Ocean**: start from top and left edges.
- For the **Atlantic Ocean**: start from bottom and right edges.

From these borders, we explore *inward* to all cells that are **higher or equal**,  
because water can flow *down* to the ocean from those cells.

Then we find the **intersection** of both sets of reachable cells —  
these are the positions where water can flow to **both oceans**.

---

## ⚙️ Approach (Step-by-Step)

1. Create two boolean matrices:
   - `pacific[m][n]` — cells that can flow to the Pacific
   - `atlantic[m][n]` — cells that can flow to the Atlantic

2. From the ocean-border cells:
   - Pacific → top row + left column
   - Atlantic → bottom row + right column

3. Perform **DFS** or **BFS** from these border cells.
   - Move to neighboring cells `(r ± 1, c ± 1)` only if:
     ```
     heights[next] >= heights[current]
     ```
     (we can only “climb” if next is higher or same, since we’re reversing the flow)

4. After traversing both oceans:
   - For every cell `(i, j)`, if it’s marked `true` in both matrices,  
     add it to the result list.

---

## ⏱️ Time & Space Complexity

| Metric | Complexity |
|---------|-------------|
| **Time** | `O(m * n)` — each cell is visited at most twice |
| **Space** | `O(m * n)` — for visited matrices and recursion stack |

---

## 🧮 Example Dry Run

### Input:
[ [1,2,2,3,5],
[3,2,3,4,4],
[2,4,5,3,1],
[6,7,1,4,5],
[5,1,1,2,4] ]


- Start from **Pacific** (top + left):
  - Move inward to higher or equal cells.
  - Marks top-left area reachable by Pacific.

- Start from **Atlantic** (bottom + right):
  - Move inward.
  - Marks bottom-right area reachable by Atlantic.

- Intersection → cells like `[0,4], [1,3], [1,4], [2,2], [3,0], [3,1], [4,0]`.

### Output:
[[0,4],[1,3],[1,4],[2,2],[3,0],[3,1],[4,0]]


---

## 💻 Java Code (DFS Solution)

```java
class Solution {
    public List<List<Integer>> pacificAtlantic(int[][] heights) {
        List<List<Integer>> result = new ArrayList<>();
        if (heights == null || heights.length == 0) return result;

        int rows = heights.length;
        int cols = heights[0].length;

        boolean[][] pacific = new boolean[rows][cols];
        boolean[][] atlantic = new boolean[rows][cols];

        // Run DFS from ocean borders
        for (int i = 0; i < rows; i++) {
            dfs(heights, pacific, i, 0, Integer.MIN_VALUE);         // Pacific (left)
            dfs(heights, atlantic, i, cols - 1, Integer.MIN_VALUE); // Atlantic (right)
        }

        for (int j = 0; j < cols; j++) {
            dfs(heights, pacific, 0, j, Integer.MIN_VALUE);         // Pacific (top)
            dfs(heights, atlantic, rows - 1, j, Integer.MIN_VALUE); // Atlantic (bottom)
        }

        // Intersection of both
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (pacific[i][j] && atlantic[i][j]) {
                    result.add(Arrays.asList(i, j));
                }
            }
        }

        return result;
    }

    private void dfs(int[][] heights, boolean[][] visited, int r, int c, int prevHeight) {
        int rows = heights.length, cols = heights[0].length;

        if (r < 0 || c < 0 || r >= rows || c >= cols || visited[r][c] || heights[r][c] < prevHeight)
            return;

        visited[r][c] = true;

        dfs(heights, visited, r + 1, c, heights[r][c]);
        dfs(heights, visited, r - 1, c, heights[r][c]);
        dfs(heights, visited, r, c + 1, heights[r][c]);
        dfs(heights, visited, r, c - 1, heights[r][c]);
    }
}
🧩 Why This Works
By starting from the oceans instead of every cell,
we avoid redundant traversals and make it efficient.

Water “flows” naturally toward the ocean in reverse traversal.

The intersection gives us exactly the cells that can reach both oceans.

🔁 Optional BFS Version
If the grid is large (e.g., 200×200), recursion might hit the stack limit.
A BFS using a queue (iterative) solves that problem — same logic, different traversal.