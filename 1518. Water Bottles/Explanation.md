# 🥤 Water Bottles (LeetCode 1518)

## 📌 Problem Statement

You are given two integers:

- `numBottles`: the number of full water bottles you initially have.
- `numExchange`: the number of empty bottles required to exchange for one full bottle.

Every time you drink a bottle, it becomes empty. You can exchange `numExchange` empty bottles for one new full bottle.

**Return the maximum number of bottles you can drink in total.**

---

## 💡 Example 1

**Input:**

```
numBottles = 9, numExchange = 3
```

**Dry Run Table:**

| Step | Full Bottles (to drink) | Empty Bottles (before exchange) | Action                        | Total Drunk |
|------|------------------------|---------------------------------|-------------------------------|-------------|
| 1    | 9                      | 0                               | Drink all 9 → get 9 empty     | 9           |
| 2    | 3 (from 9 empties)     | 0                               | Exchange 9→3 full             | 12          |
| 3    | 1 (from 3 empties)     | 0                               | Exchange 3→1 full             | 13          |
| 4    | 0                      | 1                               | Not enough empties            | 13          |

**Output:**  
`13`

---

## 💡 Example 2

**Input:**

```
numBottles = 15, numExchange = 4
```

**Dry Run Table:**

| Step | Full Bottles (to drink) | Empty Bottles (before exchange) | Action                              | Total Drunk |
|------|------------------------|---------------------------------|-------------------------------------|-------------|
| 1    | 15                     | 0                               | Drink all 15 → get 15 empty         | 15          |
| 2    | 3 (from 12 empties)    | 3 left over                     | Exchange 12→3 full, leftover 3      | 18          |
| 3    | 1 (from 4 empties)     | 2 left over                     | Exchange 4→1 full, leftover 2       | 19          |
| 4    | 0                      | 3                               | Not enough empties                  | 19          |

**Output:**  
`19`

---

## ✅ Constraints

- `1 <= numBottles <= 100`
- `2 <= numExchange <= 100`

---

## 🔍 How & Why

**How does the process work?**

1. Drink all available bottles → they become empty.
2. Whenever `empty >= numExchange`, exchange them for new full bottles.
3. Drink those bottles → they also become empty.
4. Repeat until fewer than `numExchange` empties remain.

**Why does this work?**

- Each bottle contributes twice: once when you drink it, then again as part of an exchange.
- The greedy strategy of exchanging as soon as possible is always optimal (waiting doesn’t give more bottles).
- By simulating the process, you guarantee no bottle is wasted.

---

## 🛠️ Approach

1. Initialize `total = numBottles`.
2. Keep track of `empty = numBottles`.
3. While `empty >= numExchange`:
    - Exchange `empty / numExchange` bottles.
    - Add to `total`.
    - Update `empty = (empty % numExchange) + newBottles`.
4. Return `total`.

---

## 💻 Java Code

```java
class Solution {
    public int numWaterBottles(int numBottles, int numExchange) {
        int total = numBottles;
        int empty = numBottles;

        while (empty >= numExchange) {
            int newBottles = empty / numExchange;
            total += newBottles;
            empty = (empty % numExchange) + newBottles;
        }

        return total;
    }
}
```

---

## ⚡ Complexity

- **Time Complexity:** O(log(numBottles)) → each exchange reduces empties significantly.
- **Space Complexity:** O(1) → only counters used.