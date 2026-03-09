## 🧠 Problem Understanding

You are given three integers:

* `zero` → number of **0s** you must use
* `one` → number of **1s** you must use
* `limit` → **maximum allowed consecutive identical elements**

A binary array is **stable** if:

1. It contains exactly `zero` number of `0`s.
2. It contains exactly `one` number of `1`s.
3. **No subarray longer than `limit` contains only the same element.**

### 🔑 Important Interpretation

The third rule means:

* You **cannot have more than `limit` consecutive `0`s**
* You **cannot have more than `limit` consecutive `1`s**

Example (`limit = 2`):

Valid:

```
00101
11010
```

Invalid:

```
0001   ❌ (3 consecutive 0s)
1110   ❌ (3 consecutive 1s)
```

So the task is:

➡️ **Count the number of binary arrays with `zero` zeros and `one` ones where no more than `limit` identical elements appear consecutively.**

Return answer **mod (10⁹ + 7)**.

---

# 💡 Key Idea (Dynamic Programming)

We build arrays step by step.

Define:

```
dp[z][o][last]
```

Where:

* `z` = zeros used
* `o` = ones used
* `last` = last placed element

  * `0` → last element was 0
  * `1` → last element was 1

`dp[z][o][0]` = number of valid arrays using `z` zeros and `o` ones ending with **0**

`dp[z][o][1]` = number of valid arrays using `z` zeros and `o` ones ending with **1**

---

# ⚙️ Transitions

### Case 1: Place `0`

If we place `0`, previous element must be `1`.

But we must ensure we **do not exceed `limit` zeros in a row**.

```
dp[z][o][0] += dp[z-1][o][1]
```

But if we already added `limit+1` zeros consecutively we must subtract invalid cases.

So we subtract configurations where the previous `limit` elements were also `0`.

```
if(z - limit - 1 >= 0)
    dp[z][o][0] -= dp[z-limit-1][o][1]
```

---

### Case 2: Place `1`

Similarly:

```
dp[z][o][1] += dp[z][o-1][0]
```

Remove invalid sequences:

```
if(o - limit - 1 >= 0)
    dp[z][o][1] -= dp[z][o-limit-1][0]
```

---

# 📊 Final Answer

We want arrays using **all zeros and ones**:

```
dp[zero][one][0] + dp[zero][one][1]
```

---

# ✅ Java Solution

```java
class Solution {
    public int numberOfStableArrays(int zero, int one, int limit) {
        int MOD = 1000000007;
        
        long[][][] dp = new long[zero + 1][one + 1][2];
        
        dp[0][0][0] = dp[0][0][1] = 1;
        
        for (int z = 0; z <= zero; z++) {
            for (int o = 0; o <= one; o++) {
                
                if (z > 0) {
                    dp[z][o][0] = (dp[z][o][0] + dp[z-1][o][1]) % MOD;
                    
                    if (z - limit - 1 >= 0) {
                        dp[z][o][0] = (dp[z][o][0] - dp[z-limit-1][o][1] + MOD) % MOD;
                    }
                }
                
                if (o > 0) {
                    dp[z][o][1] = (dp[z][o][1] + dp[z][o-1][0]) % MOD;
                    
                    if (o - limit - 1 >= 0) {
                        dp[z][o][1] = (dp[z][o][1] - dp[z][o-limit-1][0] + MOD) % MOD;
                    }
                }
            }
        }
        
        return (int)((dp[zero][one][0] + dp[zero][one][1]) % MOD);
    }
}
```

---

# ⏱ Complexity

### Time Complexity

```
O(zero × one)
```

Because we fill the DP table once.

### Space Complexity

```
O(zero × one)
```

for the DP array.

---

# 🔍 Example Walkthrough

### Input

```
zero = 1
one = 2
limit = 1
```

Possible arrays:

```
110 ❌ (two 1s together > limit)
011 ❌
101 ✅
```

Result:

```
1
```

---

# 🎯 Intuition Summary

Think of it like **building the binary array step-by-step**:

1. Track how many `0` and `1` you used.
2. Track the **last placed element**.
3. Prevent placing more than **limit consecutive identical elements**.
4. Use **DP to count all valid possibilities**.

---

✅ **Tip for interviews / contests (like your LeetCode daily problems):**

Whenever you see:

* **exact counts**
* **sequence building**
* **consecutive limits**

➡️ It almost always becomes a **DP with last state tracking**.

---

