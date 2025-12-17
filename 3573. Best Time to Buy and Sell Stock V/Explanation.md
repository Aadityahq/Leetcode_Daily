# 🧠 Problem Explanation

You are given:

* An array `prices`, where `prices[i]` is the stock price on day `i`
* An integer `k`, the maximum number of transactions allowed

### What makes this problem different?

Each transaction can be:

1. **Normal transaction**
   Buy first → sell later
   Profit = `sellPrice - buyPrice`

2. **Short selling transaction**
   Sell first → buy back later
   Profit = `sellPrice - buyBackPrice`

### Rules:

* At most `k` transactions
* One transaction must be **fully completed** before another starts
* No buying and selling on the same day
* You cannot overlap transactions

### Goal:

👉 **Maximize total profit**

---

# 🤔 Why This Problem Is Tricky

In classic stock problems:

* You only **buy then sell**

Here:

* You can **sell first then buy**
* You must track **what position you are holding**
* A transaction is only completed **when you return to a neutral state**

So simple greedy or standard DP for stock problems **does not work**.

---

# 🧩 Key Insight (The “Why”)

At any moment, you must be in **one of three states**:

1. **FREE** → not holding any stock
2. **LONG** → bought a stock, waiting to sell
3. **SHORT** → sold a stock, waiting to buy back

💡 **Important rule**
A transaction is counted **only when you close a position** (LONG → FREE or SHORT → FREE).

This guarantees:

* No overlapping transactions
* No same-day buy & sell
* Correct transaction counting

---

# 📐 DP State Definition (The “How”)

We use Dynamic Programming with transaction count:

* `free[t]`
  → Maximum profit after **t completed transactions**, holding nothing

* `long[t]`
  → Maximum profit after **t completed transactions**, holding a bought stock

* `short[t]`
  → Maximum profit after **t completed transactions**, holding a short position

---

# 🔄 State Transitions (Day by Day)

Let the current stock price be `p`.

---

### 1️⃣ FREE state

You can:

* Stay free
* Sell a long position (complete transaction)
* Buy back a short position (complete transaction)

```
free[t] =
max(
    free[t],
    long[t-1] + p,
    short[t-1] - p
)
```

---

### 2️⃣ LONG state

You can:

* Keep holding
* Buy today (from FREE)

```
long[t] =
max(
    long[t],
    free[t] - p
)
```

---

### 3️⃣ SHORT state

You can:

* Keep holding
* Sell today (from FREE)

```
short[t] =
max(
    short[t],
    free[t] + p
)
```

---

# 🛑 Why This Is Correct

✔ Transactions counted only when closed
✔ No overlapping positions
✔ No same-day buy/sell misuse
✔ Works for rising & falling prices
✔ Handles all edge cases

This exactly follows the problem constraints.

---

# ⏱️ Complexity Analysis

* **Time Complexity:** `O(n × k)`
* **Space Complexity:** `O(k)`

Efficient for `prices.length ≤ 1000`.

---

# 🎯 Final Java Solution (Reference)

```java
class Solution {
    public long maximumProfit(int[] prices, int k) {
        long NEG = Long.MIN_VALUE / 4;

        long[] free = new long[k + 1];
        long[] longPos = new long[k + 1];
        long[] shortPos = new long[k + 1];

        for (int t = 0; t <= k; t++) {
            free[t] = 0;
            longPos[t] = NEG;
            shortPos[t] = NEG;
        }

        for (int price : prices) {
            long[] newFree = new long[k + 1];
            long[] newLong = new long[k + 1];
            long[] newShort = new long[k + 1];

            for (int t = 0; t <= k; t++) {
                newFree[t] = free[t];
                if (t > 0) {
                    newFree[t] = Math.max(newFree[t], longPos[t - 1] + price);
                    newFree[t] = Math.max(newFree[t], shortPos[t - 1] - price);
                }

                newLong[t] = Math.max(longPos[t], free[t] - price);
                newShort[t] = Math.max(shortPos[t], free[t] + price);
            }

            free = newFree;
            longPos = newLong;
            shortPos = newShort;
        }

        long ans = 0;
        for (int t = 0; t <= k; t++) {
            ans = Math.max(ans, free[t]);
        }
        return ans;
    }
}
```

---

