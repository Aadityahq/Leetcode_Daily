# 486. Predict the Winner

## Problem Understanding

You are given an array `nums`.

* Two players play alternately.
* **Player 1 starts first.**
* On every turn, a player can only pick:

  * the **first element**, or
  * the **last element** of the remaining array.
* Both players play **optimally**, meaning they always make the best possible move for themselves.
* Return:

  * `true` if Player 1 can get **at least as many points as Player 2**.
  * Otherwise return `false`.

---

## Example 1

```
nums = [1,5,2]
```

```
Player1 -> 1
Remaining = [5,2]

Player2 -> 5
Remaining = [2]

Player1 -> 2

P1 = 3
P2 = 5
```

If Player1 starts with 2:

```
Player1 -> 2
Remaining = [1,5]

Player2 -> 5
Remaining = [1]

Player1 -> 1

P1 = 3
P2 = 5
```

No matter what Player1 chooses,

```
Player1 = 3
Player2 = 5
```

Answer:

```
false
```

---

## Example 2

```
nums = [1,5,233,7]
```

Player1 chooses **1**.

```
Remaining = [5,233,7]
```

Player2 can choose either 5 or 7.

Either way,

Player1 will later pick **233**.

```
P1 = 1 + 233 = 234
P2 = 12
```

Answer:

```
true
```

---

# Key Observation

Many beginners think:

> Let's calculate Player1's score.

That is difficult because Player2 is also trying to maximize their own score.

Instead, think in terms of **score difference**.

---

## New Idea

Suppose

```
f(i,j)
```

means

> Maximum score difference current player can achieve over the opponent from subarray `nums[i...j]`.

Difference means

```
(Current Player Score) - (Other Player Score)
```

---

### Base Case

If only one number remains

```
nums[i]
```

Current player simply picks it.

```
difference = nums[i]
```

So

```
dp[i][i] = nums[i]
```

---

# Transition

Suppose current player has

```
nums[i...j]
```

He has two choices.

---

## Choice 1

Pick left

```
nums[i]
```

Now opponent plays on

```
[i+1...j]
```

The opponent can achieve

```
dp[i+1][j]
```

difference.

But that difference is **in opponent's favor**.

So our difference becomes

```
nums[i] - dp[i+1][j]
```

---

## Choice 2

Pick right

```
nums[j]
```

Opponent gets

```
dp[i][j-1]
```

So

```
nums[j] - dp[i][j-1]
```

---

Take the better option

```
dp[i][j] =
max(
    nums[i]-dp[i+1][j],
    nums[j]-dp[i][j-1]
)
```

---

# Why subtraction?

Suppose

```
nums = [1,5]
```

Current player picks

```
1
```

Opponent later gets

```
5
```

Difference

```
1-5=-4
```

Formula gives

```
1-dp[1][1]

=1-5

=-4
```

Correct.

---

# DP Table Example

Take

```
nums=[1,5,2]
```

Initially

```
1 0 0
0 5 0
0 0 2
```

Fill length 2

```
dp[0][1]

max(
1-5,
5-1
)

=max(-4,4)

=4
```

```
dp[1][2]

max(
5-2,
2-5
)

=max(3,-3)

=3
```

Table

```
1 4 0
0 5 3
0 0 2
```

Now length 3

```
dp[0][2]

=max(
1-3,
2-4
)

=max(-2,-2)

=-2
```

Final

```
1 4 -2
0 5 3
0 0 2
```

Since

```
dp[0][2] = -2
```

Player1 loses.

---

# Why does `dp[0][n-1] >= 0` mean Player1 wins?

Remember

```
dp
```

stores

```
Player1 Score - Player2 Score
```

If

```
>0
```

Player1 has more score.

If

```
=0
```

Tie.

Problem says tie is also a win.

So

```java
return dp[0][n-1] >= 0;
```

---

# Java Solution (Bottom-Up DP)

```java
class Solution {
    public boolean PredictTheWinner(int[] nums) {
        int n = nums.length;
        int[][] dp = new int[n][n];

        // Base case
        for (int i = 0; i < n; i++) {
            dp[i][i] = nums[i];
        }

        // Fill DP table
        for (int len = 2; len <= n; len++) {
            for (int i = 0; i <= n - len; i++) {
                int j = i + len - 1;

                int pickLeft = nums[i] - dp[i + 1][j];
                int pickRight = nums[j] - dp[i][j - 1];

                dp[i][j] = Math.max(pickLeft, pickRight);
            }
        }

        return dp[0][n - 1] >= 0;
    }
}
```

---

# Dry Run

Input

```
nums = [1,5,233,7]
```

Diagonal

```
1
  5
    233
       7
```

Length = 2

```
[1,5] -> max(-4,4)=4

[5,233] -> 228

[233,7] ->226
```

Length = 3

```
[1,5,233]

max(
1-228,
233-4
)

=max(-227,229)

=229
```

```
[5,233,7]

max(
5-226,
7-228
)

=-221
```

Length = 4

```
[1,5,233,7]

max(
1-(-221),
7-229
)

=max(222,-222)

=222
```

Positive.

```
return true
```

---

# Complexity Analysis

* **Time Complexity:** `O(n²)`
  We fill an `n × n` DP table once.

* **Space Complexity:** `O(n²)`
  The DP table stores results for every subarray.

---

# Intuition to Remember

Whenever you see:

* Two players
* Both play optimally
* Players take turns
* Choose from available options (here, left or right end)

don't try to compute each player's absolute score. Instead, think in terms of the **maximum score difference** the current player can enforce. That transforms the game into a clean dynamic programming recurrence:

```
dp[i][j] =
max(
    nums[i] - dp[i + 1][j],
    nums[j] - dp[i][j - 1]
)
```

where `dp[i][j]` represents the best score advantage the current player can achieve over the opponent for the subarray `nums[i...j]`. This "score difference" approach is the key insight behind many optimal game DP problems.
