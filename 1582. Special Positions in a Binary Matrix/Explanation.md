# 1582. Special Positions in a Binary Matrix

### Problem

You are given a binary matrix `mat` of size `m x n`.

A position `(i, j)` is **special** if:

1. `mat[i][j] == 1`
2. All other elements in **row i** are `0`
3. All other elements in **column j** are `0`

Return the **number of such special positions**.

---

# Key Observation 🔑

For a cell `(i, j)` to be **special**:

* Row `i` must contain **exactly one `1`**
* Column `j` must contain **exactly one `1`**
* That `1` must be at `(i, j)`

So instead of checking the entire row and column every time (which is slow), we can:

1. Count **number of 1s in each row**
2. Count **number of 1s in each column**
3. Check if a cell with `1` has:

   * `rowCount[i] == 1`
   * `colCount[j] == 1`

---

# Algorithm (Step-by-Step)

### Step 1

Create two arrays:

```
rowCount[m]
colCount[n]
```

These store the number of `1`s in each row and column.

---

### Step 2

Traverse the matrix and count 1s.

```
if mat[i][j] == 1
   rowCount[i]++
   colCount[j]++
```

---

### Step 3

Traverse the matrix again.

If

```
mat[i][j] == 1
AND rowCount[i] == 1
AND colCount[j] == 1
```

Then `(i,j)` is **special**.

Increase answer.

---

# Java Solution

```java
class Solution {
    public int numSpecial(int[][] mat) {
        int m = mat.length;
        int n = mat[0].length;
        
        int[] rowCount = new int[m];
        int[] colCount = new int[n];
        
        // Count number of 1s in each row and column
        for(int i = 0; i < m; i++){
            for(int j = 0; j < n; j++){
                if(mat[i][j] == 1){
                    rowCount[i]++;
                    colCount[j]++;
                }
            }
        }
        
        int count = 0;
        
        // Find special positions
        for(int i = 0; i < m; i++){
            for(int j = 0; j < n; j++){
                if(mat[i][j] == 1 && rowCount[i] == 1 && colCount[j] == 1){
                    count++;
                }
            }
        }
        
        return count;
    }
}
```

---

# Dry Run Example

### Input

```
mat =
[1,0,0]
[0,0,1]
[1,0,0]
```

### Row counts

```
rowCount = [1,1,1]
```

### Column counts

```
colCount = [2,0,1]
```

---

Check each `1`:

| Position | rowCount | colCount | Special? |
| -------- | -------- | -------- | -------- |
| (0,0)    | 1        | 2        | ❌        |
| (1,2)    | 1        | 1        | ✅        |
| (2,0)    | 1        | 2        | ❌        |

Result = **1**

---

# Time and Space Complexity

### Time Complexity

```
O(m × n)
```

Two matrix traversals.

---

### Space Complexity

```
O(m + n)
```

For row and column arrays.

---

# Why This Approach is Good for Interviews 🎯

Naive approach:

```
For every 1 → scan full row and column
```

Time complexity:

```
O(m * n * (m+n))
```

But with **pre-counting rows and columns**, we reduce it to:

```
O(m * n)
```

This is the **optimal solution**.

---
