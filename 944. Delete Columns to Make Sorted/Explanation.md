# 📝 Delete Columns to Make Sorted
## 🔹 Problem Explanation (Simple Words)

You are given an array of strings `strs`.

* All strings have the **same length**
* Imagine writing each string on a new line → it forms a **grid**
* Each **column** is made by taking characters at the same index from all strings

Your task:
👉 **Delete the columns that are NOT sorted lexicographically (top to bottom)**
👉 Return **how many columns** you need to delete.

### What does “sorted lexicographically” mean?

A column is sorted if:

```
strs[0][col] <= strs[1][col] <= strs[2][col] <= ...
```

If at any point the order breaks → that column must be deleted.

---

## 🔹 Example Breakdown

### Example 1

```
strs = ["cba","daf","ghi"]

Grid:
c b a
d a f
g h i
```

Check columns one by one:

* **Column 0** → c, d, g → sorted ✅
* **Column 1** → b, a, h → ❌ (b > a)
* **Column 2** → a, f, i → sorted ✅

👉 Delete **1 column**

---

### Example 3

```
strs = ["zyx","wvu","tsr"]

Grid:
z y x
w v u
t s r
```

* Column 0 → z > w > t ❌
* Column 1 → y > v > s ❌
* Column 2 → x > u > r ❌

👉 Delete **all 3 columns**

---

## 🔹 Key Observation (Important)

* Number of **rows** = `strs.length`
* Number of **columns** = `strs[0].length()`
* We only need to **compare adjacent rows** for each column
* If **any comparison fails**, that column is invalid

---

## 🔹 Approach (How We Solve It)

1. Initialize `count = 0`
2. Loop through each column
3. For each column:

   * Compare characters row by row
   * If `strs[row][col] > strs[row+1][col]`

     * Column is **not sorted**
     * Increment `count`
     * Stop checking this column
4. Return `count`

---

## 🔹 Java Solution (Clean & Efficient)

```java
class Solution {
    public int minDeletionSize(String[] strs) {
        int rows = strs.length;
        int cols = strs[0].length();
        int deleteCount = 0;

        // Check each column
        for (int col = 0; col < cols; col++) {
            for (int row = 0; row < rows - 1; row++) {
                // If column is not sorted
                if (strs[row].charAt(col) > strs[row + 1].charAt(col)) {
                    deleteCount++;
                    break; // No need to check further rows for this column
                }
            }
        }

        return deleteCount;
    }
}
```

---

## 🔹 Time & Space Complexity

### ⏱ Time Complexity

```
O(n × m)
```

* `n` = number of strings (rows)
* `m` = length of each string (columns)

### 💾 Space Complexity

```
O(1)
```

* No extra space used

---

## 🔹 How to Explain This in an Interview

> “I treat the input as a grid. For each column, I check if characters from top to bottom are in non-decreasing order. If I find even one place where the order breaks, I count that column as invalid and move on. Finally, I return the total number of invalid columns.”

---

