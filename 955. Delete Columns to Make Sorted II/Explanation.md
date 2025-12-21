## 🔍 Problem Intuition (What is actually asked?)

You are given **n strings of equal length**.
You can **delete columns** (same index from every string).

Your goal is:

> After deleting some columns, the **array of strings (row-wise)** must be in **lexicographical order**
> i.e.
> `strs[0] <= strs[1] <= strs[2] <= ...`

You must return the **minimum number of columns deleted** to achieve this.

---

## ❌ Common misunderstanding

This problem is **NOT** the same as *Delete Columns to Make Sorted (I)*.

Here:

* Rows must be sorted **after deletion**
* Columns themselves **do NOT** need to be sorted

---

## 🧠 Key Insight (How to think about it)

We compare strings **row by row**, character by character **from left to right**.

For two adjacent strings `strs[i]` and `strs[i+1]`:

* If at column `j`:

  * `strs[i][j] < strs[i+1][j]` → order is fixed forever (good)
  * `strs[i][j] > strs[i+1][j]` → ❌ this column must be **deleted**
  * equal → undecided, check next column

### Important Trick

We keep an array:

```java
boolean[] sorted
```

* `sorted[i] = true` means `strs[i] <= strs[i+1]` is already guaranteed

Once a pair is sorted, we **never compare it again**.

---

## 🛠️ Algorithm (Step-by-step)

1. Initialize:

   * `sorted[i] = false` for all adjacent row pairs
2. Traverse columns from **left to right**
3. For each column:

   * Check all unsorted adjacent row pairs
   * If any pair violates lexicographic order → delete column
4. If column is valid:

   * Mark pairs as sorted where order is fixed
5. Count deletions

---

## ⏱️ Complexity

* **Time:** `O(n × m)`
* **Space:** `O(n)`
  Where:
* `n` = number of strings
* `m` = length of each string

---

## ✅ Java Solution (LeetCode-Ready)

```java
class Solution {
    public int minDeletionSize(String[] strs) {
        int n = strs.length;
        int m = strs[0].length();

        // sorted[i] = true means strs[i] <= strs[i+1] is already confirmed
        boolean[] sorted = new boolean[n - 1];
        int deletions = 0;

        // Traverse column by column
        for (int col = 0; col < m; col++) {
            boolean needDelete = false;

            // Check all adjacent rows
            for (int row = 0; row < n - 1; row++) {
                if (!sorted[row]) {
                    char c1 = strs[row].charAt(col);
                    char c2 = strs[row + 1].charAt(col);

                    // Order violated → must delete column
                    if (c1 > c2) {
                        needDelete = true;
                        break;
                    }
                }
            }

            if (needDelete) {
                deletions++;
                continue; // skip updating sorted[]
            }

            // Update sorted[] where order is now fixed
            for (int row = 0; row < n - 1; row++) {
                if (!sorted[row]) {
                    char c1 = strs[row].charAt(col);
                    char c2 = strs[row + 1].charAt(col);
                    if (c1 < c2) {
                        sorted[row] = true;
                    }
                }
            }
        }

        return deletions;
    }
}
```

---

## 🧪 Example Walkthrough (Quick)

### Input

```text
["ca","bb","ac"]
```

### Process

* Column 0: `c > b` → delete
* Column 1: `a < b < c` → sorted

### Output

```text
1
```

---

## 🎯 Why this solution works

* Deletes only **necessary columns**
* Preserves already sorted pairs
* Efficient and greedy
* Officially accepted approach

---

