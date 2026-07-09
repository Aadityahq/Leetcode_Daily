## Intuition

We are given a graph where:

* Each node represents an index in the `nums` array.
* An edge exists between two nodes if:

[
|nums[i] - nums[j]| \leq maxDiff
]

Since `nums` is **already sorted**, we can make an important observation.

### Key Observation

Suppose:

```
nums = [2, 5, 6, 8]
maxDiff = 2
```

Edges:

```
2   5 -- 6 -- 8
```

Notice:

* `5` connects to `6`
* `6` connects to `8`
* Therefore `5` can reach `8`.

Now consider:

```
2     5
```

Difference = 3 (>2)

Since the array is sorted, every number after `5` will be even farther from `2`.

So **once two consecutive elements cannot connect, every element after that starts a new connected component.**

This means we **never need to build the whole graph.**

---

# Idea

Traverse the sorted array once.

Whenever:

```
nums[i] - nums[i-1] <= maxDiff
```

they belong to the same component.

Otherwise

```
nums[i] - nums[i-1] > maxDiff
```

start a new component.

Store the component id of every index.

Example:

```
nums = [2,5,6,8]
maxDiff = 2
```

```
2   5   6   8
0   1   1   1
```

Component IDs

```
Index : 0 1 2 3
Comp  : 0 1 1 1
```

Now every query becomes:

```
Same component?
YES -> Path exists
NO  -> No path
```

---

# Algorithm

### Step 1

Create an array

```
component[]
```

where

```
component[i]
```

stores which connected component index `i` belongs to.

---

### Step 2

Start with

```
component[0] = 0
```

For every next index:

```
if nums[i] - nums[i-1] <= maxDiff
    component[i] = currentComponent
else
    currentComponent++
    component[i] = currentComponent
```

---

### Step 3

For every query

```
(u,v)
```

Check

```
component[u] == component[v]
```

If yes

```
true
```

Else

```
false
```

---

# Dry Run

### Example

```
nums = [2,5,6,8]
maxDiff = 2
```

Initially

```
component[0]=0
```

### i=1

```
5-2=3 >2
```

New component

```
component = [0,1,_,_]
```

### i=2

```
6-5=1 <=2
```

Same component

```
component = [0,1,1,_]
```

### i=3

```
8-6=2 <=2
```

Same component

```
component = [0,1,1,1]
```

Queries

```
(0,1)

0 !=1
False
```

```
(1,3)

1==1
True
```

Exactly the expected output.

---

# Why does this work?

Since the array is sorted,

If

```
nums[i]-nums[i-1] > maxDiff
```

then

```
nums[j]-nums[i-1] > maxDiff
```

for every

```
j > i
```

because numbers only increase.

Therefore no future element can reconnect these two groups.

Hence every gap larger than `maxDiff` permanently splits the graph into separate connected components.

So checking component IDs is sufficient to determine whether a path exists.

---

# Complexity Analysis

Building components:

```
O(n)
```

Answering each query:

```
O(1)
```

For `q` queries:

```
O(q)
```

Total:

```
O(n + q)
```

Space:

```
O(n)
```

---

# Java Solution

```java
class Solution {
    public boolean[] pathExistenceQueries(int n, int[] nums, int maxDiff, int[][] queries) {
        int[] component = new int[n];
        int id = 0;
        component[0] = 0;

        for (int i = 1; i < n; i++) {
            if (nums[i] - nums[i - 1] > maxDiff) {
                id++;
            }
            component[i] = id;
        }

        boolean[] answer = new boolean[queries.length];

        for (int i = 0; i < queries.length; i++) {
            int u = queries[i][0];
            int v = queries[i][1];
            answer[i] = component[u] == component[v];
        }

        return answer;
    }
}
```

### Why this solution is optimal

* We exploit the fact that `nums` is **sorted**, so connected components are separated only by gaps larger than `maxDiff`.
* We avoid explicitly building the graph, which could have **O(n²)** edges in the worst case.
* After a single linear scan to assign component IDs, each query is answered in **O(1)** time.

**Time Complexity:** `O(n + queries.length)`
**Space Complexity:** `O(n)`
