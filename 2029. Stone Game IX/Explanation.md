## LeetCode 2029 — Stone Game IX

### Problem Explanation

We have a collection of stones. Each stone has a value, and Alice and Bob take turns removing one stone.

The important rule is:

> If the sum of all removed stones becomes divisible by `3`, the player who removed that stone **loses immediately**.

Alice plays first, and if all stones are removed without anyone losing, **Bob wins automatically**.

We need to determine whether Alice can win if both players play optimally.

---

# 1. Key Observation — Only `% 3` Matters

We don't actually care about the exact value of a stone.

We only care about its remainder when divided by `3`.

For example:

```text
1 % 3 = 1
4 % 3 = 1
7 % 3 = 1
```

All of these behave exactly the same way.

Similarly:

```text
2 % 3 = 2
5 % 3 = 2
8 % 3 = 2
```

And:

```text
3 % 3 = 0
6 % 3 = 0
9 % 3 = 0
```

Therefore, we divide all stones into three groups:

```text
cnt[0] → stones whose remainder is 0
cnt[1] → stones whose remainder is 1
cnt[2] → stones whose remainder is 2
```

We calculate these counts using:

```java
for (int stone : stones) {
    cnt[stone % 3]++;
}
```

---

# 2. Why are the Three Groups Important?

Suppose the current sum has remainder `1`.

If we take:

```text
remainder 0 → 1 + 0 = 1
remainder 1 → 1 + 1 = 2
remainder 2 → 1 + 2 = 0
```

Taking a `2` would make the sum divisible by `3`.

Therefore, that player would immediately lose.

Similarly, if the current sum has remainder `2`:

```text
2 + 0 = 2
2 + 1 = 0   ← losing move
2 + 2 = 1
```

So:

* When the current remainder is `1`, taking `2` is losing.
* When the current remainder is `2`, taking `1` is losing.
* Taking `0` never changes the remainder.

This creates a very specific pattern in the game.

---

# 3. Alice Cannot Start With a `0`

Initially:

```text
sum = 0
```

If Alice takes a stone whose remainder is `0`:

```text
0 + 0 = 0
```

The sum is divisible by `3`.

Therefore, Alice immediately loses.

So Alice's first useful choices are:

```text
remainder 1
```

or

```text
remainder 2
```

That's why our solution checks both possibilities:

```java
return check(cnt[0], cnt[1], cnt[2])
        || check(cnt[0], cnt[2], cnt[1]);
```

The first `check()` assumes:

```text
Alice starts with 1
```

The second assumes:

```text
Alice starts with 2
```

Because the game is symmetric, we can swap the roles of `1` and `2`.

---

# 4. Understanding `check()`

Our function is:

```java
private boolean check(int zero, int one, int two)
```

Here:

```text
zero = number of remainder-0 stones
one  = number of remainder-1 stones
two  = number of remainder-2 stones
```

We assume Alice starts by taking a `1`.

---

## Step 1 — Alice takes a `1`

```java
if (one == 0) {
    return false;
}

one--;
```

If there are no `1`s, Alice cannot make this starting move.

So:

```java
if (one == 0) {
    return false;
}
```

Otherwise Alice takes one:

```java
one--;
```

---

# 5. Why Do We Use Pairs?

After Alice takes `1`, the sum has remainder:

```text
1
```

Now Bob cannot take `2`, because:

```text
1 + 2 = 3
```

and Bob would immediately lose.

Therefore Bob has to take another `1`.

After Bob takes `1`:

```text
1 + 1 = 2
```

Now Alice cannot take `1`, because:

```text
2 + 1 = 3
```

So Alice takes `2`.

This creates the pattern:

```text
Alice → 1
Bob   → 1
Alice → 2
Bob   → 1
Alice → 2
Bob   → 1
...
```

After Alice's first move, the remaining `1`s and `2`s are therefore consumed in pairs:

```text
(1, 2)
(1, 2)
(1, 2)
...
```

So we calculate:

```java
int pairs = Math.min(one, two);
```

For example, if:

```text
one = 4
two = 3
```

we can form:

```text
(1,2)
(1,2)
(1,2)
```

So:

```text
pairs = 3
```

---

# 6. Calculating the Number of Moves

Alice already made the first move.

So:

```java
int moves = 1;
```

Each pair:

```text
(1,2)
```

contains two moves.

Therefore:

```java
int moves = 1 + pairs * 2;
```

For example:

```text
pairs = 3

moves = 1 + 3 × 2
      = 7
```

---

# 7. What if There Are Extra `1`s?

Suppose after Alice's first move:

```text
one = 4
two = 2
```

We can create only:

```text
(1,2)
(1,2)
```

Two pairs.

But one `1` is still available.

Therefore:

```java
if (one > two) {
    moves++;
    one--;
}
```

We count that extra move.

---

# 8. What About `0` Stones?

This is the tricky part.

A stone with remainder `0` doesn't change the current remainder.

For example:

```text
current sum = 4

4 % 3 = 1

4 + 3 = 7

7 % 3 = 1
```

So a `0` stone doesn't affect whether the sum is `1` or `2` modulo `3`.

But it **does consume a turn**.

Therefore, `0` stones affect who gets the final turn.

That's why we add:

```java
moves += zero;
```

We don't need to do complicated calculations with the `0` stones.

We only need to know how many extra turns they create.

---

# 9. Final Winning Condition

Finally:

```java
return moves % 2 == 1 && one != two;
```

There are two things we check.

### Condition 1 — Odd number of moves

```java
moves % 2 == 1
```

Alice starts first.

Therefore:

```text
Odd number of moves → Alice gets the last move
Even number of moves → Bob gets the last move
```

So Alice needs the appropriate turn parity.

---

### Condition 2 — `one != two`

```java
one != two
```

We need to make sure the game doesn't end in a state where Alice is forced to make a move that makes the sum divisible by `3`.

If the remaining `1`s and `2`s are perfectly balanced in the wrong situation, the opponent can force the losing move.

---

# Complete Solution

```java
class Solution {
    public boolean stoneGameIX(int[] stones) {
        int[] cnt = new int[3];

        // Count stones according to their remainder modulo 3
        for (int stone : stones) {
            cnt[stone % 3]++;
        }

        /*
         * Try both possible first moves:
         *
         * 1. Alice starts with remainder 1
         * 2. Alice starts with remainder 2
         */
        return check(cnt[0], cnt[1], cnt[2])
            || check(cnt[0], cnt[2], cnt[1]);
    }

    private boolean check(int zero, int one, int two) {

        // Alice cannot start with a remainder-1 stone
        // if no such stone exists.
        if (one == 0) {
            return false;
        }

        // Alice takes one remainder-1 stone.
        one--;

        /*
         * After Alice takes 1:
         *
         * Bob cannot take 2 because:
         * 1 + 2 = 3
         *
         * Therefore the game follows the pattern:
         *
         * Alice -> 1
         * Bob   -> 1
         * Alice -> 2
         * Bob   -> 1
         * Alice -> 2
         * ...
         *
         * So we pair remaining 1s and 2s.
         */
        int pairs = Math.min(one, two);

        // Alice's first move + all (1,2) pairs
        int moves = 1 + pairs * 2;

        /*
         * If there are more 1s than 2s,
         * one extra 1 can be taken.
         */
        if (one > two) {
            moves++;
            one--;
        }

        /*
         * Remainder-0 stones don't change the sum modulo 3,
         * but they consume one turn each.
         *
         * Therefore they affect the parity of the moves.
         */
        moves += zero;

        /*
         * Alice wins if:
         *
         * 1. Alice gets the required final turn.
         * 2. The remaining 1s and 2s don't create a forced
         *    losing situation.
         */
        return moves % 2 == 1 && one != two;
    }
}
```

## Dry Run

Consider:

```text
stones = [2, 1]
```

Remainder counts:

```text
cnt[0] = 0
cnt[1] = 1
cnt[2] = 1
```

First we try:

```java
check(0, 1, 1)
```

Alice takes `1`:

```text
one = 0
two = 1
```

Pairs:

```text
min(0, 1) = 0
```

Moves:

```text
moves = 1
```

No `0` stones.

So:

```text
moves = 1
```

and there is still a `2`.

Therefore Alice can win.

The actual game is:

```text
Alice → 1
Bob   → 2
```

Bob's move makes:

```text
1 + 2 = 3
```

So Bob loses.

Result:

```text
true
```

---

## Complexity

### Time Complexity

```text
O(n)
```

We traverse the `stones` array once.

### Space Complexity

```text
O(1)
```

We only use an array of size `3`.

---

### Interview explanation in one minute

If the interviewer asks **"How did you solve it?"**, you can say:

> "The actual stone values don't matter; only their remainder modulo 3 matters. So I count how many stones have remainder 0, 1, and 2. Alice cannot start with a remainder-0 stone because the initial sum is already divisible by 3. Therefore, I try both possible starting moves: remainder 1 and remainder 2. After Alice starts with 1, Bob cannot choose 2 because that would make the sum divisible by 3, so the subsequent moves follow an alternating pattern involving 1s and 2s. I count these pairs and also account for remainder-0 stones, which don't change the modulo but do consume a turn and therefore change the turn parity. Finally, I check whether Alice gets the winning turn. Since we only count the three remainders, the solution takes O(n) time and O(1) space."
