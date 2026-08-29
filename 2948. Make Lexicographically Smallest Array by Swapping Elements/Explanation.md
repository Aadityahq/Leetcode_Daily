## LeetCode 2948 — Make Lexicographically Smallest Array by Swapping Elements

### 💡 Core Idea

The important observation is:

> We don't really care about individual swaps. We care about **which elements can eventually reach each other**.

Suppose the sorted values are:

```text
[1, 3, 5, 8, 9]
```

with `limit = 2`.

* `1 → 3` difference = 2 ✅
* `3 → 5` difference = 2 ✅
* `5 → 8` difference = 3 ❌
* `8 → 9` difference = 1 ✅

So we get groups:

```text
[1, 3, 5]     [8, 9]
```

Every element inside the same group can effectively be rearranged among the indices belonging to that group.

The trick is to **sort the array while remembering the original indices**.

---

## Why do we group based on sorted values?

Consider:

```text
nums = [1, 7, 6, 18, 2, 1]
limit = 3
```

Sort the `(value, index)` pairs:

```text
value   index
1       0
1       5
2       4
6       2
7       1
18      3
```

Now check adjacent differences:

```text
1 → 1   diff = 0  ✅
1 → 2   diff = 1  ✅
2 → 6   diff = 4  ❌
6 → 7   diff = 1  ✅
7 → 18  diff = 11 ❌
```

Therefore the groups are:

```text
Group 1: values [1, 1, 2], indices [0, 5, 4]
Group 2: values [6, 7],   indices [2, 1]
Group 3: values [18],      index [3]
```

Inside each group, we can put the smallest value at the smallest original index.

For Group 1:

```text
indices: 0, 5, 4
values:  1, 1, 2
```

Sort indices:

```text
0, 4, 5
```

Assign sorted values:

```text
index 0 → 1
index 4 → 1
index 5 → 2
```

For Group 2:

```text
indices: 2, 1
values:  6, 7
```

Sort indices:

```text
1, 2
```

Assign:

```text
index 1 → 6
index 2 → 7
```

Result:

```text
[1, 6, 7, 18, 1, 2]
```

---

# Java Solution

```java
import java.util.*;

class Solution {
    public int[] lexicographicallySmallestArray(int[] nums, int limit) {
        
        int n = nums.length;

        // Store {value, originalIndex}
        int[][] arr = new int[n][2];

        for (int i = 0; i < n; i++) {
            arr[i][0] = nums[i];
            arr[i][1] = i;
        }

        // Sort by value
        Arrays.sort(arr, (a, b) -> Integer.compare(a[0], b[0]));

        int start = 0;

        while (start < n) {

            int end = start;

            // Find all elements belonging to the same group
            while (end + 1 < n &&
                   arr[end + 1][0] - arr[end][0] <= limit) {
                end++;
            }

            // Collect original indices of this group
            int size = end - start + 1;
            int[] indices = new int[size];

            for (int i = 0; i < size; i++) {
                indices[i] = arr[start + i][1];
            }

            // Sort original indices
            Arrays.sort(indices);

            // Values are already sorted because arr is sorted
            for (int i = 0; i < size; i++) {
                nums[indices[i]] = arr[start + i][0];
            }

            start = end + 1;
        }

        return nums;
    }
}
```

---

# Let's understand the algorithm step by step

### Step 1: Store value + original index

For:

```text
nums = [1, 5, 3, 9, 8]
```

we create:

```text
[1, 0]
[5, 1]
[3, 2]
[9, 3]
[8, 4]
```

Meaning:

```text
[value, original index]
```

---

### Step 2: Sort by value

After sorting:

```text
[1, 0]
[3, 2]
[5, 1]
[8, 4]
[9, 3]
```

Now we can easily determine which values belong to the same swappable group.

---

### Step 3: Find groups

`limit = 2`

Check neighboring values:

```text
1 → 3   = 2 ✅
3 → 5   = 2 ✅
5 → 8   = 3 ❌
8 → 9   = 1 ✅
```

Therefore:

```text
Group 1:
values  = [1, 3, 5]
indices = [0, 2, 1]

Group 2:
values  = [8, 9]
indices = [4, 3]
```

---

### Step 4: Sort the original indices

For Group 1:

```text
indices = [0, 2, 1]
```

Sort them:

```text
[0, 1, 2]
```

The values are already sorted:

```text
[1, 3, 5]
```

So assign:

```text
index 0 → 1
index 1 → 3
index 2 → 5
```

For Group 2:

```text
indices = [4, 3]
```

becomes:

```text
[3, 4]
```

Values:

```text
[8, 9]
```

Assign:

```text
index 3 → 8
index 4 → 9
```

Final:

```text
[1, 3, 5, 8, 9]
```

---

# Why does this produce the lexicographically smallest array?

This is the most important part.

Suppose a group contains:

```text
values = [2, 5, 7]
```

and these values can be freely rearranged among:

```text
indices = [1, 4, 6]
```

To make the entire array lexicographically smallest, we want the smallest possible value at the earliest possible index.

Therefore:

```text
smallest index  → smallest value
next index      → next smallest value
...
```

So:

```text
indices = [1, 4, 6]
values  = [2, 5, 7]
```

becomes:

```text
index 1 → 2
index 4 → 5
index 6 → 7
```

Any other arrangement would put a larger number at an earlier index, making the array lexicographically larger.

---

# But there's an interesting part: transitivity

You might wonder:

> What if two values differ by more than `limit`? How can they be in the same group?

For example:

```text
values = [1, 3, 5]
limit = 2
```

`1` and `5` cannot directly swap:

```text
|1 - 5| = 4 > 2
```

But:

```text
1 ↔ 3
3 ↔ 5
```

are both allowed.

Therefore we can use `3` as an intermediate value and effectively move the elements around.

So the condition isn't:

```text
maxValue - minValue <= limit
```

Instead, it is:

> Every **adjacent pair in sorted order** must differ by at most `limit`.

That's why grouping adjacent sorted values works.

---

# Example 2 walkthrough

```text
nums = [1,7,6,18,2,1]
limit = 3
```

Sorted pairs:

```text
value   index
1       0
1       5
2       4
6       2
7       1
18      3
```

Groups:

```text
Group 1:
values  = [1,1,2]
indices = [0,5,4]

Group 2:
values  = [6,7]
indices = [2,1]

Group 3:
values  = [18]
indices = [3]
```

### Group 1

Sort indices:

```text
[0,4,5]
```

Assign:

```text
0 → 1
4 → 1
5 → 2
```

### Group 2

Sort indices:

```text
[1,2]
```

Assign:

```text
1 → 6
2 → 7
```

### Group 3

```text
3 → 18
```

Result:

```text
[1,6,7,18,1,2]
```

---

# Complexity

There are two major sorting operations.

### Sorting `(value, index)` pairs

```text
O(n log n)
```

### Sorting indices inside groups

In the worst case, this is also:

```text
O(n log n)
```

Therefore overall:

```text
Time:  O(n log n)
Space: O(n)
```

This easily works for:

```text
n ≤ 100,000
```

---

# 🧠 Interview takeaway

The key insight for this problem is:

> **Sort the values to discover connected/swappable groups, then sort the original indices within each group and assign the sorted values to those indices.**

Think of it as:

```text
Original array
      ↓
(value, original index)
      ↓
Sort by value
      ↓
Find connected groups
      ↓
Sort indices inside each group
      ↓
Put smallest values at smallest indices
      ↓
Lexicographically smallest array
```

The **most important observation** to remember for similar problems is that the allowed swap relation becomes **transitive through intermediate values**, so sorted adjacent gaps determine the connected components.
