## LeetCode 1477 — Find Two Non-overlapping Sub-arrays Each With Target Sum

### 💡 Main idea

We need to find **two non-overlapping subarrays** whose sum is `target`, and their **combined length must be minimum**.

Because all `arr[i]` are **positive**, we can use a **sliding window** to find every subarray whose sum is exactly `target`.

But finding the subarrays is only half the problem.

For every subarray we find, we need to know:

> **What is the shortest valid subarray that appeared completely before this one?**

We can store this information in a `dp` array.

---

## Approach

Suppose we find a valid subarray:

```text
arr[l ... r]
```

Its length is:

```text
r - l + 1
```

Now we need the shortest valid subarray ending **before `l`**.

We maintain:

```text
dp[i] = minimum length of a valid target-sum subarray
        that ends at or before index i
```

So when we find `[l...r]`, we can do:

```java
if (l > 0 && dp[l - 1] != INF) {
    answer = Math.min(answer, dp[l - 1] + length);
}
```

Then we update `dp[r]`.

---

# Java Solution

```java
import java.util.*;

class Solution {
    public int minSumOfLengths(int[] arr, int target) {

        int n = arr.length;
        int INF = n + 1;

        // dp[i] = minimum length of a valid subarray
        // ending at or before index i
        int[] dp = new int[n];

        Arrays.fill(dp, INF);

        int left = 0;
        int sum = 0;
        int answer = INF;

        for (int right = 0; right < n; right++) {

            sum += arr[right];

            // Shrink the window if sum becomes too large
            while (sum > target) {
                sum -= arr[left];
                left++;
            }

            // We found a subarray with sum = target
            if (sum == target) {

                int length = right - left + 1;

                // Check if there is a previous non-overlapping subarray
                if (left > 0 && dp[left - 1] != INF) {
                    answer = Math.min(answer,
                                      dp[left - 1] + length);
                }

                // Store the shortest valid subarray
                // seen up to this right index
                if (right == 0) {
                    dp[right] = length;
                } else {
                    dp[right] = Math.min(dp[right - 1], length);
                }

            } else {

                // No valid subarray ending at right,
                // so carry forward the previous best
                if (right > 0) {
                    dp[right] = dp[right - 1];
                }
            }
        }

        return answer == INF ? -1 : answer;
    }
}
```

---

# Let's understand it step by step

Consider:

```text
arr = [7, 3, 4, 7]
target = 7
```

We have three valid subarrays:

```text
[7]       -> length 1
[3,4]     -> length 2
[7]       -> length 1
```

We want:

```text
first [7] + last [7]
= 1 + 1
= 2
```

---

### Step 1: Find `[7]`

At:

```text
right = 0
left = 0
```

Window:

```text
[7]
```

Sum:

```text
7
```

So:

```text
length = 1
```

There is no previous subarray.

We store:

```text
dp[0] = 1
```

Meaning:

> The shortest valid subarray found up to index `0` has length `1`.

---

### Step 2: Find `[3,4]`

Now the window becomes:

```text
[3,4]
```

Sum:

```text
3 + 4 = 7
```

Length:

```text
2
```

But the previous valid subarray is `[7]`, which ends at index `0`.

Our current subarray starts at index `1`.

Therefore they don't overlap.

We check:

```java
dp[left - 1]
```

Here:

```text
left = 1

dp[0] = 1
```

Therefore:

```text
answer = 1 + 2
       = 3
```

---

### Step 3: Find the last `[7]`

Now:

```text
arr = [7,3,4,7]
             ↑
```

Current subarray:

```text
[7]
```

Length:

```text
1
```

It starts at index `3`.

So we check:

```text
dp[2]
```

`dp[2]` represents the shortest valid subarray completely before index `3`.

That is:

```text
[7]
```

with length:

```text
1
```

Therefore:

```text
answer = 1 + 1
       = 2
```

So the result is:

```text
2
```

---

# Why does `dp[left - 1]` guarantee non-overlapping?

This is the most important part.

Suppose our current subarray is:

```text
[l ........ r]
```

We need the previous subarray to finish before `l`.

Therefore its ending index must satisfy:

```text
previousEnd < l
```

The last index available before our current subarray is:

```text
l - 1
```

So:

```java
dp[l - 1]
```

contains the shortest valid subarray whose ending position is at most `l - 1`.

Therefore:

```text
previous subarray     current subarray

[........]            [........]
       ↑              ↑
     l - 1            l
```

They **cannot overlap**.

---

# Why do we need `dp`?

Imagine we simply find two target-sum subarrays.

That isn't enough.

For example:

```text
arr = [7, 3, 4, 7]
target = 7
```

We have:

```text
[7]      length = 1
[3,4]    length = 2
[7]      length = 1
```

Possible pairs:

```text
[7] + [3,4]
1 + 2 = 3
```

and:

```text
[7] + [7]
1 + 1 = 2
```

We need the **minimum**.

So we need to remember the shortest previous subarray.

That's exactly what `dp` does.

---

# Why Sliding Window works?

The constraints tell us:

```text
1 <= arr[i] <= 1000
```

So **every element is positive**.

This is very important.

Because all numbers are positive:

### If sum is too small

```text
sum < target
```

we need more elements, so we move:

```text
right++
```

### If sum is too large

```text
sum > target
```

we remove elements from the left:

```text
left++
```

This allows us to find every target-sum subarray efficiently.

We don't need nested loops.

---

# Dry Run

For:

```text
arr = [7,3,4,7]
target = 7
```

| `right` | Window  | Sum | Valid? | Length | `dp[right]` | Answer |
| ------: | ------- | --: | ------ | -----: | ----------: | -----: |
|       0 | `[7]`   |   7 | ✅      |      1 |           1 |    INF |
|       1 | `[3]`   |   3 | ❌      |      - |           1 |    INF |
|       2 | `[3,4]` |   7 | ✅      |      2 |           1 |      3 |
|       3 | `[7]`   |   7 | ✅      |      1 |           1 |      2 |

Final:

```text
answer = 2
```

---

# Complexity

### Time: `O(n)`

Although we have a `while` loop, both `left` and `right` only move forward.

Each element is added to the window once and removed at most once.

Therefore:

```text
O(n)
```

For:

```text
n = 100,000
```

this is efficient.

### Space: `O(n)`

Because of the:

```java
int[] dp
```

array.

---

## 🧠 Remember this pattern

This problem is a very useful combination of:

```text
Positive numbers
       ↓
Sliding Window
       ↓
Find target-sum subarray
       ↓
DP stores shortest previous subarray
       ↓
Combine two non-overlapping subarrays
```

The key line to understand is:

```java
answer = Math.min(answer, dp[left - 1] + length);
```

It means:

> **Current valid subarray + shortest valid subarray completely before it = a possible answer.**

And we take the minimum over all such pairs.
