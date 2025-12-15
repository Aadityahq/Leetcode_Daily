## 🧩 Problem Explanation (What is being asked?)

You are given an array `prices`, where:

* `prices[i]` = stock price on day `i`

A **smooth descent period** is:

* A **contiguous subarray** (continuous days)
* Prices **decrease by exactly 1** each day
* A **single day** is always considered a smooth descent period

### Key Rule

For a period of multiple days:

```
prices[i] = prices[i-1] - 1
```

---

## 📌 Example Understanding

### Example 1

**Input:**
`[3, 2, 1, 4]`

**Smooth descent periods:**

* Single days → `[3] [2] [1] [4]`
* Two days → `[3,2] [2,1]`
* Three days → `[3,2,1]`

✅ Total = **7**

---

## 🧠 Key Insight (Why this works)

If we have a **continuous descending sequence by 1**, like:

```
5 → 4 → 3 → 2
```

Then:

* Length = 4
* Number of smooth descent subarrays ending at each position:

  * At 5 → 1
  * At 4 → 2
  * At 3 → 3
  * At 2 → 4

Total = `1 + 2 + 3 + 4`

👉 So instead of checking all subarrays (which is slow), we **count how long the current valid descent continues**.

---

## ⚙️ Solution Explanation (How it works)

### Variables Used

* `cnt` → length of the current smooth descent ending at index `i`
* `ans` → total number of smooth descent periods

---

### Step-by-Step Logic

1. **Start with `cnt = 1`**

   * Every single day is a valid descent period.

2. **Traverse the array**

   * If current price is exactly `previous price - 1`

     * Extend the descent → `cnt++`
   * Else

     * Reset → `cnt = 1`

3. **Add `cnt` to answer**

   * Because `cnt` represents how many valid subarrays end at this index

---

## 💡 Dry Run (Example 1)

`prices = [3,2,1,4]`

| Day | Price | Condition | cnt | ans |
| --- | ----- | --------- | --- | --- |
| 0   | 3     | start     | 1   | 1   |
| 1   | 2     | 2 = 3−1 ✔ | 2   | 3   |
| 2   | 1     | 1 = 2−1 ✔ | 3   | 6   |
| 3   | 4     | break ❌   | 1   | 7   |

---

## ✅ Java Solution

```java
class Solution {
    public long getDescentPeriods(int[] prices) {
        long ans = 0;
        int cnt = 1;

        for (int i = 0; i < prices.length; i++) {
            if (i == 0) {
                ans += cnt;
                continue;
            }

            if (prices[i] == prices[i - 1] - 1) {
                cnt++;
            } else {
                cnt = 1;
            }

            ans += cnt;
        }

        return ans;
    }
}
```

---

## ⏱️ Complexity Analysis

* **Time Complexity:** `O(n)`

  * Single pass through the array
* **Space Complexity:** `O(1)`

  * Only two variables used

---
