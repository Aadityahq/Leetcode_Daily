# ✅ **Problem Explanation (How & Why)**

You're given:

* A city grid of size **n × n**
* A list of **unique buildings**, where each building has coordinates **(x, y)**

A building at **(x, y)** is called **covered** if:

* There is **at least one building directly above** it → same column, smaller x
* There is **at least one building directly below** it → same column, larger x
* There is **at least one building on the left** → same row, smaller y
* There is **at least one building on the right** → same row, larger y

### ✔ So a building is covered if it is enclosed from all 4 directions.

---

# 🧠 **Key Insight (Why this works)**

For each building `(x, y)`:

### We just need to check:

* Does row `x` contain a point with column `< y`?  → Left
* Does row `x` contain a point with column `> y`?  → Right
* Does column `y` contain a point with row `< x`?  → Above
* Does column `y` contain a point with row `> x`?  → Below

### ❗ Important

We do **not** need exact neighbors.
We just need *any* building on each direction.

---

# 🛠 How to do this efficiently?

We cannot check each building by scanning all others — that would be **O(n²)**.

Instead:

### STEP 1 — Build row and column maps

For each row `x`, store all y-values.
For each column `y`, store all x-values.

Use:

* `HashMap<Integer, List<Integer>> rowMap`
* `HashMap<Integer, List<Integer>> colMap`

### STEP 2 — Sort each row's list and each column's list

So we can quickly find:

* if there is any element `< y`
* if there is any element `> y`

Using binary search.

### STEP 3 — For each building, check all 4 conditions

If all true → it is covered.

---

# ✅ **Java Solution (Optimal — O(n log n))**

```java
import java.util.*;

class Solution {
    public int countCoveredBuildings(int n, int[][] buildings) {
        // Maps to store all y's in each row and all x's in each column
        Map<Integer, List<Integer>> rowMap = new HashMap<>();
        Map<Integer, List<Integer>> colMap = new HashMap<>();

        for (int[] b : buildings) {
            rowMap.computeIfAbsent(b[0], k -> new ArrayList<>()).add(b[1]);
            colMap.computeIfAbsent(b[1], k -> new ArrayList<>()).add(b[0]);
        }

        // Sort all lists for binary search
        for (List<Integer> list : rowMap.values()) Collections.sort(list);
        for (List<Integer> list : colMap.values()) Collections.sort(list);

        int covered = 0;

        for (int[] b : buildings) {
            int x = b[0], y = b[1];

            List<Integer> rowList = rowMap.get(x);
            List<Integer> colList = colMap.get(y);

            // Check left: y' < y
            int posY = Collections.binarySearch(rowList, y);
            // posY gives index of y → left exists if index > 0
            boolean left = posY > 0;

            // Right: element > y exists
            boolean right = posY < rowList.size() - 1;

            // Check above: x' < x
            int posX = Collections.binarySearch(colList, x);
            boolean above = posX > 0;

            // Below: element > x exists
            boolean below = posX < colList.size() - 1;

            if (left && right && above && below) {
                covered++;
            }
        }

        return covered;
    }
}
```

---

# 🧪 **Example Walkthrough (Why result is correct)**

### Example:

```
n = 3
buildings = [[1,2],[2,2],[3,2],[2,1],[2,3]]
```

Building `(2,2)`:

* Same row → `[1, 2, 3]` → left exists (1), right exists (3)
* Same column → `[1, 2, 3]` → above exists (1), below exists (3)

→ **Covered**

All others fail at least one direction.

✔ Output = **1**

---

# 🎯 Final Notes

This problem is about:

* Understanding directional constraints
* Designing efficient lookup structures
* Using sorted lists + binary search

