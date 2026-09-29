## LeetCode 2267 — Check if There Is a Valid Parentheses String Path

### 1. Understand the problem

We have a grid containing only:

* `'('`
* `')'`

We start at `(0,0)` and must reach `(m-1,n-1)`.

We can move only:

* **Right**
* **Down**

Every cell we visit adds one parenthesis to our string.

We need to check whether **at least one path** produces a **valid parentheses string**.

For a parentheses string to be valid:

1. At every point while reading from left to right, the number of `)` cannot be greater than the number of `(`.
2. At the end, the number of `(` must equal the number of `)`.

---

# 2. Important observation

Suppose while traversing the path:

```text
( ( ) ( ) )
```

We can keep a variable called `balance`.

* `'('` → `balance + 1`
* `')'` → `balance - 1`

For the string to be valid:

```text
balance >= 0
```

at every step, and finally:

```text
balance == 0
```

So the problem becomes:

> Is there a path from `(0,0)` to `(m-1,n-1)` such that its balance never becomes negative and ends at 0?

---

# 3. First important check: path length

Every path from `(0,0)` to `(m-1,n-1)` visits exactly:

```text
m + n - 1
```

cells.

A valid parentheses string must have **even length**.

Therefore, if:

```text
(m + n - 1) % 2 != 0
```

we can immediately return `false`.

For example:

```text
m = 2, n = 2

path length = 2 + 2 - 1 = 3
```

A string of length 3 can never be a valid parentheses string.

---

# 4. Why normal DP is not enough

Normally for a grid we might use:

```text
dp[i][j]
```

to store whether we can reach `(i,j)`.

But here reaching the same cell with different balances is different.

For example, suppose we reach:

```text
(i, j)
```

with:

```text
balance = 2
```

and another path reaches the same cell with:

```text
balance = 0
```

These are not equivalent.

The future cells will affect these two balances differently.

So we need:

```text
dp[i][j][balance]
```

Meaning:

> Can we reach cell `(i,j)` with the current parentheses balance equal to `balance`?

---

# 5. DP state

We can define:

```java
boolean[][][] dp
```

where:

```text
dp[i][j][balance]
```

means:

> There exists a path from `(0,0)` to `(i,j)` whose current balance is `balance`.

The maximum possible balance is at most the path length, which is at most:

```text
100 + 100 - 1 = 199
```

So this is manageable.

---

# 6. Transition

Suppose we are at:

```text
(i, j)
```

with balance:

```text
balance
```

The current cell determines the new balance.

If:

```text
grid[i][j] == '('
```

then:

```text
newBalance = balance + 1
```

Otherwise:

```text
newBalance = balance - 1
```

But there is an important condition:

```text
newBalance >= 0
```

If the balance becomes negative, the parentheses string is already invalid.

---

# 7. Java Solution

```java
class Solution {

    public boolean hasValidPath(char[][] grid) {

        int m = grid.length;
        int n = grid[0].length;

        // Total number of cells in every possible path
        int length = m + n - 1;

        // A valid parentheses string must have even length
        if (length % 2 != 0) {
            return false;
        }

        // Maximum possible balance is length
        boolean[][][] dp = new boolean[m][n][length + 1];

        // Starting cell
        int startBalance = grid[0][0] == '(' ? 1 : -1;

        // If first character is ')', it is already invalid
        if (startBalance < 0) {
            return false;
        }

        dp[0][0][startBalance] = true;

        // Traverse the grid
        for (int i = 0; i < m; i++) {

            for (int j = 0; j < n; j++) {

                // Skip starting cell
                if (i == 0 && j == 0) {
                    continue;
                }

                // Current cell contribution
                int change = grid[i][j] == '(' ? 1 : -1;

                for (int balance = 0; balance <= length; balance++) {

                    int newBalance = balance + change;

                    // Balance cannot become negative
                    if (newBalance < 0 || newBalance > length) {
                        continue;
                    }

                    // Come from the top
                    if (i > 0 && dp[i - 1][j][balance]) {
                        dp[i][j][newBalance] = true;
                    }

                    // Come from the left
                    if (j > 0 && dp[i][j - 1][balance]) {
                        dp[i][j][newBalance] = true;
                    }
                }
            }
        }

        // Valid parentheses string must end with balance 0
        return dp[m - 1][n - 1][0];
    }
}
```

---

# 8. Let's understand the DP with a small example

Consider:

```text
(
(
)
)
```

Suppose the grid is:

```text
[
    ['(', '('],
    [')', ')']
]
```

There are two possible paths.

One path:

```text
( → ( → ) 
```

Actually, because a `2 x 2` grid has only 3 cells per path:

```text
m + n - 1 = 3
```

So it cannot be valid.

Our first condition catches this:

```java
if (length % 2 != 0) {
    return false;
}
```

---

Consider a path of length 6:

```text
( ( ) ( ) )
```

Track the balance:

```text
Character     Balance

(               1
(               2
)               1
(               2
)               1
)               0
```

The balance never becomes negative and ends at `0`.

Therefore:

```text
valid
```

Our DP eventually stores:

```text
dp[lastRow][lastColumn][0] = true
```

and returns `true`.

---

# 9. Why do we check `balance >= 0`?

Consider:

```text
) ( )
```

Balance:

```text
) → -1
```

The moment balance becomes negative, the string can never become a valid parentheses string.

Even if we later get:

```text
( → 0
```

the original string is still invalid.

Therefore:

```java
if (newBalance < 0) {
    continue;
}
```

is extremely important.

---

# 10. Why do we only check balance `0` at the end?

Consider:

```text
( ( ) )
```

Balances:

```text
1 → 2 → 1 → 0
```

Valid.

But:

```text
( ( )
```

balances:

```text
1 → 2 → 1
```

The balance never became negative, but it ended at `1`.

Therefore, it is **not valid**.

That's why we need both conditions:

### During traversal

```text
balance >= 0
```

### At the end

```text
balance == 0
```

---

# 11. Why can we use a 3D DP?

The three dimensions represent:

```text
dp[row][column][balance]
```

For example:

```text
dp[2][3][4] = true
```

means:

> There is a path from `(0,0)` to `(2,3)` that produces a parentheses string with balance `4`.

This is the key idea of the problem.

We aren't just asking:

> Can I reach this cell?

We are asking:

> Can I reach this cell **with a particular balance**?

---

# 12. Complexity

There are:

```text
m × n
```

cells.

For every cell, we check up to:

```text
m + n - 1
```

possible balances.

Therefore:

### Time Complexity

```text
O(m × n × (m + n))
```

With `m,n <= 100`:

```text
100 × 100 × 199
≈ 2 million
```

which is completely manageable.

### Space Complexity

```text
O(m × n × (m + n))
```

because of:

```java
boolean[][][] dp
```

---

# 13. The complete thought process to remember

For this problem, remember these **4 steps**:

### Step 1 — Check path length

```java
int length = m + n - 1;

if (length % 2 != 0) {
    return false;
}
```

A valid parentheses string must have even length.

### Step 2 — Track balance

```text
'(' → +1
')' → -1
```

### Step 3 — Never allow negative balance

```text
balance < 0 → invalid path
```

### Step 4 — At destination, balance must be zero

```java
return dp[m - 1][n - 1][0];
```

So the core idea is:

> **Grid Path + Parentheses Balance = 3D DP**

This is the important pattern to recognize for LeetCode 2267.
