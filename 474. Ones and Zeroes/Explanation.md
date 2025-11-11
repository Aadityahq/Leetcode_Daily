**LeetCode 474: Ones and Zeroes** 👇

---

## 🧩 Problem Understanding

We are given:

* A list of binary strings (`strs`).
* Two integers `m` (max number of 0s allowed) and `n` (max number of 1s allowed).

We must find the **largest subset** of strings such that:

* The **total 0s ≤ m**
* The **total 1s ≤ n**

This is a **0/1 Knapsack problem** — instead of weight and value, we deal with **two constraints (0s and 1s)**.

---

## ⚙️ Approach — Dynamic Programming (DP)

### 💡 Core Idea

Each string can be:

* **Included** (if we have enough capacity for its 0s and 1s)
* **Excluded** (if we skip it)

We track the **maximum number of strings** we can include for each `(m, n)` combination.

---

### 🔢 Step-by-Step

1. **Count 0s and 1s for each string.**

   * For example, `"10"` → 1 zero, 1 one.

2. **DP array definition:**

   ```java
   int[][] dp = new int[m + 1][n + 1];
   ```

   * `dp[i][j]` = maximum number of strings that can be formed with at most `i` zeros and `j` ones.

3. **Transition (Choice making):**
   For each string (with `zeroCount`, `oneCount`):

   * Iterate `i` from `m` down to `zeroCount`
   * Iterate `j` from `n` down to `oneCount`
   * Update:

     ```java
     dp[i][j] = Math.max(dp[i][j], 1 + dp[i - zeroCount][j - oneCount]);
     ```

   **Why backwards?**
   Because we’re doing a **0/1 knapsack** — each string can only be used **once**.
   Forward iteration would reuse the same item multiple times.

4. **Answer:**
   `dp[m][n]` gives the maximum subset size.

---

## 🧠 Intuition

Think of `m` and `n` as **capacities** — similar to a bag that can hold a limited number of zeros and ones.

Each string “costs” some zeros and ones.
We want to fit as many strings as possible **without exceeding** the limits.

---

## ✅ Java Code Solution

```java
class Solution {
    public int findMaxForm(String[] strs, int m, int n) {
        int[][] dp = new int[m + 1][n + 1];
        
        for (String s : strs) {
            int zeros = 0, ones = 0;
            
            // Count zeros and ones
            for (char c : s.toCharArray()) {
                if (c == '0') zeros++;
                else ones++;
            }
            
            // Update DP in reverse to avoid reuse
            for (int i = m; i >= zeros; i--) {
                for (int j = n; j >= ones; j--) {
                    dp[i][j] = Math.max(dp[i][j], 1 + dp[i - zeros][j - ones]);
                }
            }
        }
        
        return dp[m][n];
    }
}
```

---

## 🧾 Example Walkthrough

Input:

```text
strs = ["10","0001","111001","1","0"]
m = 5, n = 3
```

| String | Zeros | Ones |
| ------ | ----- | ---- |
| 10     | 1     | 1    |
| 0001   | 3     | 1    |
| 111001 | 2     | 4    |
| 1      | 0     | 1    |
| 0      | 1     | 0    |

* `"111001"` needs 4 ones → can’t be used (since n=3)
* The rest can be selected to form the best combination.

Result = **4**

Subset = `{"10","0001","1","0"}`
Total zeros = 5, ones = 3 ✅ within limits.

---

## 🧩 Time and Space Complexity

| Aspect | Complexity   | Explanation                                                          |
| ------ | ------------ | -------------------------------------------------------------------- |
| Time   | O(L × m × n) | For each string (`L`), we iterate over the DP table of size `m × n`. |
| Space  | O(m × n)     | Only one DP table (2D array).                                        |

---

## 🎯 Summary

| Concept      | Explanation                                            |
| ------------ | ------------------------------------------------------ |
| Problem Type | 0/1 Knapsack with 2 dimensions                         |
| DP State     | dp[i][j] = max subset size with ≤ i zeros and ≤ j ones |
| Transition   | dp[i][j] = max(dp[i][j], 1 + dp[i - z][j - o])         |
| Key Insight  | Reverse iteration to prevent reuse of same string      |
| Output       | dp[m][n]                                               |

---

