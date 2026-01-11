## 🧠 Problem Understanding (What)

You’re given a **binary matrix** (`'0'` and `'1'`).

Your task is to find the **largest rectangle consisting only of `'1'`s** and return its **area**.

### Example

```
1 0 1 0 0
1 0 1 1 1
1 1 1 1 1
1 0 0 1 0
```

The largest rectangle of only `1`s has **area = 6**.

---

## 🤔 Key Insight (Why this works)

This problem looks 2D, but the **trick** is to convert it into a **series of 1D problems**.

### 🔁 Core Idea

* Treat **each row as the base of a histogram**
* Heights = number of **continuous `1`s above (including current row)**
* For **each row**, compute the **largest rectangle in a histogram**

👉 This reduces the problem to **LeetCode 84: Largest Rectangle in Histogram**, repeated for every row.

---

## 🔄 How the Transformation Works

### Step 1: Build Heights Array

For each column:

* If matrix[row][col] == `'1'` → `height[col] += 1`
* Else → `height[col] = 0`

Example after processing rows:

| Row | Heights       |
| --- | ------------- |
| 1st | `[1,0,1,0,0]` |
| 2nd | `[2,0,2,1,1]` |
| 3rd | `[3,1,3,2,2]` |
| 4th | `[4,0,0,3,0]` |

---

### Step 2: Largest Rectangle in Histogram (Stack)

For each `heights[]`:

* Use a **monotonic increasing stack**
* When current bar is **smaller**, calculate area of popped bars
* Width = distance between current index and previous smaller bar

⏱ Time per row: **O(cols)**
⏱ Total time: **O(rows × cols)**

---

## 🚀 Final Algorithm

1. Initialize `heights[]` of size `cols`
2. For each row:

   * Update `heights`
   * Compute max rectangle using stack
3. Track global maximum area

---

## ✅ Java Solution (Clean & Efficient)

```java
import java.util.Stack;

class Solution {
    public int maximalRectangle(char[][] matrix) {
        if (matrix == null || matrix.length == 0) return 0;

        int rows = matrix.length;
        int cols = matrix[0].length;
        int[] heights = new int[cols];
        int maxArea = 0;

        for (int r = 0; r < rows; r++) {
            // Build histogram heights
            for (int c = 0; c < cols; c++) {
                if (matrix[r][c] == '1') {
                    heights[c]++;
                } else {
                    heights[c] = 0;
                }
            }
            // Calculate max rectangle in histogram
            maxArea = Math.max(maxArea, largestRectangleArea(heights));
        }

        return maxArea;
    }

    private int largestRectangleArea(int[] heights) {
        Stack<Integer> stack = new Stack<>();
        int maxArea = 0;
        int n = heights.length;

        for (int i = 0; i <= n; i++) {
            int currHeight = (i == n) ? 0 : heights[i];

            while (!stack.isEmpty() && currHeight < heights[stack.peek()]) {
                int height = heights[stack.pop()];
                int width = stack.isEmpty() ? i : i - stack.peek() - 1;
                maxArea = Math.max(maxArea, height * width);
            }
            stack.push(i);
        }
        return maxArea;
    }
}
```

---

## ⏱ Complexity Analysis

| Metric | Value              |
| ------ | ------------------ |
| Time   | **O(rows × cols)** |
| Space  | **O(cols)**        |

---

## 🧩 Why This is a Hard Problem

* Requires **problem transformation**
* Combines **2D DP idea + Stack**
* Tests understanding of **histogram rectangle logic**

If you master this, many matrix + stack problems become easy 💪

---

