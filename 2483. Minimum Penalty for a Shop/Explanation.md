# 📚 Minimum Penalty for a Shop
## 🔍 Problem Understanding (in simple words)

You are given a string `customers` of length `n`.

* `'Y'` → customers came in that hour
* `'N'` → no customers came

You must decide **at which hour `j` the shop should close** (`0 ≤ j ≤ n`).

### 🧮 Penalty Rules

1. **Shop open & no customers (`N`) → penalty +1**
2. **Shop closed & customers come (`Y`) → penalty +1**

Goal 👉 **Minimize total penalty**
If multiple hours give the same minimum penalty, return the **earliest hour**.

---

## 🧠 Key Insight

For a closing hour `j`:

* **Before `j` (open hours)**
  → penalty = number of `'N'`
* **From `j` onward (closed hours)**
  → penalty = number of `'Y'`

So:

```
Total Penalty(j) =
    count of 'N' in [0 ... j-1]
  + count of 'Y' in [j ... n-1]
```

---

## 🚀 Optimized Approach (O(n))

Instead of calculating penalty from scratch for every `j`, we use a **running penalty technique**.

### Step-by-step Logic

1. **Start by assuming the shop closes at hour 0**

   * Shop closed for all hours
   * Penalty = total count of `'Y'`

2. **Move hour by hour**

   * If hour `i` was `'Y'`
     → now shop is open → penalty **decreases by 1**
   * If hour `i` was `'N'`
     → shop is open with no customers → penalty **increases by 1**

3. Track the **minimum penalty** and the **earliest hour**.

---

## 🧾 Example Walkthrough

For `"YYNY"`

| Closing Hour | Penalty |
| ------------ | ------- |
| 0            | 3       |
| 1            | 2       |
| 2            | ⭐ 1     |
| 3            | 2       |
| 4            | ⭐ 1     |

Minimum penalty = **1**, earliest hour = **2**

---

## ✅ Java Solution

```java
class Solution {
    public int bestClosingTime(String customers) {
        int n = customers.length();

        // Step 1: initial penalty = number of 'Y'
        int penalty = 0;
        for (char c : customers.toCharArray()) {
            if (c == 'Y') {
                penalty++;
            }
        }

        int minPenalty = penalty;
        int bestHour = 0;

        // Step 2: move closing hour from 1 to n
        for (int i = 0; i < n; i++) {
            if (customers.charAt(i) == 'Y') {
                penalty--; // shop open, customer came
            } else {
                penalty++; // shop open, no customer
            }

            if (penalty < minPenalty) {
                minPenalty = penalty;
                bestHour = i + 1;
            }
        }

        return bestHour;
    }
}
```

---

## ⏱ Complexity Analysis

* **Time Complexity:** `O(n)`
* **Space Complexity:** `O(1)`

---


