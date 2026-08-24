# 1872. Stone Game VIII — Java Solution

## Understanding the Problem

We have stones in a row. On every turn, a player must:

1. Choose **more than one stone** from the left (`x > 1`).
2. Remove those stones.
3. Add their sum to their score.
4. Replace them with **one new stone having that same sum**.

Alice wants to **maximize**:

[
AliceScore - BobScore
]

Bob wants to **minimize** it.

The important part is that both players play optimally.

---

## Key Observation

Suppose:

```text
stones = [-1, 2, -3, 4, -5]
```

The prefix sums are:

```text
prefix[0] = -1
prefix[1] =  1
prefix[2] = -2
prefix[3] =  2
prefix[4] = -3
```

If a player takes the first `i + 1` stones, the new leftmost stone will have value:

```text
prefix[i]
```

For example, if Alice takes the first 4 stones:

```text
[-1, 2, -3, 4]
```

Their sum is:

```text
2
```

The row becomes:

```text
[2, -5]
```

Notice something important: after merging a prefix, the game is now represented by a **prefix sum stone followed by the remaining stones**.

This allows us to solve the problem using dynamic programming.

---

# DP Idea

Let:

```text
dp[i]
```

represent the maximum score difference the **current player** can achieve when considering a state corresponding to the prefix ending at index `i`.

If the current player chooses a prefix with sum:

```text
prefix[i]
```

then they immediately gain:

```text
prefix[i]
```

After that, the opponent gets their optimal advantage, represented by `dp[i + 1]`.

Therefore:

[
dp[i] = \max(prefix[i] - dp[i+1], dp[i+1])
]

But we can simplify this problem even further.

The standard optimal recurrence for this game can be processed from right to left:

```text
best = max(best, prefixSum)
```

where `best` represents the maximum achievable score difference.

More precisely:

[
dp[i] = \max(dp[i+1], prefix[i] - dp[i+1])
]

We only need one variable instead of an entire DP array.

---

## How the Recurrence Works

At every position, the current player has two possibilities:

### Option 1: Keep the previous best answer

```text
dp[i + 1]
```

### Option 2: Take the current prefix

The player gains:

```text
prefix[i]
```

But then the opponent gets the optimal advantage:

```text
dp[i + 1]
```

So the net score difference is:

```text
prefix[i] - dp[i + 1]
```

Therefore:

```text
dp[i] = max(dp[i + 1], prefix[i] - dp[i + 1])
```

---

# Java Solution

```java
class Solution {
    public int stoneGameVIII(int[] stones) {
        int n = stones.length;

        // Convert stones array into prefix sums
        for (int i = 1; i < n; i++) {
            stones[i] += stones[i - 1];
        }

        // Initially, if only the last possible merge remains
        int best = stones[n - 1];

        // Calculate answer from right to left
        for (int i = n - 2; i >= 1; i--) {
            best = Math.max(best, stones[i] - best);
        }

        return best;
    }
}
```

---

# Dry Run

Consider:

```text
stones = [-1, 2, -3, 4, -5]
```

### Step 1: Calculate Prefix Sums

```text
stones = [-1, 1, -2, 2, -3]
```

Initialize:

```text
best = -3
```

Now iterate from right to left.

### i = 3

```text
stones[3] = 2

best = max(-3, 2 - (-3))
     = max(-3, 5)
     = 5
```

### i = 2

```text
best = max(5, -2 - 5)
     = max(5, -7)
     = 5
```

### i = 1

```text
best = max(5, 1 - 5)
     = max(5, -4)
     = 5
```

Final answer:

```text
5
```

---

## Why Don't We Start From Index `0`?

A player must choose:

```text
x > 1
```

stones.

So choosing only the first stone is not allowed.

That is why our loop runs:

```java
for (int i = n - 2; i >= 1; i--)
```

and not until `i = 0`.

---

# Complexity Analysis

### Time Complexity

```text
O(n)
```

We calculate prefix sums once and traverse the array once.

### Space Complexity

```text
O(1)
```

We modify the input array to store prefix sums and use only one extra variable.

---

## Intuition in Simple Words

The difficult part of this problem is that a move changes the array. But after a player merges the first `x` stones, their sum is simply a **prefix sum**.

So instead of simulating every possible game state, we:

1. Convert the array into prefix sums.
2. Work backwards.
3. At every step, calculate whether taking the current prefix gives a better score difference.
4. Store only the best answer so far.

This converts what looks like a complicated game simulation into a clean **O(n) dynamic programming solution**.

### Final Code Again

```java
class Solution {
    public int stoneGameVIII(int[] stones) {
        for (int i = 1; i < stones.length; i++) {
            stones[i] += stones[i - 1];
        }

        int best = stones[stones.length - 1];

        for (int i = stones.length - 2; i >= 1; i--) {
            best = Math.max(best, stones[i] - best);
        }

        return best;
    }
}
```
