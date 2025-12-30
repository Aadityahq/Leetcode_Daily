**LeetCode 840 – Magic Squares In Grid** 👇

---

## 🔮 What is a 3×3 Magic Square?

A **3×3 magic square** must satisfy **all** of the following:

1. It contains **all numbers from 1 to 9 exactly once**
2. The **sum of every row, every column, and both diagonals is the same**
3. That common sum is always **15** (property of 1–9 magic squares)

---

## 📌 Example of a valid magic square

![Image](https://study.com/cimages/videopreview/8maupllyfn.jpg)

![Image](https://upload.wikimedia.org/wikipedia/commons/thumb/e/e4/Magicsquareexample.svg/1200px-Magicsquareexample.svg.png)

---

## 🧠 Problem Understanding

You are given a grid of size `row × col`.

Your task:

* Look at **every possible 3×3 subgrid**
* Check if it forms a **valid magic square**
* Count how many such subgrids exist

🔹 Note:

* Grid values can be **0 to 15**
* But a magic square **must use only numbers 1–9**

---

## 🧩 Key Observations (Important Tricks)

1. **Center must be 5**
   Every valid 3×3 magic square using numbers 1–9 has `5` in the center.

2. **Distinct numbers from 1 to 9**

   * No duplicates
   * No numbers outside this range

3. **All sums must be 15**

   * 3 rows
   * 3 columns
   * 2 diagonals

---

## 🚀 Approach

For each possible 3×3 subgrid:

1. Check center = `5`
2. Check all numbers are **unique and between 1–9**
3. Check sums of:

   * Rows
   * Columns
   * Diagonals
     If all pass → count it

---

## ✅ Java Solution

```java
class Solution {
    public int numMagicSquaresInside(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        int count = 0;

        // Loop over all possible 3x3 subgrids
        for (int i = 0; i <= rows - 3; i++) {
            for (int j = 0; j <= cols - 3; j++) {
                if (isMagic(grid, i, j)) {
                    count++;
                }
            }
        }
        return count;
    }

    private boolean isMagic(int[][] grid, int r, int c) {
        // Center must be 5
        if (grid[r + 1][c + 1] != 5) return false;

        boolean[] seen = new boolean[10];

        // Check numbers 1 to 9 and uniqueness
        for (int i = r; i < r + 3; i++) {
            for (int j = c; j < c + 3; j++) {
                int val = grid[i][j];
                if (val < 1 || val > 9 || seen[val]) {
                    return false;
                }
                seen[val] = true;
            }
        }

        // Check rows
        for (int i = 0; i < 3; i++) {
            if (grid[r + i][c] + grid[r + i][c + 1] + grid[r + i][c + 2] != 15)
                return false;
        }

        // Check columns
        for (int j = 0; j < 3; j++) {
            if (grid[r][c + j] + grid[r + 1][c + j] + grid[r + 2][c + j] != 15)
                return false;
        }

        // Check diagonals
        if (grid[r][c] + grid[r + 1][c + 1] + grid[r + 2][c + 2] != 15)
            return false;

        if (grid[r][c + 2] + grid[r + 1][c + 1] + grid[r + 2][c] != 15)
            return false;

        return true;
    }
}
```

---

## ⏱ Time & Space Complexity

* **Time:** `O(row × col)` (each 3×3 check is constant time)
* **Space:** `O(1)` (fixed-size boolean array)

---

## 🎯 Final Takeaway

* Slide a **3×3 window**
* Validate using **strict rules**
* Count valid magic squares

