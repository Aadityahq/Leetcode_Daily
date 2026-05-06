## Intuition of the Problem

We are given a box containing:

* `#` → stones
* `*` → obstacles
* `.` → empty spaces

The box is rotated **90° clockwise**.

After rotation:

* obstacles stay fixed
* stones fall downward because of gravity

---

# Key Observation

Instead of:

1. rotating first
2. then simulating gravity

We can do it in a cleaner way:

### Step 1:

For every row, move stones `#` to the **right side** as much as possible.

Why right side?

Because after a **90° clockwise rotation**, the **right direction becomes downward gravity**.

---

### Step 2:

Rotate the matrix.

---

# Understanding Gravity Before Rotation

Example row:

```text
# . # .
```

After gravity toward right:

```text
. . # #
```

Now when rotated:

```text
.
.
#
#
```

Exactly what we need.

---

# Important Logic

Obstacles `*` divide the row into segments.

Stones can only move inside their own segment.

Example:

```text
# . # * # .
```

Left segment:

```text
# . #
```

becomes:

```text
. # #
```

Right segment:

```text
# .
```

becomes:

```text
. #
```

Final row:

```text
. # # * . #
```

---

# Approach

## Part 1 → Simulate gravity in rows

We use a pointer `empty`:

* starts from right side
* tells where next stone should go

Traverse from right → left.

### Cases

### If obstacle `*`

Obstacle blocks stones.

So:

```java
empty = j - 1;
```

because next stones can only move before obstacle.

---

### If stone `#`

Move it to `empty`.

```java
box[i][j] = '.';
box[i][empty] = '#';
empty--;
```

---

# Part 2 → Rotate Matrix

Clockwise rotation formula:

```text
rotated[j][m-1-i] = box[i][j]
```

Where:

* `m` = rows
* `n` = cols

Result size becomes:

```text
n x m
```

---

# Java Solution

```java
class Solution {
    public char[][] rotateTheBox(char[][] boxGrid) {

        int m = boxGrid.length;
        int n = boxGrid[0].length;

        // Step 1: simulate gravity towards right
        for (int i = 0; i < m; i++) {

            int empty = n - 1;

            for (int j = n - 1; j >= 0; j--) {

                // obstacle
                if (boxGrid[i][j] == '*') {
                    empty = j - 1;
                }

                // stone
                else if (boxGrid[i][j] == '#') {

                    // make current empty
                    boxGrid[i][j] = '.';

                    // place stone at empty position
                    boxGrid[i][empty] = '#';

                    empty--;
                }
            }
        }

        // Step 2: rotate matrix clockwise
        char[][] ans = new char[n][m];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                ans[j][m - 1 - i] = boxGrid[i][j];
            }
        }

        return ans;
    }
}
```

---

# Dry Run

## Input

```text
# . * .
# # * .
```

---

## Row 1

```text
# . * .
```

Before obstacle:

```text
# .
```

stone falls right:

```text
. #
```

Row becomes:

```text
. # * .
```

---

## Row 2

```text
# # * .
```

Already settled.

---

After gravity:

```text
. # * .
# # * .
```

---

## Rotate

Using:

```text
ans[j][m-1-i] = box[i][j]
```

Result:

```text
# .
# #
* *
. .
```

Correct answer.

---

# Time Complexity

## Gravity simulation

```text
O(m × n)
```

## Rotation

```text
O(m × n)
```

Total:

```text
O(m × n)
```

---

# Space Complexity

Output matrix:

```text
O(m × n)
```

---

# Why This Works

After rotation:

* gravity acts downward
* which is equivalent to moving stones right before rotation

So we preprocess rows first, then rotate.

This avoids complicated falling simulation after rotation.
