# 1288. Remove Covered Intervals

## Problem Explanation

You are given several intervals.

Each interval is represented as:

`[start, end)`

where:

* `start` is inclusive.
* `end` is exclusive (this detail doesn't affect the solution).

Your task is to **remove every interval that is completely covered by another interval**.

An interval `[a, b)` is covered by `[c, d)` if:

```
c <= a
and
b <= d
```

Meaning:

* The covering interval starts before (or at the same point).
* The covering interval ends after (or at the same point).

Finally, return **how many intervals remain**.

---

## Example 1

```
Input:
[[1,4],[3,6],[2,8]]
```

Visualize:

```
[1---------4]

      [3------6]

   [2--------------8]
```

Notice:

```
[3,6]
```

is completely inside

```
[2,8]
```

So remove it.

Remaining:

```
[1,4]
[2,8]
```

Answer:

```
2
```

---

## Example 2

```
Input:
[[1,4],[2,3]]
```

Visualization

```
[1-----------4]

   [2----3]
```

`[2,3]` is completely inside `[1,4]`.

Remove it.

Remaining:

```
[1,4]
```

Answer:

```
1
```

---

# Brute Force Idea

For every interval:

Compare it with every other interval.

If another interval covers it, remove it.

```
for every interval i
    for every interval j
        if j covers i
            remove i
```

### Complexity

```
Time = O(n²)
Space = O(1)
```

Works because n ≤ 1000, but there is a much cleaner approach.

---

# Optimal Idea

## Step 1: Sort the intervals

Sort using:

* increasing start
* if starts are equal, decreasing end

Why?

Suppose we have

```
[1,8]
[1,5]
```

If we sort only by start:

```
[1,5]
[1,8]
```

Now we'd incorrectly think `[1,5]` isn't covered because we haven't seen `[1,8]` yet.

Instead sort as:

```
[1,8]
[1,5]
```

Now the larger interval comes first.

Sorting rule:

```
start ascending

if same start
    end descending
```

Example:

Before sorting

```
[3,6]
[1,4]
[2,8]
```

After sorting

```
[1,4]
[2,8]
[3,6]
```

Another example

```
[1,5]
[1,8]
```

After sorting

```
[1,8]
[1,5]
```

Perfect.

---

## Step 2: Keep track of the farthest ending point

Initialize

```
maxEnd = 0
count = 0
```

Now scan every interval.

If

```
currentEnd > maxEnd
```

then this interval is **not covered**.

Keep it.

Update

```
maxEnd = currentEnd
count++
```

Otherwise

```
currentEnd <= maxEnd
```

This interval is covered.

Ignore it.

---

## Dry Run

Input

```
[[1,4],[3,6],[2,8]]
```

After sorting

```
[1,4]
[2,8]
[3,6]
```

Initially

```
maxEnd = 0
count = 0
```

### Interval 1

```
[1,4]

4 > 0
```

Keep it.

```
count = 1
maxEnd = 4
```

---

### Interval 2

```
[2,8]

8 > 4
```

Keep it.

```
count = 2
maxEnd = 8
```

---

### Interval 3

```
[3,6]

6 <= 8
```

Covered.

Ignore.

Final answer

```
2
```

---

## Another Dry Run

```
[[1,4],[2,3]]
```

Already sorted.

```
maxEnd = 0
count = 0
```

### First

```
[1,4]

4 > 0
```

Keep.

```
count = 1
maxEnd = 4
```

### Second

```
[2,3]

3 <= 4
```

Covered.

Answer

```
1
```

---

# Why does this work?

After sorting:

* Every future interval starts **at the same or a later position**.
* So the only thing we need to check is whether its end extends beyond the farthest end we've seen.
* If it doesn't (`end <= maxEnd`), then an earlier interval already starts no later and ends no earlier, so it covers the current interval.

---

# Algorithm

1. Sort intervals by:

   * start ascending
   * end descending
2. Initialize:

   * `maxEnd = 0`
   * `count = 0`
3. Traverse intervals:

   * If `end > maxEnd`

     * increment `count`
     * update `maxEnd`
   * Else

     * interval is covered
4. Return `count`

---

# Java Solution

```java
import java.util.Arrays;

class Solution {
    public int removeCoveredIntervals(int[][] intervals) {

        Arrays.sort(intervals, (a, b) -> {
            if (a[0] == b[0]) {
                return b[1] - a[1]; // end descending
            }
            return a[0] - b[0];     // start ascending
        });

        int count = 0;
        int maxEnd = 0;

        for (int[] interval : intervals) {
            if (interval[1] > maxEnd) {
                count++;
                maxEnd = interval[1];
            }
        }

        return count;
    }
}
```

---

# Complexity Analysis

### Time Complexity

* Sorting: **O(n log n)**
* Traversing: **O(n)**

Overall:

```
O(n log n)
```

---

### Space Complexity

Only a few variables are used.

```
O(1)
```

(ignoring the space used internally by the sorting algorithm)

---

# Key Takeaways

* **Sort by start ascending and end descending.**
* Maintain the **maximum end** encountered so far.
* If the current interval's end is **less than or equal to `maxEnd`**, it is covered.
* Otherwise, it is a new uncovered interval, so count it and update `maxEnd`.

This sorting strategy is the crucial insight that reduces the problem from **O(n²)** to **O(n log n)**.
