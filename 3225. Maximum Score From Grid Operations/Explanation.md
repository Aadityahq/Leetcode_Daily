# Understanding the Problem

You have an `n x n` grid.

Initially:

* every cell is **white**

Operation:

* choose a column `j`
* choose a row `i`
* color black all cells from top → row `i` in column `j`

So each column finally looks like:

* top part = black
* remaining bottom part = white

---

## Important Observation

For every column, only one thing matters:

> how many cells from the top are black

Let's define:

* `h[j]` = number of black cells in column `j`

Example:

```text
h[j] = 3
```

means rows:

```text
0,1,2 => black
3...n-1 => white
```

---

# When does a white cell contribute to score?

A white cell contributes if:

* it is white
* and one of its horizontal neighbors is black

So for a cell `(r,c)`:

it contributes if:

* `(r,c)` is white
* and either:

  * left neighbor black
  * right neighbor black

---

# Core Insight

Suppose we compare two neighboring columns:

```text
column A height = a
column B height = b
```

---

## Case 1: `a > b`

Rows:

```text
0 ... b-1      both black
b ... a-1      A black, B white
a ... end      both white
```

Only rows `b → a-1` in column B contribute.

Contribution:

```text
sum(grid[r][B]) for r in [b, a-1]
```

---

## Case 2: `b > a`

Similarly:
Rows `a → b-1` in column A contribute.

---

# Big DP Idea

We process columns from left → right.

For each column we store:

* how many black cells current column has

Transition between neighboring columns determines score contribution.

---

# Meaning of DP Arrays

The solution uses 2 DP states:

```java
dp0[i]
dp1[i]
```

where `i` = black height of previous column.

---

The states are subtle.

---

# State Meaning

## `dp0[i]`

Maximum score so far when:

* previous column has height `i`
* and current interaction is "open"

---

## `dp1[i]`

Maximum score so far when:

* previous column has height `i`
* and contribution between previous columns already resolved

---

You do NOT need to fully memorize semantic meaning.

The important part:

> We transition between every pair of heights.

---

# Transition Logic

We iterate:

```java
for current previous-height i
for next-height y
```

---

# What are `prev` and `curr`?

These are the MOST IMPORTANT variables.

---

## `curr`

Contribution when:

```text
next column height < current column height
```

Meaning:
current column has some white cells touching black cells from left.

---

## `prev`

Contribution when:

```text
next column height > current column height
```

Meaning:
previous column gets contribution.

---

# Visual Understanding

Suppose:

```text
previous height = 2
next height = 5
```

Rows:

```text
0,1 => both black
2,3,4 => prev white, next black
```

So contribution comes from previous column.

That is what `prev` accumulates.

---

Suppose:

```text
previous height = 5
next height = 2
```

Rows:

```text
2,3,4 => next white, prev black
```

Contribution comes from next column.

That is what `curr` accumulates.

---

# Why Prefix-like Running Updates?

Instead of recomputing sums repeatedly:

```java
for rows ...
```

the code incrementally updates:

```java
curr -= ...
prev += ...
```

making transitions efficient.

---

# Full Walkthrough of Code

---

# Initialization

```java
long[] dp0 = new long[n + 1];
long[] dp1 = new long[n + 1];
```

Initially:

* before processing columns
* score = 0

---

# Process Adjacent Columns

```java
for (int j = 1; j < n; j++)
```

We process pair:

```text
(j-1 , j)
```

---

# New DP Arrays

```java
long[] new_dp0 = new long[n + 1];
long[] new_dp1 = new long[n + 1];
```

---

# Previous Column Height

```java
for (int i = 0; i <= n; i++)
```

`i` = black height of previous column.

---

# Initialize curr

```java
for (int x = 0; x < i; x++)
    curr += grid[x][j];
```

Initially assumes:

```text
y = 0
```

meaning next column entirely white.

So rows `[0 ... i-1]`
in current column contribute.

---

# Iterate New Height

```java
for (int y = 0; y <= n; y++)
```

`y` = next column black height.

---

# Update curr

```java
if (y > 0 && y <= i)
    curr -= grid[y - 1][j];
```

As black height grows,
fewer rows remain contributing.

---

# Update prev

```java
if (y > i)
    prev += grid[y - 1][j - 1];
```

When next column becomes taller,
previous column gains contribution.

---

# DP Transition

```java
new_dp0[y] =
    Math.max(new_dp0[y],
             Math.max(prev + dp0[i], dp1[i]));
```

and

```java
new_dp1[y] =
    Math.max(new_dp1[y],
             Math.max(curr + dp1[i],
                      curr + prev + dp0[i]));
```

These combine:

* previous best scores
* current pair contribution

---

# Final Answer

```java
long res = 0;
for (long v : dp1)
    res = Math.max(res, v);
```

Take maximum over all ending heights.

---

# Complexity

Outer loops:

```text
j -> n
i -> n
y -> n
```

Total:

```text
O(n^3)
```

With `n ≤ 100`, this works.

---

# Real Intuition Behind Entire Problem

The whole problem reduces to:

> Choosing heights for every column.

Then every adjacent pair contributes:

```text
difference interval sum
```

DP tries all neighboring height combinations and builds optimal answer column-by-column.

---

# Tiny Example

Suppose:

```text
Column A height = 4
Column B height = 2
```

Then rows:

```text
2,3
```

in column B are:

* white
* adjacent to black in A

Contribution:

```text
grid[2][B] + grid[3][B]
```

That exact idea powers the entire DP.
