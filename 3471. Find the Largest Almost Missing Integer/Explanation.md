# 3414. Maximum Score of Non-overlapping Intervals

## Problem Explanation

We are given `n` intervals:

```text
intervals[i] = [left, right, weight]
```

For every interval:

* `left` → starting point
* `right` → ending point
* `weight` → score we get if we select it

We can select **at most 4 intervals**.

The selected intervals must not overlap.

Two intervals are considered non-overlapping only when:

```text
previous.right < next.left
```

So:

```text
[1, 5] and [5, 8]
```

are **overlapping**, because they share point `5`.

Our goal is to:

1. Select at most 4 non-overlapping intervals.
2. Maximize their total weight.
3. If multiple selections have the same maximum score, return the **lexicographically smallest array of original indices**.

---

# Main Idea

This is a variation of the classic **Weighted Interval Scheduling** problem.

We can solve it using:

```text
Sort intervals by ending position
        ↓
Binary Search
        ↓
Dynamic Programming
        ↓
Take / Skip
        ↓
Handle lexicographical tie
```

The important difference from normal weighted interval scheduling is that we can choose **at most 4 intervals**, so our DP needs an extra dimension for the number of intervals selected.

---

# 1. Store the Original Index

We first create:

```java
long[][] arr = new long[n][4];
```

Each interval is stored as:

```text
[start, end, weight, originalIndex]
```

For example:

```text
intervals:

index 0 → [1, 3, 5]
index 1 → [4, 6, 7]
index 2 → [8, 10, 4]
```

becomes:

```text
[1, 3, 5, 0]
[4, 6, 7, 1]
[8, 10, 4, 2]
```

We keep the original index because the final answer must contain the **original indices**, not the positions after sorting.

---

# 2. Sort by Ending Position

The code does:

```java
Arrays.sort(arr, (a, b) -> Long.compare(a[1], b[1]));
```

So intervals are sorted by their `right` endpoint.

For example:

```text
[1, 5, 10]
[2, 3, 4]
[4, 8, 7]
[9, 12, 5]
```

becomes:

```text
[2, 3, 4]
[1, 5, 10]
[4, 8, 7]
[9, 12, 5]
```

Why sort by ending position?

Because when we are processing an interval, we want to find the **last interval that finishes before this interval starts**.

That is exactly what binary search will find.

---

# 3. Find the Previous Compatible Interval

Suppose the current interval is:

```text
[6, 10, 8]
```

A previous interval can be selected with it only if:

```text
previousEnd < 6
```

So if previous intervals end at:

```text
3, 5, 6, 8
```

the last compatible one is:

```text
5
```

We need to find the first ending position that is:

```text
>= currentStart
```

and then take the position immediately before it.

That's what this method does:

```java
private int lowerBound(long[] ends, int length, long target) {
    int left = 0;
    int right = length;

    while (left < right) {
        int mid = left + (right - left) / 2;

        if (ends[mid] >= target) {
            right = mid;
        } else {
            left = mid + 1;
        }
    }

    return left;
}
```

For example:

```text
ends = [2, 4, 5, 7, 10]
target = 6
```

`lowerBound()` returns:

```text
3
```

because:

```text
ends[3] = 7
```

is the first value `>= 6`.

Therefore:

```text
p = 3
```

means the first `3` intervals are compatible.

So we use:

```java
dp[k - 1][p]
```

---

# 4. Understanding the DP

The most important part of this solution is:

```java
Node[][] dp = new Node[K + 1][n + 1];
```

Here:

```text
dp[k][i]
```

means:

> The best result we can obtain by considering the first `i` intervals and selecting exactly `k` intervals.

Each state stores a `Node`:

```java
static class Node {
    long score;
    int[] ids;
}
```

So every DP state remembers:

```text
maximum score
+
indices that produced that score
```

---

# Why Do We Need `k`?

Because the problem allows at most 4 intervals.

We therefore maintain:

```text
k = 0
k = 1
k = 2
k = 3
k = 4
```

For example:

```text
dp[2][10]
```

means:

> Among the first 10 intervals, what is the best way to select exactly 2 non-overlapping intervals?

---

# 5. Base Case

The code initializes:

```java
for (int i = 0; i <= n; i++) {
    dp[0][i] = new Node(0, new int[0]);
}
```

This means:

> If we need to select exactly 0 intervals, the score is 0 and the answer is an empty array.

For example:

```text
dp[0][5] = {
    score = 0,
    ids = []
}
```

This is important because when we select the first interval, we may build the answer from a solution containing zero previous intervals.

---

# 6. Two Choices: Skip or Take

For every interval, there are two possibilities.

## Choice 1: Skip

We don't select the current interval.

Therefore, the answer remains whatever was possible using the first `i - 1` intervals:

```java
dp[k][i] = dp[k][i - 1];
```

For example:

```text
dp[2][5]
```

can simply inherit:

```text
dp[2][4]
```

because we decided not to use interval `5`.

---

# 7. Choice 2: Take

Suppose the current interval is:

```text
[left, right, weight]
```

We want to select it.

Before this interval, we can only use intervals whose:

```text
end < left
```

We already found the number of such intervals using:

```java
int p = lowerBound(ends, i - 1, left);
```

Therefore, if we want exactly `k` intervals total:

```text
k - 1
```

intervals must come before the current interval.

So we start from:

```java
dp[k - 1][p]
```

Then add the current interval's weight:

```java
dp[k - 1][p].score + weight
```

---

# 8. Add the Current Index

We also need to add the current interval's original index:

```java
int[] ids = addSorted(
    dp[k - 1][p].ids,
    originalIndex
);
```

The helper method is:

```java
private int[] addSorted(int[] ids, int value) {
    int[] result = Arrays.copyOf(ids, ids.length + 1);

    result[ids.length] = value;

    Arrays.sort(result);

    return result;
}
```

Why sort the indices?

Because the problem asks for the answer as a **lexicographically smallest array of indices**.

For example, if we select:

```text
index 7
index 2
index 5
```

the answer needs to be represented as:

```text
[2, 5, 7]
```

rather than:

```text
[7, 2, 5]
```

Sorting the selected indices makes comparison straightforward.

---

# 9. Comparing Two Solutions

Now we have two possible answers:

```text
skip
```

and:

```text
take
```

We need to determine which one is better.

That's what:

```java
better(a, b)
```

does.

First, compare the scores:

```java
if (a.score != b.score) {
    return a.score > b.score;
}
```

The larger score wins.

For example:

```text
A → score = 20
B → score = 15
```

A is better.

---

# 10. Lexicographical Tie-Breaking

What if the scores are equal?

For example:

```text
A → score = 20, ids = [1, 5]
B → score = 20, ids = [2, 3]
```

We compare:

```text
1 vs 2
```

Since:

```text
1 < 2
```

we choose:

```text
[1, 5]
```

The code does exactly that:

```java
for (int i = 0; i < len; i++) {
    if (a.ids[i] != b.ids[i]) {
        return a.ids[i] < b.ids[i];
    }
}
```

So the priority is:

```text
Higher score
     ↓
If equal
     ↓
Lexicographically smaller indices
```

---

# 11. Why Compare Length at the End?

Suppose we have:

```text
A = [1, 2]
B = [1, 2, 3]
```

The first two elements are equal.

Then the shorter array is considered lexicographically smaller:

```text
[1, 2] < [1, 2, 3]
```

So we have:

```java
return a.ids.length < b.ids.length;
```

In this problem, weights are positive, so for a fixed number `k`, choosing an additional interval generally improves the score. But this comparison makes the `better()` function correctly handle arrays of different lengths as well.

---

# 12. Why `Node` Is Useful

Instead of having:

```text
dp = maximum score only
```

we store:

```java
Node {
    long score;
    int[] ids;
}
```

For example:

```text
dp[2][5]
```

could contain:

```text
score = 15
ids = [1, 4]
```

So we always know both:

* how good the solution is
* which intervals produced it

This makes handling the lexicographical requirement much easier to understand than encoding the indices into a `long`.

---

# 13. Final Answer

At the end, we have:

```text
dp[1][n]
dp[2][n]
dp[3][n]
dp[4][n]
```

The problem says **at most 4**, not exactly 4.

Therefore we cannot simply return:

```java
dp[4][n]
```

We need to consider all possibilities:

```java
Node answer = null;

for (int k = 1; k <= K; k++) {
    if (better(dp[k][n], answer)) {
        answer = dp[k][n];
    }
}
```

This compares the best solution using:

```text
1 interval
2 intervals
3 intervals
4 intervals
```

and returns the best one.

---

# Example

Consider:

```text
intervals = [
    [1, 3, 2],   // 0
    [4, 5, 2],   // 1
    [1, 5, 5],   // 2
    [6, 9, 3],   // 3
    [6, 7, 1],   // 4
    [8, 9, 1]    // 5
]
```

One possible selection is:

```text
index 2 → [1,5,5]
index 3 → [6,9,3]
```

Check compatibility:

```text
5 < 6
```

So they don't overlap.

Total score:

```text
5 + 3 = 8
```

Therefore:

```text
[2, 3]
```

is a candidate answer.

The DP considers all possible combinations and eventually determines that:

```text
score = 8
ids = [2, 3]
```

is the best solution.

---

# Complete Flow

The entire algorithm can be visualized as:

```text
Input intervals
       ↓
Store original indices
       ↓
Sort by ending position
       ↓
Create ends[]
       ↓
For every interval:
       ↓
Binary search for first end >= current start
       ↓
This gives compatible previous intervals
       ↓
DP[k][i]
       ↓
       ├── Skip current interval
       │       ↓
       │   dp[k][i-1]
       │
       └── Take current interval
               ↓
       dp[k-1][p] + current weight
               ↓
       Add current original index
       ↓
Compare Skip vs Take
       ↓
Higher score wins
       ↓
If score equal → lexicographically smaller indices
       ↓
Compare k = 1, 2, 3, 4
       ↓
Final answer
```

---

# Complexity

Let `n` be the number of intervals.

### Sorting

```text
O(n log n)
```

### Binary Search

For each of the `n` intervals, we perform a binary search:

```text
O(n log n)
```

### DP

There are only 4 values of `k`:

```text
4 × n = O(n)
```

However, your implementation also creates/sorts small arrays of at most 4 indices. Since the maximum size is only 4, that is constant work per state.

Therefore:

```text
Time Complexity:  O(n log n)
Space Complexity: O(n)
```

---

## The key concept to remember

This problem is basically:

> **Weighted Interval Scheduling + at most 4 selections + lexicographical tie-breaking.**

The most important transition is:

```text
Take current interval
        ↓
Find last compatible interval
        ↓
Use dp[k - 1][p]
        ↓
Add current weight
```

And the most important condition is:

```text
previousEnd < currentStart
```

not `<=`, because intervals sharing a boundary are considered overlapping.
