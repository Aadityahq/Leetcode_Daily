io# Intuition of the Problem

You are standing at index `0` of the array and want to reach the last index in **minimum jumps**.

From any index `i`, you can move to:

* `i + 1`
* `i - 1`
* Any index `j` where `arr[i] == arr[j]`

The important thing is:

> Every jump costs `1 step`.

Whenever a problem asks for:

* **minimum steps**
* **minimum moves**
* **shortest path**

we should immediately think about:

# BFS (Breadth First Search)

Because BFS explores all positions level by level, the first time we reach the last index will always be the minimum number of steps.

---

# Example Understanding

Array:

```text
[100,-23,-23,404,100,23,23,23,3,404]
```

Start at index `0`.

Possible jumps from index `0`:

* index `1`
* index `4` because `arr[0] = arr[4] = 100`

Best path:

```text
0 → 4 → 3 → 9
```

Total jumps = `3`

---

# Main Idea

We treat every index as a node in a graph.

From one node we can go to:

* left neighbor
* right neighbor
* all same-valued indices

Then use BFS to find shortest path.

---

# Step-by-Step Approach

## Step 1: Store Same Value Indices

We need fast access to all indices having same value.

Example:

```text
arr = [7,6,9,6,9,6,9,7]
```

Map becomes:

```text
7 -> [0,7]
6 -> [1,3,5]
9 -> [2,4,6]
```

So from index `0`, we can directly jump to index `7`.

We use:

```java
HashMap<Integer, List<Integer>>
```

---

# Step 2: BFS Traversal

We use:

```java
Queue<Integer>
```

Queue stores indices to visit.

We also use:

```java
boolean visited[]
```

to avoid revisiting indices.

---

# Why Visited Array?

Without visited:

* same nodes get added many times
* infinite loops possible
* TLE occurs

---

# Step 3: Explore 3 Possible Moves

From current index:

## Option 1 → Move Right

```java
currIdx + 1
```

---

## Option 2 → Move Left

```java
currIdx - 1
```

---

## Option 3 → Jump to Same Values

```java
for(int newIdx : mp.get(arr[currIdx]))
```

This explores all indices having same value.

---

# MOST IMPORTANT OPTIMIZATION

```java
mp.get(arr[currIdx]).clear();
```

This line is extremely important.

---

# Why Do We Clear?

Suppose:

```text
arr = [7,7,7,7,7,7]
```

Without clearing:

Every time we visit a `7`, we again iterate through all `7` indices.

That becomes:

```text
O(N²)
```

and causes TLE.

---

# After Clearing

Once all same-value indices are processed once:

```java
mp.get(arr[currIdx]).clear();
```

we never process them again.

This makes complexity nearly:

# O(N)

---

# Dry Run

## Input

```text
[7,6,9,6,9,6,9,7]
```

Map:

```text
7 -> [0,7]
6 -> [1,3,5]
9 -> [2,4,6]
```

---

## BFS Start

Queue:

```text
[0]
```

Steps = `0`

---

## Process index 0

Possible moves:

* 1
* 7 (same value)

Queue:

```text
[1,7]
```

Steps = `1`

Now when we process:

```text
7
```

it is the last index.

Answer = `1`

---

# Complete Java Solution

```java
class Solution {
    public int minJumps(int[] arr) {

        int n = arr.length;

        // Edge case
        if(n == 1) return 0;

        // Store all indices for same values
        HashMap<Integer, List<Integer>> mp = new HashMap<>();

        for(int i = 0; i < n; i++) {
            mp.putIfAbsent(arr[i], new ArrayList<>());
            mp.get(arr[i]).add(i);
        }

        // BFS queue
        Queue<Integer> q = new LinkedList<>();

        // Visited array
        boolean[] visited = new boolean[n];

        q.offer(0);
        visited[0] = true;

        int steps = 0;

        while(!q.isEmpty()) {

            int size = q.size();

            while(size-- > 0) {

                int currIdx = q.poll();

                // Reached last index
                if(currIdx == n - 1) {
                    return steps;
                }

                // Move Right
                if(currIdx + 1 < n && !visited[currIdx + 1]) {
                    visited[currIdx + 1] = true;
                    q.offer(currIdx + 1);
                }

                // Move Left
                if(currIdx - 1 >= 0 && !visited[currIdx - 1]) {
                    visited[currIdx - 1] = true;
                    q.offer(currIdx - 1);
                }

                // Jump to same value indices
                for(int newIdx : mp.get(arr[currIdx])) {

                    if(!visited[newIdx]) {
                        visited[newIdx] = true;
                        q.offer(newIdx);
                    }
                }

                // Important optimization
                mp.get(arr[currIdx]).clear();
            }

            steps++;
        }

        return -1;
    }
}
```

---

# Time Complexity

## Building Map

```text
O(N)
```

## BFS Traversal

Each index visited once.

Because of:

```java
clear()
```

same-value lists are processed once.

So overall:

# O(N)

---

# Space Complexity

* HashMap → `O(N)`
* Queue → `O(N)`
* Visited array → `O(N)`

Total:

# O(N)

---

# Key Learning From This Problem

This is a classic:

# BFS + Graph Traversal + Optimization

Important concepts learned:

* Convert array problem into graph
* BFS for shortest path
* HashMap for grouping
* Avoid repeated processing using `clear()`

---

# Interview Explanation (Short Version)

> We model each index as a graph node.
> From each node, we can move left, right, or to indices having same value.
> Since we need minimum jumps, we use BFS.
> A hashmap stores all indices for each value.
> We use visited array to avoid revisits.
> After processing same-value indices once, we clear the list to avoid repeated traversals and reduce complexity from O(N²) to O(N).
