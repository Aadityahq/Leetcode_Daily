Here’s a simple explanation of LeetCode 1140 – Stone Game II, followed by the Java solution and why it works.

### Understanding the Problem

You have piles of stones in a row.

Example:

Example

piles = [2, 7, 9, 4, 4]

Two players:

* Alice starts first.

* Bob plays second.

A variable M controls how many piles can be taken.

Initially:

M = 1

On each turn, a player can take the first X remaining piles, where:

1≤X≤2M1 \le X \le 2M1≤X≤2M

After taking:

M=max⁡(M,X)M = \max(M, X)M=max(M,X)

Both players play optimally (they always make the best possible move).

We must return the maximum stones Alice can collect.

### Why Greedy Does NOT Work

Suppose Alice always takes the maximum possible piles.

For:

Input

[2, 7, 9, 4, 4]

If Alice takes 2 piles immediately:

* Alice gets 2 + 7 = 9

* Bob takes the rest.

But if Alice takes 1 pile first, she can later get 10 stones.

So we must consider future consequences, which means Dynamic Programming (DP) is needed.

### Key Idea

Instead of directly calculating Alice’s stones, define:

dp(i,M)=maximum stones current player can get from index i onwarddp(i, M) = \text{maximum stones current player can get from index } i \text{ onward}dp(i,M)=maximum stones current player can get from index i onward

Let:

* suffix[i]suffix[i]suffix[i] = total stones from iii to end.

If the current player takes XXX piles:

* They immediately leave the remaining piles to the opponent.

* The opponent can then get dp(i+X,max⁡(M,X))dp(i+X, \max(M,X))dp(i+X,max(M,X)) stones.

So the current player gets:

suffix[i]−dp(i+X,max⁡(M,X))\text{suffix}[i] - dp(i+X,\max(M,X))suffix[i]−dp(i+X,max(M,X))

We try all valid XXX and take the maximum.

### Step-by-Step Example

For:

Input

[2, 7, 9, 4, 4]

Suffix sums:

| Index | Suffix Sum |
| ----- | ---------- |
| 0     | 26         |
| 1     | 24         |
| 2     | 17         |
| 3     | 8          |
| 4     | 4          |

Start:

dp(0,1)

Possible choices:

* Take 1 pile → opponent gets dp(1,1)dp(1,1)dp(1,1).

* Take 2 piles → opponent gets dp(2,2)dp(2,2)dp(2,2).

The optimal result becomes 10.

### Java Solution (Memoized DP)

Java

```
class Solution {
    private int[][] memo;
    private int[] suffix;
    private int n;

    public int stoneGameII(int[] piles) {
        n = piles.length;

        // suffix[i] = total stones from i to end
        suffix = new int[n + 1];
        for (int i = n - 1; i >= 0; i--) {
            suffix[i] = suffix[i + 1] + piles[i];
        }

        memo = new int[n][n + 1];
        return dfs(0, 1);
    }

    private int dfs(int i, int m) {
        // no piles left
        if (i >= n) return 0;

        // if we can take all remaining piles
        if (i + 2 * m >= n) {
            return suffix[i];
        }

        if (memo[i][m] != 0) {
            return memo[i][m];
        }

        int best = 0;

        // try taking X piles (1 to 2M)
        for (int x = 1; x <= 2 * m; x++) {
            int opponent = dfs(i + x, Math.max(m, x));
            best = Math.max(best, suffix[i] - opponent);
        }

        return memo[i][m] = best;
    }
}
```

### How This Code Works

### 1. Suffix Sum

Java

```
suffix[i] = suffix[i + 1] + piles[i];
```

This allows us to get the total remaining stones in O(1) time.

### 2. Base Case

Java

```
if (i + 2 * m >= n)
    return suffix[i];
```

If the player is allowed to take all remaining piles, they should do so.

### 3. Recursive Choice

Java

```
for (int x = 1; x <= 2 * m; x++) {
    int opponent = dfs(i + x, Math.max(m, x));
    best = Math.max(best, suffix[i] - opponent);
}
```

We simulate every legal move.

* suffix[i]suffix[i]suffix[i] = all remaining stones.

* opponentopponentopponent = best the opponent can achieve afterward.

* Subtracting gives the current player’s gain.

### Why This Formula Is Correct

Suppose remaining stones = 26.

If after your move the opponent can eventually collect 16, then:

26−16=1026 - 16 = 1026−16=10

So you can guarantee 10 stones.

This is a standard zero-sum game technique.

### Time Complexity

* States: n×nn \times nn×n (index and M)

* Each state tries at most 2M≤2n2M \le 2n2M≤2n moves.

O(n3)O(n^3)O(n3)

With n≤100n \le 100n≤100, this easily fits the limits.

### Dry Run (Short)

For:

```
piles = [1,2,3,4,5,100]
```

Alice can strategically force access to the last pile 100.

Optimal play gives:

```
104
```

which matches the expected output.

### Important Interview Insight

The most important observation is:

Current player's stones = Remaining stones − Opponent's best result

That converts a difficult minimax game into a clean memoized DP problem.

This pattern appears in many Stone Game problems on LeetCode.
