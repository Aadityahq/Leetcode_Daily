## 🔍 Problem Explanation

You are given a **2D matrix** `grid` of size `m x n` with two important properties:

1. **Each row is sorted in non-increasing order** (left → right)
2. **Each column is sorted in non-increasing order** (top → bottom)

👉 Non-increasing means values **do not increase** as you move right or down (they either stay the same or decrease).

Your task is to **count how many numbers are negative** (`< 0`) in the matrix.

---

## 🧠 Key Observation

Because the matrix is sorted:

* If a number is **negative**, then

  * all numbers **to the right in the same row** are also negative
  * all numbers **below in the same column** are also negative

This allows us to avoid checking every element.

---

## 🚀 Optimal Approach (O(m + n))

### Idea:

Start from the **top-right corner** of the matrix.

* If `grid[row][col]` is **negative**:

  * Then all elements **below** it in that column are also negative
  * Add `(m - row)` to the count
  * Move **left** (`col--`)
* Else (number ≥ 0):

  * Move **down** (`row++`)

This way, we traverse at most `m + n` steps.

---

## ✨ Example Walkthrough

For:

```
4   3   2  -1
3   2   1  -1
1   1  -1  -2
-1 -1  -2  -3
```

* Start at `grid[0][3] = -1`
* Count all negatives below it
* Move left and repeat

Final count = **8**

---

## 💻 Java Solution

```java
class Solution {
    public int countNegatives(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        int row = 0;
        int col = n - 1;
        int count = 0;

        while (row < m && col >= 0) {
            if (grid[row][col] < 0) {
                // All elements below this in the same column are negative
                count += (m - row);
                col--; // move left
            } else {
                row++; // move down
            }
        }

        return count;
    }
}
```

---

## ⏱️ Complexity Analysis

* **Time Complexity:** `O(m + n)`
* **Space Complexity:** `O(1)` (no extra space used)

---

## ✅ Summary

* The sorted property of the matrix helps skip many checks
* Starting from the **top-right corner** is the key trick
* Efficient and clean solution suitable for interviews

