# 1871. Jump Game VII

## Problem Understanding

You are given a binary string `s`.

* `'0'` → you can stand on this index
* `'1'` → blocked index

You start at index `0`.

From any index `i`, you can jump to any index `j` such that:

[
i + minJump \le j \le i + maxJump
]

and `s[j] == '0'`.

You need to determine whether you can reach the last index.

---

# Example Walkthrough

## Example 1

```text
s = "011010"
minJump = 2
maxJump = 3
```

Indexes:

```text
0 1 2 3 4 5
0 1 1 0 1 0
```

Start at `0`.

Possible jumps:

* `0 + 2 = 2`
* `0 + 3 = 3`

Index `2` is `'1'` ❌
Index `3` is `'0'` ✅

Move to `3`.

From `3`:

* `3 + 2 = 5`
* `3 + 3 = 6` (out of bounds)

Index `5` is `'0'` ✅

Reached last index → `true`

---

# Brute Force Idea

From every reachable index:

* try every jump from `minJump` to `maxJump`

This becomes very slow.

Worst case:

```text
O(n * (maxJump-minJump))
```

With `n = 10^5`, this can cause TLE.

---

# Optimized Idea (BFS + Sliding Window)

We use:

* **BFS** → to explore reachable positions
* **Sliding window optimization** → avoid checking same indexes repeatedly

---

# Key Observation

Suppose from index `i` we can jump to:

```text
[i + minJump , i + maxJump]
```

If another node later checks overlapping ranges, we should NOT reprocess indexes already checked.

So we maintain:

```java
farthest
```

Meaning:

> all indexes before `farthest` are already processed.

This makes every index visited only once.

---

# Efficient BFS Logic

For each current index `i`:

```java
start = max(i + minJump, farthest)
end = min(i + maxJump, n - 1)
```

Check only this new range.

If:

```java
s.charAt(j) == '0'
```

then:

* mark reachable
* push into queue

---

# Why This Works

Without `farthest`:

* same indexes are checked many times

With `farthest`:

* every index is scanned once only

Thus total complexity becomes:

## Time Complexity

[
O(n)
]

## Space Complexity

[
O(n)
]

Perfect for `10^5`.

---

# Accepted Java Solution

```java
import java.util.*;

class Solution {
    public boolean canReach(String s, int minJump, int maxJump) {
        int n = s.length();

        Queue<Integer> queue = new LinkedList<>();
        queue.offer(0);

        boolean[] visited = new boolean[n];
        visited[0] = true;

        int farthest = 1;

        while (!queue.isEmpty()) {
            int current = queue.poll();

            // Explore new valid range only
            int start = Math.max(current + minJump, farthest);
            int end = Math.min(current + maxJump, n - 1);

            for (int i = start; i <= end; i++) {
                if (s.charAt(i) == '0' && !visited[i]) {

                    // Reached destination
                    if (i == n - 1) {
                        return true;
                    }

                    visited[i] = true;
                    queue.offer(i);
                }
            }

            // Update farthest checked position
            farthest = end + 1;
        }

        return n == 1;
    }
}
```

---

# Dry Run

## Input

```text
s = "011010"
minJump = 2
maxJump = 3
```

---

### Initial

```text
queue = [0]
farthest = 1
```

---

## Process index 0

Range:

```text
[2,3]
```

Check:

* 2 → '1' ❌
* 3 → '0' ✅

```text
queue = [3]
```

Update:

```text
farthest = 4
```

---

## Process index 3

Range:

```text
[5,5]
```

Check:

* 5 → '0' ✅

Reached last index.

Return `true`.

---

# Important Interview Points

## Why BFS?

Because:

* each position is a state
* we explore all reachable states level by level

---

## Why `farthest` is necessary?

Without it:

* overlapping ranges repeatedly scan same indexes
* causes TLE

It converts repeated scanning into single-pass scanning.

---

# Alternate DP Approach

Another accepted approach uses:

* DP array
* Prefix sum / sliding count

But BFS + farthest is:

* easier to understand
* easier to implement
* very common in interviews

---

# Final Takeaway

The core trick is:

```text
Never scan the same range twice
```

That single optimization reduces complexity from potentially quadratic to linear.
