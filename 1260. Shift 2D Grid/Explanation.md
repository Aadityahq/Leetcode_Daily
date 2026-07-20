# 1260. Shift 2D Grid

## Problem Explanation

You are given:

* A **2D grid** of size **m × n**
* An integer **k**

You need to perform the **shift operation** exactly **k times**.

### One Shift Operation

Every element moves **one position forward**.

Rules:

1. `grid[i][j]` → `grid[i][j+1]`
2. Last element of a row → First element of next row
3. Last element of the entire grid → First position of the grid

---

### Example

#### Input

```text
1 2 3
4 5 6
7 8 9
```

After **1 shift**

```text
9 1 2
3 4 5
6 7 8
```

Notice:

* 9 moved from last position to first.
* Everything else shifted right by one.

---

## Observation

Instead of shifting one by one (which is slow),

Think of the grid as a **single 1D array**.

Original indices:

```text
2D Grid

1 2 3
4 5 6
7 8 9

↓

1D

[1,2,3,4,5,6,7,8,9]
```

Now shifting right by one becomes

```text
[9,1,2,3,4,5,6,7,8]
```

Then convert back into 2D.

---

## Key Idea

Every cell has a unique 1D index.

### Convert 2D → 1D

For cell

```text
(i, j)
```

its index is

```java
index = i * n + j;
```

Example (`n = 3`)

```text
(0,0) -> 0
(0,1) -> 1
(0,2) -> 2
(1,0) -> 3
(1,1) -> 4
(1,2) -> 5
(2,0) -> 6
...
```

---

### After shifting by k

New index becomes

```java
newIndex = (index + k) % (m * n);
```

The modulo is important because once we reach the end, we wrap back to the beginning.

---

### Convert back to 2D

Given a 1D index,

```java
row = newIndex / n;
col = newIndex % n;
```

---

# Dry Run

Grid

```text
1 2 3
4 5 6
7 8 9
```

m = 3

n = 3

Total cells = 9

k = 1

---

### Element 1

Current index

```text
0
```

New index

```text
(0+1)%9 = 1
```

Position

```text
1/3 = 0

1%3 = 1

→ (0,1)
```

So

```text
1 goes to (0,1)
```

---

### Element 2

Current index

```text
1
```

New index

```text
2
```

Goes to

```text
(0,2)
```

---

### Element 3

Current index

```text
2
```

New index

```text
3
```

Goes to

```text
(1,0)
```

---

Continue similarly.

For 9

Current index

```text
8
```

New index

```text
(8+1)%9 = 0
```

Goes to

```text
(0,0)
```

Final grid

```text
9 1 2
3 4 5
6 7 8
```

Correct!

---

# Why This Works

Each element has exactly one unique linear index.

Instead of physically moving elements one step at a time,

we directly compute **where that element should end up after k shifts**.

This avoids repeated shifting.

---

# Algorithm

1. Find rows and columns.
2. Compute total cells.
3. Reduce unnecessary shifts

```java
k %= total;
```

4. Create a new grid.
5. For every cell:

   * Compute current index.
   * Compute shifted index.
   * Convert shifted index back to row and column.
   * Place the value.
6. Convert the result grid into `List<List<Integer>>`.

---

# Java Solution

```java
class Solution {
    public List<List<Integer>> shiftGrid(int[][] grid, int k) {
        int m = grid.length;
        int n = grid[0].length;
        int total = m * n;

        k %= total;

        int[][] shifted = new int[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                int index = i * n + j;

                int newIndex = (index + k) % total;

                int newRow = newIndex / n;
                int newCol = newIndex % n;

                shifted[newRow][newCol] = grid[i][j];
            }
        }

        List<List<Integer>> ans = new ArrayList<>();

        for (int i = 0; i < m; i++) {
            List<Integer> row = new ArrayList<>();

            for (int j = 0; j < n; j++) {
                row.add(shifted[i][j]);
            }

            ans.add(row);
        }

        return ans;
    }
}
```

---

# Complexity Analysis

### Time Complexity

We visit every cell exactly once.

```text
O(m × n)
```

---

### Space Complexity

We create another grid of the same size.

```text
O(m × n)
```

---

# Why do we use `index = i * n + j`?

Suppose

```text
1 2 3
4 5 6
7 8 9
```

There are 3 columns.

The linear numbering is

```text
(0,0) -> 0
(0,1) -> 1
(0,2) -> 2

(1,0) -> 3
(1,1) -> 4
(1,2) -> 5

(2,0) -> 6
(2,1) -> 7
(2,2) -> 8
```

Formula

```java
index = row * numberOfColumns + column;
```

works because every completed row contributes `n` elements before the current row.

---

# Why do we use `newRow = newIndex / n` and `newCol = newIndex % n`?

Suppose

```text
newIndex = 5
n = 3
```

Then

```java
row = 5 / 3 = 1
col = 5 % 3 = 2
```

So index `5` corresponds to position

```text
(1,2)
```

which is correct.

This conversion between 2D and 1D indices is the core idea that makes the solution efficient.
