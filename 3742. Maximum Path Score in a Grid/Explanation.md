# Problem Understanding

We are given a grid where every cell contains:

| Value | Score Added | Cost Added |
| ----- | ----------- | ---------- |
| 0     | 0           | 0          |
| 1     | 1           | 1          |
| 2     | 2           | 1          |

We start from:

```text
(0,0)
```

and must reach:

```text
(m-1,n-1)
```

Allowed moves:

* Right
* Down

Goal:

* maximize total score
* while total cost ≤ `k`

If no valid path exists → return `-1`.

---

# Main Idea

At every cell we have two important things:

1. Current position `(i,j)`
2. Current cost used

Because:

* reaching the same cell with different costs changes future possibilities

So we must track:

```text
(row, col, costUsed)
```

This becomes a **DP + recursion (memoization)** problem.

---

# What Your DP Stores

```java
dp[i][j][cost]
```

means:

> Maximum score obtainable starting from `(i,j)`
> when current used cost is `cost`.

If impossible:

```java
limit = -2
```

is returned.

---

# Recursive Thinking

From every cell we have only 2 choices:

```text
go down
go right
```

We recursively compute both and take the better one.

---

# Important Observation

Cost is paid when entering/using a non-zero cell.

So:

```java
int toadd = (grid[i][j] != 0) ? 1 : 0;
```

* `0` cell → no cost
* `1` or `2` → cost increases by 1

---

# Dry Run

Grid:

```text
0 1
2 0
```

k = 1

---

## Start at `(0,0)`

Value = `0`

Cost remains `0`

Two choices:

### Go Right

Path:

```text
0 -> 1 -> 0
```

Score:

```text
1
```

Cost:

```text
1
```

---

### Go Down

Path:

```text
0 -> 2 -> 0
```

Score:

```text
2
```

Cost:

```text
1
```

Maximum = `2`

---

# Understanding the Base Cases

---

## 1. Cost Exceeded

```java
if(cost > k) return limit;
```

If cost becomes larger than allowed:

```text
invalid path
```

---

## 2. Reached Destination

```java
if(i == grid.length-1 && j == grid[0].length-1)
```

Now we check:

```java
if(grid[i][j] != 0)
    if(cost + 1 > k)
        return limit;
```

Because destination cell may also consume cost.

If valid:

```java
return grid[i][j];
```

Return the score of last cell.

---

## 3. Out of Bounds

```java
if(i == grid.length || j == grid[0].length)
    return limit;
```

Invalid move.

---

# Memoization

```java
if(dp[i][j][cost] != -1)
    return dp[i][j][cost];
```

Without memoization:

* same states are recomputed many times
* exponential recursion

Memoization stores already computed answers.

This reduces complexity drastically.

---

# Transition

```java
ans = Math.max(
    compute(i+1, j, cost+toadd, ...),
    compute(i, j+1, cost+toadd, ...)
);
```

We try:

* moving down
* moving right

and take the better score.

---

# Why Add Current Cell After Recursion?

```java
return dp[i][j][cost] =
       (ans == limit ? ans : ans + grid[i][j]);
```

Suppose:

Current cell = `2`

Future best path score = `5`

Then total becomes:

```text
2 + 5 = 7
```

So we add current cell value after choosing best future path.

---

# Why `limit = -2`?

Because valid scores can be:

```text
0,1,2,...
```

So `-2` safely represents:

```text
impossible state
```

Later:

```java
return ans == limit ? -1 : ans;
```

converts impossible answer into problem-required `-1`.

---

# Time Complexity

States:

[
m \times n \times k
]

Each state computes only once.

So:

[
O(m \times n \times k)
]

Efficient for constraints.

---

# Complete Intuition

This is a classic:

```text
Grid DP + Resource Constraint
```

Pattern:

```text
position + remaining budget/cost
```

We recursively explore all valid paths,
but memoization prevents repeated work.

So the algorithm efficiently finds:

```text
Maximum score path under cost limit
```
