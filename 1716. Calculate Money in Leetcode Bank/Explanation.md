# 🧮 1716. Calculate Money in Leetcode Bank

## 🧠 Problem Understanding

Hercy saves money following a **pattern**:

* On the **first Monday**, he deposits **$1**.

* Each next day of that week (Tuesday to Sunday), he adds **$1 more** than the previous day.
  → So the first week looks like this:
  `1, 2, 3, 4, 5, 6, 7` → Total = **28**

* On **the next Monday**, he starts with **$2**, then continues the same pattern:
  `2, 3, 4, 5, 6, 7, 8` → Total = **35**

* Similarly, week 3 starts with `$3`:
  `3, 4, 5, 6, 7, 8, 9` → Total = **42**

So, **each week starts $1 higher** than the previous week.

---

## ⚙️ How It Works

Let’s break it down by days and weeks.

Let:

* `n` = total days Hercy saves money
* `weeks` = full weeks completed (`n / 7`)
* `days` = leftover days after full weeks (`n % 7`)

### 🧩 Example: `n = 10`

* `weeks = 10 / 7 = 1`
* `days = 10 % 7 = 3`

So:

* 1 full week (7 days)
* 3 days in the next week

Now let’s calculate step by step 👇

#### Week 1 (starting with $1):

1 + 2 + 3 + 4 + 5 + 6 + 7 = **28**

#### Remaining 3 days (starting next Monday = $2):

2 + 3 + 4 = **9**

✅ Total = 28 + 9 = **37**

---

## 💡 Why It Works

1. Each **new week’s starting amount** increases by $1.

   * Week 1 starts with $1
   * Week 2 starts with $2
   * Week 3 starts with $3
     → This means each week’s total increases by **7** compared to the previous week.

2. Within each week, the pattern is an **arithmetic series**:

   * 7 numbers
   * Difference = 1
   * So sum = `7 * start + 21` (since 0+1+2+3+4+5+6 = 21)

3. For remaining days, again use the same pattern starting from the next Monday’s starting amount.

---

## 🧾 Step-by-Step Logic Summary

| Step | Explanation                 | Formula                                                 |
| ---- | --------------------------- | ------------------------------------------------------- |
| 1    | Find full weeks             | `weeks = n / 7`                                         |
| 2    | Find remaining days         | `days = n % 7`                                          |
| 3    | Add total of each full week | `total += 7*(1 + i) + 21` for each week                 |
| 4    | Add remaining days          | `start = 1 + weeks; total += start + (start + 1) + ...` |

---

## 💻 Java Solution

```java
class Solution {
    public int totalMoney(int n) {
        int weeks = n / 7;   // full weeks
        int days = n % 7;    // remaining days
        int total = 0;
        
        // Add money from full weeks
        for (int i = 0; i < weeks; i++) {
            // Each full week = 7*(starting value) + 21
            total += 7 * (1 + i) + 21;
        }
        
        // Add money for remaining days
        int start = 1 + weeks;  // starting amount for next week
        for (int i = 0; i < days; i++) {
            total += start + i;
        }
        
        return total;
    }
}
```

---

## ✅ Example Walkthrough: n = 20

* `weeks = 20 / 7 = 2`
* `days = 20 % 7 = 6`

👉 Week 1 = 1 + 2 + 3 + 4 + 5 + 6 + 7 = 28
👉 Week 2 = 2 + 3 + 4 + 5 + 6 + 7 + 8 = 35
👉 Remaining 6 days = 3 + 4 + 5 + 6 + 7 + 8 = 33

**Total = 28 + 35 + 33 = 96**

---

## 🏁 Final Answer

| Input | Output | Explanation               |
| ----- | ------ | ------------------------- |
| 4     | 10     | 1+2+3+4                   |
| 10    | 37     | (1+2+3+4+5+6+7) + (2+3+4) |
| 20    | 96     | (1–7) + (2–8) + (3–8)     |

---


