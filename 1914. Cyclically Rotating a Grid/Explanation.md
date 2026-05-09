
# 1914. Cyclically Rotating a Grid

## Problem Understanding

You are given a matrix and an integer `k`.

The matrix contains multiple **layers (rings)**.

Example for a `4 x 4` matrix:

```text
1   2   3   4
5   6   7   8
9  10  11  12
13 14  15  16
```

Layers are:

* Outer layer:

```text
1 2 3 4 8 12 16 15 14 13 9 5
```

* Inner layer:

```text
6 7 11 10
```

For one rotation:

* Every element moves **one step counter-clockwise**.

We must rotate every layer `k` times.

---

# Key Observation

Actually rotating one-by-one is slow.

Instead:

1. Extract layer elements into a list.
2. Rotate the list.
3. Put values back into matrix.

This makes the problem much easier.

---

# How Rotation Works

Suppose layer:

```text
[1,2,3,4,8,12,16,15,14,13,9,5]
```

After 2 counter-clockwise rotations:

```text
[3,4,8,12,16,15,14,13,9,5,1,2]
```

Instead of rotating 2 times manually:

```java
shift = k % size
```

because rotating full cycle changes nothing.

---

# Step-by-Step Algorithm

For every layer:

## 1. Extract Elements

Traverse in order:

* top row
* right column
* bottom row
* left column

Store into list.

---

## 2. Rotate

If list size is `len`

```java
rot = k % len
```

Counter-clockwise rotation means:

```java
newIndex = (i + rot) % len
```

---

## 3. Put Back

Again traverse boundary in same order and fill rotated values.

---

# Dry Run

## Input

```text
1  2  3  4
5  6  7  8
9 10 11 12
13 14 15 16
k = 2
```

Outer layer extracted:

```text
[1,2,3,4,8,12,16,15,14,13,9,5]
```

After rotation:

```text
[3,4,8,12,16,15,14,13,9,5,1,2]
```

Placed back:

```text
3  4  8 12
2  6  7 16
1 10 11 15
5  9 13 14
```

Now rotate inner layer:

```text
[6,7,11,10]
```

After rotation by 2:

```text
[11,10,6,7]
```

Final matrix:

```text
3  4  8 12
2 11 10 16
1  7  6 15
5  9 13 14
```

---

# Java Solution

```java
class Solution {
    public int[][] rotateGrid(int[][] grid, int k) {
        int m = grid.length;
        int n = grid[0].length;

        int layers = Math.min(m, n) / 2;

        for (int layer = 0; layer < layers; layer++) {

            List<Integer> list = new ArrayList<>();

            int top = layer;
            int left = layer;
            int bottom = m - layer - 1;
            int right = n - layer - 1;

            // top row
            for (int j = left; j <= right; j++) {
                list.add(grid[top][j]);
            }

            // right column
            for (int i = top + 1; i <= bottom - 1; i++) {
                list.add(grid[i][right]);
            }

            // bottom row
            for (int j = right; j >= left; j--) {
                list.add(grid[bottom][j]);
            }

            // left column
            for (int i = bottom - 1; i >= top + 1; i--) {
                list.add(grid[i][left]);
            }

            int size = list.size();
            int rot = k % size;

            List<Integer> rotated = new ArrayList<>();

            // counter-clockwise rotation
            for (int i = 0; i < size; i++) {
                rotated.add(list.get((i + rot) % size));
            }

            int idx = 0;

            // fill top row
            for (int j = left; j <= right; j++) {
                grid[top][j] = rotated.get(idx++);
            }

            // fill right column
            for (int i = top + 1; i <= bottom - 1; i++) {
                grid[i][right] = rotated.get(idx++);
            }

            // fill bottom row
            for (int j = right; j >= left; j--) {
                grid[bottom][j] = rotated.get(idx++);
            }

            // fill left column
            for (int i = bottom - 1; i >= top + 1; i--) {
                grid[i][left] = rotated.get(idx++);
            }
        }

        return grid;
    }
}
```

---

# Why This Works

We process each layer independently.

For every layer:

* Extraction keeps elements in traversal order.
* Rotating list simulates cyclic movement.
* Writing back restores matrix structure.

Because each layer is independent, all rotations are correct.

---

# Time Complexity

Let total elements = `m * n`

Each element is:

* extracted once
* rotated once
* inserted once

So:

```text
Time: O(m * n)
```

---

# Space Complexity

We store one layer at a time.

Worst case:

```text
O(m + n)
```

---

# Important Interview Points

## Why use `k % size`?

After one complete cycle, layer becomes same.

So extra rotations are useless.

---

## Why process layer separately?

Each ring rotates independently.

Inner layer should not affect outer layer.

---

## Why extraction order matters?

While putting back, order must exactly match traversal order.

Otherwise matrix becomes incorrect.

---

# Alternative Approach

Could rotate in-place one step `k` times.

But complexity becomes:

```text
O(k * m * n)
```

Too slow since:

```text
k <= 10^9
```

So list rotation approach is optimal.
