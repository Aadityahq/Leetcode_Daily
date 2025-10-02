# 🥤 Water Bottles II — README

## 📌 Problem Statement  
You are given:  
- `numBottles`: the number of **full water bottles** you start with.  
- `numExchange`: the **initial exchange rate** — how many empty bottles are needed to get **1 new full bottle**.  

Rules:  
1. You can **drink full bottles**, which then turn into empty bottles.  
2. You can **exchange** empty bottles for **1 new full bottle** if you have enough.  
3. After **every exchange**, the exchange rate (`numExchange`) increases by **1**.  
4. You cannot exchange multiple times with the same exchange rate.  

**Goal:** Return the **maximum number of bottles you can drink**.

---

## 🔎 Example 1  
Input:
```
numBottles = 13, numExchange = 6
```

Step-by-step:  
- Start: `13 full`, drink them → total drunk = 13, now `13 empty`.  
- Exchange 6 empty → 1 full, remaining 7 empty, rate = 7.  
  - Drink 1 → total = 14, empties = 8.  
- Exchange 7 empty → 1 full, remaining 1 empty, rate = 8.  
  - Drink 1 → total = 15, empties = 2.  
- Need 8 empties for the next exchange, but only 2 left → stop.  

✅ Answer = 15.

---

## 🤔 How Do We Solve This?
- **Step 1:** Always drink all bottles you currently have → adds to your total.  
- **Step 2:** Keep track of empty bottles after drinking.  
- **Step 3:** While you can still exchange:  
  - Trade `numExchange` empties for 1 full bottle.  
  - Drink it → increase total count.  
  - It becomes empty again → add back to empties.  
  - Increase the exchange rate (`numExchange += 1`).  

This ensures we simulate the process exactly as described.

---

## 🧮 Why Does This Work?
- Each cycle of **drink → exchange → drink** adds exactly **1 more bottle** to the total.  
- The limiting factor is when **empties < numExchange**.  
- Since the exchange rate keeps growing, eventually you will not have enough empties to continue.  
- The simulation guarantees we count **every possible drink** without missing any opportunity.  

---

## 💻 Java Solution
```java
class Solution {
    public int maxBottlesDrunk(int numBottles, int numExchange) {
        int totalDrunk = numBottles; // drink all initial bottles
        int empty = numBottles;

        while (empty >= numExchange) {
            empty -= numExchange; // trade empties for 1 full
            numExchange++;        // exchange rate increases
            totalDrunk++;         // drink that new full bottle
            empty++;              // it becomes empty
        }

        return totalDrunk;
    }
}
```

---

## ⏱️ Complexity Analysis
- **Time Complexity:** `O(numBottles + numExchange)` → very small since both ≤ 100.  
- **Space Complexity:** `O(1)` → we only use a few variables.  

---

## ✅ Key Takeaways
- The trick is understanding that **numExchange increases after every exchange**, so you cannot exchange multiple times at the same rate.  
- A **simulation approach** is the easiest and most reliable way here.  
- This problem teaches **greedy resource management** with a dynamically changing rule.  
