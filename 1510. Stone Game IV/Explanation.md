### 1510. Stone Game IV

### Understanding the Problem

Alice and Bob are playing a game.

* There are n stones initially.

* On each turn, a player can remove any perfect square number of stones:

  * 1, 4, 9, 16, 25, …

* The player who cannot make a move loses.

* Both players play optimally (they always choose the best possible move).

We need to determine:

Will Alice win if both play perfectly?

### Example

### Example 1

Input:

```
n = 1
```

Alice removes `1² = 1`.

```
1 → 0
```

Bob has no stones left, so Alice wins.

Output:

```
true
```

### Example 2

Input:

```
n = 2
```

Alice can only remove `1`.

```
2 → 1
```

Now Bob removes `1`.

```
1 → 0
```

Alice cannot move, so Alice loses.

Output:

```
false
```

### Key Observation

For any number of stones `i`:

* If there exists at least one square number `s` such that after removing it the opponent is in a losing position, then `i` is a winning position.

This is a classic Dynamic Programming + Game Theory problem.

### DP Idea

Let:

```
dp[i] = true  → current player can win with i stones
dp[i] = false → current player loses with i stones
```

### Base Case

```
dp[0] = false
```

With `0` stones, the current player cannot move, so they lose.

### Transition

For every `i` from `1` to `n`:

Try every square number `j*j ≤ i`.

If:

```
dp[i - j*j] == false
```

then the opponent loses after this move, so:

```
dp[i] = true
```

and we stop checking further.

### Dry Run

Suppose `n = 5`.

### i = 1

Remove `1`:

```
dp[1-1] = dp[0] = false
```

So:

```
dp[1] = true
```

### i = 2

Remove `1`:

```
dp[1] = true
```

No losing state found.

```
dp[2] = false
```

### i = 3

Remove `1`:

```
dp[2] = false
```

So:

```
dp[3] = true
```

### i = 4

Possible squares: `1, 4`

Remove `4`:

```
dp[0] = false
```

So:

```
dp[4] = true
```

### i = 5

* Remove `1` → `dp[4] = true`

* Remove `4` → `dp[1] = true`

All moves lead to winning states for the opponent.

```
dp[5] = false
```

### Java Solution

Java

```
class Solution {
    public boolean winnerSquareGame(int n) {
        boolean[] dp = new boolean[n + 1];

        // dp[0] = false by default

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j * j <= i; j++) {
                int square = j * j;

                // If opponent loses after this move
                if (!dp[i - square]) {
                    dp[i] = true;
                    break;
                }
            }
        }

        return dp[n];
    }
}
```

### Why This Works

For every state `i`:

* We simulate all legal moves.

* If any move leaves the opponent in a losing state, the current player can choose that move and guarantee victory.

This follows the standard minimax principle used in impartial games.

### Complexity Analysis

Let `n` be up to `100000`.

For each `i`, we check at most `√i` square numbers.

### Time Complexity

```
O(n × √n)
```

For `n = 100000`, this is efficient enough.

### Space Complexity

```
O(n)
```

because we store the `dp` array.

### Intuition to Remember

Think of it as:

“Can I make a move that forces my opponent into a losing position?”

* Yes → current state is winning.

* No → current state is losing.

That single idea is the entire foundation of this problem.
