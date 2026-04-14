# 🧠 Problem Understanding (Intuition)

* You have robots on a number line.
* You have factories with:

  * position
  * capacity (how many robots it can repair)

💡 Each robot will go to **one factory** → cost = distance
[
|robot[i] - factory[j]|
]

Goal:
👉 Assign every robot to a factory (respecting limits)
👉 Minimize total distance

---

# 🔥 Key Observations

### 1. Sort Everything

* Sort robots
* Sort factories by position

👉 Why?
Because optimal assignment is always **in order (no crossing matching)**

---

### 2. Greedy Structure

If robots are sorted:

```
r1 r2 r3 r4
```

Factories:

```
f1 (limit 2), f2 (limit 2)
```

👉 Optimal:

* First few robots → first factory
* Next → next factory

🚫 Never skip around randomly — that increases distance

---

# 🧩 DP Idea

Let:

```
dp[i][j] = minimum distance to fix first i robots using first j factories
```

---

## Transition

At factory `j`, we can take `k` robots:

```
k = 0 to factory[j].limit
```

Then:

```
dp[i][j] = min(
    dp[i][j-1],   // skip factory
    dp[i-k][j-1] + cost of assigning last k robots to factory j
)
```

---

## Cost Calculation

If assigning robots:

```
robots[i-k] ... robots[i-1]
```

to factory at position `pos`:

```
cost = sum(|robot[x] - pos|)
```

---

# ⚡ Optimization Trick

Instead of recomputing cost every time:
👉 Accumulate it while iterating `k`

---

# ✅ Java Solution

```java
import java.util.*;

class Solution {
    public long minimumTotalDistance(List<Integer> robot, int[][] factory) {
        Collections.sort(robot);
        Arrays.sort(factory, (a, b) -> a[0] - b[0]);

        int n = robot.size();
        int m = factory.length;

        long[][] dp = new long[n + 1][m + 1];

        // Initialize with large values
        for (long[] row : dp) Arrays.fill(row, Long.MAX_VALUE);
        dp[0][0] = 0;

        for (int j = 1; j <= m; j++) {
            int pos = factory[j - 1][0];
            int limit = factory[j - 1][1];

            for (int i = 0; i <= n; i++) {
                // Case 1: skip this factory
                dp[i][j] = dp[i][j - 1];

                long cost = 0;

                // Case 2: assign k robots to this factory
                for (int k = 1; k <= Math.min(i, limit); k++) {
                    cost += Math.abs(robot.get(i - k) - pos);

                    if (dp[i - k][j - 1] != Long.MAX_VALUE) {
                        dp[i][j] = Math.min(dp[i][j],
                                dp[i - k][j - 1] + cost);
                    }
                }
            }
        }

        return dp[n][m];
    }
}
```

---

# 🧪 Example Walkthrough

### Input:

```
robot = [0,4,6]
factory = [[2,2],[6,2]]
```

Sorted:

```
robots:   [0,4,6]
factory:  [2(limit2), 6(limit2)]
```

### Best assignment:

* 0 → 2 (cost 2)
* 4 → 2 (cost 2)
* 6 → 6 (cost 0)

Total = **4**

---

# 🧠 Why This Works

### 1. Sorting ensures optimal pairing

No crossing assignments = minimal distance

---

### 2. DP handles capacity constraints

Factories can only take limited robots

---

### 3. Trying all k ensures global optimal

We check:

* assign 0 robots
* assign 1 robot
* assign 2 robots
  ...

---

# ⏱ Complexity

* Time:
  [
  O(n \cdot m \cdot limit)
  ]
  Worst ≈ **100 × 100 × 100 = 10⁶ → OK**

* Space:
  [
  O(n \cdot m)
  ]

---

# 🚀 Key Takeaways

* This is **not simulation** → it's **assignment optimization**
* Always:

  * Sort robots
  * Sort factories
* Use **DP with grouping**
* Assign **contiguous robots to factories**

---
