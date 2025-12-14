**LeetCode 2147 – Number of Ways to Divide a Long Corridor**,
---

## 🔍 Problem Understanding (What is being asked?)

You are given a corridor represented by a string:

* `'S'` → Seat
* `'P'` → Plant

There are **fixed dividers** at:

* **Before index 0**
* **After index n − 1**

You may add **optional dividers** between characters.

### Goal

Divide the corridor into **non-overlapping sections** such that:

* **Each section contains exactly 2 seats (`'S'`)**
* Any number of plants (`'P'`) are allowed
* Count the **number of distinct ways** to place dividers
* Return the answer **modulo 10⁹ + 7**

If it’s impossible, return **0**.

---

## ❌ When is it impossible?

* If **total seats = 0** → no valid section
* If **total seats is odd** → can’t make pairs of 2

👉 In both cases, return **0**.

---

## 💡 Key Observation (Why this works)

Think in terms of **seat positions**:

* Every section must contain **exactly 2 seats**
* So seats must be grouped as:

  ```
  (S, S) | (S, S) | (S, S) | ...
  ```

Between **two consecutive seat-pairs**, we can place dividers in **multiple valid positions**, depending on how many plants exist between them.

---

## 🧠 Core Insight

Suppose seat positions are:

```
S ... S | ... | S ... S
```

Let:

* `pos[i-1]` = last seat of previous section
* `pos[i]` = first seat of next section

The number of valid divider positions between these sections is:

```
pos[i] - pos[i-1]
```

👉 Because a divider can be placed **anywhere in that gap**

---

## 🧮 How to Compute the Answer

1. Store **indices of all seats**
2. Check validity (even number of seats, non-zero)
3. For every new section (starting from the 3rd seat):

   * Multiply the gap size between seat pairs
4. Take modulo at each step

---

## ✅ Example Walkthrough

### Input

```
"SSPPSPS"
```

Seat positions:

```
[0, 1, 4, 6]
```

Seat pairs:

```
(0,1) | (4,6)
```

Gap between sections:

```
4 - 1 = 3
```

👉 You can place the divider in **3 ways**

---

## 🧩 Java Solution (Given)

```java
class Solution {
    public int numberOfWays(String corridor) {
        int mod = 1_000_000_007;
        ArrayList<Integer> pos = new ArrayList<>();

        // Collect positions of all seats
        for (int i = 0; i < corridor.length(); i++) {
            if (corridor.charAt(i) == 'S') {
                pos.add(i);
            }
        }

        // Invalid if seats are zero or odd
        if (pos.size() % 2 == 1 || pos.size() == 0) {
            return 0;
        }

        long res = 1;

        // Multiply the number of divider positions between seat pairs
        for (int i = 2; i < pos.size(); i += 2) {
            int gap = pos.get(i) - pos.get(i - 1);
            res = (res * gap) % mod;
        }

        return (int) res;
    }
}
```

---

## ⏱️ Complexity Analysis

* **Time Complexity:** `O(n)`
* **Space Complexity:** `O(n)` (seat positions)

Efficient enough for `n ≤ 10⁵`.

---

## 🏁 Final Summary

* Pair seats into groups of **two**
* Count **how many places a divider can go** between each pair
* Multiply all choices
* Use modulo to avoid overflow

This problem is a great example of:

* **Greedy grouping**
* **Combinatorics**
* **Index-based reasoning**


