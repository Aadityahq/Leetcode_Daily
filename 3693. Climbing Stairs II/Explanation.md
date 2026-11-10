# LeetCode 3693. Climbing Stairs II



Medium

Dynamic Programming

Java



## 1. Understanding the problem

You are given:

- An integer `n`, representing the destination step.
- An integer array `costs` of length `n`.
- You start at step `0` with a total cost of `0`.

You can jump from your current step `i` to:

- `i + 1`
- `i + 2`
- `i + 3`

Cost of each jump:

\\[ \text{Jump Cost} = costs[j] + (j-i)^2 \\]

Here:

- `i` = current step.
- `j` = destination step.
- `costs[j]` = cost of reaching the destination step.
- `(j-i)²` = square of the jump distance.

Our task is to reach step `n` with the minimum possible total cost.

Important: Step `0` has no cost. Also, Java arrays are zero-indexed, so the cost of reaching step `j` is stored at `costs[j - 1]`.

## 2. Approach: Dynamic Programming

First, let's understand why we need Dynamic Programming.

Suppose we want to reach step `4`.

We can reach step `4` from:

- Step `3` using a jump of 1.
- Step `2` using a jump of 2.
- Step `1` using a jump of 3.

These are the only three possible ways to reach step `4`.

But which one should we choose?

We should choose the path that gives us the minimum total cost.

This is where Dynamic Programming helps.

### Step 1: Define the DP array

Let:

\\[ dp[i] = \text{Minimum cost to reach step } i \\]

Our starting condition is:

\\[ dp[0] = 0 \\]

Because we start at step `0` without paying anything.

### Step 2: Find the recurrence relation

To reach step `i`, we can come from three previous steps:

1. From `i - 1`
2. From `i - 2`
3. From `i - 3`

Let's calculate the cost for each possibility.

Coming from step i - 1:

\\[ dp[i-1] + costs[i-1] + 1^2 \\]

Coming from step i - 2:

\\[ dp[i-2] + costs[i-1] + 2^2 \\]

Coming from step i - 3:

\\[ dp[i-3] + costs[i-1] + 3^2 \\]

Notice that the destination step is always `i`, so its cost is always `costs[i - 1]`.

We take the minimum of all three possibilities:

\\[ \boxed{ dp[i] = costs[i-1] + \min \begin{cases} dp[i-1]+1\\\ dp[i-2]+4\\\ dp[i-3]+9 \end{cases} } \\]

We only consider previous steps that are greater than or equal to zero.

## 3. Java Solution

```

class Solution {
    public int climbStairs(int n, int[] costs) {

        int[] dp = new int[n + 1];

        // Base condition
        dp[0] = 0;

        for (int i = 1; i <= n; i++) {

            dp[i] = Integer.MAX_VALUE;

            // Try jumping 1, 2, or 3 steps
            for (int jump = 1; jump <= 3; jump++) {

                if (i - jump >= 0) {

                    int currentCost =
                        dp[i - jump] + costs[i - 1] + jump * jump;

                    dp[i] = Math.min(dp[i], currentCost);
                }
            }
        }

        return dp[n];
    }
}

```

## 4. Dry Run – Example 1

Input:

```
n = 4
costs = [1, 2, 3, 4]
```

We create a DP array:

```
dp = [0, 0, 0, 0, 0]
```

Initially, `dp[0] = 0`.

Let's calculate each step.

Step 1: Reach step 1

Only possible from step 0.

\\[ dp[1] = 0 + 1 + 1^2 = 2 \\]

Step 2: Reach step 2

We have two possibilities:

From step 1:

\\[ dp[1] + 2 + 1^2 = 2+2+1=5 \\]

From step 0:

\\[ dp[0] + 2 + 2^2 = 0+2+4=6 \\]

Minimum:

\\[ dp[2] = \min(5,6)=5 \\]

Step 3: Reach step 3

Three possibilities:

From step 2:

\\[ 5+3+1=9 \\]

From step 1:

\\[ 2+3+4=9 \\]

From step 0:

\\[ 0+3+9=12 \\]

Minimum:

\\[ dp[3] = 9 \\]

Step 4: Reach step 4

Three possibilities:

From step 3:

\\[ 9+4+1=14 \\]

From step 2:

\\[ 5+4+4=13 \\]

From step 1:

\\[ 2+4+9=15 \\]

Minimum:

\\[ dp[4] = 13 \\]

### Final DP table

| Step | Minimum cost (dp[i]) |
| ---- | -------------------- |
| 0    | 0                    |
| 1    | 2                    |
| 2    | 5                    |
| 3    | 9                    |
| 4    | 13                   |

Output: 13

The optimal path is:

```
0 → 1 → 2 → 4

Cost = 2 + 3 + 8 = 13
```

## 5. Complexity Analysis

Let `n` be the number of steps.

Time Complexity:

\\[ O(n \times 3) = \boxed{O(n)} \\]

Why?

- We iterate from step `1` to `n`.
- For every step, we check at most 3 previous steps.
- Therefore, the total operations are proportional to `n`.

Space Complexity:

\\[ \boxed{O(n)} \\]

Why?

\- We use a DP array of size `n + 1` to store minimum costs.

## 6. Important concepts to remember

| Concept      | Explanation                                 |
| ------------ | ------------------------------------------- |
| DP State     | `dp[i]` = minimum cost to reach step `i`    |
| Base Case    | `dp[0] = 0`                                 |
| Choices      | Jump 1, 2, or 3 steps                       |
| Transition   | Minimum cost among all valid previous steps |
| Final Answer | `dp[n]`                                     |
| Time         | O(n)                                        |
| Space        | O(n)                                        |

## 7. Why does this solution work?

Remember these three points:

1. Optimal Substructure: To find the minimum cost of reaching step `i`, we only need the minimum costs of reaching the previous three steps.
2. Overlapping Subproblems: The same previous steps are used repeatedly when calculating different DP states. We store their answers in the `dp` array instead of recalculating them.
3. Minimum Choice: We calculate the cost of all valid jumps and select the minimum.

Interview tip: If an interviewer asks why you used Dynamic Programming, explain:

"We need to find the minimum cost to reach the destination. Since we can reach each step from at most three previous steps, I calculate the minimum cost for each step using the previously computed results. This avoids recalculating the same subproblems and gives us an O(n) solution."

Practice questions to test your understanding:

1. Why do we use `costs[i - 1]` instead of `costs[i]`?
2. Why do we initialize `dp[i]` with `Integer.MAX_VALUE`?
3. What would change if we were allowed to jump up to 4 steps instead of 3?

Try answering these three questions yourself. They'll help you understand the DP transition rather than just memorizing the code.