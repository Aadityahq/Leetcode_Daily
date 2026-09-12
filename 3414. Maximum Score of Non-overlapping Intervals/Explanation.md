## 3414. Maximum Score of Non-overlapping Intervals

### Problem Explanation

We are given a list of intervals:

```text
intervals[i] = [lefti, righti, weighti]
```

Each interval has:

* `lefti` → starting position
* `righti` → ending position
* `weighti` → score we get by selecting it

We can select **at most 4 intervals**.

The selected intervals must be **non-overlapping**.

Two intervals are non-overlapping only when:

```text
previousRight < nextLeft
```

Notice the **strictly smaller** condition.

For example:

```text
[1, 5]
[5, 8]
```

are overlapping because they both contain point `5`.

Therefore, the second interval must start at a position **greater than 5**.

Our goal is:

1. Maximize the total weight.
2. If multiple selections have the same maximum weight, return the **lexicographically smallest array of original indices**.

---

# Main Idea

This is essentially a **Weighted Interval Scheduling** problem, with one additional restriction:

> We can select at most 4 intervals.

We can solve it using:

```text
Sorting
   ↓
Binary Search
   ↓
Dynamic Programming
```

---

# Step 1: Store the Original Index

We first convert:

```java
List<List<Integer>>
```

into:

```java
int[][] arr
```

Each interval becomes:

```text
[start, end, weight, originalIndex]
```

For example:

```text
intervals =
[
    [1, 3, 2],
    [4, 5, 2],
    [1, 5, 5]
]
```

becomes:

```text
[1, 3, 2, 0]
[4, 5, 2, 1]
[1, 5, 5, 2]
```

The last value is the original index.

We need this because we will sort the intervals, so their positions will change.

---

# Step 2: Sort the Intervals

We sort the intervals according to their starting position:

```java
Arrays.sort(arr, (a, b) -> Integer.compare(a[0], b[0]));
```

Why?

Suppose we have:

```text
[1, 3, 5]
[4, 6, 7]
[8, 10, 4]
```

Once sorted by starting position, all possible future intervals are to the **right** of the current interval.

That allows us to efficiently find the next compatible interval using binary search.

---

# Step 3: Find the Next Compatible Interval

Suppose the current interval is:

```text
[1, 5, 10]
```

The next interval must have:

```text
start > 5
```

because intervals sharing a boundary are considered overlapping.

So we need to find:

> The first interval whose starting point is greater than the current interval's ending point.

We use binary search.

```java
private int findNext(int[][] arr, int i) {

    int end = arr[i][1];

    int left = i + 1;
    int right = arr.length;

    while (left < right) {

        int mid = left + (right - left) / 2;

        if (arr[mid][0] > end) {
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
Current interval:
[1, 5]

Sorted intervals:
[1,5]
[3,7]
[5,8]
[6,9]
[10,12]
```

We need:

```text
start > 5
```

So the first valid interval is:

```text
[6,9]
```

Therefore:

```text
next[i] = index of [6,9]
```

---

# Step 4: Dynamic Programming

Now comes the main part.

We define:

```java
dp[i][k]
```

where:

* `i` = current interval
* `k` = maximum number of intervals we can still choose

So:

> `dp[i][k]` represents the maximum score we can obtain from interval `i` onward when we can choose at most `k` intervals.

Since we can choose at most 4 intervals:

```text
k = 0, 1, 2, 3, 4
```

---

# Two Choices

At every interval, we have exactly two possibilities.

## Choice 1: Skip the interval

We don't select interval `i`.

Then we simply move to:

```text
i + 1
```

So:

```text
skip = dp[i + 1][k]
```

---

## Choice 2: Take the interval

Suppose:

```text
arr[i] = [left, right, weight, index]
```

If we take it, we get:

```text
weight
```

and then we can only continue from:

```text
next[i]
```

Also, because we used one interval, we have:

```text
k - 1
```

intervals remaining.

Therefore:

```text
take = weight + dp[next[i]][k - 1]
```

---

# DP Formula

Therefore:

```text
dp[i][k] =
    max(
        dp[i + 1][k],
        weight[i] + dp[next[i]][k - 1]
    )
```

This gives us the maximum possible score.

---

# But There Is a Problem

The problem doesn't ask only for the maximum score.

It also says:

> If multiple answers have the same score, return the lexicographically smallest indices.

For example, suppose:

```text
[1, 4]
```

and

```text
[2, 3]
```

both produce the same score.

We need:

```text
[1, 4]
```

because:

```text
[1, 4] < [2, 3]
```

lexicographically.

Therefore, storing only the score isn't enough.

We also need to store the corresponding selected indices.

---

# Storing the Selected Indices

We use another DP array:

```java
long[][] choice
```

So:

```text
choice[i][k]
```

stores the lexicographically smallest set of indices that produces:

```text
dp[i][k]
```

There can be at most **4 indices**.

The maximum number of intervals is only 4, which makes storing the answer manageable.

---

# Why Use `long`?

An interval index can be at most:

```text
50000 - 1 = 49999
```

and:

```text
2^16 = 65536
```

So one index fits inside **16 bits**.

We need at most 4 indices:

```text
4 × 16 = 64 bits
```

A Java `long` contains exactly 64 bits.

So we can store all four indices inside one `long`.

For example, conceptually:

```text
[2, 7, 15]
```

can be stored as:

```text
0003 | 0008 | 0010 | 0000
```

We actually store:

```text
index + 1
```

so that:

```text
0
```

can represent an empty position.

---

# Taking an Interval

When we take the current interval, we put its original index at the beginning of the answer:

```java
long takeChoice =
        prepend(arr[i][3], choice[next[i]][k - 1]);
```

The `prepend()` method is:

```java
private long prepend(int index, long key) {
    return (key << 16) | (index + 1L);
}
```

So if the current index is `2` and the remaining answer is:

```text
[3, 7]
```

the new answer becomes:

```text
[2, 3, 7]
```

---

# Handling the Tie

Now suppose:

```text
takeScore == skipScore
```

Both choices give the same maximum score.

We then compare their index arrays.

```java
if (lexicographicallySmaller(takeChoice, skipChoice)) {
    choice[i][k] = takeChoice;
} else {
    choice[i][k] = skipChoice;
}
```

This ensures that the final answer is not just maximum in score, but also **lexicographically smallest**.

---

# Why Build DP From Right to Left?

Our transition is:

```text
i → i + 1
```

when skipping, or:

```text
i → next[i]
```

when taking.

Both transitions move toward a **larger index**.

Therefore, when calculating:

```text
dp[i][k]
```

we already need:

```text
dp[i + 1][k]
```

and:

```text
dp[next[i]][k - 1]
```

So we calculate:

```text
i = n - 1
n - 2
...
0
```

That's why the code uses:

```java
for (int i = n - 1; i >= 0; i--)
```

---

# Example

Consider:

```text
intervals =
[
    [1,3,2],  // 0
    [4,5,2],  // 1
    [1,5,5],  // 2
    [6,9,3],  // 3
    [6,7,1],  // 4
    [8,9,1]   // 5
]
```

Consider interval `2`:

```text
index 2 = [1,5,5]
```

If we take it, we get:

```text
score = 5
```

The next interval must start after `5`.

The first valid interval is:

```text
index 3 = [6,9,3]
```

So we can take:

```text
index 2
+
index 3
```

giving:

```text
score = 5 + 3 = 8
```

Therefore:

```text
[2,3]
```

is a possible answer.

The other intervals cannot produce a better score, so the answer is:

```text
[2,3]
```

---

# Complete Code

```java
import java.util.*;

class Solution {

    private static final int MAX = 4;

    public int[] maximumWeight(List<List<Integer>> intervals) {

        int n = intervals.size();

        // [start, end, weight, originalIndex]
        int[][] arr = new int[n][4];

        for (int i = 0; i < n; i++) {
            arr[i][0] = intervals.get(i).get(0);
            arr[i][1] = intervals.get(i).get(1);
            arr[i][2] = intervals.get(i).get(2);
            arr[i][3] = i;
        }

        // Sort by start time
        Arrays.sort(arr, (a, b) ->
            Integer.compare(a[0], b[0])
        );

        // Find next compatible interval for every interval
        int[] next = new int[n];

        for (int i = 0; i < n; i++) {
            next[i] = findNext(arr, i);
        }

        /*
         * dp[i][k] =
         * maximum score from i onward
         * using at most k intervals.
         */
        long[][] dp = new long[n + 1][MAX + 1];

        /*
         * choice[i][k] =
         * lexicographically smallest indices producing dp[i][k].
         */
        long[][] choice = new long[n + 1][MAX + 1];

        // Bottom-up DP
        for (int i = n - 1; i >= 0; i--) {

            for (int k = 1; k <= MAX; k++) {

                // Skip current interval
                long skipScore = dp[i + 1][k];
                long skipChoice = choice[i + 1][k];

                // Take current interval
                long takeScore =
                    arr[i][2] + dp[next[i]][k - 1];

                long takeChoice =
                    prepend(
                        arr[i][3],
                        choice[next[i]][k - 1]
                    );

                if (takeScore > skipScore) {

                    dp[i][k] = takeScore;
                    choice[i][k] = takeChoice;

                } else if (takeScore < skipScore) {

                    dp[i][k] = skipScore;
                    choice[i][k] = skipChoice;

                } else {

                    // Same score -> lexicographically smaller
                    dp[i][k] = takeScore;

                    if (lexicographicallySmaller(
                            takeChoice,
                            skipChoice)) {

                        choice[i][k] = takeChoice;

                    } else {
                        choice[i][k] = skipChoice;
                    }
                }
            }
        }

        return decode(choice[0][MAX]);
    }

    // Find first interval with start > current end
    private int findNext(int[][] arr, int i) {

        int end = arr[i][1];

        int left = i + 1;
        int right = arr.length;

        while (left < right) {

            int mid = left + (right - left) / 2;

            if (arr[mid][0] > end) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }

        return left;
    }

    // Add index to the beginning of encoded answer
    private long prepend(int index, long key) {

        return (key << 16) | (index + 1L);
    }

    // Check which encoded answer is lexicographically smaller
    private boolean lexicographicallySmaller(long a, long b) {

        for (int pos = 3; pos >= 0; pos--) {

            int shift = pos * 16;

            int x = (int) ((a >>> shift) & 0xFFFF);
            int y = (int) ((b >>> shift) & 0xFFFF);

            if (x != y) {
                return x < y;
            }
        }

        return false;
    }

    // Convert encoded long back to int[]
    private int[] decode(long key) {

        int[] result = new int[MAX];
        int count = 0;

        for (int pos = 3; pos >= 0; pos--) {

            int shift = pos * 16;

            int value =
                (int) ((key >>> shift) & 0xFFFF);

            if (value == 0) {
                break;
            }

            result[count++] = value - 1;
        }

        return Arrays.copyOf(result, count);
    }
}
```

## Complexity

Let `n` be the number of intervals.

### Sorting

```text
O(n log n)
```

### Finding next interval

We perform binary search for every interval:

```text
n × O(log n)
= O(n log n)
```

### Dynamic Programming

There are only 4 possible values of `k`:

```text
n × 4
```

Therefore:

```text
O(n)
```

### Overall

```text
Time Complexity:  O(n log n)
Space Complexity: O(n)
```

### Interview way to remember this problem

Think of it as:

```text
Maximum weight
       +
Non-overlapping intervals
       +
At most 4 intervals
       +
Lexicographically smallest answer
```

which leads directly to:

```text
Sort by start
      ↓
Binary search next compatible interval
      ↓
DP(i, k)
      ↓
Take / Skip
      ↓
Store score + lexicographically smallest indices
```
