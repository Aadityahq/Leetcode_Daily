# LeetCode 1563 — Stone Game V

### Problem in simple words

We have stones arranged in a row.

Alice repeatedly does this:

1. Splits the current row into **two non-empty parts**.
2. Bob calculates the sum of both parts.
3. Bob removes the part having the **larger sum**.
4. Alice gets the sum of the part that remains.
5. Alice continues the game with the remaining part.

If both parts have the same sum, Alice can choose which part remains.

The goal is to **maximize Alice's total score**.

---

## Example

For:

```text
[6, 2, 3, 4, 5, 5]
```

Alice can split it like:

```text
[6, 2, 3] | [4, 5, 5]
     11   |    14
```

Since `14 > 11`, Bob removes the right part.

Alice gets:

```text
11
```

Now we have:

```text
[6, 2, 3]
```

Split:

```text
[6] | [2, 3]
 6   |   5
```

Bob removes `[6]`.

Alice gets:

```text
5
```

Score:

```text
11 + 5 = 16
```

Then:

```text
[2] | [3]
 2   |  3
```

Bob removes `[3]`.

Alice gets `2`.

Final score:

```text
11 + 5 + 2 = 18
```

---

# 1. What makes this problem difficult?

At every step, Alice has many possible ways to split the array.

For example:

```text
[6,2,3,4]
```

can be divided as:

```text
[6] | [2,3,4]
[6,2] | [3,4]
[6,2,3] | [4]
```

And after Bob removes one side, Alice has to make another decision.

So we have many overlapping subproblems.

This strongly suggests **Dynamic Programming**.

---

# 2. DP Definition

We define:

```text
dp[l][r]
```

as:

> The maximum score Alice can obtain from the subarray `stoneValue[l...r]`.

For example:

```text
dp[1][3]
```

means:

```text
maximum score Alice can obtain from:

stoneValue[1], stoneValue[2], stoneValue[3]
```

---

# 3. How do we calculate `dp[l][r]`?

Suppose Alice splits:

```text
[l ........ k] | [k+1 ........ r]
```

We need the sum of both parts.

Let's call them:

```text
leftSum
rightSum
```

There are **three cases**.

---

## Case 1: `leftSum < rightSum`

Example:

```text
[6,2,3] | [4,5,5]
   11   |   14
```

Bob removes the right side.

Alice keeps:

```text
[6,2,3]
```

She receives `11` points.

Then she can continue playing on `[6,2,3]`.

Therefore:

```text
candidate = leftSum + dp[l][k]
```

---

## Case 2: `leftSum > rightSum`

Example:

```text
[6,2] | [3,4]
  8   |  7
```

Bob removes the left side.

Alice keeps:

```text
[3,4]
```

She receives `7`.

Then she continues playing on `[3,4]`.

Therefore:

```text
candidate = rightSum + dp[k+1][r]
```

---

## Case 3: `leftSum == rightSum`

Example:

```text
[7,7] | [7,7]
  14  |  14
```

Since both sums are equal, Alice can choose which side remains.

So we have two choices:

```text
leftSum + dp[l][k]
```

or

```text
rightSum + dp[k+1][r]
```

We take the maximum:

```text
candidate = max(
    leftSum + dp[l][k],
    rightSum + dp[k+1][r]
)
```

---

# 4. How do we calculate subarray sums efficiently?

We could calculate:

```text
sum(l, k)
sum(k+1, r)
```

using a loop.

But that would make the solution too slow.

Instead, we use **Prefix Sum**.

Create:

```text
prefix[i]
```

where:

```text
prefix[i] = sum of elements from index 0 to i-1
```

For example:

```text
stoneValue = [6,2,3,4]
```

Then:

```text
prefix = [0,6,8,11,15]
```

Now the sum from `l` to `r` is:

```text
prefix[r + 1] - prefix[l]
```

For example:

```text
sum(1,3)
= prefix[4] - prefix[1]
= 15 - 6
= 9
```

So we can calculate every split's sum in **O(1)**.

---

# 5. DP Order

There is an important detail.

When calculating:

```text
dp[l][r]
```

we need:

```text
dp[l][k]
```

and:

```text
dp[k+1][r]
```

These are **smaller intervals**.

Therefore, we calculate the DP by increasing subarray length:

```text
length = 2
length = 3
length = 4
...
length = n
```

For example:

```text
[6,2]       length 2
[6,2,3]     length 3
[6,2,3,4]   length 4
...
```

A single stone has no valid split, so:

```text
dp[i][i] = 0
```

which is already the default value of an integer array in Java.

---

# 6. Java Solution

```java
class Solution {

    public int stoneGameV(int[] stoneValue) {

        int n = stoneValue.length;

        // 1. Prefix Sum
        int[] prefix = new int[n + 1];

        for (int i = 0; i < n; i++) {
            prefix[i + 1] = prefix[i] + stoneValue[i];
        }

        // 2. DP
        // dp[l][r] = maximum score Alice can get
        // from subarray l to r
        int[][] dp = new int[n][n];

        // Try all subarray lengths
        for (int len = 2; len <= n; len++) {

            // Starting index
            for (int l = 0; l + len - 1 < n; l++) {

                int r = l + len - 1;

                // Try every possible split
                for (int k = l; k < r; k++) {

                    // Sum of left part
                    int leftSum = prefix[k + 1] - prefix[l];

                    // Sum of right part
                    int rightSum = prefix[r + 1] - prefix[k + 1];

                    if (leftSum < rightSum) {

                        // Right part is removed.
                        // Alice keeps the left part.
                        dp[l][r] = Math.max(
                                dp[l][r],
                                leftSum + dp[l][k]
                        );

                    } else if (leftSum > rightSum) {

                        // Left part is removed.
                        // Alice keeps the right part.
                        dp[l][r] = Math.max(
                                dp[l][r],
                                rightSum + dp[k + 1][r]
                        );

                    } else {

                        // Both parts have equal sum.
                        // Alice can choose either side.

                        dp[l][r] = Math.max(
                                dp[l][r],
                                Math.max(
                                        leftSum + dp[l][k],
                                        rightSum + dp[k + 1][r]
                                )
                        );
                    }
                }
            }
        }

        return dp[0][n - 1];
    }
}
```

---

# 7. Let's understand the code flow

The most important part is:

```java
for (int len = 2; len <= n; len++)
```

We consider every possible interval.

Then:

```java
for (int l = 0; l + len - 1 < n; l++)
```

chooses the starting index.

We calculate:

```java
int r = l + len - 1;
```

So now we have:

```text
[l ........ r]
```

Then:

```java
for (int k = l; k < r; k++)
```

tries every possible place where Alice can split.

For example:

```text
[6,2,3,4]
```

The possible splits are:

```text
[6] | [2,3,4]

[6,2] | [3,4]

[6,2,3] | [4]
```

The loop over `k` tries all three.

---

# 8. Why does this DP work?

This is the most important part to understand.

Suppose we're solving:

```text
dp[l][r]
```

Alice must make **some split** `k`.

Once she makes that split, Bob's decision is completely determined by the two sums:

```text
leftSum
rightSum
```

If:

```text
leftSum < rightSum
```

the right side disappears, so the only future game is:

```text
dp[l][k]
```

If:

```text
leftSum > rightSum
```

the left side disappears, so the future game is:

```text
dp[k+1][r]
```

If they're equal, Alice chooses the better future.

Therefore, for every possible split, we know exactly what score Alice can achieve.

So we simply take:

```text
maximum over all splits
```

That is exactly what the DP is doing.

---

# 9. Complexity

There are approximately:

```text
O(n²)
```

different subarrays.

For every subarray, we try:

```text
O(n)
```

possible splits.

Therefore:

```text
Time Complexity = O(n³)
```

The DP table contains:

```text
n × n
```

elements.

Therefore:

```text
Space Complexity = O(n²)
```

For:

```text
n <= 500
```

this solution is acceptable.

---

# 10. Important interview explanation

If the interviewer asks **"Why did you use DP?"**, you can say:

> "At every step Alice can split the current subarray at multiple positions, and after Bob removes one side, the game becomes the same problem on a smaller subarray. This creates many overlapping subproblems, so I use interval DP where `dp[l][r]` represents the maximum score obtainable from the subarray between `l` and `r`."

If they ask **"Why prefix sum?"**, say:

> "For every DP state I need to try every possible split, so I need the sum of the left and right portions repeatedly. Prefix sums allow me to calculate each subarray sum in O(1) instead of O(n)."

If they ask **"Why three cases?"**, say:

> "Bob removes the side with the greater sum. Therefore, if the left sum is smaller, Alice continues with the left side; if the right sum is smaller, she continues with the right side. When both sums are equal, Alice can choose either side."

### The core formula to remember

```text
leftSum < rightSum:
    dp[l][r] = max(dp[l][r], leftSum + dp[l][k])

leftSum > rightSum:
    dp[l][r] = max(dp[l][r], rightSum + dp[k+1][r])

leftSum == rightSum:
    dp[l][r] = max(
        dp[l][r],
        leftSum + dp[l][k],
        rightSum + dp[k+1][r]
    )
```

The **one-line intuition** is:

> **Try every split, determine which side Bob removes based on the sums, add the sum of the surviving side, and use DP to find the best future score.**
