This is one of those LeetCode problems where the **actual algorithm is simpler than it first appears**.

---

# 877. Stone Game

## Problem Understanding

We have an array of piles.

```
piles = [5,3,4,5]
```

Two players:

* Alice (starts first)
* Bob

Rules:

* On each turn, a player can only take

    * the **leftmost** pile or
    * the **rightmost** pile.
* They keep playing until every pile is taken.
* Whoever collects **more stones wins**.

Both players play **optimally**, meaning they always make the best possible move.

We have to determine:

> **Can Alice always win?**

Return

```
true  -> Alice wins
false -> Bob wins
```

---

# Example

```
piles = [5,3,4,5]
```

Initially

```
5 3 4 5
^     ^
```

Alice has two choices.

Take left

```
Alice = 5

Remaining

3 4 5
```

or take right

```
Alice = 5

Remaining

5 3 4
```

Alice will choose whichever move eventually gives her the maximum score.

Since both play perfectly, we cannot use greedy logic.

---

# Why Greedy Doesn't Work

Suppose

```
[3,9,1,2]
```

Greedy says

Take

```
3 vs 2

Take 3
```

But later this may allow Bob to take

```
9
```

which is much better.

Sometimes taking the smaller pile now gives a larger total later.

So we must consider **future moves**.

---

# Dynamic Programming Idea

Instead of storing total stones,

we store

> **Maximum score difference the current player can achieve.**

Suppose

```
dp[i][j]
```

means

> Maximum difference between current player and opponent when only piles from `i` to `j` remain.

Difference means

```
(Current player's score)
-
(Opponent's score)
```

---

## Base Case

If only one pile exists

```
i == j
```

Current player simply takes it.

```
dp[i][i] = piles[i]
```

Example

```
[7]

Current player gets 7

Difference = 7
```

---

# Transition

Current player has two choices.

## Choice 1

Take left pile.

Gain

```
piles[i]
```

Now opponent becomes the current player.

Opponent can achieve

```
dp[i+1][j]
```

difference.

So our difference becomes

```
piles[i] - dp[i+1][j]
```

---

## Choice 2

Take right pile

Gain

```
piles[j]
```

Opponent gets

```
dp[i][j-1]
```

Difference

```
piles[j] - dp[i][j-1]
```

Take whichever is better.

```
dp[i][j]
=
max(
    piles[i]-dp[i+1][j],
    piles[j]-dp[i][j-1]
)
```

---

# Why subtract?

Suppose

Current player takes

```
5
```

After that

Opponent can beat current player by

```
3
```

Then our final advantage becomes

```
5-3=2
```

because opponent's advantage reduces ours.

That is why subtraction appears.

---

# DP Table Filling

We first know

```
Length = 1
```

Then

```
Length = 2
```

Then

```
Length = 3
```

...

until

```
Length = n
```

because

```
dp[i][j]
```

depends on

```
dp[i+1][j]
dp[i][j-1]
```

which are smaller intervals.

---

# Dry Run

Example

```
[5,3,4,5]
```

Initially

```
dp

5
 3
  4
   5
```

Length = 2

```
dp[0][1]

max(
5-3,
3-5
)

=max(2,-2)=2
```

Similarly

```
dp[1][2]=1

dp[2][3]=1
```

Length = 3

Continue similarly.

Finally

```
dp[0][3]
```

becomes positive.

Positive means

Alice can finish with more stones.

Return

```
true
```

---

# Time Complexity

There are

```
n²
```

states.

Each state takes

```
O(1)
```

Time

```
O(n²)
```

Space

```
O(n²)
```

---

# Java Solution (DP)

```java
class Solution {
    public boolean stoneGame(int[] piles) {
        int n = piles.length;

        int[][] dp = new int[n][n];

        // Base case
        for (int i = 0; i < n; i++) {
            dp[i][i] = piles[i];
        }

        // Fill DP table
        for (int len = 2; len <= n; len++) {
            for (int i = 0; i + len - 1 < n; i++) {
                int j = i + len - 1;

                int takeLeft = piles[i] - dp[i + 1][j];
                int takeRight = piles[j] - dp[i][j - 1];

                dp[i][j] = Math.max(takeLeft, takeRight);
            }
        }

        return dp[0][n - 1] > 0;
    }
}
```

---

# Even Simpler Solution (Accepted)

There is a famous mathematical observation for this problem.

Since:

* the number of piles is **even**
* total number of stones is **odd**
* Alice moves first

Alice can always choose to take either **all even-indexed piles** or **all odd-indexed piles**.

Before the game starts:

```
Indices

0 1 2 3 4 5
```

One set is

```
Even indices
```

Another is

```
Odd indices
```

Alice calculates which set has the larger total.

On her first move, she chooses an end that guarantees access to that parity, and thereafter she can always continue taking piles of the chosen parity. Since the total number of stones is odd, the two parity sums cannot be equal, so one parity is strictly larger. Alice commits to the larger one and wins.

Therefore, the answer is **always `true`**.

```java
class Solution {
    public boolean stoneGame(int[] piles) {
        return true;
    }
}
```

---

# Which solution should you write?

* **For interviews:** Write the **DP solution** first. It demonstrates your understanding of optimal play and dynamic programming.
* **For LeetCode 877 specifically:** `return true;` is accepted because of the mathematical guarantee built into the problem's constraints.

The DP approach is more broadly useful because the same idea extends to harder game problems such as Stone Game II, Stone Game III, and Predict the Winner, where the answer is **not** always guaranteed.
