## 🔢 Problem Summary (Plain English)

You have an **n × 3 grid** and **3 colors**:

* 🔴 Red
* 🟡 Yellow
* 🟢 Green

### Rules:

* Every cell must be painted **exactly one color**
* **No two adjacent cells** (horizontal or vertical) can have the **same color**

Your task is to count **how many valid ways** you can paint the grid.

Since the number can be very large, return the answer modulo **10⁹ + 7**.

---

![Image](https://cdn.matthewjamestaylor.com/img/responsive-three-column-diagram.png)

![Image](https://assets.leetcode.com/uploads/2020/03/26/e1.png)

![Image](https://i.sstatic.net/E70Ya.png)

---

## 🧠 Key Insight (This is the trick)

Instead of thinking about all cells individually, we focus **row by row**.

Each row has **3 cells**, and valid colorings of one row fall into **two patterns**:

---

## 🎨 Two Types of Row Patterns

### 1️⃣ Type X – All 3 colors are different

Example:

```
R Y G
Y G R
```

* Number of ways for **1 row** = **6**

  * (3 × 2 × 1)

### 2️⃣ Type Y – Only 2 colors used

Example:

```
R Y R
G R G
```

* First cell: 3 choices
* Second cell: 2 choices
* Third cell: 1 choice (must differ from middle)
* Total = **6**

So for **n = 1**:

```
x = 6 (Type X)
y = 6 (Type Y)
Total = 12
```

✔️ Matches Example 1

---

## 🔁 How Rows Affect Each Other (DP Transition)

Let:

* `x` = number of ways where the previous row is **Type X**
* `y` = number of ways where the previous row is **Type Y**

Now we build the next row.

---

### 🔄 Transition Rules

From **Type X**:

* Can form **3** new Type X rows
* Can form **2** new Type Y rows

From **Type Y**:

* Can form **2** new Type X rows
* Can form **2** new Type Y rows

---

### 📐 Mathematical Formulas

```java
new_x = 3*x + 2*y
new_y = 2*x + 2*y
```

We repeat this for each row from `2` to `n`.

---

## ✅ Java Solution Explained Line by Line

```java
class Solution {
    public int numOfWays(int n) {
        final int MOD = 1000000007;

        // x = ways ending with 3 distinct colors
        // y = ways ending with 2 colors
        long x = 6, y = 6;   // base case (n = 1)

        for (int i = 2; i <= n; i++) {
            long new_x = (3 * x + 2 * y) % MOD;
            long new_y = (2 * x + 2 * y) % MOD;
            x = new_x;
            y = new_y;
        }

        return (int)((x + y) % MOD);
    }
}
```

---

## ⏱️ Time & Space Complexity

| Metric               | Value  |
| -------------------- | ------ |
| **Time Complexity**  | `O(n)` |
| **Space Complexity** | `O(1)` |

✔️ Efficient even for `n = 5000`

---

## 🔍 Example Check

### Input:

```
n = 1
```

Output:

```
12
```

### Input:

```
n = 5000
```

Output:

```
30228214
```

✔️ Matches expected results

---

## 🧠 Final Intuition

* Reduce the problem using **patterns**
* Use **Dynamic Programming**
* Keep only **two variables** instead of a DP table
* Build row-by-row efficiently
