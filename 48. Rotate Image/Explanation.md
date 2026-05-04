# 48. Rotate Image

## Problem Understanding

You are given an `n x n` matrix (square matrix).

You need to rotate the matrix by **90 degrees clockwise** **without using another matrix**.

That means the changes must happen **inside the same matrix** (in-place).

---

## Example

### Input

```text
1 2 3
4 5 6
7 8 9
```

### After 90° Clockwise Rotation

```text
7 4 1
8 5 2
9 6 3
```

---

# Main Idea

Instead of creating a new matrix:

We do the rotation in **2 steps**:

1. **Transpose the matrix**
2. **Reverse every row**

---

# Why does this work?

---

## Step 1: Transpose

Transpose means:

Swap:

```text
matrix[i][j] ↔ matrix[j][i]
```

So rows become columns.

### Example

Before transpose:

```text
1 2 3
4 5 6
7 8 9
```

After transpose:

```text
1 4 7
2 5 8
3 6 9
```

---

## Step 2: Reverse every row

Now reverse each row:

```text
1 4 7   ->   7 4 1
2 5 8   ->   8 5 2
3 6 9   ->   9 6 3
```

Final matrix:

```text
7 4 1
8 5 2
9 6 3
```

Which is the required rotated image.

---

# Visualization

## Original Matrix

```text
1 2 3
4 5 6
7 8 9
```

## After Transpose

```text
1 4 7
2 5 8
3 6 9
```

## After Reversing Rows

```text
7 4 1
8 5 2
9 6 3
```

---

# Java Solution

```java
class Solution {
    public void rotate(int[][] matrix) {
        int n = matrix.length;

        // Step 1: Transpose the matrix
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {

                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }

        // Step 2: Reverse each row
        for (int i = 0; i < n; i++) {

            int left = 0;
            int right = n - 1;

            while (left < right) {

                int temp = matrix[i][left];
                matrix[i][left] = matrix[i][right];
                matrix[i][right] = temp;

                left++;
                right--;
            }
        }
    }
}
```

---

# Dry Run

## Input

```text
1 2 3
4 5 6
7 8 9
```

---

## Transpose

Swap:

* `(0,1)` with `(1,0)`
* `(0,2)` with `(2,0)`
* `(1,2)` with `(2,1)`

Result:

```text
1 4 7
2 5 8
3 6 9
```

---

## Reverse Rows

### Row 1

```text
1 4 7 -> 7 4 1
```

### Row 2

```text
2 5 8 -> 8 5 2
```

### Row 3

```text
3 6 9 -> 9 6 3
```

Final Answer:

```text
7 4 1
8 5 2
9 6 3
```

---

# Time Complexity

## Transpose

We visit upper half of matrix:

O(n^2)

## Reverse Rows

Again:

O(n^2)

### Total Complexity

O(n^2)

---

# Space Complexity

Since we are modifying the same matrix and using only a few variables:

O(1)

---

# Important Interview Point

The interviewer mainly wants to check:

* Can you think of an **in-place transformation**
* Do you know the concept of:

  * transpose
  * reversing
* Can you avoid extra space

---

# Key Observation to Remember

For **90° clockwise rotation**:

```text
Transpose + Reverse Rows
```

For **90° anti-clockwise rotation**:

```text
Transpose + Reverse Columns
```
