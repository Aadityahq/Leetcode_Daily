## Intuition

From any index `i`, you can jump:

* at most `d` steps left or right
* only to a **smaller value**
* and all elements between must also be **smaller than `arr[i]`**

The question asks:

> “What is the maximum number of indices we can visit starting from any index?”

This is a classic **DFS + DP (memoization)** problem.

---

# Key Observation

Suppose:

```text
arr = [6,4,14,6,8]
```

If we already know:

```text
maximum jumps starting from index 3 = 2
```

then whenever another index jumps to `3`, we don't recompute again.

So:

* Use **DFS** to explore jumps
* Use **DP memoization** to store answers

---

# Meaning of `dfs(i)`

```java
dfs(i)
```

means:

> Maximum indices we can visit starting from index `i`

Including the current index itself.

---

# Transition

From index `i`:

We try jumping:

* right → `i+1 ... i+d`
* left → `i-1 ... i-d`

But only if:

```java
arr[i] > arr[next]
```

If true:

```java
1 + dfs(next)
```

because:

* `1` → current index
* `dfs(next)` → best path after jump

Take maximum among all possibilities.

---

# Very Important Logic: `break`

This part is crucial:

```java
else break;
```

Why?

Because jumping rules say:

You can only jump if **all elements between are smaller**.

Suppose:

```text
[10, 8, 12, 7]
```

From `10`:

* can jump to `8`
* cannot jump beyond `12`

Because `12` blocks the path.

So once we find:

```java
arr[next] >= arr[i]
```

we stop searching further in that direction.

---

# Dry Run

## Example

```text
arr = [6,4,14,6,8,13,9,7,10,6,12]
d = 2
```

Start from index `10` (`12`):

Possible jumps:

* to `8` (`10`)
* to `9` (`6`)

Best path:

```text
10 -> 8 -> 6 -> 7
```

Length = `4`

---

# Code Explanation

## DFS Function

```java
int dfs(int[] a, int[] dp, int i, int d)
```

Parameters:

* `a` → array
* `dp[i]` → answer already computed
* `i` → current index
* `d` → max jump distance

---

## Memoization

```java
if(dp[i] != 0) return dp[i];
```

If already computed, return directly.

Avoids repeated work.

---

## Initial Answer

```java
int mx = 1;
```

At minimum we can always stay on current index.

So answer starts from `1`.

---

# Right Side Traversal

```java
for(int j=1; j<=d && i+j<n; j++)
```

Check all positions within distance `d`.

---

## Valid Jump

```java
if(a[i] > a[i+j])
```

Can jump only to smaller element.

Then:

```java
mx = Math.max(mx, 1 + dfs(...));
```

---

## Blocking Element

```java
else break;
```

A greater/equal element blocks further movement.

---

# Left Side Traversal

Same logic.

---

# Store Result

```java
return dp[i] = mx;
```

Memoize answer.

---

# Main Function

```java
for(int i=0; i<n; i++)
```

We can start from ANY index.

So compute best answer from every position.

---

# Time Complexity

Each index is solved once due to memoization.

For every index we check at most `d` positions left and right.

## Complexity

```text
O(n * d)
```

Where:

* `n <= 1000`

Efficient enough.

---

# Space Complexity

```text
O(n)
```

For DP array + recursion stack.

---

# Why DFS + DP Works Perfectly

Without memoization:

* same states recomputed many times
* exponential complexity

With DP:

* each index solved once
* becomes efficient

This pattern is called:

# DFS on DAG + Memoization

Because jumps always go from bigger → smaller values, cycles cannot form.

---

# Final Understanding

The solution works because:

1. From every index we explore all valid jumps
2. Recursively compute best path
3. Cache results using DP
4. Use `break` because larger elements block movement
5. Take maximum over all starting indices
