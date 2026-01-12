### 🧭 1266. Minimum Time Visiting All Points (Easy)

## 🔍 Problem Understanding (in simple words)

You are given `n` points on a 2D grid.
You must **visit them in the given order**.

From any point, in **1 second**, you can:

* Move **up / down** by 1 unit
* Move **left / right** by 1 unit
* Move **diagonally** (1 unit x and 1 unit y together)

Your goal:
➡️ **Find the minimum time needed to visit all points in order.**

---

## 🧠 Key Insight (Very Important)

Suppose you want to move from point
`(x1, y1)` → `(x2, y2)`

### Differences:

```
dx = |x2 - x1|
dy = |y2 - y1|
```

### Optimal Movement Strategy

* Use **diagonal moves** as much as possible (they cover both x and y in 1 second)
* After diagonals, move straight for the remaining distance

### ⏱️ Minimum time between two points:

```
max(dx, dy)
```

### ❓ Why max(dx, dy)?

* Each diagonal move reduces both `dx` and `dy`
* Number of diagonals = `min(dx, dy)`
* Remaining moves = `|dx - dy|`
* Total time = `min(dx, dy) + |dx - dy| = max(dx, dy)`

✅ This is the **fastest possible** way.

---

## 🧪 Example Walkthrough

### Example 1

```
points = [[1,1],[3,4],[-1,0]]
```

#### Step 1: (1,1) → (3,4)

```
dx = |3 - 1| = 2
dy = |4 - 1| = 3
time = max(2, 3) = 3
```

#### Step 2: (3,4) → (-1,0)

```
dx = | -1 - 3 | = 4
dy = | 0 - 4 | = 4
time = max(4, 4) = 4
```

### Total Time

```
3 + 4 = 7 seconds
```

---

## ✅ Final Approach

1. Start from the first point
2. For every consecutive pair:

   * Compute `dx` and `dy`
   * Add `max(dx, dy)` to answer
3. Return total time

---

## 💡 Java Solution (Clean & Efficient)

```java
class Solution {
    public int minTimeToVisitAllPoints(int[][] points) {
        int time = 0;

        for (int i = 1; i < points.length; i++) {
            int dx = Math.abs(points[i][0] - points[i - 1][0]);
            int dy = Math.abs(points[i][1] - points[i - 1][1]);

            // Minimum time between two points
            time += Math.max(dx, dy);
        }

        return time;
    }
}
```

---

## 🧩 Why This Works (Summary)

✔ Diagonal moves are the most powerful
✔ Each second should reduce distance as much as possible
✔ Using `max(dx, dy)` guarantees minimal time
✔ Linear time solution: **O(n)**
✔ No extra space needed: **O(1)**

---

## 🚀 Complexity Analysis

| Metric           | Value    |
| ---------------- | -------- |
| Time Complexity  | **O(n)** |
| Space Complexity | **O(1)** |

---

