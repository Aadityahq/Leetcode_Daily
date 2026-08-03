This is a **Game Theory + Dynamic Programming (DP)** problem.

The trick is **not** to calculate Alice's and Bob's scores separately. Instead, calculate the **maximum score difference** the current player can achieve.

---

# Intuition

Suppose we are at index `i`.

Remaining stones are:

```
stoneValue[i], stoneValue[i+1], ...
```

The current player (Alice or Bob) has **3 choices**:

* Take 1 stone
* Take 2 stones
* Take 3 stones

After taking stones, the opponent plays optimally.

So every move should consider:

> "If I take these stones now, how much advantage will my opponent get later?"

---

## Key Observation

Instead of storing scores separately, store

```
dp[i] = Maximum score difference
        (Current Player Score - Opponent Score)
        starting from index i.
```

This makes the problem much simpler.

---

# Example

```
stoneValue = [1,2,3,7]
```

Alice starts.

If Alice takes

```
1
```

she gets

```
+1
```

Now Bob starts from

```
[2,3,7]
```

Suppose Bob can achieve a score difference of

```
dp[1] = 8
```

That means Bob can end up **8 points ahead** from there.

So Alice's advantage becomes

```
1 - dp[1]
```

because Bob's future advantage reduces Alice's current advantage.

Therefore,

```
Current Difference
=
Current Taken Stones
-
Opponent's Best Difference
```

This is the whole DP idea.

---

# DP Definition

```
dp[i]
=
Maximum score difference current player can obtain
starting from index i.
```

---

# Transition

At index `i`, we try taking

### Take 1 stone

```
sum = stoneValue[i]

difference
=
sum - dp[i+1]
```

---

### Take 2 stones

```
sum = stoneValue[i] + stoneValue[i+1]

difference
=
sum - dp[i+2]
```

---

### Take 3 stones

```
sum = stoneValue[i]
    + stoneValue[i+1]
    + stoneValue[i+2]

difference
=
sum - dp[i+3]
```

Take the maximum.

```
dp[i]
=
max(
sum1-dp[i+1],
sum2-dp[i+2],
sum3-dp[i+3]
)
```

---

# Why subtract?

Suppose

```
I gain 6 points now.
```

After that,

Opponent can obtain

```
4 points more than me.
```

My final advantage becomes

```
6 - 4 = 2
```

So

```
Current Gain
-
Opponent Advantage
```

---

# Base Case

When no stones remain

```
dp[n]=0
```

because nobody can score anything.

---

# Bottom-Up Calculation

We calculate

```
dp[n]

↓

dp[n-1]

↓

...

↓

dp[0]
```

because every state depends on future states.

---

# Dry Run

Example

```
stoneValue =
[1,2,3,6]
```

n=4

```
dp[4]=0
```

---

### i=3

Take only

```
6

difference

6-0=6
```

```
dp[3]=6
```

---

### i=2

Take 1

```
3-6=-3
```

Take 2

```
9-0=9
```

```
dp[2]=9
```

---

### i=1

Take1

```
2-9=-7
```

Take2

```
5-6=-1
```

Take3

```
11-0=11
```

```
dp[1]=11
```

---

### i=0

Take1

```
1-11=-10
```

Take2

```
3-9=-6
```

Take3

```
6-6=0
```

Maximum

```
0
```

So

```
dp[0]=0
```

Difference is

```
0
```

Meaning

```
Alice Score = Bob Score
```

Answer

```
Tie
```

---

# Why does this work?

At every position:

Current player chooses

```
max(
current gain
-
future opponent advantage
)
```

Since the opponent also plays optimally, `dp[next]` already represents their best possible advantage. Subtracting it gives the current player's net advantage.

This is a classic **minimax optimization** using dynamic programming.

---

# Complexity

There are

```
n
```

states.

Each state checks at most

```
3
```

moves.

### Time

```
O(3n)
=
O(n)
```

### Space

```
O(n)
```

---

# Java Solution

```java
class Solution {
    public String stoneGameIII(int[] stoneValue) {
        int n = stoneValue.length;

        int[] dp = new int[n + 1];

        for (int i = n - 1; i >= 0; i--) {
            dp[i] = Integer.MIN_VALUE;

            int sum = 0;

            for (int k = 0; k < 3 && i + k < n; k++) {
                sum += stoneValue[i + k];
                dp[i] = Math.max(dp[i], sum - dp[i + k + 1]);
            }
        }

        if (dp[0] > 0)
            return "Alice";
        else if (dp[0] < 0)
            return "Bob";
        else
            return "Tie";
    }
}
```

---

# Algorithm Summary

1. Let `dp[i]` be the **maximum score difference** the current player can achieve starting from index `i`.
2. Initialize `dp[n] = 0`.
3. Iterate from right to left.
4. For each position, try taking 1, 2, and 3 stones.
5. Compute:

   ```
   difference = stonesTakenSum - dp[nextIndex]
   ```
6. Store the maximum difference in `dp[i]`.
7. Finally:

   * `dp[0] > 0` → Alice wins.
   * `dp[0] < 0` → Bob wins.
   * `dp[0] == 0` → Tie.

This works because `dp[i]` always represents the **best possible score difference** the player whose turn it is can enforce from that position onward, assuming both players play optimally.
