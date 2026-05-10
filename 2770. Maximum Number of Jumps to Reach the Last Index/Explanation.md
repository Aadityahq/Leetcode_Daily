# Intuition

We need to reach the last index with the **maximum number of jumps**.

From index `i`, we can jump to `j` if:

[
-target \le nums[j] - nums[i] \le target
]

Since we want the **maximum jumps**, this becomes a **DP (Dynamic Programming)** problem.

---

# Key Observation

Define:

* `dp[i]` = maximum jumps needed to reach index `i`
* If index `i` is unreachable, `dp[i] = -1`

Initially:

* We start at index `0`
* So:

```text
dp[0] = 0
```

Now for every pair `(j , i)` where `j < i`:

If:

```text
abs(nums[i] - nums[j]) <= target
```

and `j` is reachable,

then:

```text
dp[i] = max(dp[i], dp[j] + 1)
```

---

# Why This Works

To reach index `i`:

* We try all previous indices `j`
* If jumping from `j → i` is valid,
* then the number of jumps becomes:

```text
jumps to reach j + 1
```

We take the maximum because the problem asks for the **maximum number of jumps**.

---

# Dry Run

Example:

```text
nums = [1,3,6,4,1,2]
target = 2
```

Start:

```text
dp = [0,-1,-1,-1,-1,-1]
```

---

### i = 1

Check `j = 0`

```text
3 - 1 = 2  ✓
```

So:

```text
dp[1] = max(-1, 0+1) = 1
```

```text
dp = [0,1,-1,-1,-1,-1]
```

---

### i = 2

* `6 - 1 = 5` ✗
* `6 - 3 = 3` ✗

Cannot reach.

```text
dp = [0,1,-1,-1,-1,-1]
```

---

### i = 3

Check:

* from 0 → `4-1=3` ✗
* from 1 → `4-3=1` ✓

```text
dp[3] = dp[1]+1 = 2
```

```text
dp = [0,1,-1,2,-1,-1]
```

---

### i = 5

Possible from:

* 3 → `2-4=-2` ✓

```text
dp[5] = dp[3]+1 = 3
```

Answer:

```text
3
```

---

# Time Complexity

We check every pair `(j, i)`:

```text
O(n²)
```

`n <= 1000`, so this works perfectly.

---

# Java Solution

```java
class Solution {
    public int maximumJumps(int[] nums, int target) {
        int n = nums.length;

        int[] dp = new int[n];

        // Mark all indices unreachable initially
        for (int i = 0; i < n; i++) {
            dp[i] = -1;
        }

        // Starting index
        dp[0] = 0;

        // Try to reach every index
        for (int i = 1; i < n; i++) {

            // Check all previous indices
            for (int j = 0; j < i; j++) {

                // Jump condition
                if (dp[j] != -1 &&
                    Math.abs(nums[i] - nums[j]) <= target) {

                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
        }

        return dp[n - 1];
    }
}
```

---

# Why We Use `Math.max`

Because there may be multiple ways to reach the same index.

Example:

```text
0 → 3
0 → 1 → 3
```

The second path has more jumps.

So we always keep the maximum.

---

# DP State Meaning

| State        | Meaning                    |
| ------------ | -------------------------- |
| `dp[i] = -1` | Cannot reach index `i`     |
| `dp[i] = k`  | Maximum jumps to reach `i` |

---

# Pattern Used

This is a classic:

* **1D Dynamic Programming**
* Similar to:

  * Jump Game
  * LIS-style transitions
  * DAG longest path

Because jumps only go forward (`j > i`), there are no cycles.
