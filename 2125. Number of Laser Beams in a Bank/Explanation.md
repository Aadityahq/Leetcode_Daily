## 🧠 Problem Understanding

You are given a **binary string array** representing the layout of a bank.

* `'1'` → a security device
* `'0'` → empty space

We need to calculate the **total number of laser beams** between these security devices.

---

## 📘 Rules Recap

A **laser beam** exists **between every pair of devices** that meet both conditions:

1. They are in **different rows** (`r1` < `r2`)
2. All rows **between them** have **no devices** (only '0's)

---

## 🔍 Key Observation (How to Think)

Instead of checking every possible pair (which would be inefficient), notice this pattern:

👉 If a row has `a` devices and the **next non-empty row** has `b` devices,
then the number of beams **between these two rows** is:

```
beams = a * b
```

### Why?

* Each device in the first row can connect to **every device** in the next non-empty row.
* The total beams formed = (number of devices in row1) × (number of devices in row2).

Then, move on to the next non-empty row, repeating the process.

---

## 🧩 Step-by-Step Example

### Example:

```
bank = ["011001","000000","010100","001000"]
```

| Row | String     | Devices (`1`s count) | Action |
| --- | ---------- | -------------------- | ------ |
| 0   | 011001 → 3 | previous = 3         |        |
| 1   | 000000 → 0 | skip                 |        |
| 2   | 010100 → 2 | beams = 3 * 2 = 6    |        |
| 3   | 001000 → 1 | beams = 2 * 1 = 2    |        |

✅ **Total beams = 6 + 2 = 8**

---

## 💡 Approach (Why this works)

* We only care about **rows that contain at least one device**.
* For each **pair of consecutive active rows**, multiply their counts.
* This approach avoids unnecessary comparisons → **O(m × n)** time, which is efficient.

---

## 🧮 Algorithm (Step Summary)

1. Initialize `prevDevices = 0`, `totalBeams = 0`
2. For each row in `bank`:

   * Count number of `'1'`s → `currDevices`
   * If `currDevices > 0`:

     * `totalBeams += prevDevices * currDevices`
     * Update `prevDevices = currDevices`
3. Return `totalBeams`

---

## 💻 Java Solution

```java
class Solution {
    public int numberOfBeams(String[] bank) {
        int prevDevices = 0;   // stores device count of previous non-empty row
        int totalBeams = 0;

        for (String row : bank) {
            int currDevices = 0;

            // count number of '1's in this row
            for (char c : row.toCharArray()) {
                if (c == '1') currDevices++;
            }

            // if this row has any device
            if (currDevices > 0) {
                totalBeams += prevDevices * currDevices;  // form beams with previous row
                prevDevices = currDevices;  // update previous count
            }
        }

        return totalBeams;
    }
}
```

---

## 🕒 Time & Space Complexity

| Complexity         | Explanation                   |
| ------------------ | ----------------------------- |
| **Time:** O(m × n) | We scan every cell once       |
| **Space:** O(1)    | Only a few variables are used |

---

## ✅ Example Walkthrough

Input:

```
["011001","000000","010100","001000"]
```

Execution:

```
Row 0: 3 devices → prev = 3
Row 1: 0 devices → skip
Row 2: 2 devices → total += 3 * 2 = 6, prev = 2
Row 3: 1 device  → total += 2 * 1 = 2, prev = 1
```

✅ **Output = 8**

---

### 🧾 Final Answer:

**Output:** `8`

---

