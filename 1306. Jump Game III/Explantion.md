## Intuition of the Problem

You are standing at index `start` in the array.

From any index `i`, you can jump:

* Forward → `i + arr[i]`
* Backward → `i - arr[i]`

Your task is to check:

> Can we reach **any index having value `0`**?

---

## Example Understanding

### Example

```text
arr = [4,2,3,0,3,1,2]
start = 5
```

Start at index `5`.

```text
arr[5] = 1
```

So possible jumps:

* `5 + 1 = 6`
* `5 - 1 = 4`

Now continue exploring.

Path:

```text
5 → 4 → 1 → 3
```

At index `3`:

```text
arr[3] = 0
```

So answer = `true`.

---

# Main Idea

This problem is basically a **graph traversal** problem.

Think:

* Every index = a node
* Jumps = edges

We need to know:

> Is there a path from `start` to any index containing `0`?

This can be solved using:

* BFS
* DFS

We will use **DFS** here.

---

# Important Observation

Without tracking visited indices, we may get stuck in an infinite loop.

Example:

```text
1 ↔ 3 ↔ 1 ↔ 3 ...
```

So we need a `visited[]` array.

---

# DFS Approach

At any index:

## 1. Base Cases

### Out of bounds

If index becomes negative or exceeds array size:

```java
return false;
```

---

### Already visited

Avoid cycles.

```java
return false;
```

---

### Found zero

```java
if(arr[index] == 0)
    return true;
```

---

## 2. Mark visited

```java
visited[index] = true;
```

---

## 3. Explore both directions

```java
forward = index + arr[index]
backward = index - arr[index]
```

If either path reaches `0`, answer is true.

---

# Dry Run

```text
arr = [4,2,3,0,3,1,2]
start = 0
```

Start:

```text
index = 0
arr[0] = 4
```

Possible jumps:

```text
0 + 4 = 4
0 - 4 = -4 (invalid)
```

Go to `4`

```text
4 -> 1 -> 3
```

At `3`:

```text
arr[3] = 0
```

Return `true`.

---

# Time Complexity

Each index is visited at most once.

```text
Time: O(n)
```

---

# Space Complexity

Visited array + recursion stack.

```text
Space: O(n)
```

---

# Java Solution (DFS)

```java
class Solution {

    public boolean canReach(int[] arr, int start) {

        boolean[] visited = new boolean[arr.length];

        return dfs(arr, start, visited);
    }

    private boolean dfs(int[] arr, int index, boolean[] visited) {

        // Out of bounds
        if(index < 0 || index >= arr.length) {
            return false;
        }

        // Already visited
        if(visited[index]) {
            return false;
        }

        // Found zero
        if(arr[index] == 0) {
            return true;
        }

        // Mark visited
        visited[index] = true;

        // Explore both directions
        int forward = index + arr[index];
        int backward = index - arr[index];

        return dfs(arr, forward, visited) ||
               dfs(arr, backward, visited);
    }
}
```

---

# Why This Works

At every index, we try all possible valid jumps.

Because:

* we mark visited indices,
* we never revisit nodes,
* and we explore every reachable position,

if a `0` is reachable, DFS will definitely find it.

---

# Alternative (BFS)

This problem can also be solved using:

* Queue
* Breadth First Search

BFS is iterative and avoids recursion stack overflow.

But DFS is simpler and cleaner for understanding.
